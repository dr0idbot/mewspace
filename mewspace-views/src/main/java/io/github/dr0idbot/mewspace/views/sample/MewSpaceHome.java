package io.github.dr0idbot.mewspace.views.sample;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import io.github.dr0idbot.mewspace.apiclient.countries.model.Country;
import io.github.dr0idbot.mewspace.components.UIDialog;
import io.github.dr0idbot.mewspace.service.countries.CountriesService;
import io.github.dr0idbot.mewspace.service.exception.UIException;
import io.github.dr0idbot.mewspace.views.constant.MewSpaceViewsConstant;

@Route("")
@PageTitle("Countries")
@Menu(title = "Countries", order = 1, icon = "lucide/earth.svg")
public class MewSpaceHome extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public MewSpaceHome(CountriesService countriesService) {

		var countriesGrid = new Grid<Country>(Country.class, false);

		countriesGrid.addColumn(Country::name).setHeader(MewSpaceViewsConstant.NAME).setSortable(true).setFrozen(true)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(Country::alpha2Code).setHeader(MewSpaceViewsConstant.ALPHA_2_CODE).setSortable(true)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(Country::alpha3Code).setHeader(MewSpaceViewsConstant.ALPHA_3_CODE).setSortable(true)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(Country::numericCode).setHeader(MewSpaceViewsConstant.NUMERIC_CODE).setResizable(true)
				.setAutoWidth(true);
		countriesGrid.addColumn(Country::capital).setHeader(MewSpaceViewsConstant.CAPITAL).setSortable(true)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(Country::region).setHeader(MewSpaceViewsConstant.REGION).setSortable(true)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(Country::subregion).setHeader(MewSpaceViewsConstant.SUBREGION).setSortable(true)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(Country::demonym).setHeader(MewSpaceViewsConstant.DEMONYM).setSortable(true)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(Country::nativeName).setHeader(MewSpaceViewsConstant.NATIVE_NAME).setResizable(true)
				.setAutoWidth(true);
		countriesGrid.addColumn(Country::flag).setHeader(MewSpaceViewsConstant.FLAG).setResizable(true)
				.setAutoWidth(true);
		countriesGrid.addColumn(Country::cioc).setHeader(MewSpaceViewsConstant.CIOC).setResizable(true)
				.setAutoWidth(true);
		countriesGrid.addColumn(Country::independent).setHeader(MewSpaceViewsConstant.INDEPENDENT).setSortable(true)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(Country::population).setHeader(MewSpaceViewsConstant.POPULATION).setSortable(true)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(Country::populationDensity).setHeader(MewSpaceViewsConstant.POPULATION_DENSITY)
				.setSortable(true).setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(Country::area).setHeader(MewSpaceViewsConstant.AREA).setSortable(true).setResizable(true)
				.setAutoWidth(true);
		countriesGrid.addColumn(Country::gini).setHeader(MewSpaceViewsConstant.GINI).setSortable(true).setResizable(true)
				.setAutoWidth(true);
		countriesGrid.addColumn(c -> String.valueOf(c.latlng())).setHeader(MewSpaceViewsConstant.LATLNG)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(c -> String.valueOf(c.topLevelDomain()))
				.setHeader(MewSpaceViewsConstant.TOP_LEVEL_DOMAINS).setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(c -> String.valueOf(c.callingCodes())).setHeader(MewSpaceViewsConstant.CALLING_CODES)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(c -> String.valueOf(c.timezones())).setHeader(MewSpaceViewsConstant.TIMEZONES)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(c -> String.valueOf(c.borders())).setHeader(MewSpaceViewsConstant.BORDERS)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(c -> String.valueOf(c.currencies())).setHeader(MewSpaceViewsConstant.CURRENCIES)
				.setResizable(true).setAutoWidth(true);
		countriesGrid.addColumn(c -> String.valueOf(c.languages())).setHeader(MewSpaceViewsConstant.LANGUAGES)
				.setResizable(true).setAutoWidth(true);

		try {
			countriesGrid.setItems(countriesService.getCountries());
		} catch (UIException e) {
			UIDialog.show(this, "Could not load countries", e);
		}

		setHeightFull();
		add(countriesGrid);
	}

}
