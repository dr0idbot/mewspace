package io.github.dr0idbot.mewspace.views;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;

import io.github.dr0idbot.mewspace.constants.MewSpaceCSSConstant;
import io.github.dr0idbot.mewspace.views.constant.MewSpaceViewsConstant;
import io.github.dr0idbot.vlucide.LucideIcon;
import io.github.dr0idbot.vlucide.LucideLineCap;
import io.github.dr0idbot.vlucide.LucideLineJoin;
import io.github.dr0idbot.vlucide.LucideSvgIcon;

@Route("lucide-icons")
@PageTitle(MewSpaceViewsConstant.LUCIDE_ICONS_TITLE)
@Menu(title = MewSpaceViewsConstant.LUCIDE_ICONS_TITLE, order = 2, icon = "lucide/gallery.svg")
public class LucideIconGallery extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private static final String COLOR_DEFAULT = "Default";
	private static final double DEFAULT_FONT_SIZE = 40.0;
	private static final double DEFAULT_STROKE_WIDTH = 1.0;
	private static final int PAGE_SIZE = 1600;

	private static final List<Double> STROKE_WIDTHS = List.of(0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 4.0);
	private static final List<String> LINE_CAP_CHOICES = List.of(COLOR_DEFAULT, "butt", "round", "square");
	private static final List<String> LINE_JOIN_CHOICES = List.of(COLOR_DEFAULT, "miter", "bevel");
	private static final Map<String, String> COLORS = createColors();
	private static final Map<String, String> FILL_COLORS = createFillColors();
	private static final String CURRENT_COLOR_LABEL = "Current color";
	private static final String DEFAULT_FILL_LABEL = "None";

	private double fontSize = DEFAULT_FONT_SIZE;
	private double strokeWidth = DEFAULT_STROKE_WIDTH;
	private String color = COLOR_DEFAULT;
	private String fillColor = DEFAULT_FILL_LABEL;
	private String lineCap = COLOR_DEFAULT;
	private String lineJoin = COLOR_DEFAULT;
	private int pageIndex;

	private final List<LucideIcon> filteredIcons = new ArrayList<>();
	private final List<LucideSvgIcon> icons = new ArrayList<>();
	private final Div gallery = new Div();

	private final Span pageLabel = new Span();
	private final Span countLabel = new Span();
	private final Button firstButton = createPageButton(LucideIcon.CHEVRONS_LEFT, MewSpaceViewsConstant.PAGE_FIRST,
			() -> gotoPage(0));
	private final Button previousButton = createPageButton(LucideIcon.CHEVRON_LEFT, MewSpaceViewsConstant.PAGE_PREVIOUS,
			() -> gotoPage(pageIndex - 1));
	private final Button nextButton = createPageButton(LucideIcon.CHEVRON_RIGHT, MewSpaceViewsConstant.PAGE_NEXT,
			() -> gotoPage(pageIndex + 1));
	private final Button lastButton = createPageButton(LucideIcon.CHEVRONS_RIGHT, MewSpaceViewsConstant.PAGE_LAST,
			() -> gotoPage(pageCount() - 1));

	public LucideIconGallery() {
		var fontSizeField = new TextField(MewSpaceViewsConstant.LABEL_FONT_SIZE);
		fontSizeField.setValue(String.valueOf((long) fontSize));
		fontSizeField.addValueChangeListener(e -> {
			var value = e.getValue();
			if (value == null || value.isBlank()) {
				return;
			}
			try {
				fontSize = Double.parseDouble(value.trim());
			} catch (NumberFormatException ex) {
				return;
			}
			applySettings();
		});

		var strokeWidthComboBox = new ComboBox<>(MewSpaceViewsConstant.LABEL_STROKE_WIDTH, STROKE_WIDTHS);
		strokeWidthComboBox.setValue(strokeWidth);
		strokeWidthComboBox.addValueChangeListener(e -> {
			if (e.getValue() != null) {
				strokeWidth = e.getValue();
				applySettings();
			}
		});

		var colorComboBox = new ComboBox<>(MewSpaceViewsConstant.LABEL_COLOR, COLORS.keySet());
		colorComboBox.setValue(color);
		colorComboBox.setAllowCustomValue(true);
		colorComboBox.addCustomValueSetListener(e -> colorComboBox.setValue(e.getDetail()));
		colorComboBox.addValueChangeListener(e -> {
			if (e.getValue() != null) {
				color = e.getValue();
				applySettings();
			}
		});

		var fillComboBox = new ComboBox<>(MewSpaceViewsConstant.LABEL_FILL_COLOR, FILL_COLORS.keySet());
		fillComboBox.setValue(fillColor);
		fillComboBox.setAllowCustomValue(true);
		fillComboBox.addCustomValueSetListener(e -> fillComboBox.setValue(e.getDetail()));
		fillComboBox.addValueChangeListener(e -> {
			if (e.getValue() != null) {
				fillColor = e.getValue();
				applySettings();
			}
		});

		var lineCapComboBox = new ComboBox<>(MewSpaceViewsConstant.LABEL_LINE_CAP, LINE_CAP_CHOICES);
		lineCapComboBox.setValue(lineCap);
		lineCapComboBox.addValueChangeListener(e -> {
			if (e.getValue() != null) {
				lineCap = e.getValue();
				applySettings();
			}
		});

		var lineJoinComboBox = new ComboBox<>(MewSpaceViewsConstant.LABEL_LINE_JOIN, LINE_JOIN_CHOICES);
		lineJoinComboBox.setValue(lineJoin);
		lineJoinComboBox.addValueChangeListener(e -> {
			if (e.getValue() != null) {
				lineJoin = e.getValue();
				applySettings();
			}
		});

		var filter = new TextField(MewSpaceViewsConstant.LABEL_FILTER);
		filter.addValueChangeListener(e -> renderGallery(e.getValue()));

		var controls = new HorizontalLayout(fontSizeField, strokeWidthComboBox, colorComboBox, fillComboBox,
				lineCapComboBox, lineJoinComboBox, filter);
		controls.setAlignItems(Alignment.END);

		var pagination = new HorizontalLayout(firstButton, previousButton, pageLabel, nextButton, lastButton,
				countLabel);
		pagination.setAlignItems(Alignment.CENTER);
		pagination.setJustifyContentMode(JustifyContentMode.CENTER);
		pagination.setWidthFull();
		pageLabel.addClassName(MewSpaceCSSConstant.LUCIDE_PAGINATION_LABEL);
		countLabel.addClassName(MewSpaceCSSConstant.LUCIDE_PAGINATION_LABEL);

		gallery.addClassName(MewSpaceCSSConstant.LUCIDE_ICON_GALLERY);
		var scroller = new Scroller(gallery);
		scroller.setSizeFull();

		renderGallery(null);

		setHeightFull();
		setFlexGrow(1, scroller);
		add(controls, scroller, pagination);
	}

	private void renderGallery(String filterValue) {
		filteredIcons.clear();
		var filter = filterValue == null ? "" : filterValue.trim().toLowerCase();
		for (LucideIcon lucideIcon : LucideIcon.values()) {
			if (lucideIcon.getIconName().contains(filter)) {
				filteredIcons.add(lucideIcon);
			}
		}
		gotoPage(0);
	}

	private void renderPage() {
		gallery.removeAll();
		icons.clear();
		var start = pageIndex * PAGE_SIZE;
		var end = Math.min(start + PAGE_SIZE, filteredIcons.size());
		for (int i = start; i < end; i++) {
			var lucideIcon = filteredIcons.get(i);
			var icon = createIcon(lucideIcon);
			var item = new Div(icon);
			item.addClassName(MewSpaceCSSConstant.LUCIDE_ICON_ITEM);
			item.getElement().setAttribute("title", lucideIcon.getIconName());
			gallery.add(item);
			icons.add(icon);
		}
		updatePagination();
	}

	private void gotoPage(int page) {
		pageIndex = Math.max(0, Math.min(page, pageCount() - 1));
		renderPage();
	}

	private void updatePagination() {
		pageLabel.setText(MewSpaceViewsConstant.PAGE_OF.formatted(pageIndex + 1, pageCount()));
		countLabel.setText(MewSpaceViewsConstant.ICONS_COUNT.formatted(filteredIcons.size()));
		var hasPrevious = pageIndex > 0;
		var hasNext = pageIndex < pageCount() - 1;
		firstButton.setEnabled(hasPrevious);
		previousButton.setEnabled(hasPrevious);
		nextButton.setEnabled(hasNext);
		lastButton.setEnabled(hasNext);
	}

	private int pageCount() {
		return Math.max(1, (int) Math.ceil(filteredIcons.size() / (double) PAGE_SIZE));
	}

	private Button createPageButton(LucideIcon lucideIcon, String label, Command action) {
		var button = new Button(lucideIcon.create());
		button.setAriaLabel(label);
		button.setTooltipText(label);
		button.addClickListener(e -> action.execute());
		return button;
	}

	private LucideSvgIcon createIcon(LucideIcon lucideIcon) {
		var icon = lucideIcon.create();
		icon.setDecorative(true);
		applySettings(icon);
		return icon;
	}

	private void applySettings() {
		icons.forEach(this::applySettings);
	}

	private void applySettings(LucideSvgIcon icon) {
		icon.setStrokeWidth(strokeWidth);
		icon.setSize(fontSize + "px");
		icon.setColor(resolveCssColor(color, COLORS));
		icon.setFill(resolveCssColor(fillColor, FILL_COLORS));
		icon.setStrokeLinecap(LucideLineCap.from(lineCap).orElse(null));
		icon.setStrokeLinejoin(LucideLineJoin.from(lineJoin).orElse(null));
	}

	private static String resolveCssColor(String choice, Map<String, String> map) {
		if (COLOR_DEFAULT.equals(choice) || DEFAULT_FILL_LABEL.equals(choice)) {
			return null;
		}
		var cssColor = map.get(choice);
		return cssColor != null && !cssColor.isBlank() ? cssColor : choice;
	}

	private static Map<String, String> createFillColors() {
		var fills = new LinkedHashMap<String, String>();
		fills.put(DEFAULT_FILL_LABEL, null);
		fills.put(CURRENT_COLOR_LABEL, "currentColor");
		fills.put("Black", "#000000");
		fills.put("White", "#ffffff");
		fills.put("Slate", "#64748b");
		fills.put("Green", "#22c55e");
		fills.put("Teal", "#14b8a6");
		fills.put("Blue", "#3b82f6");
		fills.put("Indigo", "#6366f1");
		return fills;
	}

	private static Map<String, String> createColors() {
		var colors = new LinkedHashMap<String, String>();
		colors.put(COLOR_DEFAULT, null);
		colors.put("Black", "#000000");
		colors.put("White", "#ffffff");
		colors.put("Slate", "#64748b");
		colors.put("Red", "#ef4444");
		colors.put("Orange", "#f97316");
		colors.put("Amber", "#f59e0b");
		colors.put("Green", "#22c55e");
		colors.put("Teal", "#14b8a6");
		colors.put("Blue", "#3b82f6");
		colors.put("Indigo", "#6366f1");
		colors.put("Purple", "#a855f7");
		colors.put("Pink", "#ec4899");
		return colors;
	}

}
