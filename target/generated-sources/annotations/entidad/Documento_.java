package entidad;

import entidad.Expediente;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-08-01T01:59:14", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Documento.class)
public class Documento_ { 

    public static volatile SingularAttribute<Documento, Integer> idDocumento;
    public static volatile SingularAttribute<Documento, Expediente> idExpediente;
    public static volatile SingularAttribute<Documento, String> nombreDocumento;
    public static volatile SingularAttribute<Documento, Date> fechaDeCarga;
    public static volatile SingularAttribute<Documento, String> url;

}