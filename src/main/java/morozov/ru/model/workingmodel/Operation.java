package morozov.ru.model.workingmodel;

import javax.persistence.*;

@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pair", nullable = false)
    private CurrencyPair pair;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date", nullable = false)
    private ValCursModel date;
    private long amount;

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

    public ValCursModel getDate() {
        return date;
    }

    public void setDate(ValCursModel date) {
        this.date = date;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}