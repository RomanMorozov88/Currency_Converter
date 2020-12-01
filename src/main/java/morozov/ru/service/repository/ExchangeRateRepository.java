package morozov.ru.service.repository;

import morozov.ru.model.workingmodel.rate.ExchangeRate;
import morozov.ru.model.workingmodel.rate.ExchangeRateCompositeID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {

    ExchangeRate findById(ExchangeRateCompositeID id);

    @Query("FROM ExchangeRate r where r.id.infoId = ?1 and (r.id.date between ?2 and ?3)")
    List<ExchangeRate> getRatesForPeriod(String infoId, Calendar start, Calendar end);
}
