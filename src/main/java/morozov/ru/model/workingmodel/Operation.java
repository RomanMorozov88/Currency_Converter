package morozov.ru.model.workingmodel;

import morozov.ru.model.workingmodel.pair.CurrencyPair;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "from_currency", nullable = false)
    @JoinColumn(name = "to_currency", nullable = false)
    private CurrencyPair pair;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar date;
    private double fromAmount;
    private double toAmount;

    public Operation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CurrencyPair getPair() {
        return pair;
    }

    public void setPair(CurrencyPair pair) {
        this.pair = pair;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public double getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(double fromAmount) {
        this.fromAmount = fromAmount;
    }

    public double getToAmount() {
        return toAmount;
    }

    public void setToAmount(double toAmount) {
        this.toAmount = toAmount;
    }
}