//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.06 at 02:00:14 PM MDT 
//


package gme.cacore_cacore._4_4.edu_northwestern_radiology;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScaleType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ScaleType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Nominal"/>
 *     &lt;enumeration value="Ordinal"/>
 *     &lt;enumeration value="Ratio"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ScaleType")
@XmlEnum
public enum ScaleType {

    @XmlEnumValue("Nominal")
    NOMINAL("Nominal"),
    @XmlEnumValue("Ordinal")
    ORDINAL("Ordinal"),
    @XmlEnumValue("Ratio")
    RATIO("Ratio");
    private final String value;

    ScaleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ScaleType fromValue(String v) {
        for (ScaleType c: ScaleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
