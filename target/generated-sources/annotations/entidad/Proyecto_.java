package entidad;

import entidad.DocenteProyecto;
import entidad.Empresa;
import entidad.Estudiante;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-08-01T01:59:14", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Proyecto.class)
public class Proyecto_ { 

    public static volatile SingularAttribute<Proyecto, String> descripcion;
    public static volatile CollectionAttribute<Proyecto, Estudiante> estudianteCollection;
    public static volatile SingularAttribute<Proyecto, Integer> idProyecto;
    public static volatile SingularAttribute<Proyecto, String> estado;
    public static volatile SingularAttribute<Proyecto, Integer> numAlumnosSug;
    public static volatile SingularAttribute<Proyecto, Date> fechaInicio;
    public static volatile SingularAttribute<Proyecto, Date> fechaRegistro;
    public static volatile SingularAttribute<Proyecto, String> urlDocumento;
    public static volatile SingularAttribute<Proyecto, String> nombreProyecto;
    public static volatile CollectionAttribute<Proyecto, DocenteProyecto> docenteProyectoCollection;
    public static volatile SingularAttribute<Proyecto, Empresa> idEmpresa;
    public static volatile SingularAttribute<Proyecto, Date> fechaFin;

}