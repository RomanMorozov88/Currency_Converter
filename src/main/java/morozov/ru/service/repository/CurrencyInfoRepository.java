package morozov.ru.service.repository;

import morozov.ru.model.workingmodel.CurrencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyInfoRepository extends JpaRepository<CurrencyInfo, String> {
}