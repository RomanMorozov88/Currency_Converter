package morozov.ru.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import morozov.ru.model.workingmodel.CurrencyInfo;
import morozov.ru.model.workingmodel.CurrencyPair;
import morozov.ru.model.workingmodel.ExchangeStatistics;
import morozov.ru.model.workingmodel.Operation;
import morozov.ru.service.repository.CurrencyInfoRepository;
import morozov.ru.service.repository.CurrencyPairRepository;
import morozov.ru.service.serviceinterface.OperationService;
import morozov.ru.service.serviceinterface.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Query implements GraphQLQueryResolver {

    private CurrencyInfoRepository currencyInfoRepository;
    private CurrencyPairRepository currencyPairRepository;
    private OperationService operationService;
    private StatisticsService statisticsService;

    @Autowired
    public Query(
            CurrencyInfoRepository currencyInfoRepository,
            CurrencyPairRepository currencyPairRepository,
            OperationService operationService,
            StatisticsService statisticsService
    ) {
        this.currencyInfoRepository = currencyInfoRepository;
        this.currencyPairRepository = currencyPairRepository;
        this.operationService = operationService;
        this.statisticsService = statisticsService;
    }

    public List<CurrencyInfo> getAllCurrencyInfo() {
        return currencyInfoRepository.findAll();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Double getCurrencyConversion(String fromId, String toId, double amount) {
        return operationService.conversion(fromId, toId, amount);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ExchangeStatistics getStatistics(String fromId, String toId) {
        ExchangeStatistics result = null;
        CurrencyPair pair = currencyPairRepository.findByFromCurrency_IdAndToCurrency_Id(fromId, toId);
        if (pair != null) {
            result = statisticsService.getStatistics(pair);
        }
        return result;
    }

    public List<Operation> getOperations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return operationService.getAllOnPage(pageable);
    }
}
