package com.ngtest.core.constants;

import java.time.Duration;

public final class Timeout {

    public static final Duration SHORT = Duration.ofSeconds(8);
    public static final Duration MEDIUM = Duration.ofSeconds(30);
    public static final Duration LONG = Duration.ofSeconds(90);
    public static final Duration EXTRA_LONG = Duration.ofSeconds(120);

    private Timeout() {
    }
}
