package com.bridgelabz;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BookOrderTest {

    @BeforeTest
    public void setUp() {
        RestAssured.baseURI = "http://3.19.232.27:3000";
        RestAssured.port = 3000;
    }

    @Test
    public void getAllBook() throws ParseException {
        JSONObject object=new JSONObject();
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/getBookDetails");
        int statusCode = response.getStatusCode();
        ResponseBody body = response.getBody();
        JSONObject object2 = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object2.get("message");
        System.out.println("message"+message);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals("Successfully fetched data from DB", message);

    }

    @Test
    public void searchBookByTitle() throws ParseException {
        JSONObject object=new JSONObject();
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .queryParam("title","The Girl in Room 105")
                .get("/searchBookByTitle");
        int statusCode = response.getStatusCode();
        ResponseBody body = response.getBody();
        JSONObject object2 = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object2.get("message");
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals("Successfully fetched data from DB", message);
    }

    @Test
    public void searchBookByEmptyTitle() throws ParseException {
        JSONObject object=new JSONObject();
        Response response=RestAssured.given()
        .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .queryParam("title","")
                .get("/searchBookByTitle");
        int statusCode = response.getStatusCode();
        ResponseBody body = response.getBody();
        JSONObject object2 = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object2.get("message");
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals("Successfully fetched data from DB", message);
    }

    @Test
    public void searchBookByInvalidTitle() throws ParseException {
        JSONObject object=new JSONObject();
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .queryParam("title","Agnipankh")
                .get("/searchBookByTitle");
        int statusCode = response.getStatusCode();
        ResponseBody body = response.getBody();
        JSONObject object2 = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object2.get("message");
        Assert.assertEquals(statusCode, 200);
        //Assert.assertEquals("Error while retrieving books details", message);
    }

    @Test
    public void customerDetails_IfValid_ShouldReturnSuccessMessage() throws ParseException {
        JSONObject object=new JSONObject();
        object.put("id","5e2583871da1780d97a47896");
        object.put("Name","123priya");
        object.put("Phone_Number","9090909090");
        object.put("Pincode", "400403");
        object.put( "Address", "Worli");
        object.put("city", "Goa");
        object.put("LandMark","MUMBAI");
        object.put( "Email", "jitupatil8899@gmail.com");
        object.put("Type", "HOME");
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body(object.toJSONString())
                .post("/customerDetails");
        int statusCode = response.getStatusCode();
        ResponseBody body = response.getBody();
        JSONObject object2 = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object2.get("message");
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals("Registration success", message);

    }

    @Test
    public void customerDetails_ifInvalidDetails_ShouldReturnValideMessage() throws ParseException {
        JSONObject object=new JSONObject();
        object.put("id","5e2583871da1780d97a47896");
        object.put("Name","priya");
        object.put("Phone_Number","9090909090");
        object.put("Pincode", "400403");
        object.put( "Address", "Worli");
        object.put("city", "Goa");
        object.put("LandMark","MUMBAI");
        object.put( "Email", "jitupatil8899@gmail.com");
        object.put("Type", "");
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body(object.toJSONString())
                .post("/customerDetails");
        int statusCode = response.getStatusCode();
        ResponseBody body = response.getBody();
        JSONObject object2 = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object2.get("message");
        Assert.assertEquals(statusCode, 400);
        Assert.assertEquals("Please enter proper inputs", message);
    }

    @Test
    public void sortBookByPrice_IndesendingOrder_shoulReturnBookList() throws ParseException {
        JSONObject object=new JSONObject();
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body(object.toJSONString())
                .get("/sortBookByAttribute?attribute=1");
        int statusCode = response.getStatusCode();
        ResponseBody body = response.getBody();
        JSONObject object2 = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object2.get("message");
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals("Successfully fetched sorted data from DB", message);
    }

    @Test
    public void sortBookByPrice_InassendingOrder_shoulReturnBookList() throws ParseException {
        JSONObject object=new JSONObject();
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body(object.toJSONString())
                .get("/sortBookByAttribute?attribute=-1");
        int statusCode = response.getStatusCode();
        ResponseBody body = response.getBody();
        JSONObject object2 = (JSONObject) new JSONParser().parse(body.prettyPrint());
        String message = (String) object2.get("message");
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals("Successfully fetched sorted data from DB", message);
    }

}






