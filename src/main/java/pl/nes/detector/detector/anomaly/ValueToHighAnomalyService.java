package pl.nes.detector.detector.anomaly;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import pl.nes.detector.dto.Measurement;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ValueToHighAnomalyService implements BaseAnomalyService {
    private Set<ValueToHighAnomaly> valueToHighs;

    @Value("${value-to-high.anomaly.config.path}")
    private String configPath;

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        valueToHighs = mapper.readValue(
                new ClassPathResource(configPath).getFile(), new TypeReference<HashSet<ValueToHighAnomaly>>() {
                }
        );
    }

    public String validate(Measurement measurement) {
        return valueToHighs.stream()
                .filter(anomaly -> anomaly.validate(measurement))
                .findFirst()
                .map(x -> x.toString(measurement))
                .orElse(null);
    }

}
