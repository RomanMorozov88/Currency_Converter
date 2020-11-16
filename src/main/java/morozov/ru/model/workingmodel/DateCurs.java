package morozov.ru.model.workingmodel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "dates")
public class DateCurs {

    @OneToMany(mappedBy = "date", cascade = CascadeType.ALL)
    private List<ExchangeRate> valutes = new ArrayList<>();
    @Id
    @Temporal(TemporalType.DATE)
    private Calendar date;
    private String name;

    public DateCurs() {
    }

    public List<ExchangeRate> getValutes() {
        return valutes;
    }

    public void setValutes(List<ExchangeRate> valutes) {
        this.valutes = valutes;
    }

    public void setValute(ExchangeRate valute) {
        this.valutes.add(valute);
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
