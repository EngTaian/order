package br.com.taian.order.service;

import br.com.taian.order.enumeration.OrderStatus;
import br.com.taian.order.enumeration.PaymentStatus;
import br.com.taian.order.exception.BusinessException;
import br.com.taian.order.exception.InvalidAccessException;
import br.com.taian.order.model.Order;
import br.com.taian.order.repository.OrderRepository;
import br.com.taian.order.util.CrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Slf4j
@Service
public class OrderService extends CrudServiceImpl<OrderRepository, Order> {


    public void validateOrderAccess(Long id, Long dealerId){
        Optional<Order> optionalOrder = this.repository.findOrderByIdAndDealerId(id, dealerId);
        if(!optionalOrder.isPresent()){
            log.error(String.format("Dealer %s can't access this order %s", dealerId, id));
            throw new InvalidAccessException(String.format("Dealer %s can't access this order %s", dealerId, id)  );
        }
    }

    public Order updateStatus(Long id, OrderStatus orderStatus){
        Order order = findById(id);
        if(ObjectUtils.isEmpty(order)){
            throw new BusinessException("Could not found order by id " + id);
        }
        order.setOrderStatus(orderStatus);
        return this.repository.saveAndFlush(order);
    }

    public Order updatePaymentStatus(Long id, PaymentStatus paymentStatus){
        Order order = findById(id);
        if(ObjectUtils.isEmpty(order)){
            throw new BusinessException("Could not found order by id " + id);
        }
        order.setPaymentStatus(paymentStatus);
        return this.repository.saveAndFlush(order);
    }

}
