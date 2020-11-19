package morozov.ru.service.serviceinterface;

import morozov.ru.model.workingmodel.Operation;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OperationService {

    public Double conversion (String fromId, String toId, double amount);

    public List<Operation> getAllOnPage(Pageable pageable);
}
