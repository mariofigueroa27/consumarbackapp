package com.fabrica.hutchisonspring.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class DescargaDirectaIndirectaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private String modalidad;

    @NotNull
    private LocalTime jornadaInicio;

    @NotNull
    private LocalTime jornadaFin;

    @NotNull
    private Double descargo;

    @NotNull
    private Long bodega;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public LocalTime getJornadaInicio() {
        return jornadaInicio;
    }

    public void setJornadaInicio(LocalTime jornadaInicio) {
        this.jornadaInicio = jornadaInicio;
    }

    public LocalTime getJornadaFin() {
        return jornadaFin;
    }

    public void setJornadaFin(LocalTime jornadaFin) {
        this.jornadaFin = jornadaFin;
    }

    public Double getDescargo() {
        return descargo;
    }

    public void setDescargo(Double descargo) {
        this.descargo = descargo;
    }

    public Long getBodega() {
        return bodega;
    }

    public void setBodega(Long bodega) {
        this.bodega = bodega;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((DescargaDirectaIndirectaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DescargaDirectaIndirectaDTO{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", modalidad='" + modalidad + '\'' +
                ", jornadaInicio=" + jornadaInicio +
                ", jornadaFin=" + jornadaFin +
                ", descargo=" + descargo +
                ", bodega=" + bodega +
                '}';
    }
}
