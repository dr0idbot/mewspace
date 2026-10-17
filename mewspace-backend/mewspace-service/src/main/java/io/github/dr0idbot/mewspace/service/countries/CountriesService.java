package io.github.dr0idbot.mewspace.service.countries;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import io.github.dr0idbot.mewspace.apiclient.countries.CountriesRestClient;
import io.github.dr0idbot.mewspace.apiclient.countries.model.City;
import io.github.dr0idbot.mewspace.apiclient.countries.model.Country;
import io.github.dr0idbot.mewspace.apiclient.countries.model.Currency;
import io.github.dr0idbot.mewspace.apiclient.countries.model.DistanceResult;
import io.github.dr0idbot.mewspace.apiclient.countries.model.IpLookup;
import io.github.dr0idbot.mewspace.apiclient.countries.model.Language;
import io.github.dr0idbot.mewspace.apiclient.countries.model.PostalResult;
import io.github.dr0idbot.mewspace.service.exception.UIException;

@Service
public class CountriesService {

	private static final String MSG_COUNTRY_DETAILS = "Could not load the country details.";
	private static final String MSG_RANDOM_COUNTRY = "Could not load a random country.";
	private static final String MSG_COUNTRY_LIST = "Could not load the list of countries.";
	private static final String MSG_NEIGHBOURS = "Could not load the neighbouring countries.";
	private static final String MSG_REFERENCE = "Could not load the reference data.";
	private static final String MSG_CITIES = "Could not load the city data.";
	private static final String MSG_DISTANCE = "Could not calculate the distance.";
	private static final String MSG_POSTAL = "Could not look up the postal code.";
	private static final String MSG_IP = "Could not determine the location for that IP address.";
	private static final String MSG_NOT_FOUND = "The requested data was not found.";

	private final CountriesRestClient countriesRestClient;

	public CountriesService(CountriesRestClient countriesRestClient) {
		this.countriesRestClient = countriesRestClient;
	}

	public Country getCountryByAlpha(String code) throws UIException {
		return execute(MSG_COUNTRY_DETAILS, () -> countriesRestClient.getCountryByAlpha(code));
	}

	public Country getCountryByNumericCode(String code) throws UIException {
		return execute(MSG_COUNTRY_DETAILS, () -> countriesRestClient.getCountryByNumericCode(code));
	}

	public Country getCountryByCioc(String code) throws UIException {
		return execute(MSG_COUNTRY_DETAILS, () -> countriesRestClient.getCountryByCioc(code));
	}

	public Country getRandomCountry() throws UIException {
		return execute(MSG_RANDOM_COUNTRY, () -> countriesRestClient.getRandomCountry());
	}

	public List<Country> searchCountriesByName(String name) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.searchCountriesByName(name));
	}

	public List<Country> getCountries() throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountries());
	}

	public List<Country> getCountries(int limit, int offset) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountries(limit, offset));
	}

	public List<Country> getCountriesByRegion(String region) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountriesByRegion(region));
	}

	public List<Country> getCountriesBySubregion(String subregion) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountriesBySubregion(subregion));
	}

	public List<Country> getCountriesByCapital(String capital) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountriesByCapital(capital));
	}

	public List<Country> getCountriesByCurrency(String currency) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountriesByCurrency(currency));
	}

	public List<Country> getCountriesByLanguage(String language) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountriesByLanguage(language));
	}

	public List<Country> getCountriesByCallingCode(String callingCode) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountriesByCallingCode(callingCode));
	}

	public List<Country> getCountriesByTimezone(String timezone) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountriesByTimezone(timezone));
	}

	public List<Country> getCountriesByTopLevelDomain(String tld) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountriesByTopLevelDomain(tld));
	}

	public List<Country> getCountriesByDemonym(String demonym) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountriesByDemonym(demonym));
	}

	public List<Country> getCountriesByPopulationRange(long min, long max) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountriesByPopulationRange(min, max));
	}

	public List<Country> getCountriesByAreaRange(double min, double max) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountriesByAreaRange(min, max));
	}

	public List<Country> getCountriesByIndependent(boolean independent) throws UIException {
		return execute(MSG_COUNTRY_LIST, () -> countriesRestClient.getCountriesByIndependent(independent));
	}

	public List<Country> getBorders(String countryName) throws UIException {
		return execute(MSG_NEIGHBOURS, () -> countriesRestClient.getBorders(countryName));
	}

	public List<String> getRegions() throws UIException {
		return execute(MSG_REFERENCE, () -> countriesRestClient.getRegions());
	}

	public List<String> getSubregions() throws UIException {
		return execute(MSG_REFERENCE, () -> countriesRestClient.getSubregions());
	}

	public List<Currency> getCurrencies() throws UIException {
		return execute(MSG_REFERENCE, () -> countriesRestClient.getCurrencies());
	}

	public List<Language> getLanguages() throws UIException {
		return execute(MSG_REFERENCE, () -> countriesRestClient.getLanguages());
	}

	public List<String> getTimezones() throws UIException {
		return execute(MSG_REFERENCE, () -> countriesRestClient.getTimezones());
	}

	public List<City> searchCities(String query, String countryCode, Integer limit) throws UIException {
		return execute(MSG_CITIES, () -> countriesRestClient.searchCities(query, countryCode, limit));
	}

	public City getCity(long geonameId) throws UIException {
		return execute(MSG_CITIES, () -> countriesRestClient.getCity(geonameId));
	}

	public List<City> getNearbyCities(double lat, double lng) throws UIException {
		return execute(MSG_CITIES, () -> countriesRestClient.getNearbyCities(lat, lng));
	}

	public DistanceResult getDistance(double lat1, double lng1, double lat2, double lng2) throws UIException {
		return execute(MSG_DISTANCE, () -> countriesRestClient.getDistance(lat1, lng1, lat2, lng2));
	}

	public DistanceResult getDistance(long fromGeonameId, long toGeonameId) throws UIException {
		return execute(MSG_DISTANCE, () -> countriesRestClient.getDistance(fromGeonameId, toGeonameId));
	}

	public List<PostalResult> lookupPostal(String countryCode, String postalCode) throws UIException {
		return execute(MSG_POSTAL, () -> countriesRestClient.lookupPostal(countryCode, postalCode));
	}

	public IpLookup geolocateIp(String ip) throws UIException {
		return execute(MSG_IP, () -> countriesRestClient.geolocateIp(ip));
	}

	private <T> T execute(String message, Callable<T> action) throws UIException {
		try {
			return action.call();
		} catch (Exception e) {
			String friendly = isNotFound(e) ? MSG_NOT_FOUND : message;
			String desc = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
			throw new UIException(friendly, desc, e);
		}
	}

	private static boolean isNotFound(Throwable e) {
		Throwable cause = e;
		while (cause != null) {
			if (cause instanceof RestClientResponseException responseException
					&& responseException.getStatusCode().value() == 404) {
				return true;
			}
			cause = cause.getCause();
		}
		return false;
	}
}
