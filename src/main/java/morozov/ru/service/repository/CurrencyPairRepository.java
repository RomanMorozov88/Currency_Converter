package morozov.ru.service.repository;

import morozov.ru.model.workingmodel.pair.CurrencyPair;
import morozov.ru.model.workingmodel.pair.CurrencyPairCompositeID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Integer> {

    CurrencyPair findById(CurrencyPairCompositeID id);

}
