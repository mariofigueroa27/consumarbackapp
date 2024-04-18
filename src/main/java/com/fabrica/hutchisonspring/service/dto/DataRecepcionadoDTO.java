package com.fabrica.hutchisonspring.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class DataRecepcionadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    @NotNull
    private Instant fecha;

    @NotNull
    private String jornada;

    @NotNull
    private String lado;

    @NotNull
    private String codigoDespacho;

    @NotNull
    private Double capacidad;

    @NotNull
    private Double recepcionado;

    private String nave;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public String getLado() {
        return lado;
    }

    public void setLado(String lado) {
        this.lado = lado;
    }

    public String getCodigoDespacho() {
        return codigoDespacho;
    }

    public void setCodigoDespacho(String codigoDespacho) {
        this.codigoDespacho = codigoDespacho;
    }

    public Double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Double capacidad) {
        this.capacidad = capacidad;
    }

    public Double getRecepcionado() {
        return recepcionado;
    }

    public void setRecepcionado(Double recepcionado) {
        this.recepcionado = recepcionado;
    }

    public String getNave() {
        return nave;
    }

    public void setNave(String nave) {
        this.nave = nave;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((DataRecepcionadoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DataRecepcionadoDTO{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", jornada='" + jornada + '\'' +
                ", lado='" + lado + '\'' +
                ", codigoDespacho='" + codigoDespacho + '\'' +
                ", capacidad=" + capacidad +
                ", recepcionado=" + recepcionado +
                ", nave='" + nave + '\'' +
                '}';
    }
}
