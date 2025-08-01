package entidad;

import entidad.Proyecto;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-08-01T01:59:14", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Empresa.class)
public class Empresa_ { 

    public static volatile SingularAttribute<Empresa, String> descripcion;
    public static volatile SingularAttribute<Empresa, String> numeroTelefonico;
    public static volatile SingularAttribute<Empresa, Integer> idEmpresa;
    public static volatile CollectionAttribute<Empresa, Proyecto> proyectoCollection;
    public static volatile SingularAttribute<Empresa, String> rfc;
    public static volatile SingularAttribute<Empresa, String> nombreEmpresa;

}