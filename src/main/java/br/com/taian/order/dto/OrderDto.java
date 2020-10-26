package br.com.taian.order.dto;

import br.com.taian.order.enumeration.OrderStatus;
import br.com.taian.order.enumeration.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {

    @JsonProperty
    private String make;

    @JsonProperty
    private String model;

    @JsonProperty
    private Double price;

}
