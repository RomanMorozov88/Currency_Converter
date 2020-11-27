package morozov.ru.model.workingmodel.pair;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Составной ключ для CurrencyPair
 */
@Embeddable
public class CurrencyPairCompositeID implements Serializable {

    private String fromCurrencyId;
    private String toCurrencyId;

    public CurrencyPairCompositeID() {
    }

    public CurrencyPairCompositeID(String fromCurrencyId, String toCurrencyId) {
        this.fromCurrencyId = fromCurrencyId;
        this.toCurrencyId = toCurrencyId;
    }

    public String getFromCurrencyId() {
        return fromCurrencyId;
    }

    public void setFromCurrencyId(String fromCurrencyId) {
        this.fromCurrencyId = fromCurrencyId;
    }

    public String getToCurrencyId() {
        return toCurrencyId;
    }

    public void setToCurrencyId(String toCurrencyId) {
        this.toCurrencyId = toCurrencyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyPairCompositeID)) return false;
        CurrencyPairCompositeID that = (CurrencyPairCompositeID) o;
        return Objects.equals(fromCurrencyId, that.fromCurrencyId) &&
                Objects.equals(toCurrencyId, that.toCurrencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromCurrencyId, toCurrencyId);
    }
}
