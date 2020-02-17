package pl.nes.detector.detector.anomaly;

import pl.nes.detector.dto.Measurement;

public interface BaseAnomalyService {
    String validate(Measurement measurement);
}
