package com.fabrica.hutchisonspring.defaults;

public interface ApplicationDefaults {
    interface Security {
        interface Authentication {
            interface Jwt {
                String secret = null;
                String base64Secret = null;
                long tokenValidityInSeconds = 1800; // 30 minutes
                long tokenValidityInSecondsForRememberMe = 2592000; // 30 days
            }
        }
    }
}
