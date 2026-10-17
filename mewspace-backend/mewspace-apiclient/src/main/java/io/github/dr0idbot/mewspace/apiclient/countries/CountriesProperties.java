package io.github.dr0idbot.mewspace.apiclient.countries;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mewspace.api.countries")
public record CountriesProperties(String baseUrl) {

	public static final String DEFAULT_BASE_URL = "https://countries.dev";

	public CountriesProperties {
		baseUrl = baseUrl != null && !baseUrl.isBlank() ? baseUrl : DEFAULT_BASE_URL;
	}
}
