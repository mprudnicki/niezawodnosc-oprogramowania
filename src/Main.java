import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.log(Level.INFO, "NIEZAWODNOSC OPROGRAMOWANIA\nMaciej Rudnicki, I8B3S4");
        FileReader fileReader = new FileReader();
        try {
            logger.log(Level.INFO, "Reading programme arguments...");
            String configFilePath = "config.properties";
            Map properties = fileReader.readConfigFile(configFilePath);
            String fileName = properties.get("data_source").toString();
            double acceptedDifference = Double.parseDouble(properties.get("accepted_difference").toString());
            logger.log(Level.INFO, "Reading input file...");
            List values = Optional.ofNullable(fileReader.readValues(fileName)).orElseThrow(() -> new IllegalArgumentException("[ERROR] File containing illegal or no values"));
            logger.log(Level.INFO, "Values: " + values);
            logger.log(Level.INFO, "Initializing Estimate Calculator...");
            EstimateCalculator estimateCalculator = new EstimateCalculator(values, acceptedDifference);
            logger.log(Level.INFO, "Executing programme...");
            estimateCalculator.execute();
        } catch (IllegalArgumentException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }
}

