package morozov.ru.model.workingmodel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pairs")
public class CurrencyPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "from_currency", nullable = false)
    private CurrencyInfo fromCurrency;
    @ManyToOne
    @JoinColumn(name = "to_currency", nullable = false)
    private CurrencyInfo toCurrency;
    @OneToMany(mappedBy = "pair", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Operation> operations = new ArrayList<>();

    public CurrencyPair() {
    }

    public CurrencyPair(CurrencyInfo fromCurrency, CurrencyInfo toCurrency) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CurrencyInfo getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(CurrencyInfo fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public CurrencyInfo getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(CurrencyInfo toCurrency) {
        this.toCurrency = toCurrency;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public void setOperation(Operation operation) {
        this.operations.add(operation);
    }
}
