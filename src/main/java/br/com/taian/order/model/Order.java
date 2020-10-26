package br.com.taian.order.model;

import br.com.taian.order.enumeration.PaymentStatus;
import br.com.taian.order.util.GeneratorIdentifierEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="payment_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
public class Order extends GeneratorIdentifierEntity<Long> implements Serializable {

    @NotNull
    @Column
    private Long dealId;

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

    @Column
    private String code;

    @Column
    private Date dateDue;
}
