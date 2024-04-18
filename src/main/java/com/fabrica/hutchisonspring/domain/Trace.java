package com.fabrica.hutchisonspring.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "dbo_estado_vehiculo")
public class Trace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(name = "BL")
    private String bl;

    @Column(name = "C_Despacho")
    private boolean dispatch;

    @Column(name = "C_Salida_APMTC")
    private boolean apmtcExit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

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

    public boolean isDispatch() {
        return this.dispatch;
    }

    public boolean getDispatch() {
        return this.dispatch;
    }

    public void setDispatch(boolean dispatch) {
        this.dispatch = dispatch;
    }

    public boolean isApmtcExit() {
        return this.apmtcExit;
    }

    public boolean getApmtcExit() {
        return this.apmtcExit;
    }

    public void setApmtcExit(boolean apmtcExit) {
        this.apmtcExit = apmtcExit;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Trace)) {
            return false;
        }
        Trace trace = (Trace) o;
        return id.equals(trace.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Trace{" +
            "id=" + getId() +
            ", bl='" + getBl() + '\'' +
            ", dispatch=" + isDispatch() +
            ", apmtcExit=" + isApmtcExit() +
            ", vehicle=" + getVehicle() +
            "}";
        // @formatter:on
    }
}
