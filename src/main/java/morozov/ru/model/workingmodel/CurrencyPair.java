package morozov.ru.model.workingmodel;

import javax.persistence.*;

@Entity
@Table(name = "pairs")
public class CurrencyPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ExchangeRate from;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ExchangeRate to;

    public CurrencyPair() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ExchangeRate getFrom() {
        return from;
    }

    public void setFrom(ExchangeRate from) {
        this.from = from;
    }

    public ExchangeRate getTo() {
        return to;
    }

    public void setTo(ExchangeRate to) {
        this.to = to;
    }
}
