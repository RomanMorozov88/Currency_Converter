package morozov.ru.service.util;

import morozov.ru.model.fromxsdcentralbank.ValCurs;
import morozov.ru.model.workingmodel.CurrencyInfo;
import morozov.ru.service.repository.CurrencyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

/**
 * Получение\загрузка данных при старте приложения
 * а так же в для обновления данных по курсам.
 */
@Component
public class DataInit {

    private CurrencyInfoRepository currencyInfoRepository;
    private ValCursGatherer valCursGatherer;
    private ValCursDistiller valCursDistiller;

    @Autowired
    public DataInit(
            CurrencyInfoRepository currencyInfoRepository,
            ValCursGatherer valCursGatherer,
            ValCursDistiller valCursDistiller
    ) {
        this.currencyInfoRepository = currencyInfoRepository;
        this.valCursGatherer = valCursGatherer;
        this.valCursDistiller = valCursDistiller;
    }

    @PostConstruct
    @Transactional
    public void getValCurses() {
        Calendar date = Calendar.getInstance();
        ValCurs valCurs = valCursGatherer.getValCursFromCB(
                date.get(Calendar.DAY_OF_MONTH),
                //Не забываем, что
                //The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0.
                date.get(Calendar.MONTH) + 1,
                date.get(Calendar.YEAR)
        );
        this.saveValCurses(valCurs);
    }

    private void saveValCurses(ValCurs valCurs) {
        try {
            List<CurrencyInfo> infos = valCursDistiller.infoDistillation(valCurs);
            for (CurrencyInfo vi : infos) {
                currencyInfoRepository.save(vi);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
