package com.googlecode.wicket.jquery.ui.samples.pages.effect;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.effect.Effect;
import com.googlecode.wicket.jquery.ui.effect.JQueryEffectBehavior;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.SimpleTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;


public class ReplaceEffectPage extends AbstractEffectPage
{
	private static final long serialVersionUID = 1L;

	private int count = 0;

	public ReplaceEffectPage()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback);

		// TabbedPanel (component) //
		final TabbedPanel tabbedPanel = new TabbedPanel("component", this.newTabList());
		this.add(tabbedPanel);

		// Form //
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// Button //
		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				SimpleTab tab = (SimpleTab) tabbedPanel.getModelObject().get(0);
				tab.setContent(String.format("replaced %d time(s)", ++count));

				JQueryEffectBehavior.replace(target, tabbedPanel, Effect.Drop, Effect.Slide);
			}
		});
	}

	private List<ITab> newTabList()
	{
		List<ITab> tabs = new ArrayList<ITab>();

		tabs.add(new SimpleTab(Model.of("Tab #1"), Model.of("my content")));
		tabs.add(new AjaxTab(Model.of("Tab (ajax)")) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getLazyPanel(String panelId)
			{
				try
				{
					// sleep the thread for a half second to simulate a long load
					Thread.sleep(500);
				}
				catch (InterruptedException e)
				{
					error(e.getMessage());
				}

				return new Fragment(panelId, "panel", ReplaceEffectPage.this);
			}
		});

		return tabs;
	}
}
