package api.util;

import api.exceptions.ApiException;

public class CheckNumber {

    public static int checkInteger(String number, String message) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new ApiException(message);
        }
    }

    public static float checkFloat(String number, String message) {
        try {
            return Float.parseFloat(number);
        } catch (NumberFormatException e) {
            throw new ApiException(message);
        }
    }

    public static double checkDouble(String number, String message) {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            throw new ApiException(message);
        }
    }

}
