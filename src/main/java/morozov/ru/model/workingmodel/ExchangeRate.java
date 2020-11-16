package morozov.ru.model.workingmodel;

import javax.persistence.*;

@Entity
@Table(name = "rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private DateCurs date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ValuteInfo info;
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

    public DateCurs getDate() {
        return date;
    }

    public void setDate(DateCurs date) {
        this.date = date;
    }

    public ValuteInfo getInfo() {
        return info;
    }

    public void setInfo(ValuteInfo info) {
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
