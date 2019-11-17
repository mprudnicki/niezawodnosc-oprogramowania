package models;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class JelinskiMoranda implements Model {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private List<Integer> values;
    private double acceptableDifference;
    private double calculatedFiValue;
    private double calculatedNValue;
    private double calculatedExpectedTimeValue;

    public JelinskiMoranda(List<Integer> values, double difference) {
        logger.log(Level.INFO, "\t> Constructing Jelinski-Moranda...");
        this.values = values;
        this.acceptableDifference = difference;
    }

    @Override
    public void execute() {
        logger.log(Level.INFO, "[Jelinski-Moranda] Programme execution start");
        calculatedNValue = calculateN();
        calculatedFiValue = calculateFi(calculatedNValue);
        calculatedExpectedTimeValue = calculateExpectedTimeValue(calculatedNValue, calculatedFiValue);
        logger.log(Level.INFO, "\t\t[Jelinski-Moranda] Value of N: " + calculatedNValue);
        logger.log(Level.INFO, "\t\t[Jelinski-Moranda] Value of Φ: " + calculatedFiValue);
        logger.log(Level.INFO, "\t\t[Jelinski-Moranda] Expected value: " + calculatedExpectedTimeValue);
    }

    @Override
    public double calculateExpectedTimeValue(double calculatedNValue, double calculatedFiValue) {
        return 1.0 / (calculatedFiValue * (calculatedNValue - values.size() + 1));
    }

    @Override
    public double calculateFi(double calculatedNValue) {
        logger.log(Level.INFO,"[Jelinski-Moranda] Calculating value of Φ...");
        return values.size() / (calculatedNValue * calculateFirstFiSum() - calculateSecondFiSum());
    }

    @Override
    public double calculateN() {
        logger.log(Level.INFO,"[Jelinski-Moranda] Calculating value of N...");
        long nValue = values.size();
        double difference = Double.POSITIVE_INFINITY;
        while(difference > acceptableDifference) {
            nValue++;
            difference = Math.abs(calculateLeft(nValue) - calculateRight(nValue));
        }
        return nValue;
    }

    private double calculateLeft(long n) {
        return IntStream.range(1, values.size() + 1).mapToDouble(e -> (1.0 / (n - (e - 1)))).sum();
    }

    private double calculateRight(long n) {
        return values.size() * calculateFirstFiSum() / (n * calculateFirstFiSum() - calculateSecondFiSum());
    }

    private double calculateFirstFiSum() {
        return values.stream().mapToDouble(Integer::doubleValue).sum();
    }

    private double calculateSecondFiSum() {
        return IntStream.range(0, values.size()).mapToDouble(e -> values.get(e) * (e - 1)).sum();
    }
}
