package com.fabrica.hutchisonspring.config;

import com.fabrica.hutchisonspring.defaults.ApplicationDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final CorsConfiguration cors = new CorsConfiguration();

    private final Security security = new Security();

    private final BatchFolder batchFolder = new BatchFolder();

    public CorsConfiguration getCors() {
        return cors;
    }

    public Security getSecurity() {
        return security;
    }

    public BatchFolder getBatchFolder() {
        return batchFolder;
    }

    public static class Security {
        private final Authentication authentication = new Authentication();

        public Authentication getAuthentication() {
            return authentication;
        }

        public static class Authentication {
            private final Jwt jwt = new Jwt();

            public Jwt getJwt() {
                return jwt;
            }

            public static class Jwt {
                private String secret = ApplicationDefaults.Security.Authentication.Jwt.secret;
                private String base64Secret = ApplicationDefaults.Security.Authentication.Jwt.base64Secret;
                private long tokenValidityInSeconds = ApplicationDefaults.Security.Authentication.Jwt
                        .tokenValidityInSeconds;
                private long tokenValidityInSecondsForRememberMe = ApplicationDefaults.Security.Authentication.Jwt
                        .tokenValidityInSecondsForRememberMe;

                public String getSecret() {
                    return secret;
                }
                public void setSecret(String secret) {
                    this.secret = secret;
                }
                public String getBase64Secret() {
                    return base64Secret;
                }
                public void setBase64Secret(String base64Secret) {
                    this.base64Secret = base64Secret;
                }
                public long getTokenValidityInSeconds() {
                    return tokenValidityInSeconds;
                }
                public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
                    this.tokenValidityInSeconds = tokenValidityInSeconds;
                }
                public long getTokenValidityInSecondsForRememberMe() {
                    return tokenValidityInSecondsForRememberMe;
                }
                public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
                    this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
                }
            }
        }
    }

    public static class BatchFolder {
        private String path = "/batch-files/";

        private String okSuffix = "-ok";

        private String errorSuffix = "-err";

        private String extension = ".dat";

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getOkSuffix() {
            return okSuffix;
        }

        public void setOkSuffix(String okSuffix) {
            this.okSuffix = okSuffix;
        }

        public String getErrorSuffix() {
            return errorSuffix;
        }

        public void setErrorSuffix(String errorSuffix) {
            this.errorSuffix = errorSuffix;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }
    }
}
