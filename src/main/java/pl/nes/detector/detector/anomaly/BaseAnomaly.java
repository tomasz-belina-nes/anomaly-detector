package pl.nes.detector.detector.anomaly;

import pl.nes.detector.dto.Measurement;

public interface BaseAnomaly {
    boolean validate(Measurement measurement);
    String toString(Measurement measurement);
}
