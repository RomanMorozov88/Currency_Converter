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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import static java.lang.Double.parseDouble;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private OperationRepository operationRepository;
    private ExchangeRateRepository exchangeRateRepository;
    private DecimalFormat decimalFormat;

    @Autowired
    public StatisticsServiceImpl(
            OperationRepository operationRepository,
            ExchangeRateRepository exchangeRateRepository,
            DecimalFormat decimalFormat
    ) {
        this.operationRepository = operationRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.decimalFormat = decimalFormat;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public ExchangeStatistics getStatistics(CurrencyPair pair) {
        ExchangeStatistics result = new ExchangeStatistics();
        result.setPair(pair);
        String fromId = pair.getFromCurrency().getId();
        List<Operation> operations = this.getNeededOperations(pair);
        double totalFrom = 0;
        double totalTo = 0;
        double average = 0;
        ExchangeRate rate = null;
        for (Operation o : operations) {
            rate = exchangeRateRepository.findById(new ExchangeRateCompositeID(o.getDate(), fromId));
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

    private List<Operation> getNeededOperations(CurrencyPair pair) {
        //Задаётся недельный промежуток от текущей даты.
        Calendar end = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_YEAR, -1);
        return operationRepository.getOperations(pair, start, end);
    }

}
