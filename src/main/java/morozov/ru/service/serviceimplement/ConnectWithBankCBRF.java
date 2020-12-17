package morozov.ru.service.serviceimplement;

import morozov.ru.model.fromxsdcentralbank.ValCurs;
import morozov.ru.model.workingmodel.CurrencyInfo;
import morozov.ru.service.serviceinterface.ConnectWithBank;
import morozov.ru.service.serviceinterface.CurrencyInfoService;
import morozov.ru.service.util.ValCursDistiller;
import morozov.ru.service.util.ValCursGatherer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

/**
 * Работа с сайтом ЦБ РФ
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ConnectWithBankCBRF implements ConnectWithBank {

    private CurrencyInfoService currencyInfoService;
    private ValCursGatherer valCursGatherer;
    private ValCursDistiller valCursDistiller;

    @Autowired
    public ConnectWithBankCBRF(
            CurrencyInfoService currencyInfoService,
            ValCursGatherer valCursGatherer,
            ValCursDistiller valCursDistiller
    ) {
        this.currencyInfoService = currencyInfoService;
        this.valCursGatherer = valCursGatherer;
        this.valCursDistiller = valCursDistiller;
    }

    @Override
    public boolean getValCurses() {
        boolean result = false;
        ValCurs valCurs = valCursGatherer.getValCursFromCB();
        if (valCurs != null) {
            this.saveValCurses(valCurs);
            result = true;
        }
        return result;
    }

    @Override
    public boolean getValCurses(Calendar date) {
        boolean result = false;
        ValCurs valCurs = valCursGatherer.getValCursFromCB(
                date.get(Calendar.DAY_OF_MONTH),
                //Не забываем, что
                //The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0.
                date.get(Calendar.MONTH) + 1,
                date.get(Calendar.YEAR)
        );
        if (valCurs != null) {
            this.saveValCurses(valCurs);
            result = true;
        }
        return result;
    }

    private void saveValCurses(ValCurs valCurs) {
        List<CurrencyInfo> infos = null;
        try {
            infos = valCursDistiller.infoDistillation(valCurs);
            for (CurrencyInfo vi : infos) {
                currencyInfoService.saveInfo(vi);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
