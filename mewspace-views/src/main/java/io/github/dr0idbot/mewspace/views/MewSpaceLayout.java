package io.github.dr0idbot.mewspace.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.ScrollerVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;

import io.github.dr0idbot.mewspace.constants.MewSpaceCSSConst;

@Layout
public final class MewSpaceLayout extends AppLayout {

	private static final long serialVersionUID = 1L;

	MewSpaceLayout() {
		setPrimarySection(Section.DRAWER);
		addToDrawer(createApplicationHeader(), createApplicationDrawer(), createApplicationFooter());
	}

	private Component createApplicationHeader() {
		var appLogo = new Avatar("MewSpace");
		appLogo.addClassName(MewSpaceCSSConst.APP_LOGO);
		appLogo.addThemeVariants(AvatarVariant.XSMALL);

		var appName = new Span("MewSpace");
		appName.addClassName(MewSpaceCSSConst.APP_NAME);

		var header = new HorizontalLayout(appLogo, appName);
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		header.setPadding(true);
		return header;
	}

	private Component createApplicationDrawer() {
		var scroller = new Scroller(createSideNav());
		scroller.addThemeVariants(ScrollerVariant.OVERFLOW_INDICATORS);
		return scroller;
	}

	private Component createApplicationFooter() {
		var footer = new VerticalLayout(new Span("Made with \u2764\uFE0F with Vaadin"));
		footer.setAlignItems(FlexComponent.Alignment.CENTER);
		footer.addClassName(MewSpaceCSSConst.APP_FOOTER);
		return footer;
	}

	private SideNav createSideNav() {
		var nav = new SideNav();
		nav.setMinWidth(200, Unit.PIXELS);
		MenuConfiguration.getMenuEntries().forEach(entry -> nav.addItem(createSideNavItem(entry)));
		return nav;
	}

	private SideNavItem createSideNavItem(MenuEntry menuEntry) {
		if (menuEntry.icon() != null) {
			Component icon = null;
			if (menuEntry.icon().contains(".svg")) {
				icon = new SvgIcon(menuEntry.icon());
			} else {
				icon = new Icon(menuEntry.icon());
			}
			return new SideNavItem(menuEntry.title(), menuEntry.menuClass(), icon);
		} else {
			return new SideNavItem(menuEntry.title(), menuEntry.menuClass());
		}
	}
}
