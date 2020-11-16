package morozov.ru.model.workingmodel;

import javax.persistence.*;

@Entity
@Table(name = "valutes")
public class ValuteModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date", nullable = false)
    private ValCursModel date;
    private int numCode;
    private String charCode;
    private long nominal;
    private String name;
    private String value;
    @Id
    private String id;

    public ValuteModel() {
    }

    public ValCursModel getDate() {
        return date;
    }

    public void setDate(ValCursModel date) {
        this.date = date;
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

    public long getNominal() {
        return nominal;
    }

    public void setNominal(long nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
