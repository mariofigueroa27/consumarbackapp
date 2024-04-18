package com.fabrica.hutchisonspring.service.dto;

import java.util.Objects;

import javax.validation.constraints.Size;

public class TraceDTO {
    private Long id;

    @Size(max = 50)
    private String bl;

    private boolean dispatch;

    private boolean apmtcExit;

    private Long vehicleId;

    private String vehicleChassis;

    private String vehicleTradeMark;

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

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleChassis() {
        return vehicleChassis;
    }

    public void setVehicleChassis(String vehicleChassis) {
        this.vehicleChassis = vehicleChassis;
    }

    public String getVehicleTradeMark() {
        return vehicleTradeMark;
    }

    public void setVehicleTradeMark(String vehicleTradeMark) {
        this.vehicleTradeMark = vehicleTradeMark;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TraceDTO)) {
            return false;
        }
        TraceDTO traceDTO = (TraceDTO) o;
        return id.equals(traceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        // @formatter:off
        return "TraceDTO{" +
            "id=" + getId() +
            ", bl='" + getBl() + '\'' +
            ", dispatch=" + isDispatch() +
            ", apmtcExit=" + isApmtcExit() +
            ", vehicleId=" + getVehicleId() +
            ", vehicleChassis='" + getVehicleChassis() + '\'' +
            ", vehicleTradeMark='" + getVehicleTradeMark() + '\'' +
            "}";
        // @formatter:on
    }
}
