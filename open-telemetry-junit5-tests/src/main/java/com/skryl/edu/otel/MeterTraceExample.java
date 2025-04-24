package com.skryl.edu.otel;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.LongHistogram;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;

public class MeterTraceExample {
    private static final String INSTRUMENTATION_NAME = TraceExample.class.getName();
    private final Meter meter;
    private final Tracer tracer;

    public MeterTraceExample(OpenTelemetry openTelemetry) {
        this.meter = openTelemetry.getMeter(INSTRUMENTATION_NAME);
        this.tracer = openTelemetry.getTracer(INSTRUMENTATION_NAME);
    }

    public void doWorkV2() throws InterruptedException {
        LongCounter counter = meter.counterBuilder("my_custom_metric").build();
        var gauge = meter.gaugeBuilder("my_custom_gauge")
                .setDescription("custom gauge")
                .ofLongs()
                .build();
        LongHistogram histogram = meter.histogramBuilder("super.timer").ofLongs().setUnit("ms").build();

        for (int i = 0; i < 500; i++) {
            System.out.println("do work");
            long startTime = System.currentTimeMillis();
            Span exampleSpan = tracer.spanBuilder("exampleSpan").startSpan();
            Context exampleContext = Context.current().with(exampleSpan);
            try (Scope scope = exampleContext.makeCurrent()) {
                counter.add(1);

                exampleSpan.setAttribute("good", true);
                exampleSpan.setAttribute("exampleNumber", i);

                long gaugeValue = System.currentTimeMillis() % 100;
                gauge.set(gaugeValue);

                Thread.sleep(1000);
            } finally {
                histogram.record(
                        System.currentTimeMillis() - startTime, Attributes.empty(), exampleContext);
                exampleSpan.end();
            }
        }
        System.out.println("Exiting");
    }
}
