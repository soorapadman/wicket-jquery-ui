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
package com.googlecode.wicket.jquery.ui.effect;

/**
 * Provides an enumeration of effect available in jQuery<br/>
 *
 * @author Sebastien Briquet - sebfz1
 */
public enum Effect
{
	/** The blind effect hides or shows an element by wrapping the element in a container, and “pulling the blinds” */
	Blind("blind"),

	/** The bounce effect bounces an element. When used with hide or show, the last or first bounce will also fade in/out. */
	Bounce("bounce"),

	/** The clip effect will hide or show an element by clipping the element vertically or horizontally. */
	Clip("clip"),

	/** The drop effect hides or shows an element fading in/out and sliding in a direction. */
	Drop("drop"),

	/** The explode effect hides or shows an element by splitting it into pieces. */
	Explode("explode"),

	/** The fade effect hides or shows an element by fading it. */
	Fade("fade"),

	/** The fold effect hides or shows an element by folding it. */
	Fold("fold"),

	/** The highlight effect hides or shows an element by animating its background color first. */
	Highlight("highlight"),

	/** Creates a puff effect by scaling the element up and hiding it at the same time. */
	Puff("puff"),

	/** The pulsate effect hides or shows an element by pulsing it in or out. */
	Pulsate("pulsate"),

	/** Shrink or grow an element by a percentage factor.*/
	Scale("scale"),

	/** Shakes the element multiple times, vertically or horizontally. */
	Shake("shake"),

	/** Resize an element to a specified width and height. */
	Size("size"),

	/** Slides the element out of the viewport. */
	Slide("slide"),

	/** Transfers the outline of an element to another element */
	Transfer("transfer");

	private final String name;

	private Effect(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
