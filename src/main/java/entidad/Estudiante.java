/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import static entidad.Persona_.apellidoM;
import static entidad.Persona_.apellidoP;
import static entidad.Persona_.nombre;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author es982
 */
@Entity
@Table(name = "estudiante")
@NamedQueries({
    @NamedQuery(
        name = "Estudiante.findEstudianteByExpediente",
        query = "SELECT e FROM Estudiante e WHERE e.idExpediente = :idExpediente"
    ),
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e"),
    @NamedQuery(name = "Estudiante.findByNumControl", query = "SELECT e FROM Estudiante e WHERE e.numControl = :numControl"),
    @NamedQuery(name = "Estudiante.findBySemestre", query = "SELECT e FROM Estudiante e WHERE e.semestre = :semestre")})
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "num_control")
    private String numControl;
    @Basic(optional = false)
    @Column(name = "semestre")
    private int semestre;
    @JoinColumn(name = "id_departamento", referencedColumnName = "id_departamento")
    @ManyToOne(optional = false)
    private Departamento idDepartamento;
    @JoinColumn(name = "id_expediente", referencedColumnName = "id_expediente")
    @ManyToOne(optional = false)
    private Expediente idExpediente;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @OneToOne(optional = false)
    private Persona idPersona;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto")
    @ManyToOne
    private Proyecto idProyecto;

    public Estudiante() {
    }

    public Estudiante(String numControl) {
        this.numControl = numControl;
    }

    public Estudiante(String numControl, int semestre) {
        this.numControl = numControl;
        this.semestre = semestre;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Departamento getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Departamento idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Expediente getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(Expediente idExpediente) {
        this.idExpediente = idExpediente;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public Proyecto getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Proyecto idProyecto) {
        this.idProyecto = idProyecto;
    }
    
        public String getNombreCompleto() {
    return nombre + " " + apellidoP + " " + apellidoM;
}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numControl != null ? numControl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.numControl == null && other.numControl != null) || (this.numControl != null && !this.numControl.equals(other.numControl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Estudiante[ numControl=" + numControl + " ]";
    }
    
}
