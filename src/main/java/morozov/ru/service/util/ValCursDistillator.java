package morozov.ru.service.util;

import morozov.ru.model.fromxsdcentralbank.ValCurs;
import morozov.ru.model.workingmodel.DateCurs;
import morozov.ru.model.workingmodel.ExchangeRate;
import morozov.ru.model.workingmodel.ValuteInfo;

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
public class ValCursDistillator {

    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

    public static DateCurs dateDistillation(ValCurs valCurs) throws ParseException {
        DateCurs result = new DateCurs();
        result.setName(valCurs.getName());
        result.setDate(dateConverter(valCurs.getDate()));
        List<ExchangeRate> newValutes = result.getValutes();
        ExchangeRate exchangeRate = null;
        for (ValCurs.Valute v : valCurs.getValute()) {
            exchangeRate = rateConverter(v);
            exchangeRate.setDate(result);
            newValutes.add(exchangeRate);
        }
        return result;
    }

    public static List<ValuteInfo> infoDistillation(ValCurs valCurs) {
        List<ValuteInfo> result = new ArrayList<>();
        ValuteInfo info = null;
        for (ValCurs.Valute v : valCurs.getValute()) {
            info = infoConverter(v);
            result.add(info);
        }
        return result;
    }

    private static Calendar dateConverter(String stringDate) throws ParseException {
        Calendar date = Calendar.getInstance();
        date.setTime(SIMPLE_DATE_FORMAT.parse(stringDate));
        return date;
    }

    private static ExchangeRate rateConverter(ValCurs.Valute valute) {
        ExchangeRate result = new ExchangeRate();
        result.setNominal(valute.getNominal());
        String toDouble = valute.getValue().replace(',', '.');
        result.setValue(Double.parseDouble(toDouble));
        result.setInfo(infoConverter(valute));
        return result;
    }

    private static ValuteInfo infoConverter(ValCurs.Valute valute) {
        ValuteInfo result = new ValuteInfo();
        result.setNumCode(valute.getNumCode());
        result.setCharCode(valute.getCharCode());
        result.setName(valute.getName());
        result.setId(valute.getID());
        return result;
    }

}
