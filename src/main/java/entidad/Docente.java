/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author es982
 */
@Entity
@Table(name = "docente")
@NamedQueries({
    @NamedQuery(name = "Docente.findAll", query = "SELECT d FROM Docente d"),
    @NamedQuery(name = "Docente.findByNoTarjeta", query = "SELECT d FROM Docente d WHERE d.noTarjeta = :noTarjeta")})
public class Docente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "no_tarjeta")
    private String noTarjeta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docente")
    private Collection<DocenteProyecto> docenteProyectoCollection;
    @JoinColumn(name = "id_departamento", referencedColumnName = "id_departamento")
    @ManyToOne(optional = false)
    private Departamento idDepartamento;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @OneToOne(optional = false)
    private Persona idPersona;

    public Docente() {
    }

    public Docente(String noTarjeta) {
        this.noTarjeta = noTarjeta;
    }

    public String getNoTarjeta() {
        return noTarjeta;
    }

    public void setNoTarjeta(String noTarjeta) {
        this.noTarjeta = noTarjeta;
    }

    public Collection<DocenteProyecto> getDocenteProyectoCollection() {
        return docenteProyectoCollection;
    }

    public void setDocenteProyectoCollection(Collection<DocenteProyecto> docenteProyectoCollection) {
        this.docenteProyectoCollection = docenteProyectoCollection;
    }

    public Departamento getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Departamento idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noTarjeta != null ? noTarjeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Docente)) {
            return false;
        }
        Docente other = (Docente) object;
        if ((this.noTarjeta == null && other.noTarjeta != null) || (this.noTarjeta != null && !this.noTarjeta.equals(other.noTarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Docente[ noTarjeta=" + noTarjeta + " ]";
    }
    
}
