package patrisp.api.requestmethod;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import patrisp.api.Specifications;

import static io.restassured.RestAssured.given;

public class LoginRequests extends Specifications{
    public String getAuthCookie(String username, String password) {
        Specifications.installSpecification(Specifications.requestSpecification(URL, ContentType.HTML), Specifications.responseSpecification(200));
        Response tokenResponse = given()
                .when()
                .get("/auth/login");

        String html = tokenResponse.getBody().asString();
        Document document = Jsoup.parse(html);
        String token = document.select("auth-login").attr(":token");
        token = token.substring(1, token.length() - 1);
        String cookie = tokenResponse.getCookie("orangehrm");

        Specifications.installSpecification(Specifications.requestSpecification(URL, ContentType.URLENC), Specifications.responseSpecification(302));
        Response authResponse = given()
                .header("Cookie", "orangehrm=" + cookie)
                .formParams(
                        "_token", token,
                        "username", username,
                        "password", password)
                .when()
                .post("/auth/validate");
        authResponse.then().statusCode(302);
        return authResponse.getCookie("orangehrm");
    }
}
