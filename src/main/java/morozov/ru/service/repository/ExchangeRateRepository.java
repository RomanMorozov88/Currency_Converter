package morozov.ru.service.repository;

import morozov.ru.model.workingmodel.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {

    ExchangeRate findByDateAndInfo_Id(Calendar date, String infoId);
}
