package entidad;

import entidad.Docente;
import entidad.DocenteProyectoPK;
import entidad.Proyecto;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-08-01T01:59:14", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(DocenteProyecto.class)
public class DocenteProyecto_ { 

    public static volatile SingularAttribute<DocenteProyecto, String> rolDocente;
    public static volatile SingularAttribute<DocenteProyecto, Docente> docente;
    public static volatile SingularAttribute<DocenteProyecto, Proyecto> proyecto;
    public static volatile SingularAttribute<DocenteProyecto, DocenteProyectoPK> docenteProyectoPK;

}