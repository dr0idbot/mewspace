package io.github.dr0idbot.mewspace.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.AnchorTarget;
import com.vaadin.flow.component.html.AnchorTargetValue;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.page.ColorScheme;
import com.vaadin.flow.component.page.ColorScheme.Value;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.aura.Aura;
import com.vaadin.flow.theme.lumo.Lumo;

import io.github.dr0idbot.vlucide.LucideIcon;

public class MewSpaceThemeControl extends Div {

	private static final long serialVersionUID = 1L;
	private Registration styleSheetResgistration;

	public MewSpaceThemeControl() {
		MenuBar menuBar = new MenuBar();

		MenuItem colorSchemeItem = menuBar.addItem(LucideIcon.MOON.create());
		colorSchemeItem.addClickListener(e -> eventColorSchemeChange(e.getSource()));

		MenuItem theme = menuBar.addItem("Base", listener("Base"));

		SubMenu themeSubMenu = theme.getSubMenu();
		themeSubMenu.addItem("Lumo", listener("Lumo"));
		themeSubMenu.addItem("Aura", listener("Aura"));
		themeSubMenu.addItem("Base", listener("Base"));

		var anchor = new Anchor("https://github.com", LucideIcon.CODE.create());
		anchor.setTarget(AnchorTargetValue.forString(AnchorTarget.BLANK.toString()));
		anchor.getElement().getStyle().setTextDecoration("none");
		menuBar.addItem(anchor);

		add(menuBar);
	}

	private ComponentEventListener<ClickEvent<MenuItem>> listener(String string) {
		return e -> {
			if (styleSheetResgistration != null) {
				styleSheetResgistration.remove();
			}

			if (string.equals("Lumo")) {
				styleSheetResgistration = e.getUI().getPage().addStyleSheet(Lumo.STYLESHEET);
			} else if (string.equals("Aura")) {
				e.getUI().getPage().addStyleSheet(Aura.STYLESHEET);
			}
		};
	}

	private void eventColorSchemeChange(MenuItem menuItem) {
		menuItem.removeAll();
		Value colorScheme = UI.getCurrent().getPage().getColorScheme();
		if (colorScheme.equals(ColorScheme.Value.DARK)) {
			menuItem.add(LucideIcon.SUN.create());
			UI.getCurrent().getPage().setColorScheme(ColorScheme.Value.LIGHT);
		} else {
			menuItem.add(LucideIcon.MOON.create());
			UI.getCurrent().getPage().setColorScheme(ColorScheme.Value.DARK);
		}
	}

}
