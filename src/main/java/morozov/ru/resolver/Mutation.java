package morozov.ru.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import morozov.ru.service.serviceinterface.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Mutation implements GraphQLMutationResolver {

    private OperationService operationService;

    @Autowired
    public Mutation(OperationService operationService) {
        this.operationService = operationService;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Double getCurrencyConversion(String fromId, String toId, double amount) {
        return operationService.conversion(fromId, toId, amount);
    }

}