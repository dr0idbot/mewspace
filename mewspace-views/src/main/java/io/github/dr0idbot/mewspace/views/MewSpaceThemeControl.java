package io.github.dr0idbot.mewspace.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.AnchorTarget;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.page.ColorScheme.Value;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.aura.Aura;
import com.vaadin.flow.theme.lumo.Lumo;

import io.github.dr0idbot.vlucide.LucideIcon;

public class MewSpaceThemeControl extends Div {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(MewSpaceThemeControl.class);
	private static final String LOG_PREFIX = "[THEME-CONTROL] ";

	private static final String THEME_BASE = "Base";
	private static final String THEME_LUMO = "Lumo";
	private static final String THEME_AURA = "Aura";
	private static final String SOURCE_URL = "https://github.com/dr0idbot/mewspace";

	private Registration styleSheetRegistration;
	private boolean defaultsApplied;

	public MewSpaceThemeControl() {
		MenuBar menuBar = new MenuBar();

		MenuItem colorSchemeItem = menuBar.addItem(LucideIcon.MOON.create());
		colorSchemeItem.addClickListener(e -> eventColorSchemeChange(e.getSource()));

		MenuItem themeItem = menuBar.addItem(THEME_AURA);

		SubMenu themeSubMenu = themeItem.getSubMenu();
		themeSubMenu.addItem(THEME_LUMO, e -> applyTheme(themeItem, e.getUI(), THEME_LUMO));
		themeSubMenu.addItem(THEME_AURA, e -> applyTheme(themeItem, e.getUI(), THEME_AURA));
		themeSubMenu.addItem(THEME_BASE, e -> applyTheme(themeItem, e.getUI(), THEME_BASE));

		var anchor = new Anchor(SOURCE_URL, LucideIcon.CODE.create());
		anchor.setTarget(AnchorTarget.BLANK);
		menuBar.addItem(anchor);

		add(menuBar);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		if (defaultsApplied) {
			return;
		}
		defaultsApplied = true;
		UI ui = attachEvent.getUI();
		styleSheetRegistration = ui.getPage().addStyleSheet(Aura.STYLESHEET);
		ui.getPage().setColorScheme(Value.LIGHT);
		log.info("{}Default theme {} with light color scheme applied", LOG_PREFIX, THEME_AURA);
	}

	private void applyTheme(MenuItem themeItem, UI ui, String themeName) {
		if (styleSheetRegistration != null) {
			styleSheetRegistration.remove();
			styleSheetRegistration = null;
			log.info("{}Removed previously applied theme stylesheet", LOG_PREFIX);
		}
		switch (themeName) {
		case THEME_LUMO -> styleSheetRegistration = ui.getPage().addStyleSheet(Lumo.STYLESHEET);
		case THEME_AURA -> styleSheetRegistration = ui.getPage().addStyleSheet(Aura.STYLESHEET);
		default -> {
		}
		}
		themeItem.setText(themeName);
		log.info("{}Theme changed to {}", LOG_PREFIX, themeName);
	}

	private void eventColorSchemeChange(MenuItem menuItem) {
		UI ui = menuItem.getUI().orElseThrow();
		Value colorScheme = ui.getPage().getColorScheme();
		Value newColorScheme = Value.DARK.equals(colorScheme) ? Value.LIGHT : Value.DARK;
		ui.getPage().setColorScheme(newColorScheme);
		menuItem.removeAll();
		menuItem.add(Value.DARK.equals(newColorScheme) ? LucideIcon.MOON.create() : LucideIcon.SUN.create());
		log.info("{}Color scheme changed from {} to {}", LOG_PREFIX, colorScheme, newColorScheme);
	}

}
