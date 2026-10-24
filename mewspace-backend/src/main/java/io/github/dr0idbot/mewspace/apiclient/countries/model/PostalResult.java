package io.github.dr0idbot.mewspace.apiclient.countries.model;

public record PostalResult(
		String countryCode,
		String postalCode,
		String placeName,
		AdminRegion admin1,
		AdminRegion admin2,
		Double latitude,
		Double longitude,
		String accuracy) {

	public record AdminRegion(String name, String code) {
	}
}
