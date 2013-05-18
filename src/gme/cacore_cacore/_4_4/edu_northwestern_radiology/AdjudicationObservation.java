//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.06 at 02:00:14 PM MDT 
//


package gme.cacore_cacore._4_4.edu_northwestern_radiology;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import uri.iso_org._21090.BL;
import uri.iso_org._21090.CD;
import uri.iso_org._21090.II;
import uri.iso_org._21090.ST;


/**
 * <p>Java class for AdjudicationObservation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdjudicationObservation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="observationUid" type="{uri:iso.org:21090}II"/>
 *         &lt;element name="observationScope" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="personObserversRoleInThisProcedure" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="identifierWithinAcceptedPersonObserversRole" type="{uri:iso.org:21090}ST" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="identifierWithinRejectedPersonObserversRole" type="{uri:iso.org:21090}ST" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reasonForChoice" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="reasonForDiscordance" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="comment" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *         &lt;element name="imageQualityIssuesDiscordance" type="{uri:iso.org:21090}BL"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdjudicationObservation", propOrder = {
    "observationUid",
    "observationScope",
    "personObserversRoleInThisProcedure",
    "identifierWithinAcceptedPersonObserversRole",
    "identifierWithinRejectedPersonObserversRole",
    "reasonForChoice",
    "reasonForDiscordance",
    "comment",
    "imageQualityIssuesDiscordance"
})
public class AdjudicationObservation {

    @XmlElement(required = true)
    protected II observationUid;
    @XmlElement(required = true)
    protected CD observationScope;
    protected CD personObserversRoleInThisProcedure;
    protected List<ST> identifierWithinAcceptedPersonObserversRole;
    protected List<ST> identifierWithinRejectedPersonObserversRole;
    @XmlElement(required = true)
    protected CD reasonForChoice;
    protected CD reasonForDiscordance;
    protected ST comment;
    @XmlElement(required = true)
    protected BL imageQualityIssuesDiscordance;

    /**
     * Gets the value of the observationUid property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getObservationUid() {
        return observationUid;
    }

    /**
     * Sets the value of the observationUid property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setObservationUid(II value) {
        this.observationUid = value;
    }

    /**
     * Gets the value of the observationScope property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getObservationScope() {
        return observationScope;
    }

    /**
     * Sets the value of the observationScope property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setObservationScope(CD value) {
        this.observationScope = value;
    }

    /**
     * Gets the value of the personObserversRoleInThisProcedure property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getPersonObserversRoleInThisProcedure() {
        return personObserversRoleInThisProcedure;
    }

    /**
     * Sets the value of the personObserversRoleInThisProcedure property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setPersonObserversRoleInThisProcedure(CD value) {
        this.personObserversRoleInThisProcedure = value;
    }

    /**
     * Gets the value of the identifierWithinAcceptedPersonObserversRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identifierWithinAcceptedPersonObserversRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentifierWithinAcceptedPersonObserversRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ST }
     * 
     * 
     */
    public List<ST> getIdentifierWithinAcceptedPersonObserversRole() {
        if (identifierWithinAcceptedPersonObserversRole == null) {
            identifierWithinAcceptedPersonObserversRole = new ArrayList<ST>();
        }
        return this.identifierWithinAcceptedPersonObserversRole;
    }

    /**
     * Gets the value of the identifierWithinRejectedPersonObserversRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identifierWithinRejectedPersonObserversRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentifierWithinRejectedPersonObserversRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ST }
     * 
     * 
     */
    public List<ST> getIdentifierWithinRejectedPersonObserversRole() {
        if (identifierWithinRejectedPersonObserversRole == null) {
            identifierWithinRejectedPersonObserversRole = new ArrayList<ST>();
        }
        return this.identifierWithinRejectedPersonObserversRole;
    }

    /**
     * Gets the value of the reasonForChoice property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getReasonForChoice() {
        return reasonForChoice;
    }

    /**
     * Sets the value of the reasonForChoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setReasonForChoice(CD value) {
        this.reasonForChoice = value;
    }

    /**
     * Gets the value of the reasonForDiscordance property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getReasonForDiscordance() {
        return reasonForDiscordance;
    }

    /**
     * Sets the value of the reasonForDiscordance property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setReasonForDiscordance(CD value) {
        this.reasonForDiscordance = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setComment(ST value) {
        this.comment = value;
    }

    /**
     * Gets the value of the imageQualityIssuesDiscordance property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getImageQualityIssuesDiscordance() {
        return imageQualityIssuesDiscordance;
    }

    /**
     * Sets the value of the imageQualityIssuesDiscordance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setImageQualityIssuesDiscordance(BL value) {
        this.imageQualityIssuesDiscordance = value;
    }

}
