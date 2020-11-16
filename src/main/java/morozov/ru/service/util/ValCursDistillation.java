package morozov.ru.service.util;

import morozov.ru.model.fromxsdcentralbank.ValCurs;
import morozov.ru.model.workingmodel.ValCursModel;
import morozov.ru.model.workingmodel.ValuteModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Т.к. "The entity class must be a top-level class" -
 * то с помощью методов этого класса перегоняем полученный от ЦБ и сгенерированный
 * ValCurs с вложенным ValCurs.Valute в два разных класса,
 * необходимых для наших нужд.
 */
public class ValCursDistillation {

    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

    public static ValCursModel distillation(ValCurs valCurs) throws ParseException {
        ValCursModel result = new ValCursModel();
        result.setName(valCurs.getName());
        result.setDate(dateConverter(valCurs.getDate()));
        List<ValuteModel> newValutes = result.getValutes();
        ValuteModel valuteModel = null;
        for (ValCurs.Valute v : valCurs.getValute()) {
            valuteModel = valuteConverter(v);
            valuteModel.setDate(result);
            newValutes.add(valuteModel);
        }
        return result;
    }

    private static Calendar dateConverter(String stringDate) throws ParseException {
        Calendar date = Calendar.getInstance();
        date.setTime(SIMPLE_DATE_FORMAT.parse(stringDate));
        return date;
    }

    private static ValuteModel valuteConverter(ValCurs.Valute valute) {
        ValuteModel result = new ValuteModel();
        result.setNumCode(valute.getNumCode());
        result.setCharCode(valute.getCharCode());
        result.setNominal(valute.getNominal());
        result.setName(valute.getName());
        result.setValue(valute.getValue());
        result.setId(valute.getID());
        return result;
    }

}
