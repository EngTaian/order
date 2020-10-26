package br.com.taian.order.enumeration;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public enum PaymentStatus {

    PAYMENT_WAITING("Aguardando Pagamento"),
    PAID("Pago"),
    PAYMENTO_NOT_MADE("Pagamento não realizado")
    ;

    private String label;

    PaymentStatus(String label){
        this.label=label;
    }

    public static PaymentStatus valueOf(Integer ordinal){
        if(!ObjectUtils.isEmpty(ordinal)){
            for(PaymentStatus status : PaymentStatus.values()){
                if(status.ordinal() == ordinal)
                    return status;
            }
        }
        return null;
    }

}
