package morozov.ru.service.serviceimplement;

import morozov.ru.model.workingmodel.pair.CurrencyPair;
import morozov.ru.model.workingmodel.rate.ExchangeRate;
import morozov.ru.model.workingmodel.ExchangeStatistics;
import morozov.ru.model.workingmodel.Operation;
import morozov.ru.model.workingmodel.rate.ExchangeRateCompositeID;
import morozov.ru.service.repository.ExchangeRateRepository;
import morozov.ru.service.repository.OperationRepository;
import morozov.ru.service.serviceinterface.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public ExchangeStatistics getStatistics(CurrencyPair pair) {
        ExchangeStatistics result = new ExchangeStatistics();
        result.setPair(pair);
        String fromId = pair.getFromCurrency().getId();

        Calendar end = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_YEAR, -1);

        List<Operation> operations = operationRepository.getOperations(pair, start, end);
        Map<String, ExchangeRate> ratesMap = this.getNeededRatesInMap(fromId, start, end);

        double totalFrom = 0;
        double totalTo = 0;
        double average = 0;
        ExchangeRate rate = null;
        String dateKey = null;
        for (Operation o : operations) {
            dateKey = simpleDateFormat.format(o.getDate().getTime());
            rate = ratesMap.get(dateKey);
            totalFrom += o.getFromAmount();
            totalTo += o.getToAmount();
            average += rate.getValue();
        }
        result.setTotalSumFrom(parseDouble(decimalFormat.format(totalFrom)));
        result.setTotalSumTo(parseDouble(decimalFormat.format(totalTo)));
        result.setAverageRate(
                parseDouble(
                        decimalFormat.format(average / operations.size())
                )
        );
        return result;
    }

    private Map<String, ExchangeRate> getNeededRatesInMap(String fromId, Calendar start, Calendar end) {
        Map<String, ExchangeRate> result = null;
        List<ExchangeRate> rates = exchangeRateRepository.getRatesForPeriod(fromId, start, end);
        if (rates != null) {
            result = rates.stream()
                    .collect(Collectors.toMap(
                            rate -> simpleDateFormat.format(rate.getId().getDate().getTime()),
                            rate -> rate
                    ));
        }
        return result;
    }

}
