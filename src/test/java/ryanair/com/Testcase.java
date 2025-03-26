package ryanair.com;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class Testcase {
	
	ExtentReports extent = ReportManager.getReportObject();
    ExtentTest test;
    
	
	String url = "http://127.0.0.1:8900";
	int userId;
	int bookingId;
	
    // Use Gson to pretty print
    Gson gson;
    String prettyJson;
	
	
	@Test(priority = 0)
	public void createUser() throws IOException {
		
        Faker faker = new Faker();

        String randomEmail = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        String reqBody = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/user_test_data.json")));
        String replacedBody = reqBody
                .replace("test1@gmail.com", randomEmail)
                .replace("Srikanth", firstName)
                .replace("Sathinathan", lastName);

        test = extent.createTest("Create User");
        test.info("Request Body:\n" + replacedBody);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(replacedBody)
                .when()
                .post(url + "/user");

        int statusCode = response.getStatusCode();
        String responseBody = response.prettyPrint();

        test.info("Status Code: " + statusCode);
        test.info("Response Body:\n" + responseBody);
        
        userId = response.jsonPath().getInt("id");
        test.info("User ID: " + userId);


        if (statusCode == 201) {
            test.pass("User created successfully with 201 status");
        } else {
            test.fail("Failed to create user. Status: " + statusCode);
        }
		
	}
	
	@Test(priority = 1)
	public void getAllUser() {
		Response response = given().contentType(ContentType.JSON).when().get(url + "/user");
		
		test = extent.createTest("Get all User");
		int statusCode = response.getStatusCode();
        String responseBody = response.asString();
        
        // Use Gson to pretty print
        gson = new GsonBuilder().setPrettyPrinting().create();
        prettyJson = gson.toJson(gson.fromJson(responseBody, Object.class));
        
        test.info("Status Code: " + statusCode);
        test.info("<pre>" + prettyJson + "</pre>");

	}
	
	@Test(priority = 2)
	public void getUserById() {
		Response response = given().contentType(ContentType.JSON).when().get(url + "/user/"+userId);
		
		test = extent.createTest("Get User details by "+userId);
		int statusCode = response.getStatusCode();
        String responseBody = response.prettyPrint();
        
        // Use Gson to pretty print
        gson = new GsonBuilder().setPrettyPrinting().create();
        prettyJson = gson.toJson(gson.fromJson(responseBody, Object.class));
        
        test.info("Status Code: " + statusCode);
        test.info("<pre>" + prettyJson + "</pre>");
	}
	
	@Test(priority = 3)
	public void createBooking() throws IOException {
	    // Initialize Faker to generate random data
	    Faker faker = new Faker();

	    // Generate a future date (20 days from now)
	    Date randomDate = faker.date().future(20, TimeUnit.DAYS);

	    // Format the random date to a string (yyyy-MM-dd format)
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String formattedDate = sdf.format(randomDate); // Convert Date to String

	    // Read the JSON body from file
	    String reqBody = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/booking_test_data.json")));

	    // Use String.format to replace placeholders dynamically
	    String replacedBody = reqBody.replace("{{date}}", formattedDate).replace("{{userId}}", String.valueOf(userId));
	    test = extent.createTest("Create booking");
	    test.info("Request Body:\n" + replacedBody);

	    // Send the POST request
	    Response response = given()
	            .contentType(ContentType.JSON)
	            .body(replacedBody)
	            .when()
	            .post(url + "/booking");
	    
        //bookingId = response.jsonPath().getInt("id");
        //test.info("Booking ID: " + bookingId);

	    int statusCode = response.getStatusCode();
	    String responseBody = response.asString();
	    System.out.println("Response Code: " + statusCode);
	    System.out.println("Response Body: " + responseBody);
	    
        gson = new GsonBuilder().setPrettyPrinting().create();
        prettyJson = gson.toJson(gson.fromJson(responseBody, Object.class));
        
        test.info("Status Code: " + statusCode);
        test.info("<pre>" + prettyJson + "</pre>");
		
	}
	
	@Test(priority = 4)
	public void getBookingById() {
		
		Response response = given().contentType(ContentType.JSON).when().get(url + "/booking/" + bookingId);
		
		test = extent.createTest("Get Booking By Id "+ bookingId);
		int statusCode = response.getStatusCode();
        String responseBody = response.asString();
        
        // Use Gson to pretty print
        gson = new GsonBuilder().setPrettyPrinting().create();
        prettyJson = gson.toJson(gson.fromJson(responseBody, Object.class));
        
        test.info("Status Code: " + statusCode);
        test.info("<pre>" + prettyJson + "</pre>");
		
	}
	
	@AfterSuite
	public void tearDownReport() {
	    extent.flush(); 
	}

}
