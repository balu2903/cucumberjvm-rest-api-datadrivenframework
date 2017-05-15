package step_definitions;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
		features = "classpath:features/RESTDataDrivenFramework.feature",
		plugin = {"pretty", "html:target/cucumber-html-report","json:cucumber.json"},
		//dryRun = true,
		tags = {}
		)
public class RunCukesTest{
}