package br.com.taian.order.model;

import br.com.taian.order.enumeration.OrderStatus;
import br.com.taian.order.enumeration.PaymentStatus;
import br.com.taian.order.util.GeneratorIdentifierEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name="order")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class Order extends GeneratorIdentifierEntity<Long> implements Serializable {

    @NotNull
    @Column
    private String make;

    @NotNull
    @Column
    private String  model;

    @NotNull
    @Column
    private Double price;

    @Column
    private PaymentStatus paymentStatus = PaymentStatus.PAYMENT_WAITING;

    private String code;
}
