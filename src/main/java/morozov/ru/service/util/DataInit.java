package morozov.ru.service.util;

import morozov.ru.service.serviceinterface.ConnectWithBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

/**
 * Получение\загрузка данных при старте приложения
 * а так же в для обновления данных по курсам.
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
    @Transactional
    public void firstDataInit() {
        connectWithBank.getValCurses();
    }
}
