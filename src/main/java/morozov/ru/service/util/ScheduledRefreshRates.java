package morozov.ru.service.util;

import morozov.ru.service.serviceinterface.ConnectWithBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Обновление курсов по расписанию.
 */
@Service
public class ScheduledRefreshRates {

    private ConnectWithBank connectWithBank;

    @Autowired
    public ScheduledRefreshRates(ConnectWithBank connectWithBank) {
        this.connectWithBank = connectWithBank;
    }

    /**
     * https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Scheduled(cron = "0 0 23 * * ?") // каждый день в 23:00
    public void refreshRates() {
        connectWithBank.getValCurses();
    }
}
