package com.fabrica.hutchisonspring.service.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class RoroOperationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(max = 50)
    private String puerto;

    @Size(max = 50)
    private String terminal;

    private Instant fecha;

    @Size(max = 20)
    private String muelle;

    @Size(max = 20)
    private String cliente;

    @Size(max = 20)
    private String operacion;

    @Size(max = 50)
    private String bl;

    @Size(max = 20)
    private String mercaderia;

    @Size(max = 50)
    private String consignatario;

    @Size(max = 50)
    private String chassis;

    private Long serviceOrderId;

    private Long travelId;

    private Long vehicleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getMuelle() {
        return muelle;
    }

    public void setMuelle(String muelle) {
        this.muelle = muelle;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getMercaderia() {
        return mercaderia;
    }

    public void setMercaderia(String mercaderia) {
        this.mercaderia = mercaderia;
    }

    public String getConsignatario() {
        return consignatario;
    }

    public void setConsignatario(String consignatario) {
        this.consignatario = consignatario;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public Long getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(Long serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoroOperationDTO that = (RoroOperationDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RoroOperationDTO{" +
                "id=" + id +
                ", puerto='" + puerto + '\'' +
                ", terminal='" + terminal + '\'' +
                ", fecha=" + fecha +
                ", muelle='" + muelle + '\'' +
                ", cliente='" + cliente + '\'' +
                ", operacion='" + operacion + '\'' +
                ", bl='" + bl + '\'' +
                ", mercaderia='" + mercaderia + '\'' +
                ", consignatario='" + consignatario + '\'' +
                ", chassis='" + chassis + '\'' +
                ", serviceOrderId=" + serviceOrderId +
                ", travelId=" + travelId +
                ", vehicleId=" + vehicleId +
                '}';
    }
}
