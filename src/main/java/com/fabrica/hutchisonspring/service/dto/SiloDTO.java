package com.fabrica.hutchisonspring.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class SiloDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String job;

    @NotNull
    private String category;

    @NotNull
    private String vessel;

    private String specialCg;

    @NotNull
    private String cgCond;

    @NotNull
    private Double mt;

    @NotNull
    private Integer m3;

    @NotNull
    private Integer qty;

    @NotNull
    private String direction;

    private String rcCond;

    @NotNull
    private String jobType;

    private String oprMode;

    @NotNull
    private String truck;

    @NotNull
    private String gateTicket;

    @NotNull
    private String location;

    private String reHandle;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    @NotNull
    private String delivery;

    @NotNull
    private String shift;

    private String hatch;

    private String eq;

    private String crane;

    @NotNull
    private String finalStat;

    private String reMark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVessel() {
        return vessel;
    }

    public void setVessel(String vessel) {
        this.vessel = vessel;
    }

    public String getSpecialCg() {
        return specialCg;
    }

    public void setSpecialCg(String specialCg) {
        this.specialCg = specialCg;
    }

    public String getCgCond() {
        return cgCond;
    }

    public void setCgCond(String cgCond) {
        this.cgCond = cgCond;
    }

    public Double getMt() {
        return mt;
    }

    public void setMt(Double mt) {
        this.mt = mt;
    }

    public Integer getM3() {
        return m3;
    }

    public void setM3(Integer m3) {
        this.m3 = m3;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRcCond() {
        return rcCond;
    }

    public void setRcCond(String rcCond) {
        this.rcCond = rcCond;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getOprMode() {
        return oprMode;
    }

    public void setOprMode(String oprMode) {
        this.oprMode = oprMode;
    }

    public String getTruck() {
        return truck;
    }

    public void setTruck(String truck) {
        this.truck = truck;
    }

    public String getGateTicket() {
        return gateTicket;
    }

    public void setGateTicket(String gateTicket) {
        this.gateTicket = gateTicket;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReHandle() {
        return reHandle;
    }

    public void setReHandle(String reHandle) {
        this.reHandle = reHandle;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getHatch() {
        return hatch;
    }

    public void setHatch(String hatch) {
        this.hatch = hatch;
    }

    public String getEq() {
        return eq;
    }

    public void setEq(String eq) {
        this.eq = eq;
    }

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    public String getFinalStat() {
        return finalStat;
    }

    public void setFinalStat(String finalStat) {
        this.finalStat = finalStat;
    }

    public String getReMark() {
        return reMark;
    }

    public void setReMark(String reMark) {
        this.reMark = reMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((SiloDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SiloDTO{" +
                "id=" + id +
                ", job='" + job + '\'' +
                ", category='" + category + '\'' +
                ", vessel='" + vessel + '\'' +
                ", specialCg='" + specialCg + '\'' +
                ", cgCond='" + cgCond + '\'' +
                ", mt=" + mt +
                ", m3=" + m3 +
                ", qty=" + qty +
                ", direction='" + direction + '\'' +
                ", rcCond='" + rcCond + '\'' +
                ", jobType='" + jobType + '\'' +
                ", oprMode='" + oprMode + '\'' +
                ", truck='" + truck + '\'' +
                ", gateTicket='" + gateTicket + '\'' +
                ", location='" + location + '\'' +
                ", reHandle='" + reHandle + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", delivery='" + delivery + '\'' +
                ", shift='" + shift + '\'' +
                ", hatch='" + hatch + '\'' +
                ", eq='" + eq + '\'' +
                ", crane='" + crane + '\'' +
                ", finalStat='" + finalStat + '\'' +
                ", reMark='" + reMark + '\'' +
                '}';
    }
}
