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

import org.apache.wicket.Application;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;

/**
 * Provides an {@code AjaxBehavior} over a {@code ListModel} <br/>
 * The #getCallbackUrl()} that can be used as {@link Chart} source
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 */
public class ListModelAjaxBehavior<T> extends AbstractAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private final IModel<List<T>> model;

	/**
	 * Constructor
	 *
	 * TODO javadoc
	 */
	public ListModelAjaxBehavior(IModel<List<T>> model)
	{
		this.model = model;
	}

	/**
	 * Constructor
	 *
	 * TODO javadoc
	 * 
	 */
	public ListModelAjaxBehavior(final ListModel<T> model)
	{
		this.model = model;
	}

	@Override
	public void onRequest()
	{
		RequestCycle.get().scheduleRequestHandlerAfterCurrent(new ListModelRequestHandler());
	}

	/**
	 * Provides the {@link IRequestHandler}
	 */
	protected class ListModelRequestHandler implements IRequestHandler
	{
		@Override
		public void respond(final IRequestCycle requestCycle)
		{
			WebResponse response = (WebResponse) requestCycle.getResponse();

			final String encoding = Application.get().getRequestCycleSettings().getResponseRequestEncoding();
			response.setContentType("application/json; charset=" + encoding);
			response.disableCaching();

			response.write(new JSONArray(model.getObject()).toString());
		}

		@Override
		public void detach(final IRequestCycle requestCycle)
		{
			// noop
		}
	}
}
