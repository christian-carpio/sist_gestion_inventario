package com.uees.mgra.producto.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
public class RestService {

    @Autowired
    RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RestService.class);


    public String invokeRest(HttpMethod httpMethod, String url, String bodyRequest) {
        return invokeRest(httpMethod, url, bodyRequest, null, null);
    }


    @SuppressWarnings("unchecked")
    public String invokeRest(HttpMethod httpMethod, String url, String bodyRequest, Map<String, String> headerParameters
            , Map<String, Object> pathParameters) {
        URI uri = null;
        HttpEntity<Object> request = null;
        ResponseEntity<String> response = null;
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
            logger.debug("Invocacion de servicio con request: {}", bodyRequest);
            HttpHeaders httpHeader = new HttpHeaders();
            httpHeader.setContentType(MediaType.APPLICATION_JSON_UTF8);

            if (headerParameters != null)
                headerParameters.entrySet().forEach(ph -> httpHeader.set(ph.getKey(), ph.getValue()));

            if (pathParameters != null)
                builder.uriVariables(pathParameters);

            uri = builder.build().toUri();
            logger.info("URL configurada: {}", uri);

            request = new HttpEntity<>(bodyRequest, httpHeader);

            response = restTemplate.exchange(uri, httpMethod, request, String.class);
            return response.getBody();

        } catch (HttpClientErrorException e) {
            logger.error("Error al consumir servicio : Error Especifico: {} - Error General: {}",
                    e.getMessage(), e.getMostSpecificCause());
            String message = "No se ha podido consumir el servicio en la url " + url + ", por favor intente de nuevo mas tarde";
            if (e.getResponseHeaders().get("message") != null && !e.getResponseHeaders().get("message").isEmpty())
                message = e.getResponseHeaders().get("message").get(0);
            if (e.getMessage().contains("\"message\"")) {
                String jsonString = e.getMessage();
                int colonIndex = jsonString.indexOf(':');
                if (colonIndex != -1) {
                    String jsonMessage = jsonString.substring(colonIndex + 1).trim();
                    message = jsonMessage.replaceAll("^\"|\"$", "");
                    JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
                    message = jsonObject.get("message").getAsString();
                }
            }
            throw new RestClientException(message);

        } catch (HttpServerErrorException e) {
            logger.error("Error al consumir servicio: Request Enviado={} - Error Especifico: {} - Error General: {}",
                    (bodyRequest != null ? bodyRequest : "").toString(), e.getMessage(), e.getMostSpecificCause());
            String message = e.getMessage();
            if (Strings.trimToNull(e.getResponseBodyAsString()) != null) {
                try {
                    Map<String, String> resp = gson.fromJson(e.getResponseBodyAsString(), Map.class);
                    message = resp.get("message");
                } catch (Exception exi) {
                    message = e.getResponseBodyAsString();
                }
                logger.error(message);
            }
            logger.error(e.getMessage());
            throw new RestClientException("No se ha podido consumir servicio, verificar error devuelto en invocacion: " + message, e);

        }
    }

    Gson gson = new Gson();

}