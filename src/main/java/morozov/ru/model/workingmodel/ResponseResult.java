package morozov.ru.model.workingmodel;

import java.util.Calendar;

public class ResponseResult {

    private Double result;
    private Calendar rateDate;

    public ResponseResult() {
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Calendar getRateDate() {
        return rateDate;
    }

    public void setRateDate(Calendar rateDate) {
        this.rateDate = rateDate;
    }
}
