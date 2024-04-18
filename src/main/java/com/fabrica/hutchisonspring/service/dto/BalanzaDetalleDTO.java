package com.fabrica.hutchisonspring.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

public class BalanzaDetalleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String ticket;

    @NotNull
    private LocalTime horaIngresoPuerto;

    @NotNull
    private LocalTime horaSalidaPuerto;

    @NotNull
    private String placa;

    @NotNull
    private String empresaTransporte;

    @NotNull
    private Double pesoBrutoPuerto;

    @NotNull
    private Double pesoNetoPuerto;

    @NotNull
    private String taraPuerto;

    @NotNull
    private Double balanzaEntrada;

    @NotNull
    private Double balanzaSalida;

    @NotNull
    private Long cliente;

    @NotNull
    private String factura;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public LocalTime getHoraIngresoPuerto() {
        return horaIngresoPuerto;
    }

    public void setHoraIngresoPuerto(LocalTime horaIngresoPuerto) {
        this.horaIngresoPuerto = horaIngresoPuerto;
    }

    public LocalTime getHoraSalidaPuerto() {
        return horaSalidaPuerto;
    }

    public void setHoraSalidaPuerto(LocalTime horaSalidaPuerto) {
        this.horaSalidaPuerto = horaSalidaPuerto;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEmpresaTransporte() {
        return empresaTransporte;
    }

    public void setEmpresaTransporte(String empresaTransporte) {
        this.empresaTransporte = empresaTransporte;
    }

    public Double getPesoBrutoPuerto() {
        return pesoBrutoPuerto;
    }

    public void setPesoBrutoPuerto(Double pesoBrutoPuerto) {
        this.pesoBrutoPuerto = pesoBrutoPuerto;
    }

    public Double getPesoNetoPuerto() {
        return pesoNetoPuerto;
    }

    public void setPesoNetoPuerto(Double pesoNetoPuerto) {
        this.pesoNetoPuerto = pesoNetoPuerto;
    }

    public String getTaraPuerto() {
        return taraPuerto;
    }

    public void setTaraPuerto(String taraPuerto) {
        this.taraPuerto = taraPuerto;
    }

    public Double getBalanzaEntrada() {
        return balanzaEntrada;
    }

    public void setBalanzaEntrada(Double balanzaEntrada) {
        this.balanzaEntrada = balanzaEntrada;
    }

    public Double getBalanzaSalida() {
        return balanzaSalida;
    }

    public void setBalanzaSalida(Double balanzaSalida) {
        this.balanzaSalida = balanzaSalida;
    }

    public Long getCliente() {
        return cliente;
    }

    public void setCliente(Long cliente) {
        this.cliente = cliente;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((BalanzaDetalleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BalanzaDetalleDTO{" +
                "id=" + id +
                ", ticket='" + ticket + '\'' +
                ", horaIngresoPuerto=" + horaIngresoPuerto +
                ", horaSalidaPuerto=" + horaSalidaPuerto +
                ", placa='" + placa + '\'' +
                ", empresaTransporte='" + empresaTransporte + '\'' +
                ", pesoBrutoPuerto=" + pesoBrutoPuerto +
                ", pesoNetoPuerto=" + pesoNetoPuerto +
                ", taraPuerto='" + taraPuerto + '\'' +
                ", balanzaEntrada=" + balanzaEntrada +
                ", balanzaSalida=" + balanzaSalida +
                ", cliente=" + cliente +
                ", factura='" + factura + '\'' +
                '}';
    }
}
