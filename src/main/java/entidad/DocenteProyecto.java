/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author es982
 */
@Entity
@Table(name = "docente_proyecto")
@NamedQueries({
    @NamedQuery(name = "DocenteProyecto.findAll", query = "SELECT d FROM DocenteProyecto d"),
    @NamedQuery(name = "DocenteProyecto.findByIdProyecto", query = "SELECT d FROM DocenteProyecto d WHERE d.docenteProyectoPK.idProyecto = :idProyecto"),
    @NamedQuery(name = "DocenteProyecto.findByNoTarjeta", query = "SELECT d FROM DocenteProyecto d WHERE d.docenteProyectoPK.noTarjeta = :noTarjeta"),
    @NamedQuery(name = "DocenteProyecto.findByRolDocente", query = "SELECT d FROM DocenteProyecto d WHERE d.rolDocente = :rolDocente")})
public class DocenteProyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DocenteProyectoPK docenteProyectoPK;
    @Column(name = "rol_docente")
    private String rolDocente;
    @JoinColumn(name = "no_tarjeta", referencedColumnName = "no_tarjeta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Docente docente;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;

    public DocenteProyecto() {
    }

    public DocenteProyecto(DocenteProyectoPK docenteProyectoPK) {
        this.docenteProyectoPK = docenteProyectoPK;
    }

    public DocenteProyecto(int idProyecto, String noTarjeta) {
        this.docenteProyectoPK = new DocenteProyectoPK(idProyecto, noTarjeta);
    }

    public DocenteProyectoPK getDocenteProyectoPK() {
        return docenteProyectoPK;
    }

    public void setDocenteProyectoPK(DocenteProyectoPK docenteProyectoPK) {
        this.docenteProyectoPK = docenteProyectoPK;
    }

    public String getRolDocente() {
        return rolDocente;
    }

    public void setRolDocente(String rolDocente) {
        this.rolDocente = rolDocente;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docenteProyectoPK != null ? docenteProyectoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocenteProyecto)) {
            return false;
        }
        DocenteProyecto other = (DocenteProyecto) object;
        if ((this.docenteProyectoPK == null && other.docenteProyectoPK != null) || (this.docenteProyectoPK != null && !this.docenteProyectoPK.equals(other.docenteProyectoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.DocenteProyecto[ docenteProyectoPK=" + docenteProyectoPK + " ]";
    }
    
}
