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
import uri.iso_org._21090.CD;
import uri.iso_org._21090.II;
import uri.iso_org._21090.ST;
import uri.iso_org._21090.TS;


/**
 * <p>Java class for TaskContextEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaskContextEntity">
 *   &lt;complexContent>
 *     &lt;extension base="{gme://caCORE.caCORE/4.4/edu.northwestern.radiology.AIM}Entity">
 *       &lt;sequence>
 *         &lt;element name="worklistTaskUid" type="{uri:iso.org:21090}II"/>
 *         &lt;element name="worklistTaskName" type="{uri:iso.org:21090}ST"/>
 *         &lt;element name="worklistTaskDescription" type="{uri:iso.org:21090}ST"/>
 *         &lt;element name="worklistTaskCategory" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="worklistTaskLevel" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="worklistTaskType" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="worklistTaskRepeatType" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="worklistTaskVariabilityType" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="worklistTaskVersion" type="{uri:iso.org:21090}ST"/>
 *         &lt;element name="worklistSubtaskUid" type="{uri:iso.org:21090}II"/>
 *         &lt;element name="worklistSubtaskName" type="{uri:iso.org:21090}ST"/>
 *         &lt;element name="worklistSubtaskStartDateTime" type="{uri:iso.org:21090}TS"/>
 *         &lt;element name="worklistSubtaskClosedDateTime" type="{uri:iso.org:21090}TS"/>
 *         &lt;element name="taskContextEntityCollection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TaskContextEntity" type="{gme://caCORE.caCORE/4.4/edu.northwestern.radiology.AIM}TaskContextEntity" maxOccurs="unbounded"/>
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
@XmlType(name = "TaskContextEntity", propOrder = {
    "worklistTaskUid",
    "worklistTaskName",
    "worklistTaskDescription",
    "worklistTaskCategory",
    "worklistTaskLevel",
    "worklistTaskType",
    "worklistTaskRepeatType",
    "worklistTaskVariabilityType",
    "worklistTaskVersion",
    "worklistSubtaskUid",
    "worklistSubtaskName",
    "worklistSubtaskStartDateTime",
    "worklistSubtaskClosedDateTime",
    "taskContextEntityCollection"
})
public class TaskContextEntity
    extends Entity
{

    @XmlElement(required = true)
    protected II worklistTaskUid;
    @XmlElement(required = true)
    protected ST worklistTaskName;
    @XmlElement(required = true)
    protected ST worklistTaskDescription;
    @XmlElement(required = true)
    protected CD worklistTaskCategory;
    @XmlElement(required = true)
    protected CD worklistTaskLevel;
    @XmlElement(required = true)
    protected CD worklistTaskType;
    protected CD worklistTaskRepeatType;
    protected CD worklistTaskVariabilityType;
    @XmlElement(required = true)
    protected ST worklistTaskVersion;
    @XmlElement(required = true)
    protected II worklistSubtaskUid;
    @XmlElement(required = true)
    protected ST worklistSubtaskName;
    @XmlElement(required = true)
    protected TS worklistSubtaskStartDateTime;
    @XmlElement(required = true)
    protected TS worklistSubtaskClosedDateTime;
    protected TaskContextEntity.TaskContextEntityCollection taskContextEntityCollection;

    /**
     * Gets the value of the worklistTaskUid property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getWorklistTaskUid() {
        return worklistTaskUid;
    }

    /**
     * Sets the value of the worklistTaskUid property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setWorklistTaskUid(II value) {
        this.worklistTaskUid = value;
    }

    /**
     * Gets the value of the worklistTaskName property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getWorklistTaskName() {
        return worklistTaskName;
    }

    /**
     * Sets the value of the worklistTaskName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setWorklistTaskName(ST value) {
        this.worklistTaskName = value;
    }

    /**
     * Gets the value of the worklistTaskDescription property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getWorklistTaskDescription() {
        return worklistTaskDescription;
    }

    /**
     * Sets the value of the worklistTaskDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setWorklistTaskDescription(ST value) {
        this.worklistTaskDescription = value;
    }

    /**
     * Gets the value of the worklistTaskCategory property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getWorklistTaskCategory() {
        return worklistTaskCategory;
    }

    /**
     * Sets the value of the worklistTaskCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setWorklistTaskCategory(CD value) {
        this.worklistTaskCategory = value;
    }

    /**
     * Gets the value of the worklistTaskLevel property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getWorklistTaskLevel() {
        return worklistTaskLevel;
    }

    /**
     * Sets the value of the worklistTaskLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setWorklistTaskLevel(CD value) {
        this.worklistTaskLevel = value;
    }

    /**
     * Gets the value of the worklistTaskType property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getWorklistTaskType() {
        return worklistTaskType;
    }

    /**
     * Sets the value of the worklistTaskType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setWorklistTaskType(CD value) {
        this.worklistTaskType = value;
    }

    /**
     * Gets the value of the worklistTaskRepeatType property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getWorklistTaskRepeatType() {
        return worklistTaskRepeatType;
    }

    /**
     * Sets the value of the worklistTaskRepeatType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setWorklistTaskRepeatType(CD value) {
        this.worklistTaskRepeatType = value;
    }

    /**
     * Gets the value of the worklistTaskVariabilityType property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getWorklistTaskVariabilityType() {
        return worklistTaskVariabilityType;
    }

    /**
     * Sets the value of the worklistTaskVariabilityType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setWorklistTaskVariabilityType(CD value) {
        this.worklistTaskVariabilityType = value;
    }

    /**
     * Gets the value of the worklistTaskVersion property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getWorklistTaskVersion() {
        return worklistTaskVersion;
    }

    /**
     * Sets the value of the worklistTaskVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setWorklistTaskVersion(ST value) {
        this.worklistTaskVersion = value;
    }

    /**
     * Gets the value of the worklistSubtaskUid property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getWorklistSubtaskUid() {
        return worklistSubtaskUid;
    }

    /**
     * Sets the value of the worklistSubtaskUid property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setWorklistSubtaskUid(II value) {
        this.worklistSubtaskUid = value;
    }

    /**
     * Gets the value of the worklistSubtaskName property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getWorklistSubtaskName() {
        return worklistSubtaskName;
    }

    /**
     * Sets the value of the worklistSubtaskName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setWorklistSubtaskName(ST value) {
        this.worklistSubtaskName = value;
    }

    /**
     * Gets the value of the worklistSubtaskStartDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link TS }
     *     
     */
    public TS getWorklistSubtaskStartDateTime() {
        return worklistSubtaskStartDateTime;
    }

    /**
     * Sets the value of the worklistSubtaskStartDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link TS }
     *     
     */
    public void setWorklistSubtaskStartDateTime(TS value) {
        this.worklistSubtaskStartDateTime = value;
    }

    /**
     * Gets the value of the worklistSubtaskClosedDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link TS }
     *     
     */
    public TS getWorklistSubtaskClosedDateTime() {
        return worklistSubtaskClosedDateTime;
    }

    /**
     * Sets the value of the worklistSubtaskClosedDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link TS }
     *     
     */
    public void setWorklistSubtaskClosedDateTime(TS value) {
        this.worklistSubtaskClosedDateTime = value;
    }

    /**
     * Gets the value of the taskContextEntityCollection property.
     * 
     * @return
     *     possible object is
     *     {@link TaskContextEntity.TaskContextEntityCollection }
     *     
     */
    public TaskContextEntity.TaskContextEntityCollection getTaskContextEntityCollection() {
        return taskContextEntityCollection;
    }

    /**
     * Sets the value of the taskContextEntityCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaskContextEntity.TaskContextEntityCollection }
     *     
     */
    public void setTaskContextEntityCollection(TaskContextEntity.TaskContextEntityCollection value) {
        this.taskContextEntityCollection = value;
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
     *         &lt;element name="TaskContextEntity" type="{gme://caCORE.caCORE/4.4/edu.northwestern.radiology.AIM}TaskContextEntity" maxOccurs="unbounded"/>
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
        "taskContextEntity"
    })
    public static class TaskContextEntityCollection {

        @XmlElement(name = "TaskContextEntity", required = true)
        protected List<TaskContextEntity> taskContextEntity;

        /**
         * Gets the value of the taskContextEntity property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the taskContextEntity property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTaskContextEntity().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TaskContextEntity }
         * 
         * 
         */
        public List<TaskContextEntity> getTaskContextEntity() {
            if (taskContextEntity == null) {
                taskContextEntity = new ArrayList<TaskContextEntity>();
            }
            return this.taskContextEntity;
        }

    }

}
