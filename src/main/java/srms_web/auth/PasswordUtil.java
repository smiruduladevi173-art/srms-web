package srms_web.auth;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordUtil {

    // =========================
    // HASH PASSWORD
    // =========================

    public static String hashPassword(
            String password
    ) {

        Argon2 argon2 =
                Argon2Factory.create();

        try {

            return argon2.hash(

                    3,          // iterations

                    65536,      // memory

                    1,          // parallelism

                    password.toCharArray()

            );

        } finally {

            argon2.wipeArray(
                    password.toCharArray()
            );

        }

    }

    // =========================
    // VERIFY PASSWORD
    // =========================

    public static boolean verifyPassword(

            String hash,

            String password

    ) {

        Argon2 argon2 =
                Argon2Factory.create();

        try {

            return argon2.verify(

                    hash,

                    password.toCharArray()

            );

        } finally {

            argon2.wipeArray(
                    password.toCharArray()
            );

        }

    }

}