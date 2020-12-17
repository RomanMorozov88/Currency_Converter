package morozov.ru.service.serviceimplement;

import morozov.ru.model.workingmodel.Operation;
import morozov.ru.model.workingmodel.ResponseResult;
import morozov.ru.model.workingmodel.pair.CurrencyPair;
import morozov.ru.model.workingmodel.rate.ExchangeRate;
import morozov.ru.service.repository.CurrencyInfoRepository;
import morozov.ru.service.repository.CurrencyPairRepository;
import morozov.ru.service.repository.ExchangeRateRepository;
import morozov.ru.service.repository.OperationRepository;
import morozov.ru.service.serviceinterface.OperationService;
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
    private DecimalFormat decimalFormat;

    @Autowired
    public OperationServiceImpl(
            CurrencyInfoRepository currencyInfoRepository,
            CurrencyPairRepository currencyPairRepository,
            ExchangeRateRepository exchangeRateRepository,
            OperationRepository operationRepository,
            DecimalFormat decimalFormat
    ) {
        this.currencyInfoRepository = currencyInfoRepository;
        this.currencyPairRepository = currencyPairRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.operationRepository = operationRepository;
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
            result = this.subConversion(fromId, toId, amount);
        }
        return result;
    }

    /**
     * Основной метод для конвертации.
     * Сначала получает данные о курсах из БД
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
    private ResponseResult subConversion(String fromId, String toId, double amount) {
        ResponseResult result = new ResponseResult();
        Calendar date = Calendar.getInstance();

        //Обновление курсов обеспечивается morozov.ru.service.util.ScheduledRefreshRates
        ExchangeRate fromRate = exchangeRateRepository.getNearestRate(fromId, date);
        ExchangeRate toRate = exchangeRateRepository.getNearestRate(toId, date);
        //Если курсов нет вообще- сразу возвращает null
        if (fromRate == null || toRate == null) {
            return null;
        }

        CurrencyPair pair = new CurrencyPair(fromRate.getInfo(), toRate.getInfo());
        Operation newOperation = new Operation();
        newOperation.setDate(date);
        newOperation.setPair(pair);
        newOperation.setFromAmount(amount);

        Double resultAmount = this.calculate(fromRate, toRate, amount);
        result.setResult(resultAmount);
        result.setRateDate(fromRate.getId().

                getDate());

        newOperation.setToAmount(resultAmount);
        pair.setOperation(newOperation);
        currencyPairRepository.save(pair);
        return result;
    }

    /**
     * Возвращает список операций
     *
     * @param pageable
     * @return
     */
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
