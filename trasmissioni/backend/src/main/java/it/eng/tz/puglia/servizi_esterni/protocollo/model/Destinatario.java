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
 *         &lt;choice&gt;
 *           &lt;sequence&gt;
 *             &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Amministrazione" minOccurs="0"/&gt;
 *             &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}AOO" minOccurs="0"/&gt;
 *           &lt;/sequence&gt;
 *           &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Persona" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Denominazione" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}IndirizzoTelematico" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Telefono" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Fax" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}IndirizzoPostale" minOccurs="0"/&gt;
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
    "amministrazione",
    "aoo",
    "persona",
    "denominazione",
    "indirizzoTelematico",
    "telefono",
    "fax",
    "indirizzoPostale"
})
@XmlRootElement(name = "Destinatario")
public class Destinatario {

    @XmlElement(name = "Amministrazione")
    protected Amministrazione amministrazione;
    @XmlElement(name = "AOO")
    protected AOO aoo;
    @XmlElement(name = "Persona")
    protected List<Persona> persona;
    @XmlElement(name = "Denominazione")
    protected String denominazione;
    @XmlElement(name = "IndirizzoTelematico")
    protected IndirizzoTelematico indirizzoTelematico;
    @XmlElement(name = "Telefono")
    protected List<Telefono> telefono;
    @XmlElement(name = "Fax")
    protected List<Fax> fax;
    @XmlElement(name = "IndirizzoPostale")
    protected IndirizzoPostale indirizzoPostale;

    /**
     * Recupera il valore della proprietà amministrazione.
     * 
     * @return
     *     possible object is
     *     {@link Amministrazione }
     *     
     */
    public Amministrazione getAmministrazione() {
        return amministrazione;
    }

    /**
     * Imposta il valore della proprietà amministrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Amministrazione }
     *     
     */
    public void setAmministrazione(Amministrazione value) {
        this.amministrazione = value;
    }

    /**
     * Recupera il valore della proprietà aoo.
     * 
     * @return
     *     possible object is
     *     {@link AOO }
     *     
     */
    public AOO getAOO() {
        return aoo;
    }

    /**
     * Imposta il valore della proprietà aoo.
     * 
     * @param value
     *     allowed object is
     *     {@link AOO }
     *     
     */
    public void setAOO(AOO value) {
        this.aoo = value;
    }

    /**
     * Gets the value of the persona property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the persona property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersona().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Persona }
     * 
     * 
     */
    public List<Persona> getPersona() {
        if (persona == null) {
            persona = new ArrayList<Persona>();
        }
        return this.persona;
    }

    /**
     * Recupera il valore della proprietà denominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Imposta il valore della proprietà denominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazione(String value) {
        this.denominazione = value;
    }

    /**
     * Recupera il valore della proprietà indirizzoTelematico.
     * 
     * @return
     *     possible object is
     *     {@link IndirizzoTelematico }
     *     
     */
    public IndirizzoTelematico getIndirizzoTelematico() {
        return indirizzoTelematico;
    }

    /**
     * Imposta il valore della proprietà indirizzoTelematico.
     * 
     * @param value
     *     allowed object is
     *     {@link IndirizzoTelematico }
     *     
     */
    public void setIndirizzoTelematico(IndirizzoTelematico value) {
        this.indirizzoTelematico = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the telefono property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTelefono().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Telefono }
     * 
     * 
     */
    public List<Telefono> getTelefono() {
        if (telefono == null) {
            telefono = new ArrayList<Telefono>();
        }
        return this.telefono;
    }

    /**
     * Gets the value of the fax property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fax property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFax().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Fax }
     * 
     * 
     */
    public List<Fax> getFax() {
        if (fax == null) {
            fax = new ArrayList<Fax>();
        }
        return this.fax;
    }

    /**
     * Recupera il valore della proprietà indirizzoPostale.
     * 
     * @return
     *     possible object is
     *     {@link IndirizzoPostale }
     *     
     */
    public IndirizzoPostale getIndirizzoPostale() {
        return indirizzoPostale;
    }

    /**
     * Imposta il valore della proprietà indirizzoPostale.
     * 
     * @param value
     *     allowed object is
     *     {@link IndirizzoPostale }
     *     
     */
    public void setIndirizzoPostale(IndirizzoPostale value) {
        this.indirizzoPostale = value;
    }

	public void setPersona(List<Persona> asList) {
		this.persona=asList;
	}

}
