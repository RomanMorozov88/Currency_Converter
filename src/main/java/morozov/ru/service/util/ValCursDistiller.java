package morozov.ru.service.util;

import morozov.ru.model.fromxsdcentralbank.ValCurs;
import morozov.ru.model.workingmodel.rate.ExchangeRate;
import morozov.ru.model.workingmodel.CurrencyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class ValCursDistiller {

    private SimpleDateFormat simpleDateFormat;

    @Autowired
    public ValCursDistiller(@Qualifier("date_bean") SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    /**
     * Из полученного от ЦБ файла с курсами извлекается лист CurrencyInfo
     * с уже привязанными к ним ExchangeRate
     * @param valCurs
     * @return
     * @throws ParseException
     */
    public List<CurrencyInfo> infoDistillation(ValCurs valCurs) throws ParseException {
        Calendar date = this.dateConverter(valCurs.getDate());
        List<CurrencyInfo> result = new ArrayList<>();
        CurrencyInfo info = null;
        ExchangeRate exchangeRate = null;
        for (ValCurs.Valute v : valCurs.getValute()) {
            exchangeRate = this.rateConverter(v);
            exchangeRate.getId().setDate(date);
            info = exchangeRate.getInfo();
            info.setRate(exchangeRate);
            result.add(info);
        }
        return result;
    }

    private Calendar dateConverter(String stringDate) throws ParseException {
        Calendar date = Calendar.getInstance();
        date.setTime(simpleDateFormat.parse(stringDate));
        return date;
    }

    private ExchangeRate rateConverter(ValCurs.Valute valute) {
        ExchangeRate result = new ExchangeRate();
        result.setNominal(valute.getNominal());
        String toDouble = valute.getValue().replace(',', '.');
        result.setValue(Double.parseDouble(toDouble));
        CurrencyInfo info = this.infoConverter(valute);
        result.setInfo(info);
        result.getId().setInfoId(info.getId());
        return result;
    }

    private CurrencyInfo infoConverter(ValCurs.Valute valute) {
        CurrencyInfo result = new CurrencyInfo();
        result.setNumCode(valute.getNumCode());
        result.setCharCode(valute.getCharCode());
        result.setName(valute.getName());
        result.setId(valute.getID());
        return result;
    }

}
