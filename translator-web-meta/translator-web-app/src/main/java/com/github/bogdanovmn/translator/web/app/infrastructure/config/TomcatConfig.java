package com.github.bogdanovmn.translator.web.app.infrastructure.config;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TomcatConfig {
	@Bean
	public TomcatServletWebServerFactory tomcatEmbedded() {

		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();

		tomcat.addInitializers(servletContext -> servletContext.setSessionTimeout(5000));

		tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {

			// connector other settings...

			// configure maxSwallowSize
			if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
				// -1 means unlimited, accept bytes
//				((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
				((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(100 * 1024*1024);
			}

		});

		return tomcat;

	}
}