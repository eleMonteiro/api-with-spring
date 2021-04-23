package com.example.GoFTecno.api;

import com.example.GoFTecno.models.Departamento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DepartamentoControllerTest {

    @Value("${server.port}")
    private int porta;

    private RequestSpecification requisicao;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    private void prepararRequisicao() {
        requisicao = new RequestSpecBuilder().setBasePath("/departamentos").setPort(porta).setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON).build();
    }

    @Test
    public void deveriaReceberMensagemOK() {
        given().spec(requisicao).param("pagina", 0).param("tamanho", 1).expect().statusCode(HttpStatus.SC_OK).when()
                .get();
    }

    @Test
    public void deveriaCriarUmDepartamento() throws JsonProcessingException {
        Departamento departamento = dadoUmDepartamentoComTodosOsDados("UM");

        Departamento response = given().spec(requisicao)
                .body(objectMapper.writeValueAsString(departamento)).when().post().then()
                .statusCode(HttpStatus.SC_CREATED).extract().as(Departamento.class);

        departamento.setDnumero(response.getDnumero());
        assertEquals(departamento, response);
    }

    @Test
    public void deveriaRetornarUmDepartamento() throws JsonProcessingException {
        Departamento departamento = dadoUmDepartamentoComTodosOsDados("DOIS");

        Departamento esperado = given().spec(requisicao)
                .body(objectMapper.writeValueAsString(departamento)).when().post().then()
                .statusCode(HttpStatus.SC_CREATED).extract().as(Departamento.class);

        Departamento atual = given().spec(requisicao)
                .pathParam("id", esperado.getDnumero()).when().get("/{id}").then().extract()
                .as(Departamento.class);

        assertEquals(esperado, atual);
    }

    private Departamento dadoUmDepartamentoComTodosOsDados(String dnome) {
        Departamento departamento = new Departamento();
        departamento.setDnome(dnome);
        return departamento;
    }
}