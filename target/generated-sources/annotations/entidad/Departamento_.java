package entidad;

import entidad.Docente;
import entidad.Estudiante;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-08-01T01:59:14", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Departamento.class)
public class Departamento_ { 

    public static volatile CollectionAttribute<Departamento, Estudiante> estudianteCollection;
    public static volatile SingularAttribute<Departamento, Integer> idDepartamento;
    public static volatile CollectionAttribute<Departamento, Docente> docenteCollection;
    public static volatile SingularAttribute<Departamento, String> nombreDepartamento;

}