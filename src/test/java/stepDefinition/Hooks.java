package stepDefinition;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {

        AddplaceStepDefinition step = new AddplaceStepDefinition();
        if(AddplaceStepDefinition.placeId == null) {
            step.add_place_payload("Juan Dela Cruz");
            step.user_calls_with_post_http_request("AddPlaceAPI", "POST");
            step.verify_if_place_added_in_call("Juan Dela Cruz", "GetPlaceAPI");
        }
    }

}
