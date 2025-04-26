package com.skryl.edu.otel;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongHistogram;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MeterExample {
    private static final String INSTRUMENTATION_NAME = TraceExample.class.getName();
    private final Meter meter;
    private final Tracer tracer;

    public MeterExample(OpenTelemetry openTelemetry) {
        this.meter = openTelemetry.getMeter(INSTRUMENTATION_NAME);
        this.tracer = openTelemetry.getTracer(INSTRUMENTATION_NAME);
    }

    public void doWork() throws InterruptedException {
        LongHistogram histogram = meter.histogramBuilder("page_load_time")
                .setDescription("Time taken for page load")
                .ofLongs()
                .setUnit("ms")
                .build();

        for (int i = 0; i < 500; i++) {
            System.out.println("loading page");
            long startTime = System.currentTimeMillis();
            Span exampleSpan = tracer.spanBuilder("exampleSpan").startSpan();
            Context exampleContext = Context.current().with(exampleSpan);
            try (Scope scope = exampleContext.makeCurrent()) {
                int randomNumber = (int)(Math.random() * 20) + 1;
                if (randomNumber < 10) {
                    exampleSpan.setAttribute("good", true);
                    exampleSpan.setAttribute("pageLoadTime", randomNumber);
                } else {
                    exampleSpan.setAttribute("good", false);
                    exampleSpan.setAttribute("pageLoadTime", randomNumber);
                }
                TimeUnit.SECONDS.sleep(randomNumber);
                System.out.println("Page loaded after %d seconds".formatted(randomNumber));
            } finally {
                var duration = System.currentTimeMillis() - startTime;
                System.out.println("Duration %d ms".formatted(duration));
                histogram.record(duration, Attributes.empty(), exampleContext);
                exampleSpan.end();
            }
        }
        System.out.println("Exiting");
    }
}
