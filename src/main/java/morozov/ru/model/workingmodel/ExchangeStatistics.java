package morozov.ru.model.workingmodel;

public class ExchangeStatistics {

    private CurrencyPair pair;
    private double averageRate;
    private double totalSumFrom;
    private double totalSumTo;

    public ExchangeStatistics() {
    }

    public CurrencyPair getPair() {
        return pair;
    }

    public void setPair(CurrencyPair pair) {
        this.pair = pair;
    }

    public double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

    public double getTotalSumFrom() {
        return totalSumFrom;
    }

    public void setTotalSumFrom(double totalSumFrom) {
        this.totalSumFrom = totalSumFrom;
    }

    public double getTotalSumTo() {
        return totalSumTo;
    }

    public void setTotalSumTo(double totalSumTo) {
        this.totalSumTo = totalSumTo;
    }
}
