package patrisp.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import patrisp.readProperties.ConfigProvider;
import patrisp.tests.core.BaseTest;

public class Specifications extends BaseTest{
    public static String API_URL = ConfigProvider.URL + "web/index.php";
    public static RequestSpecification requestSpecification(String url, ContentType contentType) {
        String browserCookie = driver.manage().getCookieNamed("orangehrm").getValue();

        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(contentType)
                .addCookie("orangehrm", browserCookie)
                .build();
    }

    public static ResponseSpecification responseSpecification(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .build();
    }

    public static void installSpecification(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;

    }
}
