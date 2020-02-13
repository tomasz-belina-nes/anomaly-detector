package pl.nes.detector.detector;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.nes.detector.detector.anomaly.BaseAnomalyService;
import pl.nes.detector.dto.Measurement;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AnomalyDetectionService {

    private final Set<? extends BaseAnomalyService> anomalies;

    public void validate(Measurement measurement) {
        anomalies.forEach(x -> {
            print(x.validate(measurement));
        });
    }

    private void print(String anomaly) {
        if (!StringUtils.isEmpty(anomaly)) {
            System.out.println(anomaly);
        }
    }


}
