package org.example.config;

import io.smallrye.config.SmallRyeConfig;
import io.smallrye.config.SmallRyeConfigBuilder;
import lombok.Getter;

@Getter
public enum ConfigurationManager {
    
    INSTANCE;

    private final AppConfig config;

    ConfigurationManager() {
        SmallRyeConfig smallRyeConfig = new SmallRyeConfigBuilder()
                .addDefaultSources()
                .addDiscoveredSources()
                .addDefaultInterceptors()
                .withMapping(AppConfig.class)
                .build();
        this.config = smallRyeConfig.getConfigMapping(AppConfig.class);
    }

}
