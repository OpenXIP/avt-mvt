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
 * <p>Java class for CalculationResultIdentifier.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CalculationResultIdentifier">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Array"/>
 *     &lt;enumeration value="Binary"/>
 *     &lt;enumeration value="Histogram"/>
 *     &lt;enumeration value="Matrix"/>
 *     &lt;enumeration value="Scalar"/>
 *     &lt;enumeration value="Vector"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CalculationResultIdentifier")
@XmlEnum
public enum CalculationResultIdentifier {

    @XmlEnumValue("Array")
    ARRAY("Array"),
    @XmlEnumValue("Binary")
    BINARY("Binary"),
    @XmlEnumValue("Histogram")
    HISTOGRAM("Histogram"),
    @XmlEnumValue("Matrix")
    MATRIX("Matrix"),
    @XmlEnumValue("Scalar")
    SCALAR("Scalar"),
    @XmlEnumValue("Vector")
    VECTOR("Vector");
    private final String value;

    CalculationResultIdentifier(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CalculationResultIdentifier fromValue(String v) {
        for (CalculationResultIdentifier c: CalculationResultIdentifier.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}