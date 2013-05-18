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
import uri.iso_org._21090.BL;
import uri.iso_org._21090.CD;
import uri.iso_org._21090.II;
import uri.iso_org._21090.ST;


/**
 * <p>Java class for TimePointLesionObservationEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimePointLesionObservationEntity">
 *   &lt;complexContent>
 *     &lt;extension base="{gme://caCORE.caCORE/4.4/edu.northwestern.radiology.AIM}LesionObservationEntity">
 *       &lt;sequence>
 *         &lt;element name="calibration" type="{uri:iso.org:21090}BL"/>
 *         &lt;element name="predecessorLesionTrackingUid" type="{uri:iso.org:21090}II" minOccurs="0"/>
 *         &lt;element name="comment" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *         &lt;element name="therapeuticResponse" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="qualitativeAssessment" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="canEvaluateLesion" type="{uri:iso.org:21090}BL" minOccurs="0"/>
 *         &lt;element name="reasonUnableToEvaluate" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="canMeasureLesion" type="{uri:iso.org:21090}BL" minOccurs="0"/>
 *         &lt;element name="reasonUnableToMeasure" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="isUnequivocalProgression" type="{uri:iso.org:21090}BL" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimePointLesionObservationEntity", propOrder = {
    "calibration",
    "predecessorLesionTrackingUid",
    "comment",
    "therapeuticResponse",
    "qualitativeAssessment",
    "canEvaluateLesion",
    "reasonUnableToEvaluate",
    "canMeasureLesion",
    "reasonUnableToMeasure",
    "isUnequivocalProgression"
})
public class TimePointLesionObservationEntity
    extends LesionObservationEntity
{

    @XmlElement(required = true)
    protected BL calibration;
    protected II predecessorLesionTrackingUid;
    protected ST comment;
    protected CD therapeuticResponse;
    protected CD qualitativeAssessment;
    protected BL canEvaluateLesion;
    protected CD reasonUnableToEvaluate;
    protected BL canMeasureLesion;
    protected CD reasonUnableToMeasure;
    protected BL isUnequivocalProgression;

    /**
     * Gets the value of the calibration property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getCalibration() {
        return calibration;
    }

    /**
     * Sets the value of the calibration property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setCalibration(BL value) {
        this.calibration = value;
    }

    /**
     * Gets the value of the predecessorLesionTrackingUid property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getPredecessorLesionTrackingUid() {
        return predecessorLesionTrackingUid;
    }

    /**
     * Sets the value of the predecessorLesionTrackingUid property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setPredecessorLesionTrackingUid(II value) {
        this.predecessorLesionTrackingUid = value;
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
     * Gets the value of the therapeuticResponse property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getTherapeuticResponse() {
        return therapeuticResponse;
    }

    /**
     * Sets the value of the therapeuticResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setTherapeuticResponse(CD value) {
        this.therapeuticResponse = value;
    }

    /**
     * Gets the value of the qualitativeAssessment property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getQualitativeAssessment() {
        return qualitativeAssessment;
    }

    /**
     * Sets the value of the qualitativeAssessment property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setQualitativeAssessment(CD value) {
        this.qualitativeAssessment = value;
    }

    /**
     * Gets the value of the canEvaluateLesion property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getCanEvaluateLesion() {
        return canEvaluateLesion;
    }

    /**
     * Sets the value of the canEvaluateLesion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setCanEvaluateLesion(BL value) {
        this.canEvaluateLesion = value;
    }

    /**
     * Gets the value of the reasonUnableToEvaluate property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getReasonUnableToEvaluate() {
        return reasonUnableToEvaluate;
    }

    /**
     * Sets the value of the reasonUnableToEvaluate property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setReasonUnableToEvaluate(CD value) {
        this.reasonUnableToEvaluate = value;
    }

    /**
     * Gets the value of the canMeasureLesion property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getCanMeasureLesion() {
        return canMeasureLesion;
    }

    /**
     * Sets the value of the canMeasureLesion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setCanMeasureLesion(BL value) {
        this.canMeasureLesion = value;
    }

    /**
     * Gets the value of the reasonUnableToMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getReasonUnableToMeasure() {
        return reasonUnableToMeasure;
    }

    /**
     * Sets the value of the reasonUnableToMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setReasonUnableToMeasure(CD value) {
        this.reasonUnableToMeasure = value;
    }

    /**
     * Gets the value of the isUnequivocalProgression property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getIsUnequivocalProgression() {
        return isUnequivocalProgression;
    }

    /**
     * Sets the value of the isUnequivocalProgression property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setIsUnequivocalProgression(BL value) {
        this.isUnequivocalProgression = value;
    }

}
