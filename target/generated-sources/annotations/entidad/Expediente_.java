package entidad;

import entidad.Documento;
import entidad.Estudiante;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-08-01T01:59:14", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Expediente.class)
public class Expediente_ { 

    public static volatile CollectionAttribute<Expediente, Estudiante> estudianteCollection;
    public static volatile SingularAttribute<Expediente, String> nombreExpediente;
    public static volatile SingularAttribute<Expediente, Integer> idExpediente;
    public static volatile CollectionAttribute<Expediente, Documento> documentoCollection;
    public static volatile SingularAttribute<Expediente, Date> fechaCreacion;

}