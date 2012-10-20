package com.iocanto.plugin.browser;

public interface IBrowserDescriptorWorkingCopy extends IBrowserDescriptor {

	public void setName(String name);

    public void setLocation(String location);

    public void setParameters(String params);
    
    public IBrowserDescriptor save();
}