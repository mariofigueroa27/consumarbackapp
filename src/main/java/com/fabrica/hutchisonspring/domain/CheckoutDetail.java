package com.fabrica.hutchisonspring.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Dbo_CheckOut")
public class CheckoutDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_Despacho", referencedColumnName = "id")
    private DispatchDetail dispatchDetail;

    @Column(name = "Conductor")
    private String driverRetire;

    @Column(name = "Fec_Registro", updatable = false, insertable = false)
    private Instant registeredAt;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DispatchDetail getDispatchDetail() {
        return this.dispatchDetail;
    }

    public void setDispatchDetail(DispatchDetail dispatchDetail) {
        this.dispatchDetail = dispatchDetail;
    }

    public String getDriverRetire() {
        return this.driverRetire;
    }

    public void setDriverRetire(String driverRetire) {
        this.driverRetire = driverRetire;
    }

    public Instant getRegisteredAt() {
        return this.registeredAt;
    }

    public void setRegisteredAt(Instant registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CheckoutDetail)) {
            return false;
        }
        CheckoutDetail checkoutDetail = (CheckoutDetail) o;
        return id.equals(checkoutDetail.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        // @formatter:off
        return "CheckoutDetail{" +
            "id=" + getId() +
            ", dispatchDetail=" + getDispatchDetail() +
            ", driverRetire='" + getDriverRetire() + "'" +
            ", registeredAt=" + getRegisteredAt() +
            "}";
        // @formatter:on
    }
}
