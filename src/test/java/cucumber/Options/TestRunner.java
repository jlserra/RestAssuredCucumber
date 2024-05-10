package cucumber.Options;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features/", glue = {"stepDefinition"}, tags = "@AddPlace", plugin = "json:target/jsonReports/cucumberReport.json")
public class TestRunner {
    
}
