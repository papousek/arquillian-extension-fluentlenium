package org.arquillian.extension.fluentlenium.ftest;

import junit.framework.Assert;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.extension.fluentlenium.impl.FluentLenium;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
public class FluentLeniumTestCase {

    @Drone
    private WebDriver driver;

    @ArquillianResource
    private FluentLenium fluentLenium;

    @Test
    public void testNotNull() {
        Assert.assertNotNull(fluentLenium);
    }

    @Test
    public void testGoToAndTitle() {
        fluentLenium.goTo(getClass().getClassLoader().getResource("org/jboss/arquillian/extension/fluentlenium/simple.html").toString());
        Assert.assertEquals("Simple Page", fluentLenium.title());
    }

}
