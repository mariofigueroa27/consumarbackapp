package com.fabrica.hutchisonspring.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "hu_descarga_directa_indirecta")
public class DescargaDirectaIndirecta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(nullable = false)
    private String modalidad;

    @NotNull
    @Column(name = "jornada_inicio", nullable = false)
    private LocalTime jornadaInicio;

    @NotNull
    @Column(name = "jornada_fin", nullable = false)
    private LocalTime jornadaFin;

    @NotNull
    @Column(nullable = false)
    private Double descargo;

    @NotNull
    @Column(nullable = false)
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
        return id != null && id.equals(((DescargaDirectaIndirecta) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DescargaDirectaIndirecta{" +
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
