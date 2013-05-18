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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import uri.iso_org._21090.II;
import uri.iso_org._21090.INT;
import uri.iso_org._21090.ST;


/**
 * <p>Java class for TwoDimensionGeometricShapeEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TwoDimensionGeometricShapeEntity">
 *   &lt;complexContent>
 *     &lt;extension base="{gme://caCORE.caCORE/4.4/edu.northwestern.radiology.AIM}GeometricShapeEntity">
 *       &lt;sequence>
 *         &lt;element name="imageReferenceUid" type="{uri:iso.org:21090}II" minOccurs="0"/>
 *         &lt;element name="referencedFrameNumber" type="{uri:iso.org:21090}INT" minOccurs="0"/>
 *         &lt;element name="uri" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *         &lt;element name="twoDimensionSpatialCoordinateCollection">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TwoDimensionSpatialCoordinate" type="{gme://caCORE.caCORE/4.4/edu.northwestern.radiology.AIM}TwoDimensionSpatialCoordinate" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TwoDimensionGeometricShapeEntity", propOrder = {
    "imageReferenceUid",
    "referencedFrameNumber",
    "uri",
    "twoDimensionSpatialCoordinateCollection"
})
@XmlSeeAlso({
    TwoDimensionPoint.class,
    TwoDimensionCircle.class,
    TwoDimensionMultiPoint.class,
    TwoDimensionEllipse.class,
    TwoDimensionPolyline.class
})
public abstract class TwoDimensionGeometricShapeEntity
    extends GeometricShapeEntity
{

    protected II imageReferenceUid;
    protected INT referencedFrameNumber;
    protected ST uri;
    @XmlElement(required = true)
    protected TwoDimensionGeometricShapeEntity.TwoDimensionSpatialCoordinateCollection twoDimensionSpatialCoordinateCollection;

    /**
     * Gets the value of the imageReferenceUid property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getImageReferenceUid() {
        return imageReferenceUid;
    }

    /**
     * Sets the value of the imageReferenceUid property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setImageReferenceUid(II value) {
        this.imageReferenceUid = value;
    }

    /**
     * Gets the value of the referencedFrameNumber property.
     * 
     * @return
     *     possible object is
     *     {@link INT }
     *     
     */
    public INT getReferencedFrameNumber() {
        return referencedFrameNumber;
    }

    /**
     * Sets the value of the referencedFrameNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link INT }
     *     
     */
    public void setReferencedFrameNumber(INT value) {
        this.referencedFrameNumber = value;
    }

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
     * Gets the value of the twoDimensionSpatialCoordinateCollection property.
     * 
     * @return
     *     possible object is
     *     {@link TwoDimensionGeometricShapeEntity.TwoDimensionSpatialCoordinateCollection }
     *     
     */
    public TwoDimensionGeometricShapeEntity.TwoDimensionSpatialCoordinateCollection getTwoDimensionSpatialCoordinateCollection() {
        return twoDimensionSpatialCoordinateCollection;
    }

    /**
     * Sets the value of the twoDimensionSpatialCoordinateCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link TwoDimensionGeometricShapeEntity.TwoDimensionSpatialCoordinateCollection }
     *     
     */
    public void setTwoDimensionSpatialCoordinateCollection(TwoDimensionGeometricShapeEntity.TwoDimensionSpatialCoordinateCollection value) {
        this.twoDimensionSpatialCoordinateCollection = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="TwoDimensionSpatialCoordinate" type="{gme://caCORE.caCORE/4.4/edu.northwestern.radiology.AIM}TwoDimensionSpatialCoordinate" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "twoDimensionSpatialCoordinate"
    })
    public static class TwoDimensionSpatialCoordinateCollection {

        @XmlElement(name = "TwoDimensionSpatialCoordinate", required = true)
        protected List<TwoDimensionSpatialCoordinate> twoDimensionSpatialCoordinate;

        /**
         * Gets the value of the twoDimensionSpatialCoordinate property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the twoDimensionSpatialCoordinate property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTwoDimensionSpatialCoordinate().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TwoDimensionSpatialCoordinate }
         * 
         * 
         */
        public List<TwoDimensionSpatialCoordinate> getTwoDimensionSpatialCoordinate() {
            if (twoDimensionSpatialCoordinate == null) {
                twoDimensionSpatialCoordinate = new ArrayList<TwoDimensionSpatialCoordinate>();
            }
            return this.twoDimensionSpatialCoordinate;
        }

    }

}
