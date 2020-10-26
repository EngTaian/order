package br.com.taian.order.service;

import br.com.taian.order.enumeration.PaymentStatus;
import br.com.taian.order.exception.BusinessException;
import br.com.taian.order.model.Order;
import br.com.taian.order.repository.OrderRepository;
import br.com.taian.order.util.CrudServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
public class OrderService extends CrudServiceImpl<OrderRepository, Order> {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Order createElement(Order order){
        validateBeforeSave(order);
        order.setCode(geradorDeCodigoDeBarras());
        LocalDate dateDue = getDateDue();
        order.setDateDue(Date.from(dateDue.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return super.createElement(order);
    }

    public Order updatePaymentStatus(Long id, PaymentStatus paymentStatus){
        Order order = findById(id);
        if(ObjectUtils.isEmpty(order)){
            throw new BusinessException("Could not found order by id " + id);
        }
        order.setPaymentStatus(paymentStatus);
        return this.repository.saveAndFlush(order);
    }

    private void validateBeforeSave(Order order){
        List<String> errors = new ArrayList<>();

        if(!ObjectUtils.isEmpty(order.getDealId())){
            String url = getUrlDeal(order.getDealId());
            Object object = restTemplate.getForObject(url, Object.class);
            if(!ObjectUtils.isEmpty(object)) {
                Map<String, Object> deal = objectMapper.convertValue(object, Map.class);
                if (deal.isEmpty()) {
                    errors.add("DealService.validateBeforeSave Error ::: could not find deal");
                }
            }else{
                errors.add("DealService.validateBeforeSave Error ::: could not find deal");
            }
        }else{
            errors.add("DealService.validateBeforeSave Error ::: Deal can't be null");
        }

        if(ObjectUtils.isEmpty(order.getMake())){
            errors.add("DealService.validateBeforeSave Error ::: make can't be null");
        }

        if(ObjectUtils.isEmpty(order.getModel())){
            errors.add("DealService.validateBeforeSave Error ::: model can't be null");
        }

        if(ObjectUtils.isEmpty(order.getPrice()) || order.getPrice() < 1 ){
            errors.add("DealService.validateBeforeSave Error ::: price can't be null or zero");
        }

        if(ObjectUtils.isEmpty(order.getPaymentStatus())){
            order.setPaymentStatus(PaymentStatus.PAYMENT_WAITING);
        }

        if(errors.size() > 0){
            log.error("Validation error :::" + errors);
            throw new BusinessException("Validation error :::" + errors);
        }

    }

    private String geradorDeCodigoDeBarras(){
        String boleto = "";
        Random random = new Random();

        for(int i = 0; i < 48; i++){
            boleto += random.nextInt(9);
        }
        return boleto;
    }

    private LocalDate getDateDue(){
        LocalDate dateDue = LocalDate.now();
        return dateDue.plusDays(7L);
    }

    private String getUrlDeal(Long dealId){
        return "http://localhost:9999/deal/api/v1.0/" + dealId;
    }
}
