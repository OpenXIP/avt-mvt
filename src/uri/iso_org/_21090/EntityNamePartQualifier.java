//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.06 at 02:00:14 PM MDT 
//


package uri.iso_org._21090;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EntityNamePartQualifier.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EntityNamePartQualifier">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="LS"/>
 *     &lt;enumeration value="AC"/>
 *     &lt;enumeration value="NB"/>
 *     &lt;enumeration value="PR"/>
 *     &lt;enumeration value="VV"/>
 *     &lt;enumeration value="AD"/>
 *     &lt;enumeration value="BR"/>
 *     &lt;enumeration value="SP"/>
 *     &lt;enumeration value="CL"/>
 *     &lt;enumeration value="IN"/>
 *     &lt;enumeration value="TITLE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EntityNamePartQualifier")
@XmlEnum
public enum EntityNamePartQualifier {

    LS,
    AC,
    NB,
    PR,
    VV,
    AD,
    BR,
    SP,
    CL,
    IN,
    TITLE;

    public String value() {
        return name();
    }

    public static EntityNamePartQualifier fromValue(String v) {
        return valueOf(v);
    }

}
