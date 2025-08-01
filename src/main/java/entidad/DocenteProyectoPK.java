/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author es982
 */
@Embeddable
public class DocenteProyectoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_proyecto")
    private int idProyecto;
    @Basic(optional = false)
    @Column(name = "no_tarjeta")
    private String noTarjeta;

    public DocenteProyectoPK() {
    }

    public DocenteProyectoPK(int idProyecto, String noTarjeta) {
        this.idProyecto = idProyecto;
        this.noTarjeta = noTarjeta;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNoTarjeta() {
        return noTarjeta;
    }

    public void setNoTarjeta(String noTarjeta) {
        this.noTarjeta = noTarjeta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProyecto;
        hash += (noTarjeta != null ? noTarjeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocenteProyectoPK)) {
            return false;
        }
        DocenteProyectoPK other = (DocenteProyectoPK) object;
        if (this.idProyecto != other.idProyecto) {
            return false;
        }
        if ((this.noTarjeta == null && other.noTarjeta != null) || (this.noTarjeta != null && !this.noTarjeta.equals(other.noTarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.DocenteProyectoPK[ idProyecto=" + idProyecto + ", noTarjeta=" + noTarjeta + " ]";
    }
    
}
