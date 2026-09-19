package io.github.dr0idbot.vspace.apiclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.dr0idbot.vspace.apiclient.exception.VSpaceRestClientException;

public abstract class BaseApiClient {

	private final Logger log = LoggerFactory.getLogger(getClass());
	private final RestClient restClient;

	protected BaseApiClient(RestClient.Builder restClientBuilder, String baseUrl) {
		this.restClient = restClientBuilder.baseUrl(baseUrl).build();
		log.debug("Connecting to {}", baseUrl);
	}

	protected <T> T get(String endpoint, Class<T> responseType, MultiValueMap<String, String> queryParams)
			throws VSpaceRestClientException {
		log.debug("GET {}?{} -> {}", endpoint, queryParams, responseType.getSimpleName());
		try {
			T result = restClient.get()
					.uri(uriBuilder -> UriComponentsBuilder.fromPath(endpoint)
							.queryParams(queryParams)
							.build()
							.toUri())
					.retrieve()
					.body(responseType);
			log.debug("GET {} connected, response type: {}", endpoint, responseType.getSimpleName());
			return result;
		} catch (RestClientResponseException e) {
			throw new VSpaceRestClientException("GET " + endpoint + " failed: " + e.getStatusCode(), e);
		} catch (Exception e) {
			throw new VSpaceRestClientException("GET " + endpoint + " failed: " + e.getMessage(), e);
		}
	}

	protected <T> T get(String endpoint, Class<T> responseType, Object... uriVariables)
			throws VSpaceRestClientException {
		log.debug("GET {} -> {} with vars {}", endpoint, responseType.getSimpleName(), uriVariables);
		try {
			T result = restClient.get()
					.uri(endpoint, uriVariables)
					.retrieve()
					.body(responseType);
			log.debug("GET {} connected, response type: {}", endpoint, responseType.getSimpleName());
			return result;
		} catch (RestClientResponseException e) {
			throw new VSpaceRestClientException("GET " + endpoint + " failed: " + e.getStatusCode(), e);
		} catch (Exception e) {
			throw new VSpaceRestClientException("GET " + endpoint + " failed: " + e.getMessage(), e);
		}
	}
}
