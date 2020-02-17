package pl.nes.detector.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import pl.nes.detector.dto.Measurement;
import pl.nes.detector.detector.AnomalyDetectionService;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Value("classpath:${input.data.path}/*.json")
    private Resource[] resources;

    @Bean
    public ItemReader<Measurement> reader() {
        JsonItemReader<Measurement> delegate = new JsonItemReaderBuilder<Measurement>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Measurement.class))
                .resource(resources[0])
                .name("jsonItemReader")
                .build();
        MultiResourceItemReader<Measurement> reader = new MultiResourceItemReader<>();
        reader.setDelegate(delegate);
        reader.setResources(resources);
        return reader;
    }

    @Bean
    public MeasurementProcessor processor(AnomalyDetectionService detectingService) {
        return new MeasurementProcessor(detectingService);
    }

    @Bean
    public ItemWriter writer() {
        return new MeasurmentWriter();
    }

    @Bean
    public Job detectAnomaly(ExitListener exitListener, Step step1) {
        return jobBuilderFactory.get("detectAnomaly")
                .incrementer(new RunIdIncrementer())
                .listener(exitListener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(AnomalyDetectionService detectingService) {
        return stepBuilderFactory.get("step1")
                .<Measurement, Void>chunk(5)
                .reader(reader())
                .processor(processor(detectingService))
                .writer(writer())
                .build();
    }
}
