package org.example.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "")
public interface AppConfig {

    Api api();

    Auth auth();

    Logging logging();

    interface Api {
        String baseUrl(); // maps to "api.base-url"
    }

    interface Auth {
        String email();    // overridable via AUTH_EMAIL env var
        String password(); // overridable via AUTH_PASSWORD env var
    }

    interface Logging {
        boolean enabled();
    }
}
