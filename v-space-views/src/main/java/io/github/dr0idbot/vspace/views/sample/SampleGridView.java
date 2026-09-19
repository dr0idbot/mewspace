package io.github.dr0idbot.vspace.views.sample;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Sample Grid")
public class SampleGridView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private static final String[] NAMES = { "Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel",
			"India", "Juliet" };
	private static final String[] CATEGORIES = { "Type A", "Type B", "Type C", "Type D", "Type E" };

	private final Grid<SampleItem> grid = new Grid<>(SampleItem.class);
	private final List<SampleItem> items;

	public SampleGridView() {
		items = generateItems(5000);

		TextField nameFilter = new TextField("Name");
		nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
		nameFilter.addValueChangeListener(e -> updateGrid(e.getValue(), null));

		TextField categoryFilter = new TextField("Category");
		categoryFilter.setValueChangeMode(ValueChangeMode.EAGER);
		categoryFilter.addValueChangeListener(e -> updateGrid(null, e.getValue()));

		grid.setColumns("name", "category", "quantity");
		grid.setItems(items);

		add(new HorizontalLayout(nameFilter, categoryFilter), grid);
		setSizeFull();
	}

	private List<SampleItem> generateItems(int count) {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		return java.util.stream.IntStream.range(0, count)
				.mapToObj(i -> new SampleItem(NAMES[rand.nextInt(NAMES.length)] + "-" + (i + 1),
						CATEGORIES[rand.nextInt(CATEGORIES.length)], rand.nextInt(1, 1000)))
				.toList();
	}

	private void updateGrid(String nameFilter, String categoryFilter) {
		grid.setItems(items.stream()
				.filter(i -> nameFilter == null || nameFilter.isEmpty()
						|| i.name().toLowerCase().contains(nameFilter.toLowerCase()))
				.filter(i -> categoryFilter == null || categoryFilter.isEmpty()
						|| i.category().toLowerCase().contains(categoryFilter.toLowerCase()))
				.toList());
	}
}
