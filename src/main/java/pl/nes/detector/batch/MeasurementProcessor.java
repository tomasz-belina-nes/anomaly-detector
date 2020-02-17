package pl.nes.detector.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import pl.nes.detector.dto.Measurement;
import pl.nes.detector.detector.AnomalyDetectionService;

@RequiredArgsConstructor
public class MeasurementProcessor implements ItemProcessor<Measurement, Void> {

    private final AnomalyDetectionService detectingService;

    @Override
    public Void process(Measurement measurement) {
        detectingService.validate(measurement);
        return null;
    }
}
