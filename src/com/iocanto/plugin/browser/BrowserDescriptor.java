package com.iocanto.plugin.browser;

import org.eclipse.ui.IMemento;

/**
 * 
 */
class BrowserDescriptor implements  IBrowserDescriptor {
    private static final String MEMENTO_NAME = "name"; //$NON-NLS-1$
    private static final String MEMENTO_LOCATION = "location"; //$NON-NLS-1$
    private static final String MEMENTO_PARAMETERS = "parameters"; //$NON-NLS-1$

    protected String name;
    protected String location;
    protected String parameters;
    
    protected boolean isCurrent;
    
    {
    	isCurrent= false;
    }
    
   
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.internal.browser.IWebBrowser#getName()
     */
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.internal.browser.IBrowserDescriptor#getLocation()
     */
    public String getLocation() {
        return location;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.internal.browser.IBrowserDescriptor#getParameters()
     */
    public String getParameters() {
        return parameters;
    }

    public void delete() {
        //BrowserManager.getInstance().removeWebBrowser(this );
    }

    public boolean isWorkingCopy() {
        return false;
    }

    public IBrowserDescriptorWorkingCopy getWorkingCopy() {
    	return null;
        //return new BrowserDescriptorWorkingCopy(this );
    }

    protected void setInternal(IBrowserDescriptor browser) {
        name = browser.getName();
        location = browser.getLocation();
        parameters = browser.getParameters();
    }

    protected void save(IMemento memento) {
        memento.putString(MEMENTO_NAME, name);
        memento.putString(MEMENTO_LOCATION, location);
        memento.putString(MEMENTO_PARAMETERS, parameters);
    }

    protected void load(IMemento memento) {
        name = memento.getString(MEMENTO_NAME);
        location = memento.getString(MEMENTO_LOCATION);
        parameters = memento.getString(MEMENTO_PARAMETERS);
    }

    public String toString() {
        return "External Web browser: " + getName() + " / " + getLocation() + " / " + getParameters(); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
    }

	@Override
	public boolean isCurrent() {
		return isCurrent;
	}
	
	public void setCurrent ( boolean current ) {
		this.isCurrent = current ;
	}
}
