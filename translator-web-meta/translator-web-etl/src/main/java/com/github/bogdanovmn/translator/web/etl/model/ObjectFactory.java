
package com.github.bogdanovmn.translator.web.etl.model;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.github.bogdanovmn.translator.web.etl.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.github.bogdanovmn.translator.web.etl.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TranslatorImport }
     * 
     */
    public TranslatorImport createTranslatorImport() {
        return new TranslatorImport();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link User.RememberedWords }
     * 
     */
    public User.RememberedWords createUserRememberedWords() {
        return new User.RememberedWords();
    }

    /**
     * Create an instance of {@link TranslatorImport.Sources }
     * 
     */
    public TranslatorImport.Sources createTranslatorImportSources() {
        return new TranslatorImport.Sources();
    }

    /**
     * Create an instance of {@link TranslatorImport.TranslateProviders }
     * 
     */
    public TranslatorImport.TranslateProviders createTranslatorImportTranslateProviders() {
        return new TranslatorImport.TranslateProviders();
    }

    /**
     * Create an instance of {@link TranslatorImport.Words }
     * 
     */
    public TranslatorImport.Words createTranslatorImportWords() {
        return new TranslatorImport.Words();
    }

    /**
     * Create an instance of {@link TranslatorImport.WordsToSourceLink }
     * 
     */
    public TranslatorImport.WordsToSourceLink createTranslatorImportWordsToSourceLink() {
        return new TranslatorImport.WordsToSourceLink();
    }

    /**
     * Create an instance of {@link TranslatorImport.Translates }
     * 
     */
    public TranslatorImport.Translates createTranslatorImportTranslates() {
        return new TranslatorImport.Translates();
    }

    /**
     * Create an instance of {@link TranslatorImport.Users }
     * 
     */
    public TranslatorImport.Users createTranslatorImportUsers() {
        return new TranslatorImport.Users();
    }

    /**
     * Create an instance of {@link WordToSourceLink }
     * 
     */
    public WordToSourceLink createWordToSourceLink() {
        return new WordToSourceLink();
    }

    /**
     * Create an instance of {@link TranslateProvider }
     * 
     */
    public TranslateProvider createTranslateProvider() {
        return new TranslateProvider();
    }

    /**
     * Create an instance of {@link com.github.bogdanovmn.translator.web.etl.model.Word }
     * 
     */
    public com.github.bogdanovmn.translator.web.etl.model.Word createWord() {
        return new com.github.bogdanovmn.translator.web.etl.model.Word();
    }

    /**
     * Create an instance of {@link Translate }
     * 
     */
    public Translate createTranslate() {
        return new Translate();
    }

    /**
     * Create an instance of {@link Source }
     * 
     */
    public Source createSource() {
        return new Source();
    }

    /**
     * Create an instance of {@link User.RememberedWords.Word }
     * 
     */
    public User.RememberedWords.Word createUserRememberedWordsWord() {
        return new User.RememberedWords.Word();
    }

}
