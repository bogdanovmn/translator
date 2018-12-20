package com.github.bogdanovmn.translator.web.app.infrastructure.config;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TomcatConfig {
	@Bean
	public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {

		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

		tomcat.setSessionTimeout(48, TimeUnit.HOURS);

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