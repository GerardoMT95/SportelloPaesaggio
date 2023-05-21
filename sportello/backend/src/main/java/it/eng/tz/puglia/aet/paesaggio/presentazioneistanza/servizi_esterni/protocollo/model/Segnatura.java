//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.04.03 alle 04:26:48 PM CEST 
//


package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}InOut" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Intestazione"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Riferimenti" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Descrizione"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}TipoOperazione" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}UtenteOperazione" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}DataModifica" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}ProvvedimentoModifica" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}ProtocolloMittente" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}ProtocolloEmergenza" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}DataArrivo" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}RiferimentoArrivo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="versione" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" fixed="2013-01-23" /&gt;
 *       &lt;attribute name="xml-lang" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="it" /&gt;
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
    "intestazione",
    "riferimenti",
    "descrizione",
    "tipoOperazione",
    "utenteOperazione",
    "dataModifica",
    "provvedimentoModifica",
    "protocolloMittente",
    "protocolloEmergenza",
    "dataArrivo",
    "riferimentoArrivo"
})
@XmlRootElement(name = "Segnatura")
public class Segnatura {

    @XmlElement(name = "InOut")
    protected String inOut;
    @XmlElement(name = "Intestazione", required = true)
    protected Intestazione intestazione;
    @XmlElement(name = "Riferimenti")
    protected Riferimenti riferimenti;
    @XmlElement(name = "Descrizione", required = true)
    protected Descrizione descrizione;
    @XmlElement(name = "TipoOperazione")
    protected String tipoOperazione;
    @XmlElement(name = "UtenteOperazione")
    protected String utenteOperazione;
    @XmlElement(name = "DataModifica")
    protected String dataModifica;
    @XmlElement(name = "ProvvedimentoModifica")
    protected String provvedimentoModifica;
    @XmlElement(name = "ProtocolloMittente")
    protected ProtocolloMittente protocolloMittente;
    @XmlElement(name = "ProtocolloEmergenza")
    protected ProtocolloEmergenza protocolloEmergenza;
    @XmlElement(name = "DataArrivo")
    protected String dataArrivo;
    @XmlElement(name = "RiferimentoArrivo")
    protected String riferimentoArrivo;
    @XmlAttribute(name = "versione")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String versione;
    @XmlAttribute(name = "xml-lang")
    @XmlSchemaType(name = "anySimpleType")
    protected String xmlLang;

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
     * Recupera il valore della proprietà intestazione.
     * 
     * @return
     *     possible object is
     *     {@link Intestazione }
     *     
     */
    public Intestazione getIntestazione() {
        return intestazione;
    }

    /**
     * Imposta il valore della proprietà intestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Intestazione }
     *     
     */
    public void setIntestazione(Intestazione value) {
        this.intestazione = value;
    }

    /**
     * Recupera il valore della proprietà riferimenti.
     * 
     * @return
     *     possible object is
     *     {@link Riferimenti }
     *     
     */
    public Riferimenti getRiferimenti() {
        return riferimenti;
    }

    /**
     * Imposta il valore della proprietà riferimenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Riferimenti }
     *     
     */
    public void setRiferimenti(Riferimenti value) {
        this.riferimenti = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link Descrizione }
     *     
     */
    public Descrizione getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link Descrizione }
     *     
     */
    public void setDescrizione(Descrizione value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà tipoOperazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoOperazione() {
        return tipoOperazione;
    }

    /**
     * Imposta il valore della proprietà tipoOperazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoOperazione(String value) {
        this.tipoOperazione = value;
    }

    /**
     * Recupera il valore della proprietà utenteOperazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtenteOperazione() {
        return utenteOperazione;
    }

    /**
     * Imposta il valore della proprietà utenteOperazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtenteOperazione(String value) {
        this.utenteOperazione = value;
    }

    /**
     * Recupera il valore della proprietà dataModifica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataModifica() {
        return dataModifica;
    }

    /**
     * Imposta il valore della proprietà dataModifica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataModifica(String value) {
        this.dataModifica = value;
    }

    /**
     * Recupera il valore della proprietà provvedimentoModifica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvvedimentoModifica() {
        return provvedimentoModifica;
    }

    /**
     * Imposta il valore della proprietà provvedimentoModifica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvvedimentoModifica(String value) {
        this.provvedimentoModifica = value;
    }

    /**
     * Recupera il valore della proprietà protocolloMittente.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolloMittente }
     *     
     */
    public ProtocolloMittente getProtocolloMittente() {
        return protocolloMittente;
    }

    /**
     * Imposta il valore della proprietà protocolloMittente.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolloMittente }
     *     
     */
    public void setProtocolloMittente(ProtocolloMittente value) {
        this.protocolloMittente = value;
    }

    /**
     * Recupera il valore della proprietà protocolloEmergenza.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolloEmergenza }
     *     
     */
    public ProtocolloEmergenza getProtocolloEmergenza() {
        return protocolloEmergenza;
    }

    /**
     * Imposta il valore della proprietà protocolloEmergenza.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolloEmergenza }
     *     
     */
    public void setProtocolloEmergenza(ProtocolloEmergenza value) {
        this.protocolloEmergenza = value;
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
     * Recupera il valore della proprietà riferimentoArrivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoArrivo() {
        return riferimentoArrivo;
    }

    /**
     * Imposta il valore della proprietà riferimentoArrivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoArrivo(String value) {
        this.riferimentoArrivo = value;
    }

    /**
     * Recupera il valore della proprietà versione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersione() {
        if (versione == null) {
            return "2013-01-23";
        } else {
            return versione;
        }
    }

    /**
     * Imposta il valore della proprietà versione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersione(String value) {
        this.versione = value;
    }

    /**
     * Recupera il valore della proprietà xmlLang.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlLang() {
        if (xmlLang == null) {
            return "it";
        } else {
            return xmlLang;
        }
    }

    /**
     * Imposta il valore della proprietà xmlLang.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlLang(String value) {
        this.xmlLang = value;
    }

}
