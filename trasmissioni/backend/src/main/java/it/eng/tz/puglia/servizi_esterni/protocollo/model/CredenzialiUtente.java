//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.04.03 alle 04:26:48 PM CEST 
//


package it.eng.tz.puglia.servizi_esterni.protocollo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}CodiceAmministrazione"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}CodiceAOO"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}CodiceRegistro" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}IDUtente"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codiceAmministrazione",
    "codiceAOO",
    "codiceRegistro",
    "idUtente"
})
@XmlRootElement(name = "CredenzialiUtente")
public class CredenzialiUtente {

    @XmlElement(name = "CodiceAmministrazione", required = true)
    protected String codiceAmministrazione;
    @XmlElement(name = "CodiceAOO", required = true)
    protected String codiceAOO;
    @XmlElement(name = "CodiceRegistro")
    protected String codiceRegistro;
    @XmlElement(name = "IDUtente", required = true)
    protected String idUtente;

    /**
     * Recupera il valore della proprietà codiceAmministrazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAmministrazione() {
        return codiceAmministrazione;
    }

    /**
     * Imposta il valore della proprietà codiceAmministrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAmministrazione(String value) {
        this.codiceAmministrazione = value;
    }

    /**
     * Recupera il valore della proprietà codiceAOO.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAOO() {
        return codiceAOO;
    }

    /**
     * Imposta il valore della proprietà codiceAOO.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAOO(String value) {
        this.codiceAOO = value;
    }

    /**
     * Recupera il valore della proprietà codiceRegistro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceRegistro() {
        return codiceRegistro;
    }

    /**
     * Imposta il valore della proprietà codiceRegistro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceRegistro(String value) {
        this.codiceRegistro = value;
    }

    /**
     * Recupera il valore della proprietà idUtente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDUtente() {
        return idUtente;
    }

    /**
     * Imposta il valore della proprietà idUtente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDUtente(String value) {
        this.idUtente = value;
    }

}
