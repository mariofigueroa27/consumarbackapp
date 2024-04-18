package com.fabrica.hutchisonspring.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "ControlDeRampa")
public class ControlDeRampa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDControlDeRampa")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "IDJob")
    private Long jobId;

    @Size(max = 50)
    @Column(name = "OrdenDeServicio", length = 50)
    private String serviceOrderName;

    @Size(max = 20)
    @Column(name = "Operacion", length = 20)
    private String operation;

    @Column(name = "Fecha")
    private Date date;

    @Column(name = "Jornada")
    private String workingDay;

    @Column(name = "Nivel")
    private Integer level;

    @Size(max = 50)
    @Column(name = "Chasis", length = 50)
    private String chassis;

    @Size(max = 50)
    @Column(name = "Marca", length = 50)
    private String tradeMark;

    @Column(name = "HoraDeLectura")
    private Instant readingTime;

    @Column(name = "service_order_id")
    private Long serviceOrderId;

    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Size(max = 20)
    @Column(name = "OperacionRampa", length = 20)
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControlDeRampa that = (ControlDeRampa) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ControlDeRampa{" +
                "id=" + id +
                ", jobId=" + jobId +
                ", serviceOrderName='" + serviceOrderName + '\'' +
                ", operation='" + operation + '\'' +
                ", date=" + date +
                ", workingDay='" + workingDay + '\'' +
                ", level=" + level +
                ", chassis='" + chassis + '\'' +
                ", tradeMark='" + tradeMark + '\'' +
                ", readingTime=" + readingTime +
                ", serviceOrderId=" + serviceOrderId +
                ", vehicleId=" + vehicleId +
                ", rampOperation='" + rampOperation + '\'' +
                '}';
    }
}
