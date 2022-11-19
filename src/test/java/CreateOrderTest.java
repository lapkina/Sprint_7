import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderClient orderClient;
    private Order order;
    private int track;
    private int statusCode;

    public CreateOrderTest(Order order, int statusCode) {
        this.order = order;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {

        return new Object[][]{
                {OrderGenerator.getOrderWithBlackColor(), SC_CREATED},
                {OrderGenerator.getOrderWithGrayColor(), SC_CREATED},
                {OrderGenerator.getOrderWithTwoColor(), SC_CREATED},
                {OrderGenerator.getOrderWithoutColor(), SC_CREATED},
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Создание заказа. Teст с параметризацией")
    @Description(" Проверка создания заказа. Можно указать один из цветов — BLACK или GREY или можно указать оба цвета.")
    public void orderCanBeCreated() {

        OrderClient orderClient = new OrderClient();
        ValidatableResponse responseCreate = orderClient.postOrder(order);
        track = responseCreate.extract().path("track");
        int actualStatusCode = responseCreate.extract().statusCode();
        assertEquals( statusCode, actualStatusCode);
    }

    @After
    public void cleanUp() {
        orderClient.deleteOrder(track);
    }
}