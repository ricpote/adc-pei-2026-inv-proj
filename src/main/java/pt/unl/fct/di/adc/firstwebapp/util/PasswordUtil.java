package pt.unl.fct.di.adc.firstwebapp.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtil {

    private PasswordUtil() {
    }

    public static String hashPassword(String plainPassword) {
        if (plainPassword == null) {
            return null;
        }
        return DigestUtils.sha512Hex(plainPassword);
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        return DigestUtils.sha512Hex(plainPassword).equals(hashedPassword);
    }
}