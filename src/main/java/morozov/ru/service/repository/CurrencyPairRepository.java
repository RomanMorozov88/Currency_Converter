package morozov.ru.service.repository;

import morozov.ru.model.workingmodel.CurrencyInfo;
import morozov.ru.model.workingmodel.CurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Integer> {

    CurrencyPair findByFromCurrency_IdAndToCurrency_Id(String fromId, String toId);

    CurrencyPair findByFromCurrencyAndToCurrency(CurrencyInfo fromCurrency, CurrencyInfo toCurrency);

}
