package morozov.ru.service.serviceinterface;

import morozov.ru.model.workingmodel.pair.CurrencyPair;

public interface CurrencyPairService {

    CurrencyPair getByFromAndToIds(String fromId, String toId);
}
