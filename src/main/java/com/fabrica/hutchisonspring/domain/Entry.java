package com.fabrica.hutchisonspring.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * https://docs.microsoft.com/en-us/sql/t-sql/statements/set-identity-insert-transact-sql?view=sql-server-ver15
 */
@Entity
@Table(name = "hu_entry")
public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "ship_code", nullable = false, length = 20)
    private String shipCode;

    @NotNull
    @Min(value = 0)
    @Column(nullable = false)
    private Double weight;

    @NotNull
    @Size(max = 10)
    @Column(nullable = false)
    private String plate;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("entries")
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((Entry) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", shipCode='" + shipCode + '\'' +
                ", weight=" + weight +
                ", plate='" + plate + '\'' +
                ", employee=" + employee +
                '}';
    }
}
