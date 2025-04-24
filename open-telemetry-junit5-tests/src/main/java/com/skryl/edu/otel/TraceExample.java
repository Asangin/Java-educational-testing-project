package com.skryl.edu.otel;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

import java.util.concurrent.TimeUnit;

public class TraceExample {
    private static final String  INSTRUMENTATION_NAME = TraceExample.class.getName();
    private final Tracer tracer;

    public TraceExample(OpenTelemetry openTelemetry) {
        this.tracer = openTelemetry.getTracer(INSTRUMENTATION_NAME);
    }

    public void doWork() {
        Span span = tracer.spanBuilder("important work")
                .setAttribute("foo", 42)
                .setAttribute("bar", "a string")
                .startSpan();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            span.end();
        }
    }
}
