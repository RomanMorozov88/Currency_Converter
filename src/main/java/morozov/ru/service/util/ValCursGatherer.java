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

    /**
     * Например, для получения котировок на заданный день
     * http://www.cbr.ru/scripts/XML_daily.asp?date_req=02/03/2002
     * date_req= Date of query (dd/mm/yyyy) (c)
     *
     * @param day
     * @param month
     * @param year
     * @return
     */
    public ValCurs getValCursFromCB(int day, int month, int year) {
        StringBuilder request = new StringBuilder(cbUrl);
        request.append("?date_req=");
        this.buildRequestPart(request, day);
        request.append("/");
        this.buildRequestPart(request, month);
        request.append("/");
        request.append(year);
        return restTemplate.getForObject(request.toString(), ValCurs.class);
    }

    /**
     * Т.к. в запросе к ЦБ используется строгий формат записи даты-
     * то для корректной работы для дней\месяцев, чей номер меньше десяти
     * вначале добавляется 0.
     *
     * @param builder
     * @param number
     */
    private void buildRequestPart(StringBuilder builder, int number) {
        if (number < 10) {
            builder
                    .append("0")
                    .append(number);
        } else {
            builder
                    .append(number);
        }
    }

}