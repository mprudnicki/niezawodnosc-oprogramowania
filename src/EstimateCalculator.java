import models.JelinskiMoranda;
import models.Model;
import models.SchickWolverton;

import java.util.List;

public class EstimateCalculator {
    List<Model> models;
    Model jelinskiMoranda;
    Model schickWolverton;

    public EstimateCalculator(List values, double acceptedDifference) {
        jelinskiMoranda = new JelinskiMoranda(values, acceptedDifference);
        schickWolverton = new SchickWolverton(values, acceptedDifference);
    }

    public void execute() {
        jelinskiMoranda.execute();
        schickWolverton.execute();
    }
}
