package br.com.taian.order.enumeration;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public enum OrderStatus {

    NEW("NEW"),
    CANCELLED("Cancelado"),
    DONE("Finalizada");

    private String label;

    OrderStatus(String label){
        this.label = label;
    }

    public static OrderStatus valueOf(Integer ordinal){
        if(!ObjectUtils.isEmpty(ordinal)){
            for(OrderStatus status : OrderStatus.values()){
                if(status.ordinal() == ordinal)
                    return status;
            }
        }
        return null;
    }
}
