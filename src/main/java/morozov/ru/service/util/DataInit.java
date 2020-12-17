package morozov.ru.service.util;

import morozov.ru.service.serviceinterface.ConnectWithBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * Получение\загрузка данных при старте приложения
 */
@Component
public class DataInit {

    private ConnectWithBank connectWithBank;

    @Autowired
    public DataInit(
            ConnectWithBank connectWithBank
    ) {
        this.connectWithBank = connectWithBank;
    }

    @PostConstruct
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void firstDataInit() {
        connectWithBank.getValCurses();
    }
}
