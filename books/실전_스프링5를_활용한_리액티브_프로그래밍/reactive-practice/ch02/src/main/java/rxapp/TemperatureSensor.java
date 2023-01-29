package rxapp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;


@Component
public class TemperatureSensor {

    private static final Logger log = LoggerFactory.getLogger(TemperatureSensor.class);
    private final Random rnd = new Random();

    private final Observable<Temperature> dataStream = Observable
            .range(0, Integer.MAX_VALUE)
            .concatMap(tick -> Observable
                    .just(tick)
                    .delay(rnd.nextInt(5000), TimeUnit.MILLISECONDS)
                    .map(tickValue -> this.probe()))
            .publish()
            .refCount();

    public Observable<Temperature> temperatureStream() {
        return dataStream;
    }

    private Temperature probe() {
        double actualTemp = 16 + rnd.nextGaussian() * 10;
        log.info("Asking sensor, sensor value: {}", actualTemp);
        return new Temperature(actualTemp);
    }
}
