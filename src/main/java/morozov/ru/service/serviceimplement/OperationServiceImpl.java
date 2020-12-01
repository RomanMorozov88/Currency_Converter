package morozov.ru.service.serviceimplement;

import morozov.ru.model.workingmodel.CurrencyInfo;
import morozov.ru.model.workingmodel.ResponseResult;
import morozov.ru.model.workingmodel.pair.CurrencyPair;
import morozov.ru.model.workingmodel.pair.CurrencyPairCompositeID;
import morozov.ru.model.workingmodel.rate.ExchangeRate;
import morozov.ru.model.workingmodel.Operation;
import morozov.ru.model.workingmodel.rate.ExchangeRateCompositeID;
import morozov.ru.service.repository.CurrencyInfoRepository;
import morozov.ru.service.repository.CurrencyPairRepository;
import morozov.ru.service.repository.ExchangeRateRepository;
import morozov.ru.service.repository.OperationRepository;
import morozov.ru.service.serviceinterface.OperationService;
import morozov.ru.service.util.DataInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import static java.lang.Double.parseDouble;

@Service
public class OperationServiceImpl implements OperationService {

    private CurrencyInfoRepository currencyInfoRepository;
    private CurrencyPairRepository currencyPairRepository;
    private ExchangeRateRepository exchangeRateRepository;
    private OperationRepository operationRepository;
    private DataInit dataInit;
    private DecimalFormat decimalFormat;

    @Autowired
    public OperationServiceImpl(
            CurrencyInfoRepository currencyInfoRepository,
            CurrencyPairRepository currencyPairRepository,
            ExchangeRateRepository exchangeRateRepository,
            OperationRepository operationRepository,
            DataInit dataInit,
            DecimalFormat decimalFormat
    ) {
        this.currencyInfoRepository = currencyInfoRepository;
        this.currencyPairRepository = currencyPairRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.operationRepository = operationRepository;
        this.dataInit = dataInit;
        this.decimalFormat = decimalFormat;
    }

    /**
     * Т.к. на входе могут быть вообще несуществующие id валют-
     * сначала проверяется их корректность а затем уже происходит
     * дальнейшая работа.
     *
     * @param fromId
     * @param toId
     * @param amount
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseResult conversion(String fromId, String toId, double amount) {
        ResponseResult result = null;
        if (this.checkCurrencyInfo(fromId, toId) && amount > 0) {
            result = new ResponseResult();
            result.setResult(this.subConversion(fromId, toId, amount));
        }
        return result;
    }

    /**
     * Основной метод для конвертации.
     * Сначала получает данные о курсах из БД-
     * если их нет, то поручает dataInit их получить и записать- затем повторяет запрос на курсы.
     * Формируется пара- если в БД ещё нет похожей пары- создаётся новая.
     * Создаётся объект Operation в который помещается пара, дата и сумма для конвертации.
     * и добавляется список операций пары.
     * Пара сохраняется в БД.
     * результатом возвращается значение метода calculate().
     *
     * @param fromId
     * @param toId
     * @param amount
     * @return
     */
    private Double subConversion(String fromId, String toId, double amount) {
        Double result = null;
        Calendar date = Calendar.getInstance();
        ExchangeRate fromRate = exchangeRateRepository.findById(new ExchangeRateCompositeID(date, fromId));
        ExchangeRate toRate = exchangeRateRepository.findById(new ExchangeRateCompositeID(date, toId));
        if (fromRate == null || toRate == null) {
            //в случае, если курсов нет- информация обновляется.
            //Так же из текущего дня может быть уже доступен курс
            //на следующий день- потому идёт обращение к ЦБ и для определённой даты.
            if (dataInit.getValCurses(date) || dataInit.getValCurses()) {
                fromRate = exchangeRateRepository.findById(new ExchangeRateCompositeID(date, fromId));
                toRate = exchangeRateRepository.findById(new ExchangeRateCompositeID(date, toId));
            } else {
                //В случае неудачи обоих попыток загрузки курсов.
                return null;
            }
        }
        CurrencyPair pair = new CurrencyPair(fromRate.getInfo(), toRate.getInfo());
        Operation newOperation = new Operation();
        newOperation.setDate(date);
        newOperation.setPair(pair);
        newOperation.setFromAmount(amount);

        result = this.calculate(fromRate, toRate, amount);

        newOperation.setToAmount(result);
        pair.setOperation(newOperation);
        currencyPairRepository.save(pair);
        return result;
    }

    @Override
    public List<Operation> getAllOnPage(Pageable pageable) {
        return operationRepository.findAll(pageable).getContent();
    }

    /**
     * Расчёт значений.
     *
     * @param fromRate
     * @param toRate
     * @param amount
     * @return
     */
    private Double calculate(ExchangeRate fromRate, ExchangeRate toRate, double amount) {
        double fromValue = this.divisionByNominal(fromRate);
        double toValue = this.divisionByNominal(toRate);
        double result = (fromValue / toValue) * amount;
        return parseDouble(decimalFormat.format(result));
    }

    /**
     * Проверка на номинал. Если он отличается от единицы-
     * приводится к значению за единицу.
     *
     * @param exchangeRate
     * @return
     */
    private double divisionByNominal(ExchangeRate exchangeRate) {
        return exchangeRate.getValue() / exchangeRate.getNominal();
    }

    /**
     * Получение\создание пары.
     *
     * @param fromInfo
     * @param toInfo
     * @return
     */
    private CurrencyPair getPair(CurrencyInfo fromInfo, CurrencyInfo toInfo) {
        CurrencyPair result = currencyPairRepository
                .findById(new CurrencyPairCompositeID(fromInfo.getId(), toInfo.getId()));
        if (result == null) {
            result = new CurrencyPair(fromInfo, toInfo);
        }
        return result;
    }

    /**
     * На случай, если в запросе пришли несуществующие id
     *
     * @param fromId
     * @param toId
     * @return
     */
    private boolean checkCurrencyInfo(String fromId, String toId) {
        return currencyInfoRepository.getIfExist(fromId, toId);
    }
}
