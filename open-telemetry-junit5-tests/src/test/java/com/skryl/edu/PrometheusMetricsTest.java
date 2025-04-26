package com.skryl.edu;

import com.skryl.edu.otel.MeterExample;
import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdk;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

@Log
public class PrometheusMetricsTest {

    @Test
    public void sendTestMetricsToPrometheusV2() throws InterruptedException {
        var openTelemetry = AutoConfiguredOpenTelemetrySdk.initialize().getOpenTelemetrySdk();
        var autoConfigExample = new MeterExample(openTelemetry);
        autoConfigExample.doWork();
    }
}
