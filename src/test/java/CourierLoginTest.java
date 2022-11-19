import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CourierLoginTest {
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:курьер может авторизоваться")
    public void courierCanLogIn() {

        Courier courier = new Courier("TestTestovich", "test1234", "Testik");
        ValidatableResponse responseCreate = courierClient.create(courier);


        Credentials credential = new Credentials("TestTestovich", "test1234");
        ValidatableResponse responseLogin = courierClient.login(credential);
        id = responseLogin.extract().path("id");
        int statusCode = responseLogin.extract().statusCode();
        assertEquals(200, statusCode);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:система вернёт ошибку, если неправильно указать логин")
    public void systemWillReturnErrorIfTheLoginOrPasswordIncorrectLogin() {
        Courier courier = new Courier("kotik", "test1234", "Testik");
        ValidatableResponse responseCreate = courierClient.create(courier);

        Credentials credential = new Credentials("kotik", "test1234");
        ValidatableResponse responseLogin = courierClient.login(credential);

        int statusCode = responseLogin.extract().statusCode();
        assertEquals(404, statusCode);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:система вернёт ошибку, если неправильно указать пароль")
    public void systemWillReturnErrorIfTheLoginOrPasswordIncorrectPassword() {
        Courier courier = new Courier("TestTestovich", "test1234", "Testik");
        ValidatableResponse responseCreate = courierClient.create(courier);

        Credentials credential = new Credentials("TestTestovich", "99999");
        ValidatableResponse responseLogin = courierClient.login(credential);

        int statusCode = responseLogin.extract().statusCode();
        assertEquals(404, statusCode);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:успешный запрос возвращает id")
    public void successfulRequestReturnsId() {
        Courier courier = new Courier("TestKotik", "test1234", "TestKotik");
        ValidatableResponse responseCreate = courierClient.create(courier);

        Credentials credential = new Credentials("TestKotik", "test1234");
        ValidatableResponse responseLogin = courierClient.login(credential);
        id = responseLogin.extract().path("id");
        int courierCreated = responseLogin.extract().path("id");
        assertEquals(id, courierCreated);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:если нет какого-то поля ,то запрос возвращает ошибку")
    public void ifThereIsNoFieldRequestReturnsError() {
        Courier courier = new Courier("TestTestovich", "test1234", "Testik");
        ValidatableResponse responseCreate = courierClient.create(courier);

        Credentials credential = new Credentials("", "test1234");
        ValidatableResponse responseLogin = courierClient.login(credential);

        int statusCode = responseLogin.extract().statusCode();
        String courierCreated = responseLogin.extract().path("message");
        assertEquals("Недостаточно данных для входа", courierCreated);
        assertEquals(400, statusCode);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:для авторизации нужно передать все обязательные поля")
    public void forAuthorizationYouNeedPassAllRequiredFields (){
        Courier courier = new Courier("TestTestovich", "test1234", "Testik");
        ValidatableResponse responseCreate = courierClient.create(courier);

        Credentials credential = new Credentials("TestTestovich", "");
        ValidatableResponse responseLogin = courierClient.login(credential);

        int statusCode = responseLogin.extract().statusCode();
        assertEquals(400, statusCode);
    }
    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка:если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void ifYouLogInUnderNonExistentUserRequestReturnsError (){

        Credentials credential = new Credentials("kiskiskis", "1234456");
        ValidatableResponse responseLogin = courierClient.login(credential);

        int statusCode = responseLogin.extract().statusCode();
        String courierCreated = responseLogin.extract().path("message");
        assertEquals("Учетная запись не найдена", courierCreated);
        assertEquals(404, statusCode);
    }
    @After
    public void cleanUp() {
        courierClient.delete(id);
    }
}
