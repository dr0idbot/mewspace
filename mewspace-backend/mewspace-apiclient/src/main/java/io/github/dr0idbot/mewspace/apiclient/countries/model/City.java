package io.github.dr0idbot.mewspace.apiclient.countries.model;

public record City(
		Long geonameId,
		String name,
		String asciiName,
		String countryCode,
		String admin1Code,
		Double latitude,
		Double longitude,
		Long population,
		String timezone,
		String featureCode,
		Double distanceKm) {
}
