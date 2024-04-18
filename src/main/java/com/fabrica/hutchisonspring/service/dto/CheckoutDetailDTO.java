package com.fabrica.hutchisonspring.service.dto;

import java.time.Instant;
import java.util.Objects;

public class CheckoutDetailDTO {

    private Long id;

    private String bl;

    private Long dispatchDetailId;

    private String vehicleChassis;

    private String vehicleTradeMark;

    private String sdo;

    private Integer jobApm;

    private String driverRetire;

    private String remissionGuide;

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

    public Long getDispatchDetailId() {
        return this.dispatchDetailId;
    }

    public void setDispatchDetailId(Long dispatchDetailId) {
        this.dispatchDetailId = dispatchDetailId;
    }

    public String getVehicleChassis() {
        return this.vehicleChassis;
    }

    public void setVehicleChassis(String vehicleChassis) {
        this.vehicleChassis = vehicleChassis;
    }

    public String getVehicleTradeMark() {
        return this.vehicleTradeMark;
    }

    public void setVehicleTradeMark(String vehicleTradeMark) {
        this.vehicleTradeMark = vehicleTradeMark;
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
        if (!(o instanceof DispatchDetailDTO)) {
            return false;
        }
        CheckoutDetailDTO checkoutDetailDTO = (CheckoutDetailDTO) o;
        return id.equals(checkoutDetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        // @formatter:off
        return "CheckoutDetailDTO{" +
            "id=" + getId() +
            ", bl='" + getBl() + '\'' +
            ", dispatchDetailId=" + getDispatchDetailId() +
            ", vehicleChassis='" + getVehicleChassis() + '\'' +
            ", vehicleTradeMark='" + getVehicleTradeMark() + '\'' +
            ", sdo='" + getSdo() + '\'' +
            ", jobApm='" + getJobApm() +
            ", driverRetire='" + getDriverRetire() + '\'' +
            ", remissionGuide='" + getRemissionGuide() + '\'' +
            ", registeredAt=" + getRegisteredAt() +
            "}";
        // @formatter:on
    }
}
