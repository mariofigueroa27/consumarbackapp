package com.fabrica.hutchisonspring.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "hu_silo")
public class Silo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String job;

    @NotNull
    @Column(nullable = false)
    private String category;

    @NotNull
    @Column(nullable = false)
    private String vessel;

    @Column(name = "special_cg")
    private String specialCg;

    @NotNull
    @Column(name = "cg_cond", nullable = false)
    private String cgCond;

    @NotNull
    @Column(nullable = false)
    private Double mt;

    @NotNull
    @Column(nullable = false)
    private Integer m3;

    @NotNull
    @Column(nullable = false)
    private Integer qty;

    @NotNull
    @Column(nullable = false)
    private String direction;

    @Column(name = "rc_cond")
    private String rcCond;

    @NotNull
    @Column(name = "job_type", nullable = false)
    private String jobType;

    @Column(name = "opr_mode")
    private String oprMode;

    @NotNull
    @Column(nullable = false)
    private String truck;

    @NotNull
    @Column(name = "gate_ticket", nullable = false)
    private String gateTicket;

    @NotNull
    @Column(nullable = false)
    private String location;

    @Column(name = "re_handle")
    private String reHandle;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @NotNull
    @Column(nullable = false)
    private String delivery;

    @NotNull
    @Column(nullable = false)
    private String shift;

    @Column
    private String hatch;

    @Column
    private String eq;

    @Column
    private String crane;

    @NotNull
    @Column(name = "final_stat", nullable = false)
    private String finalStat;

    @Column(name = "re_mark")
    private String reMark;

    @NotNull
    @Column(nullable = false)
    private Long cargo;

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

    public Long getCargo() {
        return cargo;
    }

    public void setCargo(Long cargo) {
        this.cargo = cargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((Silo) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Silo{" +
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
                ", cargo=" + cargo +
                '}';
    }
}
