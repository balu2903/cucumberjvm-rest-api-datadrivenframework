package step_definitions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.log4j.Logger;

public class Hooks{
    private static final Logger logger = Logger.getLogger(Hooks.class);

    @Before
    public void setUp() {
    	logger.info("setUp");


    }

     @After
    public void tearDown(Scenario scenario) {
        logger.info("tearDown scenario->"+scenario);
    }
    
}