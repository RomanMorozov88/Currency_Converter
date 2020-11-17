package morozov.ru.model.workingmodel;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.DATE)
    private Calendar date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "info", nullable = false)
    private CurrencyInfo info;
    private long nominal;
    private double value;

    public ExchangeRate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
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
}
