package io.github.dr0idbot.mewspace.apiclient.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mewspace.api.client")
public record RestClientProperties(
		Duration connectTimeout,
		Duration readTimeout) {

	public static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofSeconds(5);
	public static final Duration DEFAULT_READ_TIMEOUT = Duration.ofSeconds(10);

	public RestClientProperties {
		connectTimeout = connectTimeout != null ? connectTimeout : DEFAULT_CONNECT_TIMEOUT;
		readTimeout = readTimeout != null ? readTimeout : DEFAULT_READ_TIMEOUT;
	}
}
