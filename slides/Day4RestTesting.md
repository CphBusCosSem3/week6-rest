
##Day4: Testing with Rest Assured
### Studymaterial
[Rest Assured tutorial](https://semaphoreci.com/community/tutorials/testing-rest-endpoints-using-rest-assured)  


### Refined version
[Extended version with more explanations](https://github.com/CphBusCosSem3/Exercises/blob/master/SP/SP5/REST-test-gettingStarted.pdf)
0. Check that Maven is installed:
	1. From terminal: `mvn -v`
	2. If not install from here: 
1. Clone the start project code [here](https://github.com/Lars-m/resassuredEx1.git)  
2. Maybe rename the project to something like: RestAssuredEx  
3. Add Junit test for all 4 calculator methods  
4. And a Junit for divide by 0 exception  
5. Add this to the POM.XML   

```
<dependency>
    <groupId>com.jayway.restassured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>2.9.0</version>
</dependency>
```  
6. Create new Junit test called: ServiceIntegrationTest  
	- create package first - then rightclick -> new -> Unit Tests -> Junit  
	- Add these to the new test class imports:
	```
    import io.restassured.RestAssured;
    import static io.restassured.RestAssured.*;
    import io.restassured.parsing.Parser;
    import static org.hamcrest.Matchers.*;
    ```  
    1. Add the following to the pom.xml:
    ```
    <dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>rest-assured</artifactId>
      <version>3.0.1</version>
      <scope>test</scope>
    </dependency>
    ```
7. Specify  
    1. base URI URI (what host you are targeting)   
    2. default Parser (how is data sent to you, ie. JSON)   
    3. and base path (what is the root of the REST API)  
    4. Insert this in the new test class:  
 
```
    @BeforeClass
    public static void setUpBeforeAll() {
      RestAssured.baseURI = "http://localhost";
      RestAssured.port = 8080;
      RestAssured.basePath = "/Test1";
      RestAssured.defaultParser = Parser.JSON;
    }
```
	5. change port if you need to.  
8. Test cases for rest assured  
	1. Add this test case:  
	```  
    @Test
    public void serverIsRunning() {
      given().
     when().get().
     then().
     statusCode(200);
    }
    ```  
	2. Alternatively: (if the BeforeClass method was not there.  
	```  
    @Test
    public void serverIsRunningV2() {
    given().when().get("http://localhost:8080/Test2/").then().statusCode(200);
    }  
    ```  
	3. What the test does is making a get request to the root of the web app and check if status code is 200.  
	
9. Start tomcat server and run the test  

### Create test for the add method
1. Add a test case
```
    @Test
    public void addOperation() {
     given().pathParam("n1", 2).pathParam("n2", 2).
     when().get("/api/calculator/add/{n1}/{n2}").
     then().
     statusCode(200).
     body("result", equalTo(4), "operation", equalTo("2 + 2"));
    }
``` 
2. Run the test
3. Create tests for sub, mul, div as well
4. Create tests for:
	1. Non existing routes
	2. Illegal arguments
	3. divide by 0
	4. Example:
	```
    @Test
    public void addOperationWrongArguments() {
      given().pathParam("n1", 2).pathParam("n2", 2.2).
      when().get("/api/calculator/add/{n1}/{n2}").
      then().
      statusCode(400).
      body("code", equalTo(400));
    }
    ```


### Maven and Testing
- Open a terminal
- cd to the root of a maven project
- `mvn <phasename>`
  - Possible phases:  
    - **validate**  
	  - validate the project is correct and all necessary information is available  
    - **compile**  
      - compile the source code of the project   
    - **test**  
      - test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed  
    - **package**  
       - take the compiled code and package it in its distributable format, such as a JAR.  
    - **verify**  
       - run any checks on results of integration tests to ensure quality criteria are met  
    - **install**  
       - install the package into the local repository, for use as a dependency in other   projects locally  
    - **deploy**  
       - done in the build environment, copies the final package to the remote repository for sharing with other developers and projects.  


### Maven and Unit tests
- 'Clean and build' or 'run' will run all tests (in maven)
	- That execution stops if the tests fail
- Rest assured tests are Integration tests (not unit tests)
- They should be executed in the verify phase (not in the test phase)
- We need to exclude these tests in the test phase
- Add this to the pom.xml:  
```
    <plugin>
	    <groupId>org.apache.maven.plugins</groupId>
	    <artifactId>maven-surefire-plugin</artifactId>
	    <version>2.12.1</version>
	    <configuration>
	       <excludes>
	          <exclude>**/*IntegrationTest*</exclude>
	       </excludes>
	    </configuration>
    </plugin>
```
- After deploying the project
	- Then comment out the <exclude> and run the tests



### Another approach
- Maven plugin for Integration tests:
- Add this to pom.xml in the plugin section:
```
<plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-failsafe-plugin</artifactId>
   <version>2.12.4</version>
   <configuration>
     <includes>
       <include>**/*IntegrationTest*</include>
     </includes>
   </configuration>
   <executions>
     <execution>
       <goals>
         <goal>integration-test</goal>
         <goal>verify</goal>
       </goals>
     </execution>
   </executions>
</plugin>
```
- run with: `mvn verify`


### Start ans Stop an embedded Tomcat with Maven
In above examples we had to start Tomcat before running our tests  
Now we want Maven to start and stop the server before and after testing.
This embedded Tomcat runs default on port: 8080
- Put this in pom.xml:   

```
<plugin>  
   <groupId>org.apache.tomcat.maven</groupId>  
   <artifactId>tomcat7-maven-plugin</artifactId>  
   <version>2.2</version>  
   <configuration>  
     <path>/</path>
   </configuration>
   <executions>
     <execution>
       <id>start-tomcat</id>
       <phase>pre-integration-test</phase>
       <goals>
         <goal>run</goal>
       </goals>
       <configuration>
         <fork>true</fork>
       </configuration>
     </execution>
     <execution>
       <id>stop-tomcat</id>
       <phase>post-integration-test</phase>
       <goals>
         <goal>shutdown</goal>
       </goals>
     </execution>
   </executions>
</plugin>
``` 
- Change rest assured tests to run on port 8080
- run: `mvn verify` from console at project root folder


### See the full pom.xml on github
[Get pom.xml from here](https://github.com/CphBusCosSem3/week6-rest/blob/master/demo/day4/RestAssuredDemo/pom.xml)


