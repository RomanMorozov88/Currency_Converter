package morozov.ru.service.serviceinterface;

import java.util.Calendar;

public interface ExchangeService {

    double getExchange(Calendar date, String fromId, String toId, double amount);

}