package com.abc;

import java.time.LocalDate;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public LocalDate now() {
    	LocalDate today = LocalDate.now();
        return today;
    }
}
