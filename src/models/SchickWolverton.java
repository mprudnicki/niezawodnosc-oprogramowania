package models;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class SchickWolverton implements Model {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private List<Integer> values;
    private double acceptableDifference;
    private double calculatedFiValue;
    private double calculatedNValue;
    private double calculatedExpectedValue;

    public SchickWolverton(List<Integer> values, double difference) {
        logger.log(Level.INFO, "\t> Constructing Shick-Wolverton...");
        this.values = values;
        this.acceptableDifference = difference;
    }

    @Override
    public void execute() {
        logger.log(Level.INFO, "[Shick-Wolverton] Programme execution start");
        calculatedNValue = calculateN();
        calculatedFiValue = calculateFi(calculatedNValue);
        calculatedExpectedValue = calculateExpectedTimeValue(calculatedNValue, calculatedFiValue);
        logger.log(Level.INFO, "\t\t[Shick-Wolverton] Value of N: " + calculatedNValue);
        logger.log(Level.INFO, "\t\t[Shick-Wolverton] Value of Φ: " + calculatedFiValue);
        logger.log(Level.INFO, "\t\t[Shick-Wolverton] Expected value: " + calculatedExpectedValue);
    }

    @Override
    public double calculateExpectedTimeValue(double calculatedNValue, double calculatedFiValue) {
        return Math.sqrt(Math.PI / (2 * calculatedFiValue * (calculatedNValue - (values.size()))));
    }

    @Override
    public double calculateFi(double n) {
        return IntStream
                .range(1, values.size() + 1)
                .mapToDouble(e -> 1.0 / ((n - (e - 1)) * calculateSumT()))
                .sum() * 2;
    }

    @Override
    public double calculateN() {
        logger.log(Level.INFO,"[Shick-Wolverton] Calculating value of N...");
        int nValue = values.size();
        final double tValue = calculateSumT();
        double difference = Double.POSITIVE_INFINITY;
        while(difference > acceptableDifference) {
            nValue++;
            difference = Math.abs(calculateFiUpperEquation(nValue, tValue) - calculateFiLowerEquation(nValue, tValue));
        }
        return nValue;
    }

    private Double calculateFiUpperEquation(int nValue, double tValue) {
        double sum = 0.0;
        for (int idx = 1; idx <= values.size(); idx++) {
            sum += 1.0 / ((nValue - (idx - 1)) * tValue);
        }
        return 2*sum;
    }

    private Double calculateFiLowerEquation(int nValue, double tValue) {
        Double sum = getSumSquaresWithIndexes();
        return (2 * values.size()) / (nValue * tValue - sum);
    }

    private Double getSumSquaresWithIndexes() {
        double result = 0.0;
        int idx = 1;
        for (Integer number : values) {
            result += (idx - 1.0) * Math.pow(number, 2);
            idx++;
        }
        return result;
    }

    private double calculateSumT() {
        return values.stream().mapToDouble(e -> Math.pow(e, 2)).sum();
    }

}
