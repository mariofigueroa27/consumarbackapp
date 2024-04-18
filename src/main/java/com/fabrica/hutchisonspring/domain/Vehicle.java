package com.fabrica.hutchisonspring.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "hu_vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 25)
    @Column(nullable = false, unique = true)
    private String chassis;

    @NotNull
    @Column(nullable = false)
    private String operation;

    @NotNull
    @Size(max = 25)
    @Column(name = "trade_mark", nullable = false, length = 25)
    private String tradeMark;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String detail;

    @Column(name = "labelled_date")
    private Instant labelledDate;

    @Column(name = "registered_at", updatable = false, insertable = false)
    private Instant registeredAt;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("vehicles")
    @JoinColumn(name = "travel_id", referencedColumnName = "id")
    private Travel travel;

    @ManyToOne
    @JsonIgnoreProperties("vehicles")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("vehicles")
    @JoinColumn(name = "service_order_id", referencedColumnName = "id")
    private ServiceOrder serviceOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String plate) {
        this.chassis = plate;
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

    public void setLabelledDate(Instant labeledDate) {
        this.labelledDate = labeledDate;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Instant registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return id != null && id.equals(((Vehicle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vehicle{" + "id=" + id + ", chassis='" + chassis + '\'' + ", operation='" + operation + '\''
                + ", tradeMark='" + tradeMark + '\'' + ", detail='" + detail + '\'' + ", labelledDate=" + labelledDate
                + ", registeredAt=" + registeredAt + ", travel=" + travel + ", user=" + user + ", serviceOrder="
                + serviceOrder + '}';
    }
}
