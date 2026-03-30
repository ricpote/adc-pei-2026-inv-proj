package pt.unl.fct.di.adc.firstwebapp.util;

import pt.unl.fct.di.adc.firstwebapp.exception.ApiException;
import pt.unl.fct.di.adc.firstwebapp.model.ErrorCode;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void requireNotBlank(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new ApiException(ErrorCode.INVALID_INPUT);
        }
    }

    public static void require(boolean condition, ErrorCode errorCode) {
        if (!condition) {
            throw new ApiException(errorCode);
        }
    }
}