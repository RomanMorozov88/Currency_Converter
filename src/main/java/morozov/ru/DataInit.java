package morozov.ru;

import morozov.ru.model.fromxsdcentralbank.ValCurs;
import morozov.ru.model.workingmodel.ExchangeRate;
import morozov.ru.model.workingmodel.CurrencyInfo;
import morozov.ru.service.repository.ExchangeRateRepository;
import morozov.ru.service.repository.CurrencyInfoRepository;
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

    private ExchangeRateRepository exchangeRateRepository;
    private CurrencyInfoRepository currencyInfoRepository;
    private ValCursGatherer valCursGatherer;
    private ValCursDistillator valCursDistillator;

    @Autowired
    public DataInit(
            ExchangeRateRepository exchangeRateRepository,
            CurrencyInfoRepository currencyInfoRepository,
            ValCursGatherer valCursGatherer,
            ValCursDistillator valCursDistillator
    ) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.currencyInfoRepository = currencyInfoRepository;
        this.valCursGatherer = valCursGatherer;
        this.valCursDistillator = valCursDistillator;
    }

    @PostConstruct
    @Transactional
    public void setDataInit() {
        ValCurs valCurs = valCursGatherer.getValCursFromCB();
        try {
            List<ExchangeRate> rates = valCursDistillator.dateDistillation(valCurs);
            List<CurrencyInfo> infos = valCursDistillator.infoDistillation(valCurs);
            for (CurrencyInfo vi : infos) {
                currencyInfoRepository.save(vi);
            }
            for (ExchangeRate e : rates) {
                exchangeRateRepository.save(e);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
