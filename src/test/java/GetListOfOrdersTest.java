import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetListOfOrdersTest {
    private OrderClient orderClient;
    private Order order;
    private CourierClient courierClient;
    private int id;
    private int track;
    private int courierId;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Список заказов")
    @Description(" Проверка,что в теле ответа возвращается список заказов")
    public void listOrders(){

        Courier courier = new Courier("mister", "123456", "Mister Kot");
        ValidatableResponse responseCreate = courierClient.create(courier);


        Credentials credential = new Credentials("mister", "123456");
        ValidatableResponse responseLogin = courierClient.login(credential);
        courierId = responseLogin.extract().path("id");

        Order order = new Order(
                "Иван",
                "Иванов",
                "Москва-Колокола",
                "Сокольники",
                "8999999999999",
                "2",
                "2022-11-19",
                "Привет",
                List.of("BLACK"));

        ValidatableResponse responseCreate1 = orderClient.postOrder(order);
        track = responseCreate1.extract().path("track");


        ValidatableResponse responseGetOrdersNumber = orderClient.getOrdersNumber(track);


        ValidatableResponse responsePutOrderId = orderClient.putOrderId(id,courierId);


        ValidatableResponse responseGetOrders = orderClient.getOrders(courierId);


        int statusCode = responseGetOrders.extract().statusCode();
        List<Object> orders = responseGetOrders.extract().path("orders");

        assertNotNull(orders);
        assertEquals(200, statusCode);
    }
    @After
    public void cleanUp() {
        orderClient.deleteOrder(track);
        courierClient.delete(courierId);
    }
}