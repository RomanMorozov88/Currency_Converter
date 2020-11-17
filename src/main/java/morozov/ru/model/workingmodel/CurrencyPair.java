package morozov.ru.model.workingmodel;

import javax.persistence.*;

@Entity
@Table(name = "pairs")
public class CurrencyPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_currency", nullable = false)
    private СurrencyInfo fromCurrency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_currency", nullable = false)
    private СurrencyInfo toCurrency;

    public CurrencyPair() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public СurrencyInfo getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(СurrencyInfo fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public СurrencyInfo getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(СurrencyInfo toCurrency) {
        this.toCurrency = toCurrency;
    }
}
