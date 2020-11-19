package morozov.ru.service.serviceimplement;

import morozov.ru.model.workingmodel.CurrencyPair;
import morozov.ru.model.workingmodel.ExchangeRate;
import morozov.ru.model.workingmodel.ExchangeStatistics;
import morozov.ru.model.workingmodel.Operation;
import morozov.ru.service.repository.ExchangeRateRepository;
import morozov.ru.service.repository.OperationRepository;
import morozov.ru.service.serviceinterface.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private OperationRepository operationRepository;
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public StatisticsServiceImpl(
            OperationRepository operationRepository,
            ExchangeRateRepository exchangeRateRepository
    ) {
        this.operationRepository = operationRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public ExchangeStatistics getStatistics(CurrencyPair pair) {
        ExchangeStatistics result = new ExchangeStatistics();
        result.setPair(pair);
        Calendar date = Calendar.getInstance();
        String fromId = pair.getFromCurrency().getId();
        List<Operation> operations = this.getNeededOperations(pair, date);
        double total = 0;
        double average = 0;
        ExchangeRate rate = null;
        for (Operation o : operations) {
            rate = exchangeRateRepository.findByDateAndInfo_Id(o.getDate(), fromId);
            total += o.getAmount();
            average += rate.getValue();
        }
        result.setTotalSum(total);
        result.setAverageRate(average / operations.size());
        return result;
    }

    private List<Operation> getNeededOperations(CurrencyPair pair, Calendar end) {
        Calendar start = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_YEAR, -1);
        return operationRepository.getOperations(pair, start, end);
    }

}
