package com.ngtest.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class FormatUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat AMOUNT_FORMATTER = new DecimalFormat("#,##0.00");

    private FormatUtil() {
    }

    public static String date(LocalDate date) {
        return DATE_FORMATTER.format(date);
    }

    public static String amount(BigDecimal amount) {
        return AMOUNT_FORMATTER.format(amount);
    }
}
