package morozov.ru.service.repository;

import morozov.ru.model.workingmodel.pair.CurrencyPair;
import morozov.ru.model.workingmodel.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    @Query("FROM Operation op where op.pair = ?1 and (op.date between ?2 and ?3)")
    List<Operation> getOperations(CurrencyPair pair, Calendar start, Calendar end);

    Page<Operation> findAll(Pageable pageable);
}
