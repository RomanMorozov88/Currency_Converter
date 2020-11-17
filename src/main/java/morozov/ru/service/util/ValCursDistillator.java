package morozov.ru.service.util;

import morozov.ru.model.fromxsdcentralbank.ValCurs;
import morozov.ru.model.workingmodel.DateCurs;
import morozov.ru.model.workingmodel.ExchangeRate;
import morozov.ru.model.workingmodel.СurrencyInfo;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Т.к. "The entity class must be a top-level class" -
 * то с помощью методов этого класса перегоняем полученный от ЦБ и сгенерированный
 * ValCurs с вложенным ValCurs.Valute в два разных класса,
 * необходимых для наших нужд.
 */
@Component
public class ValCursDistillator {

    private final String DATE_FORMAT = "dd.MM.yyyy";
    private final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

    public DateCurs dateDistillation(ValCurs valCurs) throws ParseException {
        DateCurs result = new DateCurs();
        result.setName(valCurs.getName());
        result.setDate(this.dateConverter(valCurs.getDate()));
        List<ExchangeRate> newValutes = result.getValutes();
        ExchangeRate exchangeRate = null;
        for (ValCurs.Valute v : valCurs.getValute()) {
            exchangeRate = this.rateConverter(v);
            exchangeRate.setDate(result);
            newValutes.add(exchangeRate);
        }
        return result;
    }

    public List<СurrencyInfo> infoDistillation(ValCurs valCurs) {
        List<СurrencyInfo> result = new ArrayList<>();
        СurrencyInfo info = null;
        for (ValCurs.Valute v : valCurs.getValute()) {
            info = this.infoConverter(v);
            result.add(info);
        }
        return result;
    }

    private Calendar dateConverter(String stringDate) throws ParseException {
        Calendar date = Calendar.getInstance();
        date.setTime(SIMPLE_DATE_FORMAT.parse(stringDate));
        return date;
    }

    private ExchangeRate rateConverter(ValCurs.Valute valute) {
        ExchangeRate result = new ExchangeRate();
        result.setNominal(valute.getNominal());
        String toDouble = valute.getValue().replace(',', '.');
        result.setValue(Double.parseDouble(toDouble));
        result.setInfo(infoConverter(valute));
        return result;
    }

    private СurrencyInfo infoConverter(ValCurs.Valute valute) {
        СurrencyInfo result = new СurrencyInfo();
        result.setNumCode(valute.getNumCode());
        result.setCharCode(valute.getCharCode());
        result.setName(valute.getName());
        result.setId(valute.getID());
        return result;
    }

}
