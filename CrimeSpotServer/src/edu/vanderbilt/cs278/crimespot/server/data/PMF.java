package edu.vanderbilt.cs278.crimespot.server.data;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * 
 * @author Li
 *
 */
public class PMF {

    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {}

    public static PersistenceManagerFactory get() 
    {
    	// Get persistent factory instance. 
        return pmfInstance;
    }
}
