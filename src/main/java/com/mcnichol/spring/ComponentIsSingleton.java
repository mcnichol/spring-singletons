package com.mcnichol.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ComponentIsSingleton {

    private String stringValue;

    String getStringValue() {
        return stringValue;
    }

    void setStringValue(final String stringValue) {
        this.stringValue = stringValue;
    }
}
