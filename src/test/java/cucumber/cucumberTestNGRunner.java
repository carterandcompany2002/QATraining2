package cucumber;

import org.testng.annotations.Test;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources",
        glue = "cucumber"

)
public class cucumberTestNGRunner extends AbstractTestNGCucumberTests {

    @Test
    public void runCucumberTests() {
    }
}