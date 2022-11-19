import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class CreateCourierTest {
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка: можно создать курьера")
    public void courierCanBeCreated(){
        Courier courier = new Courier("kotik-kokosik", "12345", "kotik");

        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));  // получили логин
        id = responseLogin.extract().path("id"); //вернет значение из json

        int statusCode = responseCreate.extract().statusCode();
        assertEquals(201, statusCode);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка:запрос возвращает правильный код ответа")
    public void requestReturnsCorrectResponseCode201(){
        Courier courier = new Courier("kotik-kokosik", "123456", "kotik");

        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");

        int statusCode = responseCreate.extract().statusCode();
        assertEquals(201, statusCode);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка:успешный запрос возвращает ok: true")
    public void successfulRequestReturnsOkTrue(){
        Courier courier = new Courier("kotik-kokosik", "123456", "kotik");

        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");

        boolean isCourierCreated = responseCreate.extract().path("ok");
        assertEquals(true, isCourierCreated);

    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка:нельзя создать двух одинаковых курьеров")
    public void CanNotCreateTwoIdenticalCouriers(){

        Courier courier = new Courier("kotik-kokosik", "123456", "kotik");

        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");

        Courier courier1 = new Courier("kotik-kokosik", "123456", "kotik");
        ValidatableResponse responseCreate1 = courierClient.create(courier1);

        int statusCode = responseCreate1.extract().statusCode();
        assertEquals(409, statusCode);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка:если создать пользователя с логином, который уже есть")
    public void errorWhenCreateIdenticalLogins (){
        Courier courier = new Courier("kotik-kokosik", "123456", "kotik");
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");

        Courier courier1 = new Courier("kotik-kokosik", "123456", "kotik");
        ValidatableResponse responseCreate1 = courierClient.create(courier1);
        int statusCode = responseCreate1.extract().statusCode();
        assertEquals(409, statusCode);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка:чтобы создать курьера, нужно передать в ручку все обязательные поля")
    public void creatingCourierWithOnlyAllFields(){
        Courier courier = new Courier("kotik-kokosik", "123456", "kotik");
        courier.setPassword("");
        ValidatableResponse responseCreate = courierClient.create(courier);

        String courierCreated = responseCreate.extract().path("message");
        int statusCode = responseCreate.extract().statusCode();

        assertEquals("Недостаточно данных для создания учетной записи", courierCreated);
        assertEquals(400, statusCode);
    }
    @After
    public void cleanUp(){
        courierClient.delete(id);
    }
}
