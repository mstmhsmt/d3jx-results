package org.springframework.boot.actuate.autoconfigure.logging;

import org.springframework.boot.actuate.logging.LogFileWebEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.logging.LogFile;

@EnableConfigurationProperties(LogFileWebEndpointProperties.class)
@Configuration(proxyBeanMethods = false)
@ConditionalOnEnabledEndpoint(endpoint = LogFileWebEndpoint.class)
public class LogFileWebEndpointAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @Conditional(LogFileCondition.class)
    public LogFileWebEndpoint logFileWebEndpoint(Environment environment, LogFileWebEndpointProperties properties) {
        return new LogFileWebEndpoint(environment, properties.getExternalFile());
    }

    private static class LogFileCondition extends SpringBootCondition {

        @Override
        @SuppressWarnings("deprecation")
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment environment = context.getEnvironment();
            String config = getLogFileConfig(environment, LogFile.FILE_NAME_PROPERTY, LogFile.FILE_PROPERTY);
            ConditionMessage.Builder message = ConditionMessage.forCondition("Log File");
            if (StringUtils.hasText(config)) {
                return ConditionOutcome.match(message.found(LogFile.FILE_NAME_PROPERTY).items(config));
            }
            config = getLogFileConfig(environment, LogFile.FILE_PATH_PROPERTY, LogFile.PATH_PROPERTY);
            if (StringUtils.hasText(config)) {
                return ConditionOutcome.match(message.found(LogFile.FILE_PATH_PROPERTY).items(config));
            }
            config = environment.getProperty("management.endpoint.logfile.external-file");
            if (StringUtils.hasText(config)) {
                return ConditionOutcome.match(message.found("management.endpoint.logfile.external-file").items(config));
            }
            return ConditionOutcome.noMatch(message.didNotFind("logging file").atAll());
        }

        private String getLogFileConfig(Environment environment, String configName, String deprecatedConfigName) {
            String config = environment.resolvePlaceholders("${" + configName + ":}");
            if (StringUtils.hasText(config)) {
                return config;
            }
            return environment.resolvePlaceholders("${" + deprecatedConfigName + ":}");
        }
    }
}
