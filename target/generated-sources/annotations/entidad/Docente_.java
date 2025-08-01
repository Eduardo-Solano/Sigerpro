package entidad;

import entidad.Departamento;
import entidad.DocenteProyecto;
import entidad.Persona;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-08-01T01:59:14", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Docente.class)
public class Docente_ { 

    public static volatile SingularAttribute<Docente, Departamento> idDepartamento;
    public static volatile CollectionAttribute<Docente, DocenteProyecto> docenteProyectoCollection;
    public static volatile SingularAttribute<Docente, Persona> idPersona;
    public static volatile SingularAttribute<Docente, String> noTarjeta;

}