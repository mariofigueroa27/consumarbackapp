package com.fabrica.hutchisonspring.service.dto;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.time.Instant;
import java.util.Objects;

public class ControlDeRampaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long jobId;

    @Size(max = 50)
    private String serviceOrderName;

    @Size(max = 20)
    private String operation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    private String workingDay;

    private Integer level;

    @Size(max = 50)
    private String chassis;

    @Size(max = 50)
    private String tradeMark;

    private Instant readingTime;

    private Long serviceOrderId;

    private Long vehicleId;

    @Size(max = 20)
    private String rampOperation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getServiceOrderName() {
        return serviceOrderName;
    }

    public void setServiceOrderName(String serviceOrderName) {
        this.serviceOrderName = serviceOrderName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(String workingDay) {
        this.workingDay = workingDay;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getTradeMark() {
        return tradeMark;
    }

    public void setTradeMark(String tradeMark) {
        this.tradeMark = tradeMark;
    }

    public Instant getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(Instant readingTime) {
        this.readingTime = readingTime;
    }

    public Long getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(Long serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getRampOperation() {
        return rampOperation;
    }

    public void setRampOperation(String rampOperation) {
        this.rampOperation = rampOperation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ControlDeRampaDTO that = (ControlDeRampaDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ControlDeRampaDTO{" + "id=" + id + ", jobId=" + jobId + ", serviceOrderName='" + serviceOrderName + '\''
                + ", operation='" + operation + '\'' + ", date=" + date + ", workingDay='" + workingDay + '\''
                + ", level=" + level + ", chassis='" + chassis + '\'' + ", tradeMark='" + tradeMark + '\''
                + ", readingTime=" + readingTime + ", serviceOrderId=" + serviceOrderId + ", vehicleId=" + vehicleId
                + ", rampOperation='" + rampOperation + '\'' + '}';
    }
}
