package io.github.dr0idbot.mewspace.apiclient.countries;

import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import io.github.dr0idbot.mewspace.apiclient.BaseApiClient;
import io.github.dr0idbot.mewspace.apiclient.countries.model.City;
import io.github.dr0idbot.mewspace.apiclient.countries.model.Country;
import io.github.dr0idbot.mewspace.apiclient.countries.model.Currency;
import io.github.dr0idbot.mewspace.apiclient.countries.model.DistanceResult;
import io.github.dr0idbot.mewspace.apiclient.countries.model.IpLookup;
import io.github.dr0idbot.mewspace.apiclient.countries.model.Language;
import io.github.dr0idbot.mewspace.apiclient.countries.model.PostalResult;
import io.github.dr0idbot.mewspace.apiclient.exception.MewSpaceRestClientException;

@Service
@EnableConfigurationProperties(CountriesProperties.class)
public class CountriesRestClient extends BaseApiClient {

	static final String ENDPOINT_ALPHA = "/alpha/{code}";
	static final String ENDPOINT_NUMERIC_CODE = "/numericcode/{code}";
	static final String ENDPOINT_CIOC = "/cioc/{code}";
	static final String ENDPOINT_RANDOM = "/random";
	static final String ENDPOINT_NAME = "/name/{name}";
	static final String ENDPOINT_COUNTRIES = "/countries";
	static final String ENDPOINT_REGION = "/region/{region}";
	static final String ENDPOINT_SUBREGION = "/subregion/{subregion}";
	static final String ENDPOINT_CAPITAL = "/capital/{capital}";
	static final String ENDPOINT_CURRENCY = "/currency/{currency}";
	static final String ENDPOINT_LANG = "/lang/{lang}";
	static final String ENDPOINT_CALLING_CODE = "/callingcode/{code}";
	static final String ENDPOINT_TIMEZONE = "/timezone/{tz}";
	static final String ENDPOINT_TLD = "/tld/{tld}";
	static final String ENDPOINT_DEMONYM = "/demonym/{demonym}";
	static final String ENDPOINT_POPULATION = "/population/{min},{max}";
	static final String ENDPOINT_AREA = "/area/{min},{max}";
	static final String ENDPOINT_INDEPENDENT = "/independent/{independent}";
	static final String ENDPOINT_BORDERS = "/borders/{country}";
	static final String ENDPOINT_REGIONS = "/regions";
	static final String ENDPOINT_SUBREGIONS = "/subregions";
	static final String ENDPOINT_CURRENCIES = "/currencies";
	static final String ENDPOINT_LANGUAGES = "/languages";
	static final String ENDPOINT_TIMEZONES = "/timezones";
	static final String ENDPOINT_CITIES = "/cities";
	static final String ENDPOINT_CITY = "/cities/{geonameId}";
	static final String ENDPOINT_CITIES_NEAR = "/cities/near";
	static final String ENDPOINT_DISTANCE = "/distance";
	static final String ENDPOINT_POSTAL = "/postal/{country}/{code}";
	static final String ENDPOINT_IP = "/ip/{ip}";

	private static final ParameterizedTypeReference<List<Country>> COUNTRY_LIST = new ParameterizedTypeReference<>() {
	};
	private static final ParameterizedTypeReference<List<City>> CITY_LIST = new ParameterizedTypeReference<>() {
	};
	private static final ParameterizedTypeReference<List<PostalResult>> POSTAL_LIST = new ParameterizedTypeReference<>() {
	};
	private static final ParameterizedTypeReference<List<String>> STRING_LIST = new ParameterizedTypeReference<>() {
	};
	private static final ParameterizedTypeReference<List<Currency>> CURRENCY_LIST = new ParameterizedTypeReference<>() {
	};
	private static final ParameterizedTypeReference<List<Language>> LANGUAGE_LIST = new ParameterizedTypeReference<>() {
	};

	public CountriesRestClient(RestClient.Builder restClientBuilder, CountriesProperties properties) {
		super(restClientBuilder, properties.baseUrl());
	}

	public Country getCountryByAlpha(String code) throws MewSpaceRestClientException {
		return get(ENDPOINT_ALPHA, Country.class, code);
	}

	public Country getCountryByNumericCode(String code) throws MewSpaceRestClientException {
		return get(ENDPOINT_NUMERIC_CODE, Country.class, code);
	}

	public Country getCountryByCioc(String code) throws MewSpaceRestClientException {
		return get(ENDPOINT_CIOC, Country.class, code);
	}

	public Country getRandomCountry() throws MewSpaceRestClientException {
		return get(ENDPOINT_RANDOM, Country.class);
	}

	public List<Country> searchCountriesByName(String name) throws MewSpaceRestClientException {
		return get(ENDPOINT_NAME, COUNTRY_LIST, name);
	}

	public List<Country> getCountries() throws MewSpaceRestClientException {
		return get(ENDPOINT_COUNTRIES, COUNTRY_LIST, new LinkedMultiValueMap<>());
	}

	public List<Country> getCountries(int limit, int offset) throws MewSpaceRestClientException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("limit", String.valueOf(limit));
		params.add("offset", String.valueOf(offset));
		return get(ENDPOINT_COUNTRIES, COUNTRY_LIST, params);
	}

	public List<Country> getCountriesByRegion(String region) throws MewSpaceRestClientException {
		return get(ENDPOINT_REGION, COUNTRY_LIST, region);
	}

	public List<Country> getCountriesBySubregion(String subregion) throws MewSpaceRestClientException {
		return get(ENDPOINT_SUBREGION, COUNTRY_LIST, subregion);
	}

	public List<Country> getCountriesByCapital(String capital) throws MewSpaceRestClientException {
		return get(ENDPOINT_CAPITAL, COUNTRY_LIST, capital);
	}

	public List<Country> getCountriesByCurrency(String currency) throws MewSpaceRestClientException {
		return get(ENDPOINT_CURRENCY, COUNTRY_LIST, currency);
	}

	public List<Country> getCountriesByLanguage(String language) throws MewSpaceRestClientException {
		return get(ENDPOINT_LANG, COUNTRY_LIST, language);
	}

	public List<Country> getCountriesByCallingCode(String callingCode) throws MewSpaceRestClientException {
		return get(ENDPOINT_CALLING_CODE, COUNTRY_LIST, callingCode);
	}

	public List<Country> getCountriesByTimezone(String timezone) throws MewSpaceRestClientException {
		return get(ENDPOINT_TIMEZONE, COUNTRY_LIST, timezone);
	}

	public List<Country> getCountriesByTopLevelDomain(String tld) throws MewSpaceRestClientException {
		return get(ENDPOINT_TLD, COUNTRY_LIST, tld);
	}

	public List<Country> getCountriesByDemonym(String demonym) throws MewSpaceRestClientException {
		return get(ENDPOINT_DEMONYM, COUNTRY_LIST, demonym);
	}

	public List<Country> getCountriesByPopulationRange(long min, long max) throws MewSpaceRestClientException {
		return get(ENDPOINT_POPULATION, COUNTRY_LIST, min, max);
	}

	public List<Country> getCountriesByAreaRange(double min, double max) throws MewSpaceRestClientException {
		return get(ENDPOINT_AREA, COUNTRY_LIST, min, max);
	}

	public List<Country> getCountriesByIndependent(boolean independent) throws MewSpaceRestClientException {
		return get(ENDPOINT_INDEPENDENT, COUNTRY_LIST, independent);
	}

	public List<Country> getBorders(String countryName) throws MewSpaceRestClientException {
		return get(ENDPOINT_BORDERS, COUNTRY_LIST, countryName);
	}

	public List<String> getRegions() throws MewSpaceRestClientException {
		return get(ENDPOINT_REGIONS, STRING_LIST, new LinkedMultiValueMap<>());
	}

	public List<String> getSubregions() throws MewSpaceRestClientException {
		return get(ENDPOINT_SUBREGIONS, STRING_LIST, new LinkedMultiValueMap<>());
	}

	public List<Currency> getCurrencies() throws MewSpaceRestClientException {
		return get(ENDPOINT_CURRENCIES, CURRENCY_LIST, new LinkedMultiValueMap<>());
	}

	public List<Language> getLanguages() throws MewSpaceRestClientException {
		return get(ENDPOINT_LANGUAGES, LANGUAGE_LIST, new LinkedMultiValueMap<>());
	}

	public List<String> getTimezones() throws MewSpaceRestClientException {
		return get(ENDPOINT_TIMEZONES, STRING_LIST, new LinkedMultiValueMap<>());
	}

	public List<City> searchCities(String query, String countryCode, Integer limit)
			throws MewSpaceRestClientException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		if (query != null && !query.isBlank()) {
			params.add("q", query);
		}
		if (countryCode != null && !countryCode.isBlank()) {
			params.add("country", countryCode);
		}
		if (limit != null) {
			params.add("limit", String.valueOf(limit));
		}
		return get(ENDPOINT_CITIES, CITY_LIST, params);
	}

	public City getCity(long geonameId) throws MewSpaceRestClientException {
		return get(ENDPOINT_CITY, City.class, geonameId);
	}

	public List<City> getNearbyCities(double lat, double lng) throws MewSpaceRestClientException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("lat", String.valueOf(lat));
		params.add("lng", String.valueOf(lng));
		return get(ENDPOINT_CITIES_NEAR, CITY_LIST, params);
	}

	public DistanceResult getDistance(double lat1, double lng1, double lat2, double lng2)
			throws MewSpaceRestClientException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("lat1", String.valueOf(lat1));
		params.add("lng1", String.valueOf(lng1));
		params.add("lat2", String.valueOf(lat2));
		params.add("lng2", String.valueOf(lng2));
		return get(ENDPOINT_DISTANCE, DistanceResult.class, params);
	}

	public DistanceResult getDistance(long fromGeonameId, long toGeonameId) throws MewSpaceRestClientException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("from", String.valueOf(fromGeonameId));
		params.add("to", String.valueOf(toGeonameId));
		return get(ENDPOINT_DISTANCE, DistanceResult.class, params);
	}

	public List<PostalResult> lookupPostal(String countryCode, String postalCode)
			throws MewSpaceRestClientException {
		return get(ENDPOINT_POSTAL, POSTAL_LIST, countryCode, postalCode);
	}

	public IpLookup geolocateIp(String ip) throws MewSpaceRestClientException {
		return get(ENDPOINT_IP, IpLookup.class, ip);
	}
}
