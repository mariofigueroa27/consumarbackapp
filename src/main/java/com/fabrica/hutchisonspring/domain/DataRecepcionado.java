package com.fabrica.hutchisonspring.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "hu_data_recepcionado")
public class DataRecepcionado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Instant fecha;

    @NotNull
    @Column(nullable = false)
    private String jornada;

    @NotNull
    @Column(nullable = false)
    private String lado;

    @NotNull
    @Column(name = "codigo_despacho", nullable = false)
    private String codigoDespacho;

    @NotNull
    @Column(nullable = false)
    private Double capacidad;

    @NotNull
    @Column(nullable = false)
    private Double recepcionado;

    @Column
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
        return id != null && id.equals(((DataRecepcionado) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DataRecepcionado{" +
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
