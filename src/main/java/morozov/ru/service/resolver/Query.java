package morozov.ru.service.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import morozov.ru.model.workingmodel.CurrencyInfo;
import morozov.ru.service.repository.CurrencyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    private CurrencyInfoRepository currencyInfoRepository;

    @Autowired
    public Query(CurrencyInfoRepository currencyInfoRepository) {
        this.currencyInfoRepository = currencyInfoRepository;
    }

    public List<CurrencyInfo> getAllCurrencyInfo() {
        return currencyInfoRepository.findAll();
    }
}
