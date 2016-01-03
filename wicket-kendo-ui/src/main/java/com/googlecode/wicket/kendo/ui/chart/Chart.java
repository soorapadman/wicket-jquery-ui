/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.wicket.kendo.ui.chart;

import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryGenericContainer;
import com.googlecode.wicket.jquery.core.Options;

/**
 * TODO javadoc
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 */
public class Chart<T> extends JQueryGenericContainer<List<T>> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	private final Options options;
	private ListModelAjaxBehavior<T> listModelAjaxBehavior;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param list the list of values
	 */
	public Chart(String id, List<T> list)
	{
		this(id, Model.ofList(list), new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id list the list of values
	 * @param options {@link Options}
	 */
	public Chart(String id, List<T> list, Options options)
	{
		this(id, Model.ofList(list), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list model of {@link ITab}{@code s}
	 */
	public Chart(String id, IModel<List<T>> model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the list model of values
	 * @param options {@link Options}
	 */
	public Chart(String id, IModel<List<T>> model, Options options)
	{
		super(id, model);
		
		this.options = Args.notNull(options, "options");
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.listModelAjaxBehavior = this.newListModelAjaxBehavior();
		this.add(this.listModelAjaxBehavior);

		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	// Methods //

	// Properties //

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new ChartBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CharSequence getDataCallbackUrl()
			{
				return listModelAjaxBehavior.getCallbackUrl();
			}
		};
	}

	// Factories //

	/**
	 * Gets a new {@link ListModelAjaxBehavior}
	 *
	 * @return a new {@link ListModelAjaxBehavior}
	 */
	protected ListModelAjaxBehavior<T> newListModelAjaxBehavior()
	{
		return new ListModelAjaxBehavior<T>(this.getModel());
	}
}
