//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.04.03 alle 04:26:48 PM CEST 
//


package it.eng.tz.puglia.servizi_esterni.protocollo.model;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Identificatore"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}PrimaRegistrazione" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}OraRegistrazione" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Origine"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Destinazione" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}PerConoscenza" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Riservato" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Oggetto" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Classifica" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Note" minOccurs="0"/&gt;
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
    "identificatore",
    "primaRegistrazione",
    "oraRegistrazione",
    "origine",
    "destinazione",
    "perConoscenza",
    "riservato",
    "oggetto",
    "classifica",
    "note"
})
@XmlRootElement(name = "Intestazione")
public class Intestazione {

    @XmlElement(name = "Identificatore", required = true)
    protected Identificatore identificatore;
    @XmlElement(name = "PrimaRegistrazione")
    protected PrimaRegistrazione primaRegistrazione;
    @XmlElement(name = "OraRegistrazione")
    protected OraRegistrazione oraRegistrazione;
    @XmlElement(name = "Origine", required = true)
    protected Origine origine;
    @XmlElement(name = "Destinazione", required = true)
    protected List<Destinazione> destinazione;
    @XmlElement(name = "PerConoscenza")
    protected List<PerConoscenza> perConoscenza;
    @XmlElement(name = "Riservato")
    protected String riservato;
    @XmlElement(name = "Oggetto")
    protected String oggetto;
    @XmlElement(name = "Classifica")
    protected List<Classifica> classifica;
    @XmlElement(name = "Note")
    protected String note;

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
     * Recupera il valore della proprietà primaRegistrazione.
     * 
     * @return
     *     possible object is
     *     {@link PrimaRegistrazione }
     *     
     */
    public PrimaRegistrazione getPrimaRegistrazione() {
        return primaRegistrazione;
    }

    /**
     * Imposta il valore della proprietà primaRegistrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link PrimaRegistrazione }
     *     
     */
    public void setPrimaRegistrazione(PrimaRegistrazione value) {
        this.primaRegistrazione = value;
    }

    /**
     * Recupera il valore della proprietà oraRegistrazione.
     * 
     * @return
     *     possible object is
     *     {@link OraRegistrazione }
     *     
     */
    public OraRegistrazione getOraRegistrazione() {
        return oraRegistrazione;
    }

    /**
     * Imposta il valore della proprietà oraRegistrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link OraRegistrazione }
     *     
     */
    public void setOraRegistrazione(OraRegistrazione value) {
        this.oraRegistrazione = value;
    }

    /**
     * Recupera il valore della proprietà origine.
     * 
     * @return
     *     possible object is
     *     {@link Origine }
     *     
     */
    public Origine getOrigine() {
        return origine;
    }

    /**
     * Imposta il valore della proprietà origine.
     * 
     * @param value
     *     allowed object is
     *     {@link Origine }
     *     
     */
    public void setOrigine(Origine value) {
        this.origine = value;
    }

    /**
     * Gets the value of the destinazione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destinazione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestinazione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Destinazione }
     * 
     * 
     */
    public List<Destinazione> getDestinazione() {
        if (destinazione == null) {
            destinazione = new ArrayList<Destinazione>();
        }
        return this.destinazione;
    }

    /**
     * Gets the value of the perConoscenza property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the perConoscenza property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerConoscenza().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PerConoscenza }
     * 
     * 
     */
    public List<PerConoscenza> getPerConoscenza() {
        if (perConoscenza == null) {
            perConoscenza = new ArrayList<PerConoscenza>();
        }
        return this.perConoscenza;
    }

    /**
     * Recupera il valore della proprietà riservato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiservato() {
        return riservato;
    }

    /**
     * Imposta il valore della proprietà riservato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiservato(String value) {
        this.riservato = value;
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
     * Gets the value of the classifica property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the classifica property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClassifica().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Classifica }
     * 
     * 
     */
    public List<Classifica> getClassifica() {
        if (classifica == null) {
            classifica = new ArrayList<Classifica>();
        }
        return this.classifica;
    }

    /**
     * Recupera il valore della proprietà note.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Imposta il valore della proprietà note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

	public void setDestinazione(List<Destinazione> asList) {
		this.destinazione=asList;
	}

}
