package models;

public interface Model {
    void execute();
    double calculateExpectedTimeValue(double calculatedNValue, double calculatedFiValue);
    double calculateFi(double n);
    double calculateN();

}
