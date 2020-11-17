package morozov.ru;

import morozov.ru.model.fromxsdcentralbank.ValCurs;
import morozov.ru.model.workingmodel.DateCurs;
import morozov.ru.model.workingmodel.СurrencyInfo;
import morozov.ru.service.repository.ValCursRepository;
import morozov.ru.service.repository.СurrencyInfoRepository;
import morozov.ru.service.util.ValCursDistillator;
import morozov.ru.service.util.ValCursGatherer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;

@Component
public class DataInit {

    private ValCursRepository valCursRepository;
    private СurrencyInfoRepository сurrencyInfoRepository;
    private ValCursGatherer valCursGatherer;
    private ValCursDistillator valCursDistillator;

    @Autowired
    public DataInit(
            ValCursRepository valCursRepository,
            СurrencyInfoRepository сurrencyInfoRepository,
            ValCursGatherer valCursGatherer,
            ValCursDistillator valCursDistillator
    ) {
        this.valCursRepository = valCursRepository;
        this.сurrencyInfoRepository = сurrencyInfoRepository;
        this.valCursGatherer = valCursGatherer;
        this.valCursDistillator = valCursDistillator;
    }

    @PostConstruct
    @Transactional
    public void setDataInit() {
        ValCurs valCurs = valCursGatherer.getValCursFromCB();
        DateCurs dateCurs = null;
        try {
            dateCurs = valCursDistillator.dateDistillation(valCurs);
            List<СurrencyInfo> infos = valCursDistillator.infoDistillation(valCurs);
            for (СurrencyInfo vi : infos) {
                сurrencyInfoRepository.save(vi);
            }
            valCursRepository.save(dateCurs);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
