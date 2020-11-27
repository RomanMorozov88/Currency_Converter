package morozov.ru.model.workingmodel.rate;

import morozov.ru.model.workingmodel.CurrencyInfo;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "rates")
public class ExchangeRate {

    @EmbeddedId
    private ExchangeRateCompositeID id = new ExchangeRateCompositeID();

    @ManyToOne
    @JoinColumn(name = "info", nullable = false)
    @MapsId("infoId")
    private CurrencyInfo info;
    private long nominal;
    private double value;

    public ExchangeRate() {
    }

    public ExchangeRateCompositeID getId() {
        return id;
    }

    public void setId(Calendar date, CurrencyInfo info) {
        this.info = info;
        this.id.setInfoId(this.info.getId());
        this.id.setDate(date);
    }

    public CurrencyInfo getInfo() {
        return info;
    }

    public void setInfo(CurrencyInfo info) {
        this.info = info;
    }

    public long getNominal() {
        return nominal;
    }

    public void setNominal(long nominal) {
        this.nominal = nominal;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeRate)) return false;
        ExchangeRate that = (ExchangeRate) o;
        return nominal == that.nominal &&
                Double.compare(that.value, value) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, info, nominal, value);
    }
}
