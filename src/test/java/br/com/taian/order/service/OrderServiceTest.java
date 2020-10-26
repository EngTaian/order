package br.com.taian.order.service;

import br.com.taian.order.enumeration.PaymentStatus;
import br.com.taian.order.exception.BusinessException;
import br.com.taian.order.model.Order;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createOrderAndShoudReturnOrder(){
        Order order = orderService.createElement(getOrderData());
        Assertions.assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatus.PAYMENT_WAITING);
        Assertions.assertThat(order.getId()).isNotZero();
        Assertions.assertThat(order.getDateDue()).isNotNull();
        Assertions.assertThat(order.getMake()).isEqualTo("Chevrolet");
        Assertions.assertThat(order.getModel()).isEqualTo("Onix");
    }

    @Test
    public void createOrderAndShoudReturnErrorWithDealIdNull(){
        thrown.expect(BusinessException.class);
        Order order = getOrderData();
        order.setDealId(null);
        orderService.createElement(order);
    }

    @Test
    public void createOrderAndShoudReturnErrorWithInvalidDealId(){
        thrown.expect(BusinessException.class);
        Order order = getOrderData();
        order.setDealId(0L);
        orderService.createElement(order);
    }

    @Test
    public void createOrderAndShoudReturnErrorWithModelNull(){
        thrown.expect(BusinessException.class);
        Order order = getOrderData();
        order.setModel(null);
        orderService.createElement(order);
    }

    @Test
    public void createOrderAndShoudReturnErrorWithMakeNull(){
        thrown.expect(BusinessException.class);
        Order order = getOrderData();
        order.setModel(null);
        orderService.createElement(order);
    }

    @Test
    public void createOrderAndShoudReturnErrorWithPriceNull(){
        thrown.expect(BusinessException.class);
        Order order = getOrderData();
        order.setModel(null);
        orderService.createElement(order);
    }

    @Test
    public void createOrderAndShoudReturnErrorWithPriceIsZero(){
        thrown.expect(BusinessException.class);
        Order order = getOrderData();
        order.setModel(null);
        orderService.createElement(order);
    }

    private Order getOrderData(){
        Order order = new Order();
        order.setDealId(1L);
        order.setPrice(49950.00);
        order.setMake("Chevrolet");
        order.setModel("Onix");

        return order;
    }

}
