package pl.nes.detector.batch;


import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class MeasurmentWriter<T> implements ItemWriter<T> {
    @Override
    public void write(List<? extends T> items) throws Exception {

    }
}