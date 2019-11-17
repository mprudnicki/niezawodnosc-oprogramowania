import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {
    public Map readConfigFile(String path) throws IOException {
        Map<String, String> properties = new HashMap<>();
        Properties configFile = new java.util.Properties();
            configFile.load(this.getClass().getClassLoader().
                    getResourceAsStream(path));
            String dataPath = Optional.ofNullable(configFile.getProperty("data-source")).orElseThrow(IllegalArgumentException::new);
            String difference = Optional.ofNullable(configFile.getProperty("accepted-difference")).orElseThrow(IllegalArgumentException::new);
            properties.put("data_source", dataPath);
            properties.put("accepted_difference", difference);
        return properties;
    }

    public List readValues(String path) {
        final List values = new LinkedList<Integer>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            values.addAll(stream.map(e -> e.split("\\s+"))
                    .flatMap(Arrays::stream)
                    .map(Integer::valueOf)
                    .collect(Collectors.toList()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;

    }
}
