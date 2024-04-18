package com.fabrica.hutchisonspring.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class DataApmDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private Double mt;

    @Size(min = 10, max = 10)
    private String gateTicket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMt() {
        return mt;
    }

    public void setMt(Double mt) {
        this.mt = mt;
    }

    public String getGateTicket() {
        return gateTicket;
    }

    public void setGateTicket(String gateTicket) {
        this.gateTicket = gateTicket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((DataApmDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DataApmDTO{" +
                "id=" + id +
                ", mt=" + mt +
                ", gateTicket='" + gateTicket + '\'' +
                '}';
    }
}
