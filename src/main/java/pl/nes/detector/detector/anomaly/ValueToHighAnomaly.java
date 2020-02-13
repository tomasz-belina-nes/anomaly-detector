package pl.nes.detector.detector.anomaly;

import lombok.Data;
import pl.nes.detector.dto.Measurement;

@Data
public class ValueToHighAnomaly implements BaseAnomaly{
    private Double limit;
    private String parentPattern;

    public boolean validate(Measurement measurement){
        if (measurement.getMeasuredValue() > limit && measurement.getParentId().matches(parentPattern)) {
            return true;
        }
        return false;
    }

    public String toString(Measurement measurement) {
        return "ValueToHigh," + measurement.getDeviceId() + "," + measurement.getMeasuredValue();
    }
}
