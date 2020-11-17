package morozov.ru.service.util;

import morozov.ru.model.fromxsdcentralbank.ValCurs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ValCursGatherer {

    @Value("${cb.rates.url}")
    private String cbUrl;
    private RestTemplate restTemplate;

    @Autowired
    public ValCursGatherer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ValCurs getValCursFromCB() {
        return restTemplate.getForObject(cbUrl, ValCurs.class);
    }

}