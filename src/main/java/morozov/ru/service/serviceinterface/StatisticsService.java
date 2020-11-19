package morozov.ru.service.serviceinterface;

import morozov.ru.model.workingmodel.CurrencyPair;
import morozov.ru.model.workingmodel.ExchangeStatistics;

public interface StatisticsService {

    ExchangeStatistics getStatistics(CurrencyPair pair);
}
