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
@Table(name = "Dbo_Detalle_Despacho")
public class DispatchDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BL")
    private String bl;

    @ManyToOne
    @JoinColumn(name = "Vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @Column(name = "SDO")
    private String sdo;

    @Column(name = "id_Job_APM")
    private Integer jobApm;

    @Column(name = "id_Conductor")
    private String driverRetire;

    @Column(name = "Guia_Remision")
    private String remissionGuide;

    @Column(name = "Fec_Registro", updatable = false, insertable = false)
    private Instant registeredAt;

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

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getSdo() {
        return this.sdo;
    }

    public void setSdo(String sdo) {
        this.sdo = sdo;
    }

    public Integer getJobApm() {
        return this.jobApm;
    }

    public void setJobApm(Integer jobApm) {
        this.jobApm = jobApm;
    }

    public String getDriverRetire() {
        return this.driverRetire;
    }

    public void setDriverRetire(String driverRetire) {
        this.driverRetire = driverRetire;
    }

    public String getRemissionGuide() {
        return this.remissionGuide;
    }

    public void setRemissionGuide(String remissionGuide) {
        this.remissionGuide = remissionGuide;
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
        if (!(o instanceof DispatchDetail)) {
            return false;
        }
        DispatchDetail dispatchDetail = (DispatchDetail) o;
        return Objects.equals(id, dispatchDetail.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        // @formatter:off
        return "DispatchDetail{" +
            "id=" + getId() +
            ", bl='" + getBl() + '\'' +
            ", vehicle=" + getVehicle() +
            ", sdo='" + getSdo() + '\'' +
            ", jobApm=" + getJobApm() +
            ", driverRetire='" + getDriverRetire() + '\'' +
            ", remissionGuide='" + getRemissionGuide() + '\'' +
            ", registeredAt=" + getRegisteredAt() +
            "}";
        // @formatter:on
    }
}
