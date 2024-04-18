package com.fabrica.hutchisonspring.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class VentaBalanzaBaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String producto;

    @NotNull
    private String cliente;

    @NotNull
    @Size(min = 9, max = 9)
    private String factura;

    @NotNull
    private Double cantidadAutorizada;

    @NotNull
    private Double viajesAutorizados;

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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public Double getCantidadAutorizada() {
        return cantidadAutorizada;
    }

    public void setCantidadAutorizada(Double cantidadAutorizada) {
        this.cantidadAutorizada = cantidadAutorizada;
    }

    public Double getViajesAutorizados() {
        return viajesAutorizados;
    }

    public void setViajesAutorizados(Double viajesAutorizados) {
        this.viajesAutorizados = viajesAutorizados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((VentaBalanzaBaseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VentaBalanzaBaseDTO{" +
                "id=" + id +
                ", producto='" + producto + '\'' +
                ", cliente='" + cliente + '\'' +
                ", factura='" + factura + '\'' +
                ", cantidadAutorizada=" + cantidadAutorizada +
                ", viajesAutorizados=" + viajesAutorizados +
                '}';
    }
}
