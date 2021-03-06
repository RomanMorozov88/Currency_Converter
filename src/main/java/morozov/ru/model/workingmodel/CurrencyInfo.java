package morozov.ru.model.workingmodel;

import morozov.ru.model.workingmodel.rate.ExchangeRate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "valutes")
public class CurrencyInfo {

    private int numCode;
    private String charCode;
    private String name;
    @Id
    private String id;
    @OneToMany(mappedBy = "info", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExchangeRate> rates = new ArrayList<>();

    public CurrencyInfo() {
    }

    public int getNumCode() {
        return numCode;
    }

    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ExchangeRate> getRates() {
        return rates;
    }

    public void setRates(List<ExchangeRate> rates) {
        this.rates = rates;
    }

    public void setRate(ExchangeRate rate) {
        this.rates.add(rate);
    }
}
