
package com.github.bogdanovmn.translator.web.etl.model;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WordToSourceLink complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WordToSourceLink">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="wordId" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="sourceId" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WordToSourceLink")
public class WordToSourceLink {

    @XmlAttribute(name = "wordId")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger wordId;
    @XmlAttribute(name = "sourceId")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger sourceId;
    @XmlAttribute(name = "count")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger count;

    /**
     * Gets the value of the wordId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getWordId() {
        return wordId;
    }

    /**
     * Sets the value of the wordId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setWordId(BigInteger value) {
        this.wordId = value;
    }

    /**
     * Gets the value of the sourceId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSourceId() {
        return sourceId;
    }

    /**
     * Sets the value of the sourceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSourceId(BigInteger value) {
        this.sourceId = value;
    }

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCount(BigInteger value) {
        this.count = value;
    }

}
