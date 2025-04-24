package com.skryl.edu;

import com.skryl.edu.otel.MeterTraceExample;
import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdk;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

@Log
public class PrometheusMetricsTest {

    @Test
    public void sendTestMetricsToPrometheusV2() throws InterruptedException {
        var openTelemetry = AutoConfiguredOpenTelemetrySdk.initialize().getOpenTelemetrySdk();
        var autoConfigExample = new MeterTraceExample(openTelemetry);
        autoConfigExample.doWorkV2();
    }
}
