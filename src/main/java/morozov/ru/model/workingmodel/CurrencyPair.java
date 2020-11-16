package morozov.ru.model.workingmodel;

import javax.persistence.*;

@Entity
@Table(name = "pairs")
public class CurrencyPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from", nullable = false)
    private ValuteModel from;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to", nullable = false)
    private ValuteModel to;

    public CurrencyPair() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ValuteModel getFrom() {
        return from;
    }

    public void setFrom(ValuteModel from) {
        this.from = from;
    }

    public ValuteModel getTo() {
        return to;
    }

    public void setTo(ValuteModel to) {
        this.to = to;
    }
}
