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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dbo_SDO")
public class Sdo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Master_BL")
    private String bl;

    @Column(name = "SDO")
    private String sdo;

    @NotNull
    @Column(name = "Release_QTY")
    private Integer releaseQty;

    @NotNull
    @Column(name = "Chas_QTY")
    private Integer chasQty;

    @Column(name = "Fec_Registro", updatable = false, insertable = false)
    private Instant registeredAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "travel_id", referencedColumnName = "id")
    private Travel travel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_order_id", referencedColumnName = "id")
    private ServiceOrder order;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBl() {
        return this.bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getSdo() {
        return this.sdo;
    }

    public void setSdo(String sdo) {
        this.sdo = sdo;
    }

    public Integer getReleaseQty() {
        return this.releaseQty;
    }

    public void setReleaseQty(Integer releaseQty) {
        this.releaseQty = releaseQty;
    }

    public Integer getChasQty() {
        return this.chasQty;
    }

    public void setChasQty(Integer chasQty) {
        this.chasQty = chasQty;
    }

    public Instant getRegisteredAt() {
        return this.registeredAt;
    }

    public void setRegisteredAt(Instant registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Travel getTravel() {
        return this.travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public ServiceOrder getOrder() {
        return this.order;
    }

    public void setOrder(ServiceOrder order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Sdo)) {
            return false;
        }
        Sdo sdo = (Sdo) o;
        return Objects.equals(id, sdo.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Sdo{" +
                "id=" + getId() +
                ", bl='" + getBl() + '\'' +
                ", sdo='" + getSdo() + '\'' +
                ", releaseQty=" + getReleaseQty() +
                ", chasQty=" + getChasQty() +
                ", registeredAt=" + getRegisteredAt() +
                ", travel=" + getTravel() +
                ", order=" + getOrder() +
                "}";
        // @formatter:on
    }
}
