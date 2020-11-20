package morozov.ru.service.repository;

import morozov.ru.model.workingmodel.CurrencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyInfoRepository extends JpaRepository<CurrencyInfo, String> {

    @Query("select case when (select ci from CurrencyInfo ci where ci.id = ?1) is not null "
            + "and (select ci from CurrencyInfo ci where ci.id = ?2) is not null "
            + "then true else false end from CurrencyInfo")
    Boolean getIfExist(String fromId, String toId);
}