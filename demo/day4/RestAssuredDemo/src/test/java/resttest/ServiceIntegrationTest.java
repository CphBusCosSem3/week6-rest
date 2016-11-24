/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resttest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.parsing.Parser;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk
 */
public class ServiceIntegrationTest {

    public ServiceIntegrationTest() {
    }

    @BeforeClass
    public static void setUpBeforeAll() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8084;
        RestAssured.basePath = "/Test1";
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void serverIsRunning() {
        given().
                when().get().
                then().
                statusCode(200);
    }

    @Test
    public void addOperation() {
        given().pathParam("n1", 2).pathParam("n2", 2).
                when().get("/api/calculator/add/{n1}/{n2}").
                then().
                statusCode(200).
                body("result", equalTo(4), "operation", equalTo("2 + 2"));
    }

    @Test
    public void subOperation() {
        //given().pathParam("n1", 2).pathParam("n2", 2).
        //when().get("/api/calculator/sub/{n1}/{n2}").
        given().when().get("/api/calculator/sub/2/2").
                then().statusCode(200).
                body("result", equalTo(0), "operation", equalTo("2 - 2"));
    }

    @Test
    public void mulOperation() {
        given().when().get("/api/calculator/mul/2/3").then()
                .statusCode(200).body("result", equalTo(6), "operation", equalTo("2 * 3"));
    }

    @Test
    public void divOperation() {
        given().when().get("/api/calculator/div/10/2").then()
                .statusCode(200).body("result", equalTo(5), "operation", equalTo("10 / 2"));
    }

    @Test
    public void addOperationWrongArguments() {
        given().pathParam("n1", 2).pathParam("n2", 2.2).
                when().get("/api/calculator/add/{n1}/{n2}").
                then().
                statusCode(400).
                body("code", equalTo(400), "message", equalTo("Illegal parameters, For input string: \"2.2\""));
    }

    @Test
    public void operationNonExistRoute() {
        given().pathParam("n1", 2).pathParam("n2", 2).
                when().get("/api/calculator/hokuspokus/{n1}/{n2}").
                then().
                statusCode(404).
                body("code", equalTo(404), "message", equalTo("Not Found"));
    }

    @Test
    public void operationDivByZero() {
        given().pathParam("n1", 2).pathParam("n2", 0).
                when().get("/api/calculator/div/{n1}/{n2}").
                then().
                statusCode(500).
                body("code", equalTo(500), "message", equalTo("/ by zero"));
    }
}
