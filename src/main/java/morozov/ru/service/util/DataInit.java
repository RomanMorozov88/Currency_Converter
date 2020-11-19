package morozov.ru.service.util;

import morozov.ru.model.fromxsdcentralbank.ValCurs;
import morozov.ru.model.workingmodel.CurrencyInfo;
import morozov.ru.service.repository.CurrencyInfoRepository;
import morozov.ru.service.util.ValCursDistiller;
import morozov.ru.service.util.ValCursGatherer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.text.ParseException;
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
        ValCurs valCurs = valCursGatherer.getValCursFromCB();
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
