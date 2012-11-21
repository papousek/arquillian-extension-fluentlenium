package org.jboss.arquillian.extension.fluentlenium.impl;

import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

public class FluentLenium extends FluentAdapter {

	private boolean managedByDrone = false;
	
    public FluentLenium() {
        initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
        // initTest is injecting page objects into @Page injection points on *this* class
        //initTest();
    }

    public FluentLenium(WebDriver webDriver) {
        initFluent(webDriver).withDefaultUrl(getDefaultBaseUrl());
        managedByDrone = true;
        // initTest is injecting page objects into @Page injection points on *this* class
        //initTest();
    }

    @Override
    public void quit() {
    	if (managedByDrone) {
    		throw new UnsupportedOperationException("Arquillian Drone will handle shutting down this browser instance.");
    	}
    	else {
    		getDriver().quit();
    	}
    }
    
    /**
     * Create an instance of the page, navigate to it, then return the instance
     *
     * @param page
     */
    public <P extends FluentPage> P goToPage(Class<P> pageClass) {
        if (pageClass == null) {
            throw new IllegalArgumentException("Page class is mandatory");
        }
        P page = createPage(pageClass);
        page.go();
        return page;
    }
    
    public boolean isManagedByDrone() {
    	return managedByDrone;
    }
}
