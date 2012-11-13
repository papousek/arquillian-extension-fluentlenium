package org.jboss.arquillian.extension.fluentlenium.impl;

import org.fluentlenium.core.FluentAdapter;
import org.openqa.selenium.WebDriver;

public class FluentLenium extends FluentAdapter {

    public FluentLenium() {
        initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
        initTest();
    }

    public FluentLenium(WebDriver webDriver) {
        initFluent(webDriver).withDefaultUrl(getDefaultBaseUrl());
        initTest();
    }

    @Override
    public void quit() {
        throw new UnsupportedOperationException("TODO");
    }
}
