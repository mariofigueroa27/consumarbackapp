package com.fabrica.hutchisonspring.service.dto;

import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotNull;

public class SdoDTO {
    private Long id;

    private String bl;

    private String sdo;

    @NotNull
    private Integer releaseQty;

    @NotNull
    private Integer chasQty;

    private Instant registeredAt;

    private Long travelId;

    private String travelNumber;

    private Long shipId;

    private String shipName;

    private Long orderId;

    private String orderNumber;

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

    public Long getTravelId() {
        return this.travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public String getTravelNumber() {
        return travelNumber;
    }

    public void setTravelNumber(String travelNumber) {
        this.travelNumber = travelNumber;
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SdoDTO)) {
            return false;
        }
        SdoDTO sdoDTO = (SdoDTO) o;
        return Objects.equals(id, sdoDTO.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        // @formatter:off
        return "SdoDTO{" +
                "id=" + getId() +
                ", bl=" + getBl() +
                ", sdo='" + getSdo() + '\'' +
                ", releaseQty=" + getReleaseQty() +
                ", chasQty=" + getChasQty() +
                ", registeredAt=" + getRegisteredAt() +
                ", travelId=" + getTravelId() +
                ", travelNumber='" + getTravelNumber() + '\'' +
                ", shipId=" + getShipId() +
                ", shipName='" + getShipName() + '\'' +
                ", orderId=" + getOrderId() +
                ", orderNumber='" + getOrderNumber() + '\'' +
                "}";
        // @formatter:on
    }
}
