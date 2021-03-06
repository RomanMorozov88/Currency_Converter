package morozov.ru.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import morozov.ru.model.workingmodel.CurrencyInfo;
import morozov.ru.model.workingmodel.pair.CurrencyPair;
import morozov.ru.model.workingmodel.ExchangeStatistics;
import morozov.ru.model.workingmodel.Operation;
import morozov.ru.service.serviceinterface.CurrencyInfoService;
import morozov.ru.service.serviceinterface.CurrencyPairService;
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

    private CurrencyInfoService currencyInfoService;
    private CurrencyPairService currencyPairService;
    private OperationService operationService;
    private StatisticsService statisticsService;

    @Autowired
    public Query(
            CurrencyInfoService currencyInfoService,
            CurrencyPairService currencyPairService,
            OperationService operationService,
            StatisticsService statisticsService
    ) {
        this.currencyInfoService = currencyInfoService;
        this.currencyPairService = currencyPairService;
        this.operationService = operationService;
        this.statisticsService = statisticsService;
    }

    public List<CurrencyInfo> getAllCurrencyInfo() {
        return currencyInfoService.getAll();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ExchangeStatistics getStatistics(String fromId, String toId) {
        ExchangeStatistics result = null;
        CurrencyPair pair = currencyPairService.getByFromAndToIds(fromId, toId);
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
