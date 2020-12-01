package morozov.ru.service.serviceinterface;

import morozov.ru.model.workingmodel.Operation;
import morozov.ru.model.workingmodel.ResponseResult;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OperationService {

    public ResponseResult conversion (String fromId, String toId, double amount);

    public List<Operation> getAllOnPage(Pageable pageable);
}
