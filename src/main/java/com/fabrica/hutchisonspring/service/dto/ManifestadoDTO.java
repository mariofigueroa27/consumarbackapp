package com.fabrica.hutchisonspring.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class ManifestadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String producto;

    @NotNull
    private String calidad;

    @NotNull
    private Integer manifestada;

    @NotNull
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
        return id != null && id.equals(((ManifestadoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ManifestadoDTO{" +
                "id=" + id +
                ", producto='" + producto + '\'' +
                ", calidad='" + calidad + '\'' +
                ", manifestada=" + manifestada +
                ", bodega=" + bodega +
                '}';
    }
}
