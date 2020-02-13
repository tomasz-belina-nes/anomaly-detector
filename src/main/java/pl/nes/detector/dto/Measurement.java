package pl.nes.detector.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Measurement {

    private long id;

    private Double measuredValue;

    private Timestamp timestamp;

    private String parentId;

    private String deviceId;

}
