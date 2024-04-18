package com.fabrica.hutchisonspring.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class VehicleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 20)
    private String chassis;

    @NotNull
    private String operation;

    @NotNull
    private String tradeMark;

    @NotNull
    private String detail;

    private Instant labelledDate;

    private Instant registeredAt;

    private Long shipId;

    private String shipName;

    private Long travelId;

    private String travelNumber;

    private Long serviceOrderId;

    private String serviceOrderNumber;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getTradeMark() {
        return tradeMark;
    }

    public void setTradeMark(String tradeMark) {
        this.tradeMark = tradeMark;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Instant getLabelledDate() {
        return labelledDate;
    }

    public void setLabelledDate(Instant labelledDate) {
        this.labelledDate = labelledDate;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Instant registeredAt) {
        this.registeredAt = registeredAt;
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

    public Long getTravelId() {
        return travelId;
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

    public Long getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(Long serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public String getServiceOrderNumber() {
        return serviceOrderNumber;
    }

    public void setServiceOrderNumber(String serviceOrderNumber) {
        this.serviceOrderNumber = serviceOrderNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((VehicleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
                "id=" + id +
                ", chassis='" + chassis + '\'' +
                ", operation='" + operation + '\'' +
                ", tradeMark='" + tradeMark + '\'' +
                ", detail='" + detail + '\'' +
                ", labelledDate=" + labelledDate +
                ", registeredAt=" + registeredAt +
                ", shipId=" + shipId +
                ", shipName='" + shipName + '\'' +
                ", travelId=" + travelId +
                ", travelNumber='" + travelNumber + '\'' +
                ", serviceOrderId=" + serviceOrderId +
                ", serviceOrderNumber='" + serviceOrderNumber + '\'' +
                ", userId=" + userId +
                '}';
    }
}
