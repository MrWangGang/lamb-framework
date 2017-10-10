package org.lamb.lambframework.core.listener;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("throw")
@PropertySource("classpath:lambframework-core_exception_message.properties")
public class ErrorMessageListener {

    public static Map<String,String> exception = new HashMap<String, String>();

    public  Map<String, String> getException() {
        return exception;
    }

    public  void setException(Map<String, String> exception) {
        this.exception = exception;
    }
}
