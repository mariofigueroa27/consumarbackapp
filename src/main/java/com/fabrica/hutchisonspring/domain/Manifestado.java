package com.fabrica.hutchisonspring.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "hu_manifestado")
public class Manifestado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "producto", nullable = false)
    private String producto;

    @NotNull
    @Column(name = "calidad", nullable = false)
    private String calidad;

    @NotNull
    @Column(name = "manifestada", nullable = false)
    private Integer manifestada;

    @NotNull
    @Column(name = "bodega", nullable = false)
    private Long bodega;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    public Integer getManifestada() {
        return manifestada;
    }

    public void setManifestada(Integer manifestada) {
        this.manifestada = manifestada;
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
        return id != null && id.equals(((Manifestado) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Manifestado{" +
                "id=" + id +
                ", producto='" + producto + '\'' +
                ", calidad='" + calidad + '\'' +
                ", manifestada=" + manifestada +
                ", bodega=" + bodega +
                '}';
    }
}
