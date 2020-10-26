package br.com.taian.order.controller;

import br.com.taian.order.dto.OrderDto;
import br.com.taian.order.enumeration.OrderStatus;
import br.com.taian.order.enumeration.PaymentStatus;
import br.com.taian.order.exception.InvalidAccessException;
import br.com.taian.order.model.Order;
import br.com.taian.order.service.OrderService;
import br.com.taian.order.util.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(value = "Order Controller", tags = {"order"})
@RestController
@RequestMapping("/order/api/{dealerId}")
public class OrderController extends BaseController<OrderService, Order, OrderDto> {

    @Override
    public ResponseEntity createElement(OrderDto orderDto){
        try {
            Order order = this.service.createElement(convertToModel(orderDto));
            ObjectNode response= objectMapper.createObjectNode();
            response.put("bar_code", order.getCode());
            response.put("date_due", order.getDateDue().toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception ex){
            log.error("Error during creating order");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during creating order");
        }
    }

    @ApiOperation("Update payment status")
    @PutMapping("/{id}/payment/status")
    public ResponseEntity updatePaymentStatus(@PathVariable Long dealerId,
                                              @PathVariable Long id,
                                              @RequestParam PaymentStatus paymentStatus){
        try {
            Order order = this.service.updatePaymentStatus(id, paymentStatus);
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("id", order.getId());
            return ResponseEntity.ok(objectNode);
        }catch (Exception ex){
            log.error("Error during update payment status");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during update payment status");
        }
    }

    @Override
    protected List<OrderDto> convertToListDto(List<Order> orders) {
        return modelMapper.map(orders, new TypeToken<List<OrderDto>>() {}.getType());
    }

    @Override
    protected OrderDto convertToDetailDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    protected Order convertToModel(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }
}
