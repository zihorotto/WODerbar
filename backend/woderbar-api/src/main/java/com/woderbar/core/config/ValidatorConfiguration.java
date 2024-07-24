package com.woderbar.core.config;

import com.woderbar.core.util.YamlPropertiesLoader;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
public class ValidatorConfiguration {

    public static final Locale DEFAULT_LOCALE = new Locale("en", "EN");

    @Bean
    public LocaleResolver localeResolver() {
        return new HeaderLocaleResolver(DEFAULT_LOCALE);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setPropertiesPersister(new YamlPropertiesLoader());
        messageSource.setFileExtensions(List.of(".yml", ".yaml"));
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(LocalValidatorFactoryBean validator) {
        var methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator.getValidator()); // ez nem biztos, hogy jó a getValidator
        return methodValidationPostProcessor;
    }

}
