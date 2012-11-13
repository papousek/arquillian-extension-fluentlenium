package org.jboss.arquillian.extension.fluentlenium.impl;

import java.lang.annotation.Annotation;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.drone.impl.DroneContext;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.test.spi.enricher.resource.ResourceProvider;
import org.openqa.selenium.WebDriver;

public class FluentLeniumResourceProvider implements ResourceProvider {

    @Inject
    private Instance<DroneContext> droneContext;

    public boolean canProvide(Class<?> type) {
        return FluentLenium.class.isAssignableFrom(type);
    }

    public Object lookup(final ArquillianResource annotation, final Annotation... qualifiers) {
        WebDriver driver = getDriver(qualifiers);

        return new FluentLenium(driver);
    }

    private WebDriver getDriver(Annotation... qualifiers) {
        for (Annotation q : qualifiers) {
            WebDriver driver = droneContext.get().get(WebDriver.class, q.annotationType());
            if (driver != null) {
                return driver;
            }
        }
        WebDriver driver = droneContext.get().get(WebDriver.class);
        if (driver == null) {
            throw new IllegalStateException("Driver hasn't been initialized yet.");
        }
        return driver;
    }
}
