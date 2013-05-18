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
 * <p>Java class for AimVersion.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AimVersion">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AIMv1_0"/>
 *     &lt;enumeration value="AIMv2_0"/>
 *     &lt;enumeration value="AIMv3_0_1"/>
 *     &lt;enumeration value="AIMv3_0_2"/>
 *     &lt;enumeration value="AIMv4_0"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AimVersion")
@XmlEnum
public enum AimVersion {

    @XmlEnumValue("AIMv1_0")
    AI_MV_1_0("AIMv1_0"),
    @XmlEnumValue("AIMv2_0")
    AI_MV_2_0("AIMv2_0"),
    @XmlEnumValue("AIMv3_0_1")
    AI_MV_3_0_1("AIMv3_0_1"),
    @XmlEnumValue("AIMv3_0_2")
    AI_MV_3_0_2("AIMv3_0_2"),
    @XmlEnumValue("AIMv4_0")
    AI_MV_4_0("AIMv4_0");
    private final String value;

    AimVersion(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AimVersion fromValue(String v) {
        for (AimVersion c: AimVersion.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
