package com.example.GoFTecno.api;

import com.example.GoFTecno.ConfigIntegration;
import com.example.GoFTecno.exceptions.Standard;
import com.example.GoFTecno.models.Departamento;
import com.example.GoFTecno.models.Empregado;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepartamentoControllerTest extends ConfigIntegration {

    private RequestSpecification requisicao;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    private void prepararRequisicao() {
        registryEntity(Departamento.class);
        registryEntity(Empregado.class);

        requisicao = new RequestSpecBuilder().setBasePath("/departamentos").setPort(porta)
                .setAccept(ContentType.JSON).setContentType(ContentType.JSON).log(LogDetail.ALL)
                .build();
    }

    @AfterEach
    public void tearDown() {
        clearTables();
    }

    @Test
    public void deveriaReceberMensagemOK() {
        given().spec(requisicao).param("pagina", 0).param("tamanho", 1).expect().statusCode(HttpStatus.SC_OK)
                .when().get();
    }

    @Test
    public void deveriaCriarUmDepartamento() throws JsonProcessingException {
        Departamento departamento = dadoUmDepartamentoComTodosOsDados();

        Departamento response = given().spec(requisicao).body(objectMapper.writeValueAsString(departamento))
                .when().post().then().statusCode(HttpStatus.SC_CREATED).extract()
                .as(Departamento.class);

        departamento.setNumero(response.getNumero());
        assertEquals(departamento, response);
    }

    @Test
    public void deveriaNaoCriarUmDepartamentoComNomeVazio() throws JsonProcessingException {
        Departamento departamento = dadoUmDepartamentoComTodosOsDados();
        departamento.setNome("");

        Standard response = given().spec(requisicao).body(objectMapper.writeValueAsString(departamento)).when()
                .post().then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).extract()
                .as(Standard.class);

        assertThat(response.getMessage()).contains("campo nome não pode ser vazio");
    }

    @Test
    public void deveriaNaoCriarUmDepartamentoComNomeNulo() throws JsonProcessingException {
        Departamento departamento = dadoUmDepartamentoComTodosOsDados();
        departamento.setNome("");

        Standard response = given().spec(requisicao).body(objectMapper.writeValueAsString(departamento)).when()
                .post().then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).extract()
                .as(Standard.class);

        assertThat(response.getMessage()).contains("campo nome não pode ser vazio");
    }

    @Test
    public void deveriaNaoCriarUmDepartamentoComNomeJaCadastradoEmOutro() throws JsonProcessingException {

        Departamento departamento = given().spec(requisicao)
                .body(objectMapper.writeValueAsString(dadoUmDepartamentoComTodosOsDados())).when()
                .post().then().statusCode(HttpStatus.SC_CREATED).extract().as(Departamento.class);

        Standard standard = given().spec(requisicao)
                .body(objectMapper.writeValueAsString(dadoUmDepartamentoComTodosOsDados())).when()
                .post().then().statusCode(HttpStatus.SC_CONFLICT).extract().as(Standard.class);

        assertThat(standard.getMessage())
                .isEqualTo("Departamento com nome " + departamento.getNome() + " já existe");

    }

    @Test
    public void deveriaEditarUmDepartamento() throws JsonProcessingException {
        Departamento departamento = dadoUmDepartamentoComTodosOsDados();

        Departamento esperado = given().spec(requisicao).body(objectMapper.writeValueAsString(departamento))
                .when().post().then().statusCode(HttpStatus.SC_CREATED).extract()
                .as(Departamento.class);

        esperado.setNome("ADM");
        Departamento atual = given().spec(requisicao).body(objectMapper.writeValueAsString(esperado))
                .pathParam("id", esperado.getNumero()).when().put("/{id}").then()
                .statusCode(HttpStatus.SC_OK).extract().as(Departamento.class);

        assertEquals(esperado, atual);
    }

    @Test
    public void deveriaNaoEditarUmDepartamentoInexistente() throws JsonProcessingException {
        Departamento departamento = dadoUmDepartamentoComTodosOsDados();

        Departamento esperado = given().spec(requisicao).body(objectMapper.writeValueAsString(departamento))
                .when().post().then().statusCode(HttpStatus.SC_CREATED).extract()
                .as(Departamento.class);

        Long id = esperado.getNumero() + 1;
        esperado.setNome("ADM");
        Standard standard = given().spec(requisicao).body(objectMapper.writeValueAsString(esperado))
                .pathParam("id", esperado.getNumero() + 1).when().put("/{id}").then()
                .statusCode(HttpStatus.SC_NOT_FOUND).extract().as(Standard.class);

        assertThat(standard.getStatus()).isEqualTo(404);
        assertThat(standard.getMessage())
                .isEqualTo("Departamento com id " + id + " não encontrado");
    }

    @Test
    public void deveriaNaoEditarUmDepartamentoComNomeJaCadastradoEmOutro() throws JsonProcessingException {
        Departamento departamento = dadoUmDepartamentoComTodosOsDados();

        given().spec(requisicao).body(objectMapper.writeValueAsString(departamento)).when().post().then()
                .statusCode(HttpStatus.SC_CREATED);

        departamento.setNome("ADM");
        Departamento departamentoADM = given().spec(requisicao)
                .body(objectMapper.writeValueAsString(departamento)).when().post().then()
                .statusCode(HttpStatus.SC_CREATED).extract().as(Departamento.class);

        departamentoADM.setNome("RH");
        Standard standard = given().spec(requisicao).body(objectMapper.writeValueAsString(departamentoADM))
                .pathParam("id", departamentoADM.getNumero()).when().put("/{id}").then()
                .statusCode(HttpStatus.SC_CONFLICT).extract().as(Standard.class);

        assertThat(standard.getStatus()).isEqualTo(409);
        assertThat(standard.getMessage()).isEqualTo("Departamento com nome RH já existe");
    }

    @Test
    public void deveriaDeletarUmDepartamento() throws JsonProcessingException {
        Departamento departamento = dadoUmDepartamentoComTodosOsDados();

        Departamento esperado = given().spec(requisicao).body(objectMapper.writeValueAsString(departamento))
                .when().post().then().statusCode(HttpStatus.SC_CREATED).extract()
                .as(Departamento.class);

        given().spec(requisicao).pathParam("id", esperado.getNumero()).when().delete("/{id}").then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void deveriaNaoDeletarUmDepartamentoInexistente() throws JsonProcessingException {
        Departamento departamento = dadoUmDepartamentoComTodosOsDados();

        Departamento esperado = given().spec(requisicao).body(objectMapper.writeValueAsString(departamento))
                .when().post().then().statusCode(HttpStatus.SC_CREATED).extract()
                .as(Departamento.class);

        Long id = esperado.getNumero() + 1;
        Standard standard = given().spec(requisicao).pathParam("id", esperado.getNumero() + 1).when()
                .delete("/{id}").then().statusCode(HttpStatus.SC_NOT_FOUND).extract()
                .as(Standard.class);

        assertThat(standard.getStatus()).isEqualTo(404);
        assertThat(standard.getMessage()).isEqualTo("Departamento com id " + id + " não encontrado");
    }

    @Test
    public void deveriaRetornarUmDepartamento() throws JsonProcessingException {
        Departamento departamento = dadoUmDepartamentoComTodosOsDados();

        Departamento esperado = given().spec(requisicao).body(objectMapper.writeValueAsString(departamento))
                .when().post().then().statusCode(HttpStatus.SC_CREATED).extract()
                .as(Departamento.class);

        Departamento atual = given().spec(requisicao).pathParam("id", esperado.getNumero()).when().get("/{id}")
                .then().statusCode(HttpStatus.SC_OK).extract().as(Departamento.class);

        assertEquals(esperado, atual);
    }

    private Departamento dadoUmDepartamentoComTodosOsDados() {
        Departamento departamento = new Departamento();
        departamento.setNome("RH");
        return departamento;
    }
}