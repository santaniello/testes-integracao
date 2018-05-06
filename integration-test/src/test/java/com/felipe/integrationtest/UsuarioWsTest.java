package com.felipe.integrationtest;

import com.felipe.integrationtest.models.Usuario;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

/*
*  Xml Testado
*
*  <list>
*     <usuario>
*        <id>1</id>
*        <nome>Mauricio Aniche</nome>
*        <email>mauricio.aniche@caelum.com.br</email>
*     </usuario>
*     <usuario>
*        <id>2</id>
*        <nome>Guilherme Silveira</nome>
*        <email>guilherme.silveira@caelum.com.br</email>
*     </usuario>
* </list>
*
*   Por default, o Rest Assured acessa localhost:8080, se quisermos alterar precisamos passar os dois parametros abaixo...
*   RestAssured.baseURI = "http//172.16.0.92";
*   RestAssured.port = 80;
*
* */
@Category(IntegrationTest.class)
public class UsuarioWsTest {
    @Test
    public void deveRetornarListaDeUsuariosComXml(){
        // XmlPath é o xml de retorno... se fosse Json, seria JsonPath
        XmlPath path = given()
                .header("Accept", "application/xml")
                .get("/usuarios")
                .andReturn().xmlPath();

        // Obtendo os valores dos nós do xml...
        Usuario usuario1 = path.getObject("list.usuario[0]", Usuario.class);
        Usuario usuario2 = path.getObject("list.usuario[1]", Usuario.class);

        Usuario esperado1 = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Usuario esperado2 = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");

        assertEquals(esperado1, usuario1);
        assertEquals(esperado2, usuario2);

        // Realizando a mesma coisa que o passo acima só que convertendo direto para uma lista

        List<Usuario> usuarios = path.getList("list.usuario",Usuario.class);

        assertEquals(esperado1, usuarios.get(0));
        assertEquals(esperado2, usuarios.get(1));

        System.out.println(path.prettyPrint());
    }

    @Test
    public void deveRetornarListaDeUsuariosComResponse(){
        // Given é utilizado para passar um header na requisição...
        // XmlPath é o xml de retorno... se fosse Json, seria JsonPath
        Response response = given()
                .header("Accept", "application/xml")
                .get("/usuarios");

        assertEquals(200, response.getStatusCode());

        XmlPath path = response.getBody().xmlPath();

        // Obtendo os valores dos nós do xml...

        Usuario usuario1 = path.getObject("list.usuario[0]", Usuario.class);
        Usuario usuario2 = path.getObject("list.usuario[1]", Usuario.class);

        Usuario esperado1 = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Usuario esperado2 = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");

        assertEquals(esperado1, usuario1);
        assertEquals(esperado2, usuario2);

        // Realizando a mesma coisa que o passo acima só que convertendo direto para uma lista

        List<Usuario> usuarios = path.getList("list.usuario",Usuario.class);

        assertEquals(esperado1, usuarios.get(0));
        assertEquals(esperado2, usuarios.get(1));
        System.out.println(path.prettyPrint());
    }

    @Test
    public void deveRetornarListaDeUsuariosComJson(){
        JsonPath path = given()
                .header("Accept", "application/json")
                /*
                 * O param envia o parâmetro via QueryString se o
                 * método fot get e pelo corpo da requisição se o meodo for post
                 */
                .param("usuario.id",1)
                .get("/usuarios/show")
                .andReturn().jsonPath();

        Usuario usuario = path.getObject("usuario", Usuario.class);
        System.out.println(path.prettyPrint());
    }

    @Test
    public void deveAdicionarUmUsuarioComXml(){
        Usuario joao = new Usuario("Joao da Silva", "joao@hotmail.com");
        XmlPath path = given()
                .header("Accept", "application/xml") // O RestAssured deserializa nossos dados baseado no content type....
                .contentType("application/xml") // O RestAssured serializa nossos dados baseado no content type....
                .body(joao)
        .expect() // serve apenas para leitura do código...
                .statusCode(200) // validando Http Status pelo Rest Assured

        .when() // serve apenas para leitura do código...
                .post("/usuarios")
        .andReturn() // serve apenas para leitura do código...
                .xmlPath();

        Usuario resposta = path.getObject("usuario",Usuario.class);

        assertEquals("Joao da Silva", resposta.getNome());
        assertEquals("joao@hotmail.com", resposta.getEmail());
    }

    /* Testando cookie com RestAssured */
    @Test
    public void deveGerarUmCookie() {
        expect()
                .cookie("rest-assured", "funciona")
                .when()
                .get("/cookie/teste");
    }

    /* Testando Header com RestAssured*/

    @Test
    public void deveGerarUmHeader() {
        expect()
                .header("novo-header", "abc")
                .when()
                .get("/cookie/teste");
    }

}
