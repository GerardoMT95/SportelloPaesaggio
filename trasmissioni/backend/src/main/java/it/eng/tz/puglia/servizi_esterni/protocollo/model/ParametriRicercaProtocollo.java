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
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}CredenzialiUtente"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}NumeroRegistrazione" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Oggetto" minOccurs="0"/&gt;
 *         &lt;element name="DataProtocolloDa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DataProtocolloA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InMittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}InOut" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}UtenteOperazione" minOccurs="0"/&gt;
 *         &lt;element name="DataArrivoDa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DataArrivoA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NumeroArrivo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InProtocolloMittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InClassifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Annullato" minOccurs="0"/&gt;
 *         &lt;element name="InProtocolloEmergenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}DataProtEmergenza" minOccurs="0"/&gt;
 *         &lt;element name="AmministrazioneRiferimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ProtocolloRiferimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AOORiferimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DataRiferimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "credenzialiUtente",
    "numeroRegistrazione",
    "oggetto",
    "dataProtocolloDa",
    "dataProtocolloA",
    "inMittente",
    "inDestinatario",
    "inOut",
    "utenteOperazione",
    "dataArrivoDa",
    "dataArrivoA",
    "numeroArrivo",
    "inProtocolloMittente",
    "inClassifica",
    "annullato",
    "inProtocolloEmergenza",
    "dataProtEmergenza",
    "amministrazioneRiferimento",
    "protocolloRiferimento",
    "aooRiferimento",
    "dataRiferimento"
})
@XmlRootElement(name = "ParametriRicercaProtocollo")
public class ParametriRicercaProtocollo {

    @XmlElement(name = "CredenzialiUtente", required = true)
    protected CredenzialiUtente credenzialiUtente;
    @XmlElement(name = "NumeroRegistrazione")
    protected String numeroRegistrazione;
    @XmlElement(name = "Oggetto")
    protected String oggetto;
    @XmlElement(name = "DataProtocolloDa")
    protected String dataProtocolloDa;
    @XmlElement(name = "DataProtocolloA")
    protected String dataProtocolloA;
    @XmlElement(name = "InMittente")
    protected String inMittente;
    @XmlElement(name = "InDestinatario")
    protected String inDestinatario;
    @XmlElement(name = "InOut")
    protected String inOut;
    @XmlElement(name = "UtenteOperazione")
    protected String utenteOperazione;
    @XmlElement(name = "DataArrivoDa")
    protected String dataArrivoDa;
    @XmlElement(name = "DataArrivoA")
    protected String dataArrivoA;
    @XmlElement(name = "NumeroArrivo")
    protected String numeroArrivo;
    @XmlElement(name = "InProtocolloMittente")
    protected String inProtocolloMittente;
    @XmlElement(name = "InClassifica")
    protected String inClassifica;
    @XmlElement(name = "Annullato")
    protected Boolean annullato;
    @XmlElement(name = "InProtocolloEmergenza")
    protected String inProtocolloEmergenza;
    @XmlElement(name = "DataProtEmergenza")
    protected String dataProtEmergenza;
    @XmlElement(name = "AmministrazioneRiferimento")
    protected String amministrazioneRiferimento;
    @XmlElement(name = "ProtocolloRiferimento")
    protected String protocolloRiferimento;
    @XmlElement(name = "AOORiferimento")
    protected String aooRiferimento;
    @XmlElement(name = "DataRiferimento")
    protected String dataRiferimento;

    /**
     * Recupera il valore della proprietà credenzialiUtente.
     * 
     * @return
     *     possible object is
     *     {@link CredenzialiUtente }
     *     
     */
    public CredenzialiUtente getCredenzialiUtente() {
        return credenzialiUtente;
    }

    /**
     * Imposta il valore della proprietà credenzialiUtente.
     * 
     * @param value
     *     allowed object is
     *     {@link CredenzialiUtente }
     *     
     */
    public void setCredenzialiUtente(CredenzialiUtente value) {
        this.credenzialiUtente = value;
    }

    /**
     * Recupera il valore della proprietà numeroRegistrazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroRegistrazione() {
        return numeroRegistrazione;
    }

    /**
     * Imposta il valore della proprietà numeroRegistrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroRegistrazione(String value) {
        this.numeroRegistrazione = value;
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
     * Recupera il valore della proprietà dataProtocolloDa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataProtocolloDa() {
        return dataProtocolloDa;
    }

    /**
     * Imposta il valore della proprietà dataProtocolloDa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataProtocolloDa(String value) {
        this.dataProtocolloDa = value;
    }

    /**
     * Recupera il valore della proprietà dataProtocolloA.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataProtocolloA() {
        return dataProtocolloA;
    }

    /**
     * Imposta il valore della proprietà dataProtocolloA.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataProtocolloA(String value) {
        this.dataProtocolloA = value;
    }

    /**
     * Recupera il valore della proprietà inMittente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInMittente() {
        return inMittente;
    }

    /**
     * Imposta il valore della proprietà inMittente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInMittente(String value) {
        this.inMittente = value;
    }

    /**
     * Recupera il valore della proprietà inDestinatario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInDestinatario() {
        return inDestinatario;
    }

    /**
     * Imposta il valore della proprietà inDestinatario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInDestinatario(String value) {
        this.inDestinatario = value;
    }

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
     * Recupera il valore della proprietà dataArrivoDa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataArrivoDa() {
        return dataArrivoDa;
    }

    /**
     * Imposta il valore della proprietà dataArrivoDa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataArrivoDa(String value) {
        this.dataArrivoDa = value;
    }

    /**
     * Recupera il valore della proprietà dataArrivoA.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataArrivoA() {
        return dataArrivoA;
    }

    /**
     * Imposta il valore della proprietà dataArrivoA.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataArrivoA(String value) {
        this.dataArrivoA = value;
    }

    /**
     * Recupera il valore della proprietà numeroArrivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroArrivo() {
        return numeroArrivo;
    }

    /**
     * Imposta il valore della proprietà numeroArrivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroArrivo(String value) {
        this.numeroArrivo = value;
    }

    /**
     * Recupera il valore della proprietà inProtocolloMittente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInProtocolloMittente() {
        return inProtocolloMittente;
    }

    /**
     * Imposta il valore della proprietà inProtocolloMittente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInProtocolloMittente(String value) {
        this.inProtocolloMittente = value;
    }

    /**
     * Recupera il valore della proprietà inClassifica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInClassifica() {
        return inClassifica;
    }

    /**
     * Imposta il valore della proprietà inClassifica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInClassifica(String value) {
        this.inClassifica = value;
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
     * Recupera il valore della proprietà inProtocolloEmergenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInProtocolloEmergenza() {
        return inProtocolloEmergenza;
    }

    /**
     * Imposta il valore della proprietà inProtocolloEmergenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInProtocolloEmergenza(String value) {
        this.inProtocolloEmergenza = value;
    }

    /**
     * Recupera il valore della proprietà dataProtEmergenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataProtEmergenza() {
        return dataProtEmergenza;
    }

    /**
     * Imposta il valore della proprietà dataProtEmergenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataProtEmergenza(String value) {
        this.dataProtEmergenza = value;
    }

    /**
     * Recupera il valore della proprietà amministrazioneRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmministrazioneRiferimento() {
        return amministrazioneRiferimento;
    }

    /**
     * Imposta il valore della proprietà amministrazioneRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmministrazioneRiferimento(String value) {
        this.amministrazioneRiferimento = value;
    }

    /**
     * Recupera il valore della proprietà protocolloRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocolloRiferimento() {
        return protocolloRiferimento;
    }

    /**
     * Imposta il valore della proprietà protocolloRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocolloRiferimento(String value) {
        this.protocolloRiferimento = value;
    }

    /**
     * Recupera il valore della proprietà aooRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAOORiferimento() {
        return aooRiferimento;
    }

    /**
     * Imposta il valore della proprietà aooRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAOORiferimento(String value) {
        this.aooRiferimento = value;
    }

    /**
     * Recupera il valore della proprietà dataRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataRiferimento() {
        return dataRiferimento;
    }

    /**
     * Imposta il valore della proprietà dataRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataRiferimento(String value) {
        this.dataRiferimento = value;
    }

}
