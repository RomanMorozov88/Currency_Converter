package morozov.ru.service.repository;

import morozov.ru.model.workingmodel.rate.ExchangeRate;
import morozov.ru.model.workingmodel.rate.ExchangeRateCompositeID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, ExchangeRateCompositeID> {

    ExchangeRate getById(ExchangeRateCompositeID id);

    @Query("FROM ExchangeRate r where r.id.infoId = ?1 and (r.id.date between ?2 and ?3)")
    List<ExchangeRate> getRatesForPeriod(String infoId, Calendar start, Calendar end);

    /**
     * Получая актуальные курсы от ЦБ нет гарантии, что эти курсы будут на текущую дату-
     * они могут или опережать или даже отставать от неё.
     * Поэтому доставть курс надо по ближайшей имеющейся в БД дате.
     *
     * @param date
     * @return
     */
    @Query(
            "from ExchangeRate as r1 where "
                    + "r1.id.infoId = ?1 "
                    + "and r1.id.date = (select max(r2.id.date) from ExchangeRate as r2 where r2.id.date <= ?2)"
    )
    ExchangeRate getNearestRate(String infoId, Calendar date);
}
