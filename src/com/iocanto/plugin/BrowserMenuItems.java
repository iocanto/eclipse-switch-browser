package com.iocanto.plugin;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.iocanto.plugin.browser.BrowserManager;
import com.iocanto.plugin.browser.IBrowserDescriptor;

public class BrowserMenuItems extends ContributionItem {

	private BrowserManager manager;
	
	@Override
	public void fill(Menu menu, int index) {
		
		manager = BrowserManager.getInstance(); 
		
		for ( IBrowserDescriptor browser : manager.getBrowers() ){
			MenuItem menuItem = new MenuItem(menu, SWT.RADIO);
			if ( browser.isCurrent() ) 
				menuItem.setSelection(true);
			menuItem.setText( browser.getName() );
			menuItem.addSelectionListener(new BrowerItemSelectionAdapter ( browser )) ;
		}
		
	}
	
	class BrowerItemSelectionAdapter extends  SelectionAdapter {
		
		IBrowserDescriptor descriptor ;
		
		BrowerItemSelectionAdapter ( IBrowserDescriptor descriptor ){
			this.descriptor  = descriptor ;
			
		} 
		
		public void widgetSelected(SelectionEvent e) {
			
			manager.saveSelectedBrower( descriptor );
			
		}
	}

}
