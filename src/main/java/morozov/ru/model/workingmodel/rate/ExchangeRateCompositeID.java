package morozov.ru.model.workingmodel.rate;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

/**
 * Составной ключ для ExchangeRate
 */
@Embeddable
public class ExchangeRateCompositeID implements Serializable {

    @Temporal(TemporalType.DATE)
    private Calendar date;
    private String infoId;

    public ExchangeRateCompositeID() {
    }

    public ExchangeRateCompositeID(Calendar date, String infoId) {
        this.date = date;
        this.infoId = infoId;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeRateCompositeID)) return false;
        ExchangeRateCompositeID that = (ExchangeRateCompositeID) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(infoId, that.infoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, infoId);
    }
}
