package morozov.ru.service.serviceimplement;

import morozov.ru.model.workingmodel.ExchangeStatistics;
import morozov.ru.model.workingmodel.Operation;
import morozov.ru.model.workingmodel.pair.CurrencyPair;
import morozov.ru.model.workingmodel.rate.ExchangeRate;
import morozov.ru.service.repository.ExchangeRateRepository;
import morozov.ru.service.repository.OperationRepository;
import morozov.ru.service.serviceinterface.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Double.parseDouble;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private OperationRepository operationRepository;
    private ExchangeRateRepository exchangeRateRepository;
    private DecimalFormat decimalFormat;
    private SimpleDateFormat simpleDateFormat;

    @Autowired
    public StatisticsServiceImpl(
            OperationRepository operationRepository,
            ExchangeRateRepository exchangeRateRepository,
            DecimalFormat decimalFormat,
            @Qualifier("date_bean") SimpleDateFormat simpleDateFormat
    ) {
        this.operationRepository = operationRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.decimalFormat = decimalFormat;
        this.simpleDateFormat = simpleDateFormat;
    }

    /**
     * Метод для сбора статистики за неделю.
     * Получает для работы Map<String, List<Operation>> и Map<String, ExchangeRate>
     * где операции соотносятся с курсами валют по датам-
     * Необходимо для того, что бы для каждой операции не приходилось идти в БД.
     * (стоит помнить- дата проведения операции
     * может отличаться от даты валютного курса- именно для избежания проблем с этим нюансом
     * и прописан такой образ действий)
     *
     * @param pair
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public ExchangeStatistics getStatistics(CurrencyPair pair) {
        ExchangeStatistics result = new ExchangeStatistics();
        result.setPair(pair);
        String fromId = pair.getFromCurrency().getId();

        Calendar end = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_YEAR, -1);

        Map<String, List<Operation>> operations = this.getGroupingOperationsInMap(pair, start, end);
        Map<String, ExchangeRate> rates = this.getRatesOperationsInMap(operations.keySet(), fromId);

        double totalFrom = 0;
        double totalTo = 0;
        double average = 0;
        int operationsCount = 0;
        List<Operation> operationsList = null;
        ExchangeRate rate = null;
        for (String stringDate : operations.keySet()) {
            operationsList = operations.get(stringDate);
            rate = rates.get(stringDate);
            for (Operation o : operationsList) {
                totalFrom += o.getFromAmount();
                totalTo += o.getToAmount();
                average += rate.getValue();
                ++operationsCount;
            }
        }
        result.setTotalSumFrom(parseDouble(decimalFormat.format(totalFrom)));
        result.setTotalSumTo(parseDouble(decimalFormat.format(totalTo)));
        result.setAverageRate(
                parseDouble(
                        decimalFormat.format(average / operationsCount)
                )
        );
        return result;
    }

    /**
     * На основе данных из карты(keySet), полученной из метода getGroupingOperationsInMap(...)
     * формирует карту с нужными ExchangeRate.
     *
     * @param stringKeys
     * @param fromId
     * @return
     */
    private Map<String, ExchangeRate> getRatesOperationsInMap(
            Set<String> stringKeys,
            String fromId
    ) {
        Calendar bufferTime = Calendar.getInstance();
        Map<String, ExchangeRate> result = null;
        result = stringKeys.stream()
                .collect(Collectors.toMap(
                        stringDate -> stringDate,
                        stringDate -> {
                            try {
                                bufferTime.setTime(simpleDateFormat.parse(stringDate));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return exchangeRateRepository.getNearestRate(fromId, bufferTime);
                        }
                ));
        return result;
    }

    /**
     * Получает список операций за некоторый период и преобразует его в
     * карту со строковыи представлекнием даты с точногстью до дня
     * в качестве ключа.
     * Нужно для устранения необохдимости идти в БД за курсом для каждой операции.
     *
     * @param pair
     * @param start
     * @param end
     * @return
     */
    private Map<String, List<Operation>> getGroupingOperationsInMap(
            CurrencyPair pair,
            Calendar start,
            Calendar end
    ) {
        List<Operation> operations = operationRepository.getOperations(pair, start, end);
        Map<String, List<Operation>> result = null;
        if (operations != null && operations.size() > 0) {
            result = operations.stream()
                    .collect(
                            Collectors.groupingBy(
                                    operation -> simpleDateFormat.format(operation.getDate().getTime())
                            )
                    );
        }
        return result;
    }

}
