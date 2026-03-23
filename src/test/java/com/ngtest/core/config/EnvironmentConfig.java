package com.ngtest.core.config;

import java.util.Map;

public final class EnvironmentConfig {

    private static final String DEFAULT_ENV = "qa";

    private static final Map<String, AppTarget> ENVIRONMENTS = Map.of(
            "qa", new AppTarget("com.losandes.bancamovil.qa", "com.losandes.bancamovil.MainActivity"),
            "staging", new AppTarget("com.losandes.bancamovil.staging", "com.losandes.bancamovil.MainActivity"),
            "preprod", new AppTarget("com.losandes.bancamovil.preprod", "com.losandes.bancamovil.MainActivity")
    );

    private EnvironmentConfig() {
    }

    public static String currentEnvironment() {
        return System.getProperty("env", DEFAULT_ENV).trim().toLowerCase();
    }

    public static AppTarget appTarget() {
        AppTarget configuredTarget = ENVIRONMENTS.get(currentEnvironment());
        if (configuredTarget == null) {
            return ENVIRONMENTS.get(DEFAULT_ENV);
        }
        return configuredTarget;
    }

    public record AppTarget(String appPackage, String appActivity) {
    }
}
