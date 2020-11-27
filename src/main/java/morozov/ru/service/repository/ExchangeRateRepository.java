package morozov.ru.service.repository;

import morozov.ru.model.workingmodel.rate.ExchangeRate;
import morozov.ru.model.workingmodel.rate.ExchangeRateCompositeID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {

    ExchangeRate findById(ExchangeRateCompositeID id);
}
