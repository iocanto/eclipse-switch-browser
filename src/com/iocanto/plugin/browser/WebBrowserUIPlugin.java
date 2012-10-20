/*******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - Initial API and implementation
 *******************************************************************************/
package com.iocanto.plugin.browser;

import org.eclipse.ui.plugin.AbstractUIPlugin;
/**
 * The main web browser plugin class.
 */
public class WebBrowserUIPlugin extends AbstractUIPlugin {
    // Web browser plugin id
    public static final String PLUGIN_ID = "org.eclipse.ui.browser"; //$NON-NLS-1$

    
    private static WebBrowserUIPlugin singleton;

    /**
     * Create the WebBrowserUIPlugin
     */
    public WebBrowserUIPlugin() {
        super ();
        singleton = this ;
    }

    /**
     * Returns the singleton instance of this plugin.
     *
     * @return org.eclipse.ui.internal.browser.WebBrowserPlugin
     */
    public static WebBrowserUIPlugin getInstance() {
        return singleton;
    }
}		