import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class CreateOrderTrackNumberTest {
    private OrderClient orderClient;
    private int track;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Создание заказа")
    @Description(" Проверяем, что при создании заказа тело ответа содержит track")
    public void responseBodyContainsTrack() {
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

        ValidatableResponse responseCreate = orderClient.postOrder(order);
        track = responseCreate.extract().path("track");

        int courierCreated = responseCreate.extract().path("track");
        assertEquals(track, courierCreated);
    }

    @After
    public void cleanUp() {
        orderClient.deleteOrder(track);
    }
}