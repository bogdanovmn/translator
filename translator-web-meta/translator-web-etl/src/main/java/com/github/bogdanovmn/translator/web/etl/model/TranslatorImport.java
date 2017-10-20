
package com.github.bogdanovmn.translator.web.etl.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="sources">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded">
 *                   &lt;element name="source" type="{}Source"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="translateProviders">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded">
 *                   &lt;element name="translateProvider" type="{}TranslateProvider"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="words">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded">
 *                   &lt;element name="word" type="{}Word"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="wordsToSourceLink">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded">
 *                   &lt;element name="link" type="{}WordToSourceLink"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="translates">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded">
 *                   &lt;element name="translate" type="{}Translate"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="users">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded">
 *                   &lt;element name="user" type="{}User"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "sources",
    "translateProviders",
    "words",
    "wordsToSourceLink",
    "translates",
    "users"
})
@XmlRootElement(name = "translatorImport")
public class TranslatorImport {

    @XmlElement(required = true)
    protected TranslatorImport.Sources sources;
    @XmlElement(required = true)
    protected TranslatorImport.TranslateProviders translateProviders;
    @XmlElement(required = true)
    protected TranslatorImport.Words words;
    @XmlElement(required = true)
    protected TranslatorImport.WordsToSourceLink wordsToSourceLink;
    @XmlElement(required = true)
    protected TranslatorImport.Translates translates;
    @XmlElement(required = true)
    protected TranslatorImport.Users users;

    /**
     * Gets the value of the sources property.
     * 
     * @return
     *     possible object is
     *     {@link TranslatorImport.Sources }
     *     
     */
    public TranslatorImport.Sources getSources() {
        return sources;
    }

    /**
     * Sets the value of the sources property.
     * 
     * @param value
     *     allowed object is
     *     {@link TranslatorImport.Sources }
     *     
     */
    public void setSources(TranslatorImport.Sources value) {
        this.sources = value;
    }

    /**
     * Gets the value of the translateProviders property.
     * 
     * @return
     *     possible object is
     *     {@link TranslatorImport.TranslateProviders }
     *     
     */
    public TranslatorImport.TranslateProviders getTranslateProviders() {
        return translateProviders;
    }

    /**
     * Sets the value of the translateProviders property.
     * 
     * @param value
     *     allowed object is
     *     {@link TranslatorImport.TranslateProviders }
     *     
     */
    public void setTranslateProviders(TranslatorImport.TranslateProviders value) {
        this.translateProviders = value;
    }

    /**
     * Gets the value of the words property.
     * 
     * @return
     *     possible object is
     *     {@link TranslatorImport.Words }
     *     
     */
    public TranslatorImport.Words getWords() {
        return words;
    }

    /**
     * Sets the value of the words property.
     * 
     * @param value
     *     allowed object is
     *     {@link TranslatorImport.Words }
     *     
     */
    public void setWords(TranslatorImport.Words value) {
        this.words = value;
    }

    /**
     * Gets the value of the wordsToSourceLink property.
     * 
     * @return
     *     possible object is
     *     {@link TranslatorImport.WordsToSourceLink }
     *     
     */
    public TranslatorImport.WordsToSourceLink getWordsToSourceLink() {
        return wordsToSourceLink;
    }

    /**
     * Sets the value of the wordsToSourceLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link TranslatorImport.WordsToSourceLink }
     *     
     */
    public void setWordsToSourceLink(TranslatorImport.WordsToSourceLink value) {
        this.wordsToSourceLink = value;
    }

    /**
     * Gets the value of the translates property.
     * 
     * @return
     *     possible object is
     *     {@link TranslatorImport.Translates }
     *     
     */
    public TranslatorImport.Translates getTranslates() {
        return translates;
    }

    /**
     * Sets the value of the translates property.
     * 
     * @param value
     *     allowed object is
     *     {@link TranslatorImport.Translates }
     *     
     */
    public void setTranslates(TranslatorImport.Translates value) {
        this.translates = value;
    }

    /**
     * Gets the value of the users property.
     * 
     * @return
     *     possible object is
     *     {@link TranslatorImport.Users }
     *     
     */
    public TranslatorImport.Users getUsers() {
        return users;
    }

    /**
     * Sets the value of the users property.
     * 
     * @param value
     *     allowed object is
     *     {@link TranslatorImport.Users }
     *     
     */
    public void setUsers(TranslatorImport.Users value) {
        this.users = value;
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
     *       &lt;sequence maxOccurs="unbounded">
     *         &lt;element name="source" type="{}Source"/>
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
        "source"
    })
    public static class Sources {

        @XmlElement(required = true)
        protected List<Source> source;

        /**
         * Gets the value of the source property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the source property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSource().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Source }
         * 
         * 
         */
        public List<Source> getSource() {
            if (source == null) {
                source = new ArrayList<Source>();
            }
            return this.source;
        }

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
     *       &lt;sequence maxOccurs="unbounded">
     *         &lt;element name="translateProvider" type="{}TranslateProvider"/>
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
        "translateProvider"
    })
    public static class TranslateProviders {

        @XmlElement(required = true)
        protected List<TranslateProvider> translateProvider;

        /**
         * Gets the value of the translateProvider property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the translateProvider property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTranslateProvider().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TranslateProvider }
         * 
         * 
         */
        public List<TranslateProvider> getTranslateProvider() {
            if (translateProvider == null) {
                translateProvider = new ArrayList<TranslateProvider>();
            }
            return this.translateProvider;
        }

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
     *       &lt;sequence maxOccurs="unbounded">
     *         &lt;element name="translate" type="{}Translate"/>
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
        "translate"
    })
    public static class Translates {

        @XmlElement(required = true)
        protected List<Translate> translate;

        /**
         * Gets the value of the translate property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the translate property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTranslate().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Translate }
         * 
         * 
         */
        public List<Translate> getTranslate() {
            if (translate == null) {
                translate = new ArrayList<Translate>();
            }
            return this.translate;
        }

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
     *       &lt;sequence maxOccurs="unbounded">
     *         &lt;element name="user" type="{}User"/>
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
        "user"
    })
    public static class Users {

        @XmlElement(required = true)
        protected List<User> user;

        /**
         * Gets the value of the user property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the user property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUser().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link User }
         * 
         * 
         */
        public List<User> getUser() {
            if (user == null) {
                user = new ArrayList<User>();
            }
            return this.user;
        }

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
     *       &lt;sequence maxOccurs="unbounded">
     *         &lt;element name="word" type="{}Word"/>
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
        "word"
    })
    public static class Words {

        @XmlElement(required = true)
        protected List<Word> word;

        /**
         * Gets the value of the word property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the word property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getWord().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Word }
         * 
         * 
         */
        public List<Word> getWord() {
            if (word == null) {
                word = new ArrayList<Word>();
            }
            return this.word;
        }

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
     *       &lt;sequence maxOccurs="unbounded">
     *         &lt;element name="link" type="{}WordToSourceLink"/>
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
        "link"
    })
    public static class WordsToSourceLink {

        @XmlElement(required = true)
        protected List<WordToSourceLink> link;

        /**
         * Gets the value of the link property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the link property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLink().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link WordToSourceLink }
         * 
         * 
         */
        public List<WordToSourceLink> getLink() {
            if (link == null) {
                link = new ArrayList<WordToSourceLink>();
            }
            return this.link;
        }

    }

}
