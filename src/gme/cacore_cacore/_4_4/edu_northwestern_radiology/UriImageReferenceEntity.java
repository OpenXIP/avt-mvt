//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.06 at 02:00:14 PM MDT 
//


package gme.cacore_cacore._4_4.edu_northwestern_radiology;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import uri.iso_org._21090.ST;


/**
 * <p>Java class for UriImageReferenceEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UriImageReferenceEntity">
 *   &lt;complexContent>
 *     &lt;extension base="{gme://caCORE.caCORE/4.4/edu.northwestern.radiology.AIM}ImageReferenceEntity">
 *       &lt;sequence>
 *         &lt;element name="uri" type="{uri:iso.org:21090}ST"/>
 *         &lt;element name="mimeType" type="{uri:iso.org:21090}ST"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UriImageReferenceEntity", propOrder = {
    "uri",
    "mimeType"
})
public class UriImageReferenceEntity
    extends ImageReferenceEntity
{

    @XmlElement(required = true)
    protected ST uri;
    @XmlElement(required = true)
    protected ST mimeType;

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getUri() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setUri(ST value) {
        this.uri = value;
    }

    /**
     * Gets the value of the mimeType property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getMimeType() {
        return mimeType;
    }

    /**
     * Sets the value of the mimeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setMimeType(ST value) {
        this.mimeType = value;
    }

}
