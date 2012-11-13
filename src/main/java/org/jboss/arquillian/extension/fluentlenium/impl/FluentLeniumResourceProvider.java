package org.jboss.arquillian.extension.fluentlenium.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.drone.impl.DroneContext;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.test.spi.enricher.resource.ResourceProvider;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class FluentLeniumResourceProvider implements ResourceProvider {

    @Inject
    private Instance<DroneContext> droneContext;

    public boolean canProvide(Class<?> type) {
        return FluentLenium.class.isAssignableFrom(type);
    }

    public Object lookup(final ArquillianResource annotation, final Annotation... qualifiers) {
        WebDriver driver = null;
        try {
            driver = (WebDriver) Proxy.newProxyInstance(FluentLenium.class.getClassLoader(), new Class<?>[]{WebDriver.class, JavascriptExecutor.class}, new InvocationHandler() {
                private WebDriver driver;

                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return method.invoke(loadDriver(), args);
                }

                private WebDriver loadDriver() {
                    if (driver == null) {
                        for (Annotation q : qualifiers) {
                            driver = droneContext.get().get(WebDriver.class, q.annotationType());
                            if (driver != null) {
                                break;
                            }
                        }
                        if (driver == null) {
                            driver = droneContext.get().get(WebDriver.class);
                            if (driver == null) {
                                throw new IllegalStateException("Driver hasn't been initialized yet.");
                            }
                        }
                    }
                    return driver;
                }
            });

        } catch (Exception e) {
            throw new IllegalStateException("Can't create an instance of " + FluentLenium.class.getName(), e);
        }
        return new FluentLenium(driver);
    }
}
