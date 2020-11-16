package morozov.ru.model.workingmodel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "dates")
public class ValCursModel {

    @OneToMany(mappedBy = "date", cascade = CascadeType.ALL)
    private List<ValuteModel> valutes = new ArrayList<>();
    @Id
    @Temporal(TemporalType.DATE)
    private Calendar date;
    private String name;

    public ValCursModel() {
    }

    public List<ValuteModel> getValutes() {
        return valutes;
    }

    public void setValutes(List<ValuteModel> valutes) {
        this.valutes = valutes;
    }

    public void setValute(ValuteModel valute) {
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
