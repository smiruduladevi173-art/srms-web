package srms_web.auth;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordUtil {

    public static boolean verifyPassword(

            String hash,

            String password

    ) {

        Argon2 argon2 =
                Argon2Factory.create();

        return argon2.verify(

                hash,

                password.toCharArray()

        );
    }
}