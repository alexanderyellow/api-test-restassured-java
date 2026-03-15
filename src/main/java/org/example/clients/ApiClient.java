package org.example.clients;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import org.example.model.Session;
import org.example.utils.JsonBodyMaskingFilter;

/// Client for making API requests.
public class ApiClient {
    private final RequestSpecification requestSpec;
    private final Session session;

    public ApiClient(String baseUrl, Session session) {
        this.session = session;
        LogConfig logConfig = LogConfig.logConfig()
                .blacklistHeader("Authorization");

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setConfig(RestAssuredConfig.config().logConfig(logConfig))
                .setContentType(ContentType.JSON)
                .addFilter(new JsonBodyMaskingFilter())
                .addFilter(new AllureRestAssured());

        this.requestSpec = builder.build();
    }

    public Header getAuthorizationHeader() {
        String token = session.getAccessToken();

        if (token != null && !token.isEmpty()) {
            return new Header("Authorization", "Bearer " + token);
        }

        return null;
    }

    public RequestSpecification getRequestSpec() {
        RequestSpecification request = RestAssured.given()
                .spec(requestSpec);

        if (getAuthorizationHeader() != null) {
            request.header(getAuthorizationHeader());
        }

        return request;
    }

}
