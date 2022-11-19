import java.util.List;

public class OrderGenerator {
    public static Order getOrderWithBlackColor() {

        return new Order(
                "Иван",
                "Иванов",
                "Москва Ленин 2",
                "Лубянка",
                "8999999999999",
                "3",
                "2022-11-19",
                "Привет",
                List.of("BLACK")
        );
    }
    public static Order getOrderWithGrayColor() {

        return new Order(
                "Иван",
                "Иванов",
                "Москва Ленин 2",
                "Лубянка",
                "8999999999999",
                "3",
                "2022-11-19",
                "Привет",
                List.of("GRAY")
        );
    }
    public static Order getOrderWithTwoColor() {

        return new Order(
                "Иван",
                "Иванов",
                "Москва Ленин 2",
                "Лубянка",
                "8999999999999",
                "3",
                "2022-11-19",
                "Привет",
                List.of("GRAY", "BLACK")
        );
    }
    public static Order getOrderWithoutColor() {

        return new Order(
                "Иван",
                "Иванов",
                "Москва Ленин 2",
                "Лубянка",
                "8999999999999",
                "3",
                "2022-11-19",
                "Привет",
                null
        );
    }
}