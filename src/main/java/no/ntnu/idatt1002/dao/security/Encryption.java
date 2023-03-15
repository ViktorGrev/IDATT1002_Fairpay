package no.ntnu.idatt1002.dao.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

public final class Encryption {

    public static String encrypt(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static boolean verify(String password) {
        //BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);
        return false;
    }
}
