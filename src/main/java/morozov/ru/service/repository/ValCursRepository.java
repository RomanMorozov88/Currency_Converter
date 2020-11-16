package morozov.ru.service.repository;

import morozov.ru.model.workingmodel.ValCursModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;

@Repository
public interface ValCursRepository extends JpaRepository<ValCursModel, Calendar> {
}
