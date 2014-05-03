package com.iocanto.plugin.browser;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;

/**
 * 
 */
public class BrowserManager extends Observable {
	
	protected static BrowserManager instance;
	
	protected boolean ignorePreferenceChanges = false;
    protected List<IBrowserDescriptor > browsers;
    protected IBrowserDescriptor currentBrowser;
    protected Integer current;
    
    private IPreferenceChangeListener pcl ;

    public static BrowserManager getInstance() {
         if ( null == instance )
            instance = new BrowserManager();
        return instance;
    }
    
	public static void safeDispose() {
        if ( null == instance )
            return;
        instance.dispose();
	}
   
    
    private BrowserManager() {
	
		pcl = new IEclipsePreferences.IPreferenceChangeListener() {

			@Override
			public void preferenceChange(PreferenceChangeEvent event) {
				String property = event.getKey();
				
				if (!ignorePreferenceChanges
						&& property.equals("browsers")) { //$NON-NLS-1$
					loadBrowsers();
				}
				
			}
        };
        
		IScopeContext instanceScope = InstanceScope.INSTANCE;
		IEclipsePreferences prefs = instanceScope.getNode(WebBrowserUIPlugin.PLUGIN_ID);
		prefs.addPreferenceChangeListener(pcl);
        loadBrowsers();
    }
    
    public List<IBrowserDescriptor> getBrowers (){
		return this.browsers;
	}
	
	public void saveSelectedBrower(  IBrowserDescriptor currentBrowser ){
		currentBrowser.setCurrent ( true );
		this.currentBrowser.setCurrent ( false );
		this.currentBrowser = currentBrowser ;
		this.saveBrowsers();
	}

	
	protected void loadBrowsers() {
        
        String xmlString = Platform.getPreferencesService().getString
            (WebBrowserUIPlugin.PLUGIN_ID,  "browsers", null, null); //$NON-NLS-1$
        
        if (xmlString != null && xmlString.length() > 0) {
            browsers = new ArrayList<IBrowserDescriptor >();
            
            try {
                ByteArrayInputStream in = new ByteArrayInputStream(xmlString.getBytes());
                Reader reader = new InputStreamReader(in);
                IMemento memento = XMLMemento.createReadRoot(reader);
                
                IMemento[] children = memento.getChildren("external"); //$NON-NLS-1$
                int size = children.length;
                for (int i = 0; i < size; i++) {
                	BrowserDescriptor browser = new BrowserDescriptor();
                    browser.load(children[i]);
                    browsers.add(browser);
                }
                
                current = memento.getInteger("current"); //$NON-NLS-1
                
            } catch (Exception e) {
            	System.out.println(e.getMessage());	
            }

            IBrowserDescriptor system = new SystemBrowserDescriptor();
            if ( !browsers.contains(system)) 
            	browsers.add(0, system);
            
            currentBrowser =  browsers.get( current );
            currentBrowser.setCurrent (true);
        }
            
    }
		
	protected void saveBrowsers() {
	        try {
	            ignorePreferenceChanges = true;
	            XMLMemento memento = XMLMemento.createWriteRoot("web-browsers"); //$NON-NLS-1$

	            @SuppressWarnings("rawtypes")
				Iterator iterator = browsers.iterator();
	            while (iterator.hasNext()) {
	                Object obj = iterator.next();
	                if (obj instanceof BrowserDescriptor) {
	                    BrowserDescriptor browser = (BrowserDescriptor) obj;
	                    IMemento child = memento.createChild("external"); //$NON-NLS-1$
	                    browser.save(child);
	                } else if (obj instanceof SystemBrowserDescriptor) {
	                    memento.createChild("system"); //$NON-NLS-1$
	                }
	            }
	            
	            memento.putInteger("current", browsers.indexOf(currentBrowser)); //$NON-NLS-1$

	            StringWriter writer = new StringWriter();
	            memento.save(writer);
	            String xmlString = writer.getBuffer().toString();
	            @SuppressWarnings("deprecation")
				InstanceScope instanceScope = new InstanceScope();
	            IEclipsePreferences prefs = instanceScope.getNode(WebBrowserUIPlugin.PLUGIN_ID);
	            prefs.put("browsers", xmlString); //$NON-NLS-1$
	            prefs.flush();
	        } catch (Exception e) {
	        	System.out.println(e);
	        }
	        ignorePreferenceChanges = false;
	        
	}

	protected void dispose() {
			IScopeContext instanceScope = InstanceScope.INSTANCE;
	        IEclipsePreferences prefs = instanceScope.getNode(WebBrowserUIPlugin.PLUGIN_ID);
	        prefs.removePreferenceChangeListener(pcl);
	}
	
	/*

    public IBrowserDescriptorWorkingCopy createExternalWebBrowser() {
        return new BrowserDescriptorWorkingCopy();
    }   
    
    public IBrowserDescriptor getCurrentWebBrowser() {
        if (browsers == null)
            loadBrowsers();

        if (currentBrowser == null && browsers.size() > 0)
            return (IBrowserDescriptor) browsers.get(0);
        
        return currentBrowser; 
    }

    public void setCurrentWebBrowser(IBrowserDescriptor wb) {
        if (wb == null)
            throw new IllegalArgumentException();

        if (browsers.contains(wb))
            currentBrowser = wb;
        else
            throw new IllegalArgumentException();
        saveBrowsers();
    }
    
    */
}