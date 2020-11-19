package morozov.ru.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import morozov.ru.model.workingmodel.CurrencyInfo;
import morozov.ru.service.repository.CurrencyInfoRepository;
import morozov.ru.service.serviceinterface.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Query implements GraphQLQueryResolver {

    private CurrencyInfoRepository currencyInfoRepository;
    private OperationService operationService;

    @Autowired
    public Query(
            CurrencyInfoRepository currencyInfoRepository,
            OperationService operationService
    ) {
        this.currencyInfoRepository = currencyInfoRepository;
        this.operationService = operationService;
    }

    public List<CurrencyInfo> getAllCurrencyInfo() {
        return currencyInfoRepository.findAll();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Double getCurrencyConversion(String fromId, String toId, double amount) {
        return operationService.conversion(fromId, toId, amount);
    }

}
