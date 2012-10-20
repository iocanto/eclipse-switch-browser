package com.iocanto.plugin.browser;


class SystemBrowserDescriptor implements IBrowserDescriptor {
	
	private static String prefSystemBrowser = "default";
	
	private boolean isCurrent ;
	
    
    {
    	isCurrent= false;
    }
	
	
	public String getName() {
		return prefSystemBrowser;
	}

	public String getLocation() {
		return null;
	}

	public String getParameters() {
		return null;
	}

	public void delete() {
		// ignore
	}

	public boolean isWorkingCopy() {
		return false;
	}

	public IBrowserDescriptorWorkingCopy getWorkingCopy() {
		return null;
	}
	
	public boolean equals(Object obj) {
		return obj instanceof SystemBrowserDescriptor;
	}

	@Override
	public boolean isCurrent() {
		return isCurrent ;
	}

	@Override
	public void setCurrent(boolean current) {
		this.isCurrent  = current;
	}
}