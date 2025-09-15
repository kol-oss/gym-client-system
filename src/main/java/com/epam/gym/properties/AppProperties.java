package com.epam.gym.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@Getter
@NoArgsConstructor
public class AppProperties {
    @Value("${app.security.max-password-length}")
    private int maxPasswordLength;

    @Value("${app.security.username-delimiter}")
    private String usernameDelimiter;

    @Value("${app.data.storage-location}")
    private String storageLocation;

    @Value("${app.data.save-updates}")
    private boolean saveUpdates;
}
