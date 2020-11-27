package morozov.ru.model.workingmodel.pair;

import morozov.ru.model.workingmodel.CurrencyInfo;
import morozov.ru.model.workingmodel.Operation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pairs")
public class CurrencyPair {

    @EmbeddedId
    private CurrencyPairCompositeID id = new CurrencyPairCompositeID();

    @MapsId("fromCurrencyId")
    @ManyToOne
    @JoinColumn(name = "from_currency", nullable = false)
    private CurrencyInfo fromCurrency;
    @MapsId("toCurrencyId")
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
        this.id.setFromCurrencyId(this.fromCurrency.getId());
        this.id.setToCurrencyId(this.toCurrency.getId());
    }

    public CurrencyPairCompositeID getId() {
        return id;
    }

    public void setId(CurrencyPairCompositeID id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyPair)) return false;
        CurrencyPair that = (CurrencyPair) o;
        return Objects.equals(fromCurrency, that.fromCurrency) &&
                Objects.equals(toCurrency, that.toCurrency) &&
                Objects.equals(operations, that.operations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromCurrency, toCurrency, operations);
    }
}
