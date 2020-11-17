package morozov.ru.model.workingmodel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "valutes")
public class СurrencyInfo {

    private int numCode;
    private String charCode;
    private String name;
    @Id
    private String id;
    @OneToMany(mappedBy = "info", cascade = CascadeType.ALL)
    private List<ExchangeRate> rates = new ArrayList<>();

    public СurrencyInfo() {
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
}
