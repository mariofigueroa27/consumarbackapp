package com.fabrica.hutchisonspring.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class OrdenSurveyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String nave;

    @NotNull
    private String ticket;

    @NotNull
    private String cliente;

    @NotNull
    private Long muelle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNave() {
        return nave;
    }

    public void setNave(String nave) {
        this.nave = nave;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Long getMuelle() {
        return muelle;
    }

    public void setMuelle(Long muelle) {
        this.muelle = muelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((OrdenSurveyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrdenSurveyDTO{" +
                "id=" + id +
                ", nave='" + nave + '\'' +
                ", ticket='" + ticket + '\'' +
                ", cliente='" + cliente + '\'' +
                ", muelle=" + muelle +
                '}';
    }
}
