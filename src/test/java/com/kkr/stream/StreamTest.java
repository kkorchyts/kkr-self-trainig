package com.kkr.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamTest {

    @ParameterizedTest
    @MethodSource("provideOrders")
    void flatMapTest(List<Order> initialList) {
       Item resultItem = initialList.stream()
                .flatMap(order -> order.getItems().stream())
                .filter(item -> Item.CarType.CAR.equals(item.getType()))
                .findFirst().orElseThrow();

       Assertions.assertNotNull(resultItem);
       Assertions.assertEquals("car", resultItem.name);
    }

    @ParameterizedTest
    @MethodSource("provideOrders")
    void mapTest(List<Order> initialList) {
        List<List<Item>> resultList = initialList.stream()
                .map(Order::getItems).
                toList();

        Assertions.assertEquals(initialList.size(), resultList.size());
    }

    static Stream<Arguments> provideOrders() {
        return Stream.of(
                Arguments.of(createSingleOrder()),
                Arguments.of(createMultiOrder()));
    }

    private static List<Order> createSingleOrder() {
        List<Order> orders = new ArrayList<>();
        Order carOrder = new Order();
        carOrder.getItems().add(new Item("car", Item.CarType.CAR));
        orders.add(carOrder);

        Order insuranceOrder = new Order();
        insuranceOrder.getItems().add(new Item("insurance", Item.CarType.INSURANCE));
        orders.add(insuranceOrder);

        return orders;
    }

    private static List<Order> createMultiOrder() {
        List<Order> orders = new ArrayList<>();
        Order carOrder = new Order();
        carOrder.getItems().add(new Item("car", Item.CarType.CAR));
        carOrder.getItems().add(new Item("insurance", Item.CarType.INSURANCE));

        orders.add(carOrder);

        return orders;
    }



    @Getter
    @Setter
    @AllArgsConstructor
    private static class Item {
        private String name;
        private CarType type;

        public enum CarType {CAR, INSURANCE};
    }

    @Getter
    private static class Order {
        private final List<Item> items = new ArrayList<>();
    }
}
