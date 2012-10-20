package com.iocanto.plugin;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;


/**
 * This class is here basically just to enable the Browser Switcher toolbar
 * pulldown button.
 *
 */

public class PulldownHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	    Object trigger = event.getTrigger();
	    if (trigger instanceof Event) {
	      Widget widget = ((Event) trigger).widget;
	      if (widget instanceof ToolItem) {
	        ToolItem toolItem = (ToolItem) widget;
	        Listener[] listeners = toolItem.getListeners(SWT.Selection);
	        if (listeners.length > 0) {
	          Listener listener = listeners[0]; // should be only one listener
	          // send an event to the widget to open the menu
	          // see CommandContributionItem.openDropDownMenu(Event)
	          Event e = new Event();
	          e.type = SWT.Selection;
	          e.widget = widget;
	          e.detail = 4; // dropdown detail
	          e.y = toolItem.getBounds().height; // position menu
	          listener.handleEvent(e);
	        }
	      }
	    }
		
		return null;
	}

}
