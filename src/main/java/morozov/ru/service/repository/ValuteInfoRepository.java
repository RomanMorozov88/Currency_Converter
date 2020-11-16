package morozov.ru.service.repository;

import morozov.ru.model.workingmodel.ValuteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValuteInfoRepository extends JpaRepository<ValuteInfo, String> {
}
