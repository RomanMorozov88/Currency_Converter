package morozov.ru.service.serviceinterface;

import java.util.Calendar;

public interface ConnectWithBank {

    boolean getValCurses();

    /**
     * Получение курса на определённую дату.
     * Нужен для получения курсов в случае, когда на сайье ЦБ они
     * обновились до того, как закончился текущий день.
     *
     * @param date
     * @return
     */
    boolean getValCurses(Calendar date);
}