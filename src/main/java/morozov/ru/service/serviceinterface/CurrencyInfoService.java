package morozov.ru.service.serviceinterface;

import morozov.ru.model.workingmodel.CurrencyInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CurrencyInfoService {

    List<CurrencyInfo> getAll();
}
