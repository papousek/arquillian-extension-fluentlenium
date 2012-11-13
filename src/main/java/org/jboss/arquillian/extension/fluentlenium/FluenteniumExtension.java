package org.jboss.arquillian.extension.fluentlenium;

import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.extension.fluentlenium.impl.FluentLeniumResourceProvider;
import org.jboss.arquillian.test.spi.enricher.resource.ResourceProvider;

public class FluenteniumExtension implements LoadableExtension {

    public void register(ExtensionBuilder builder) {
        builder.service(ResourceProvider.class, FluentLeniumResourceProvider.class);
    }
}
