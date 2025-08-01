package entidad;

import entidad.Departamento;
import entidad.Expediente;
import entidad.Persona;
import entidad.Proyecto;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-08-01T01:59:14", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Estudiante.class)
public class Estudiante_ { 

    public static volatile SingularAttribute<Estudiante, Departamento> idDepartamento;
    public static volatile SingularAttribute<Estudiante, Proyecto> idProyecto;
    public static volatile SingularAttribute<Estudiante, String> numControl;
    public static volatile SingularAttribute<Estudiante, Expediente> idExpediente;
    public static volatile SingularAttribute<Estudiante, Integer> semestre;
    public static volatile SingularAttribute<Estudiante, Persona> idPersona;

}