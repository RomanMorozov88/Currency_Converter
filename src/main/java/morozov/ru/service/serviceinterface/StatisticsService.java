package morozov.ru.service.serviceinterface;

import morozov.ru.model.workingmodel.pair.CurrencyPair;
import morozov.ru.model.workingmodel.ExchangeStatistics;

public interface StatisticsService {

    ExchangeStatistics getStatistics(CurrencyPair pair);
}
