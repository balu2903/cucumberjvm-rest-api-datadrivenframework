package step_definitions;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import model.Landlord;
import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.Is;

import java.util.List;

public class RESTDataDrivenFramework {
    private static final Logger logger = Logger.getLogger(RESTDataDrivenFramework.class);

    String hostName;
    @Given("^a server named as \"(.*?)\"$")
    public void a_server_named_as(String serverName) throws Throwable {
        if(serverName.equals("localhost")){
            hostName=serverName;
        }
    }

    @Given("^a http port with number \"(.*?)\"$")
    public void a_http_port_with_number(String httpPortNumber) throws Throwable {
        RestAssured.baseURI = "http://"+hostName+":"+httpPortNumber+"";
    }


    @Given("^a server context named as \"(.*?)\"$")
    public void a_server_context_named_as(String severContext) throws Throwable {
    }

    @Given("^a valid token \"(.*?)\"$")
    public void a_valid_token(String oathToken) throws Throwable {
    }


    List<Landlord> landlords;

    @When("^I click endpoint$")
    public void i_click_endpoint() throws Throwable {
         landlords = RestAssured.given().
                when().
                get("/landlords").path("$","firstName");
    }

    @Then("^I see all the landlords$")
    public void i_see_all_the_landlords() throws Throwable {
        Assertions.assertThat(landlords).isNotNull()
                                        //.hasSize(26)
                                        ;
    }
    Landlord landlord;
    @Given("^I have landlord with below details \"(.*?)\" and \"(.*?)\" and \"(.*?)\"$")
    public void i_have_and_and(String firstName, String lastName, String trustedFlag) throws Throwable {
        landlord = new Landlord (firstName,lastName,Boolean.valueOf(trustedFlag));


    }
    String id;
    @When("^I hit the endpoint$")
    public void i_hit_the_endpoint() throws Throwable {
         id = RestAssured.given().
                contentType(ContentType.JSON).
                body(landlord).
                when().
                post("/landlords").
                then().
                statusCode(201).
                body("firstName", Is.is(landlord.getFirstName())).
                body("lastName", Is.is(landlord.getLastName())).
                body("trusted", Is.is(landlord.getTrusted())).
                        body("apartments", Is.is(IsEmptyCollection.empty())).
                        extract().
                        path("id")
                ;

    }

    @Then("^I see landlords are added to system$")
    public void i_see_landlords_are_added_to_system() throws Throwable {
        RestAssured.given().
                pathParam("id", id).
                when().
                get("/landlords/{id}").
                then().
                statusCode(200).
                body("id", Is.is(id)).
                body("firstName", Is.is(landlord.getFirstName())).
                body("lastName", Is.is(landlord.getLastName())).
                body("trusted", Is.is(landlord.getTrusted())).
                        body("apartments", Is.is(Matchers.empty()))
        ;

    }

    String updatedFirstName;
    String updatedLastName;
    @When("^I click endpoint to update firstName \"(.*?)\" and lastName \"(.*?)\" with the unique id \"(.*?)\"$")
    public void i_click_endpoint_to_update_firstName_and_lastName_with_the_unique_id(String firstName, String lastName, String id) throws Throwable {
        updatedFirstName=firstName;
        updatedLastName=lastName;
        Landlord landlord1 = new Landlord(firstName,lastName);

        RestAssured.given().
                contentType(ContentType.JSON).
                body(landlord1).
                pathParam("id",id).
                when().
                put("/landlords/{id}").
                then().
                statusCode(200).
                body("message",Is.is("LandLord with id: "+id+" successfully updated"));
    }

    @Then("^I see the values are updated for the unique id \"(.*?)\"$")
    public void i_see_the_values_are_updated_for_the_unique_id(String id) throws Throwable {
        RestAssured.given().
                pathParam("id",id).
                when().
                get("/landlords/{id}").
                then().
                statusCode(200).
                body("firstName", Is.is(updatedFirstName)).
                body("lastName", Is.is(updatedLastName))
                ;

    }


}