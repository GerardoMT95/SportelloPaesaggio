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
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}InOut"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Identificatore"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Oggetto"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Corrispondente"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Annullato" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}DataArrivo" minOccurs="0"/&gt;
 *         &lt;element name="RifProtocolloMittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "inOut",
    "identificatore",
    "oggetto",
    "corrispondente",
    "annullato",
    "dataArrivo",
    "rifProtocolloMittente"
})
@XmlRootElement(name = "MatchingProtocollo")
public class MatchingProtocollo {

    @XmlElement(name = "InOut", required = true)
    protected String inOut;
    @XmlElement(name = "Identificatore", required = true)
    protected Identificatore identificatore;
    @XmlElement(name = "Oggetto", required = true)
    protected String oggetto;
    @XmlElement(name = "Corrispondente", required = true)
    protected String corrispondente;
    @XmlElement(name = "Annullato")
    protected Boolean annullato;
    @XmlElement(name = "DataArrivo")
    protected String dataArrivo;
    @XmlElement(name = "RifProtocolloMittente")
    protected String rifProtocolloMittente;

    /**
     * Recupera il valore della proprietà inOut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInOut() {
        return inOut;
    }

    /**
     * Imposta il valore della proprietà inOut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInOut(String value) {
        this.inOut = value;
    }

    /**
     * Recupera il valore della proprietà identificatore.
     * 
     * @return
     *     possible object is
     *     {@link Identificatore }
     *     
     */
    public Identificatore getIdentificatore() {
        return identificatore;
    }

    /**
     * Imposta il valore della proprietà identificatore.
     * 
     * @param value
     *     allowed object is
     *     {@link Identificatore }
     *     
     */
    public void setIdentificatore(Identificatore value) {
        this.identificatore = value;
    }

    /**
     * Recupera il valore della proprietà oggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Imposta il valore della proprietà oggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Recupera il valore della proprietà corrispondente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrispondente() {
        return corrispondente;
    }

    /**
     * Imposta il valore della proprietà corrispondente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrispondente(String value) {
        this.corrispondente = value;
    }

    /**
     * Recupera il valore della proprietà annullato.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAnnullato() {
        return annullato;
    }

    /**
     * Imposta il valore della proprietà annullato.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAnnullato(Boolean value) {
        this.annullato = value;
    }

    /**
     * Recupera il valore della proprietà dataArrivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataArrivo() {
        return dataArrivo;
    }

    /**
     * Imposta il valore della proprietà dataArrivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataArrivo(String value) {
        this.dataArrivo = value;
    }

    /**
     * Recupera il valore della proprietà rifProtocolloMittente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRifProtocolloMittente() {
        return rifProtocolloMittente;
    }

    /**
     * Imposta il valore della proprietà rifProtocolloMittente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRifProtocolloMittente(String value) {
        this.rifProtocolloMittente = value;
    }

}
