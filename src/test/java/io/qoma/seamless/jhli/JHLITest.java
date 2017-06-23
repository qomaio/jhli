package io.qoma.seamless.jhli;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JHLITest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public JHLITest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( JHLITest.class );
    }

    public void test_fame_post()
    {
    	Set<String>tests = new HashSet<String>();
    	tests.add("Title#1:Title#2:Title#3 12345678 3484fa76");
    	
    	for(String t:tests){
    		TestCase.assertEquals(JHLI.HSUCC, JHLI.fame_post(new FAME_String(t)));
    	}
    	
    }
}
