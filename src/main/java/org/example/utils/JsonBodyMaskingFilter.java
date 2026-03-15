package org.example.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.http.Headers;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/// A filter that masks sensitive fields like password and email in request and response bodies.
public class JsonBodyMaskingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger("RestAssuredLogger");
    private static final ObjectMapper mapper = new ObjectMapper();
    private final List<String> fieldsToMask = Arrays.asList("password", "email", "accessToken");
    private final static String MASK = "[****]";

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        Set<String> blacklistedHeaders = requestSpec.getConfig().getLogConfig().blacklistedHeaders();

        StringBuilder requestLogBuilder = new StringBuilder(
                "\nRequest method: " + requestSpec.getMethod() +
                        "\nRequest URI: " + requestSpec.getURI() +
                        "\nHeaders: \n" + maskHeaders(requestSpec.getHeaders(), blacklistedHeaders)
        );

        if (requestSpec.getBody() != null) {
            String requestBody = requestSpec.getBody().toString();
            requestLogBuilder
                    .append("\nBody:\n")
                    .append(maskSensitiveFields(requestBody));
        }
        logger.info(requestLogBuilder);

        Response response = ctx.next(requestSpec, responseSpec);
        StringBuilder responseLogBuilder = new StringBuilder(
                "\n" + response.getStatusLine() +
                        "\n" + maskHeaders(response.getHeaders(), blacklistedHeaders)
        );

        String responseBody = response.getBody().asString();
        if (responseBody != null && !responseBody.trim().isEmpty()) {
            responseLogBuilder
                    .append("\nBody:\n")
                    .append(maskSensitiveFields(responseBody));
        }
        responseLogBuilder.append("\n==================\n");
        logger.info(responseLogBuilder);

        return response;
    }

    /// Parses the JSON string into a tree, masks it, and prints it back to a string.
    private String maskSensitiveFields(String body) {
        try {
            JsonNode rootNode = mapper.readTree(body);
            maskNode(rootNode);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (Exception e) {
            return body;
        }
    }

    /// Recursively traverses the JSON tree looking for blacklisted keys.
    private void maskNode(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;

            for (Map.Entry<String, JsonNode> field : objectNode.properties()) {
                if (fieldsToMask.contains(field.getKey())) {
                    objectNode.put(field.getKey(), MASK);
                } else {
                    maskNode(field.getValue());
                }
            }
        } else if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (JsonNode arrayElement : arrayNode) {
                maskNode(arrayElement);
            }
        }
    }

    /// Recursively traverses the headers looking for blacklisted keys.
    private String maskHeaders(Headers headers, Set<String> blacklistedHeaders) {
        if (headers == null || !headers.exist()) {
            return "";
        }
        return headers.asList().stream()
                .map(header -> {
                    if (blacklistedHeaders.stream().anyMatch(bh -> bh.equalsIgnoreCase(header.getName()))) {
                        return header.getName() + ": " + MASK;
                    }
                    return header.toString();
                })
                .collect(Collectors.joining("\n"));
    }
}
