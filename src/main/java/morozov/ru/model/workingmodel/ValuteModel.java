package morozov.ru.model.workingmodel;

import javax.persistence.*;

@Entity
@Table(name = "valutes")
public class ValuteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int primary_key;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date", nullable = false)
    private ValCursModel date;
    private int numCode;
    private String charCode;
    private long nominal;
    private String name;
    private String value;
    private String id;

    public ValuteModel() {
    }

    public int getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(int primary_key) {
        this.primary_key = primary_key;
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
