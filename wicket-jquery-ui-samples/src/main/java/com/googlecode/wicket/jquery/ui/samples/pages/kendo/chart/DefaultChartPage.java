package com.googlecode.wicket.jquery.ui.samples.pages.kendo.chart;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.chart.Chart;

public class DefaultChartPage extends AbstractChartPage
{
	private static final long serialVersionUID = 1L;

	public DefaultChartPage()
	{
		// Chart //
		Options options = new Options();
		options.set("series", "[ { field: 'Volume' } ]");

		this.add(new Chart<Integer>("chart", newDataModel(), options));
	}

	private static IModel<List<Integer>> newDataModel()
	{
		return new LoadableDetachableModel<List<Integer>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Integer> load()
			{
				return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
			}
		};
	}
}
