package morozov.ru.service.serviceimplement;

import morozov.ru.model.workingmodel.Operation;
import morozov.ru.service.repository.OperationRepository;
import morozov.ru.service.util.DataInit;
import morozov.ru.model.workingmodel.CurrencyPair;
import morozov.ru.model.workingmodel.ExchangeRate;
import org.springframework.data.domain.Pageable;
import morozov.ru.service.repository.CurrencyPairRepository;
import morozov.ru.service.repository.ExchangeRateRepository;
import morozov.ru.service.serviceinterface.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {

    private CurrencyPairRepository currencyPairRepository;
    private ExchangeRateRepository exchangeRateRepository;
    private OperationRepository operationRepository;
    private DataInit dataInit;

    @Autowired
    public OperationServiceImpl(
            CurrencyPairRepository currencyPairRepository,
            ExchangeRateRepository exchangeRateRepository,
            OperationRepository operationRepository,
            DataInit dataInit
    ) {
        this.currencyPairRepository = currencyPairRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.operationRepository = operationRepository;
        this.dataInit = dataInit;
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
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Double conversion(String fromId, String toId, double amount) {
        Calendar date = Calendar.getInstance();
        ExchangeRate fromRate = exchangeRateRepository.findByDateAndInfo_Id(date, fromId);
        ExchangeRate toRate = exchangeRateRepository.findByDateAndInfo_Id(date, toId);
        if (fromRate == null || toRate == null) {
            dataInit.getValCurses(
                    date.get(Calendar.DAY_OF_MONTH),
                    //Не забываем, что
                    //The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0.
                    date.get(Calendar.MONTH) + 1,
                    date.get(Calendar.YEAR)
            );
            fromRate = exchangeRateRepository.findByDateAndInfo_Id(date, fromId);
            toRate = exchangeRateRepository.findByDateAndInfo_Id(date, toId);
        }
        CurrencyPair pair = currencyPairRepository
                .findByFromCurrencyAndToCurrency(fromRate.getInfo(), toRate.getInfo());
        if (pair == null) {
            pair = new CurrencyPair(fromRate.getInfo(), toRate.getInfo());
        }
        Operation newOperation = new Operation();
        newOperation.setDate(date);
        newOperation.setPair(pair);
        newOperation.setAmount(amount);
        pair.setOperation(newOperation);
        currencyPairRepository.save(pair);
        return calculate(fromRate, toRate, amount);
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
    private double calculate(ExchangeRate fromRate, ExchangeRate toRate, double amount) {
        double fromValue = this.divisionByNominal(fromRate);
        double toValue = this.divisionByNominal(toRate);
        return (fromValue / toValue) * amount;
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
}
