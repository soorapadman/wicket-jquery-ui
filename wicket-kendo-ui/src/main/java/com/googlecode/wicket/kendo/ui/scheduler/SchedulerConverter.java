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
package com.googlecode.wicket.kendo.ui.scheduler;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.util.lang.Generics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.utils.DateUtils;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceList;

/**
 * Default implementation of {@link ISchedulerConverter}
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class SchedulerConverter implements ISchedulerConverter
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(SchedulerConverter.class);

	@Override
	public JSONObject toJson(SchedulerEvent event)
	{
		try
		{
			JSONObject object = new JSONObject();

			object.put("id", event.getId());
			object.put("isAllDay", event.isAllDay());

			if (event.getTitle() != null)
			{
				object.put("title", event.getTitle());
			}

			if (event.getDescription() != null)
			{
				object.put("description", event.getDescription());
			}

			if (event.getStart() != null)
			{
				object.put("start", DateUtils.toUTCString(event.getStart()));
			}

			if (event.getEnd() != null)
			{
				object.put("end", DateUtils.toUTCString(event.getEnd()));
			}

			// recurrence //
			if (event.getRecurrenceId() != null)
			{
				object.put("recurrenceId", event.getRecurrenceId());
			}

			if (event.getRecurrenceRule() != null)
			{
				object.put("recurrenceRule", event.getRecurrenceRule());
			}

			if (event.getRecurrenceException() != null)
			{
				object.put("recurrenceException", event.getRecurrenceException());
			}

			// resources //
			for (String field : event.getFields())
			{
				object.put(field, event.getValue(field)); // value is type of Object
			}

			return object;
		}
		catch (JSONException e)
		{
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	@Override
	public SchedulerEvent toObject(JSONObject object, List<ResourceList> lists)
	{
		try
		{
			SchedulerEvent event = new SchedulerEvent();
			event.setId(object.getInt("id"));
			event.setTitle(object.optString("title"));
			event.setDescription(object.optString("description"));

			event.setStart(object.getLong("start"));
			event.setEnd(object.getLong("end"));
			event.setAllDay(object.getBoolean("isAllDay"));

			event.setRecurrenceId(object.optString("recurrenceId"));
			event.setRecurrenceRule(object.optString("recurrenceRule"));
			event.setRecurrenceException(object.optString("recurrenceException"));

			// Resources //
			Pattern pattern = Pattern.compile("([\\w-]+)");

			for (ResourceList list : lists)
			{
				List<String> values = Generics.newArrayList();

				String field = list.getField();
				String value = object.optString(field);

				if (value != null)
				{
					Matcher matcher = pattern.matcher(value);

					while (matcher.find())
					{
						values.add(matcher.group());
					}
				}

				if (list.isMultiple())
				{
					event.setValue(field, this.convertFieldValues(field, values));
				}
				else if (!values.isEmpty())
				{
					event.setValue(field, this.convertFieldValue(field, values.get(0)));
				}
			}

			return event;
		}
		catch (JSONException e)
		{
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * Converts a single-resource {@code String} value to an {@code Object}<br>
	 * This method is called by {@link #toObject(JSONObject, List)}
	 * 
	 * @param field the resource name
	 * @param value the value to convert
	 * @return the string value, by default (no conversion)
	 */
	protected Object convertFieldValue(String field, String value)
	{
		return value;
	}

	/**
	 * Converts a {@code List} of multiple-resource {@code String} values to a {@code List} of {@code Object}<br>
	 * This method is called by {@link #toObject(JSONObject, List)}
	 * 
	 * @param field the resource name
	 * @param values the value list to convert
	 * @return the string {@code List} converted to object {@code List}
	 */
	protected final List<Object> convertFieldValues(String field, List<String> values)
	{
		List<Object> list = Generics.newArrayList();

		if (values != null)
		{
			for (String value : values)
			{
				list.add(this.convertFieldValue(field, value));
			}
		}

		return list;
	}
}
