package io.github.dr0idbot.mewspace.apiclient.countries.model;

import java.util.List;

public record Country(
		String name,
		String alpha2Code,
		String alpha3Code,
		String numericCode,
		String cioc,
		String capital,
		String region,
		String subregion,
		String demonym,
		String nativeName,
		Boolean independent,
		String flag,
		Flag flags,
		List<Double> latlng,
		List<String> topLevelDomain,
		List<String> callingCodes,
		List<String> timezones,
		List<String> borders,
		List<Currency> currencies,
		List<Language> languages,
		Long population,
		Double populationDensity,
		Double area,
		Double gini) {

	public record Flag(String png, String svg) {
	}
}
