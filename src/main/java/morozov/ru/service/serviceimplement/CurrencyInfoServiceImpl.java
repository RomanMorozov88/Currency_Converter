package morozov.ru.service.serviceimplement;

import morozov.ru.model.workingmodel.CurrencyInfo;
import morozov.ru.service.repository.CurrencyInfoRepository;
import morozov.ru.service.serviceinterface.CurrencyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyInfoServiceImpl implements CurrencyInfoService {

    private CurrencyInfoRepository currencyInfoRepository;

    @Autowired
    public CurrencyInfoServiceImpl(CurrencyInfoRepository currencyInfoRepository) {
        this.currencyInfoRepository = currencyInfoRepository;
    }

    @Override
    public List<CurrencyInfo> getAll() {
        return currencyInfoRepository.findAll();
    }
}
