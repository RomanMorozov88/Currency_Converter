package morozov.ru.service.serviceimplement;

import morozov.ru.model.workingmodel.CurrencyPair;
import morozov.ru.service.repository.CurrencyPairRepository;
import morozov.ru.service.serviceinterface.CurrencyPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyPairServiceImpl implements CurrencyPairService {

    private CurrencyPairRepository currencyPairRepository;

    @Autowired
    public CurrencyPairServiceImpl(CurrencyPairRepository currencyPairRepository) {
        this.currencyPairRepository = currencyPairRepository;
    }

    @Override
    public CurrencyPair getByFromAndToIds(String fromId, String toId) {
        return currencyPairRepository.findByFromCurrency_IdAndToCurrency_Id(fromId, toId);
    }
}
