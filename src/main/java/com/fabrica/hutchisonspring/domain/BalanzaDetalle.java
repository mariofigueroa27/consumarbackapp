package com.fabrica.hutchisonspring.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "hu_balanza_detalle")
public class BalanzaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String ticket;

    @NotNull
    @Column(name = "hora_ingreso_puerto", nullable = false)
    private LocalTime horaIngresoPuerto;

    @NotNull
    @Column(name = "hora_salida_puerto", nullable = false)
    private LocalTime horaSalidaPuerto;

    @NotNull
    @Column(nullable = false)
    private String placa;

    @NotNull
    @Column(name = "empresa_transporte", nullable = false)
    private String empresaTransporte;

    @NotNull
    @Column(name = "peso_bruto_puerto", nullable = false)
    private Double pesoBrutoPuerto;

    @NotNull
    @Column(name = "peso_neto_puerto", nullable = false)
    private Double pesoNetoPuerto;

    @NotNull
    @Column(name = "tara_puerto", nullable = false)
    private String taraPuerto;

    @NotNull
    @Column(name = "balanza_entrada", nullable = false)
    private Double balanzaEntrada;

    @NotNull
    @Column(name = "balanza_salida", nullable = false)
    private Double balanzaSalida;

    @NotNull
    @Column(nullable = false)
    private Long cliente;

    @NotNull
    @Column(nullable = false)
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
        return id != null && id.equals(((BalanzaDetalle) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BalanzaDetalle{" +
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
