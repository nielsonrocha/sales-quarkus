package com.github.nielsonrocha.sales.customer.stepsdef;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.nielsonrocha.sales.customer.CustomerIntegrationTest;
import com.github.nielsonrocha.sales.customer.entity.Customer;
import io.cucumber.java8.Pt;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerSteps extends CustomerIntegrationTest implements Pt {

    @LocalServerPort
    private int port;

    List<Customer> customers = new ArrayList<>();
    Response response;

    protected String baseUrl() {
        return "http://localhost:" + port;
    }

    public CustomerSteps() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

        Dado("Usuário deseja criar um cliente com os seguintes atributos", (io.cucumber.datatable.DataTable customerDt) -> {
            customers = customerDt.asList(Customer.class);
            assertThat(customers.size()).isPositive();
        });

        Quando("Envio uma requesição HTTP", () -> {
            response = with()
                    .contentType(ContentType.JSON)
                    .body(customers.get(0))
                    .when()
                    .request("POST", baseUrl()+"/customers");
        });

        Então("Recebo uma resposta válida com codigo {int}", (Integer int1) -> {
            response.then().statusCode(int1);
        });

        DataTableType((Map<String, String> row) -> mapper.convertValue(row, Customer.class));

    }

}
