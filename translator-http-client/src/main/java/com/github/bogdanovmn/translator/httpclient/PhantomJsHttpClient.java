package com.github.bogdanovmn.translator.httpclient;

import ch.racic.selenium.drivers.PhantomJSDriverHelper;
import ch.racic.selenium.drivers.exceptions.ExecutableNotFoundException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;

public class PhantomJsHttpClient implements HttpClient {
	private final String urlPrefix;
	private PhantomJSDriver webDriver;

	public PhantomJsHttpClient(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	@Override
	public String get(String url) throws IOException {
		PhantomJSDriver browser = this.getWebDriver();
		browser.get(this.urlPrefix + url);

		return browser.getPageSource();
	}

	private PhantomJSDriver getWebDriver() throws IOException {
		if (this.webDriver == null) {
			DesiredCapabilities caps = new DesiredCapabilities();

//			LoggingPreferences logs = new LoggingPreferences();
//			logs.enable(LogType.BROWSER, Level.OFF);
//			logs.enable(LogType.CLIENT, Level.OFF);
//			logs.enable(LogType.DRIVER, Level.OFF);
//			logs.enable(LogType.PERFORMANCE, Level.OFF);
//			logs.enable(LogType.SERVER, Level.OFF);
//			caps.setCapability(CapabilityType.LOGGING_PREFS, logs);

			try {
				PhantomJSDriverService service = new PhantomJSDriverService.Builder()
					.usingPhantomJSExecutable(PhantomJSDriverHelper.executable())
					.usingCommandLineArguments(new String[] {"--webdriver-loglevel=NONE"})
					.withLogFile(null)
					.build();
				webDriver = new PhantomJSDriver(service, caps);
			}
			catch (ExecutableNotFoundException e) {
				throw new IOException(e);
			}
		}
		return this.webDriver;
	}

	@Override
	public void close() throws IOException {
		PhantomJSDriver browser = this.getWebDriver();
		if (browser != null) {
			browser.quit();
		}
	}
}
