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
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Identificatore"/&gt;
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
    "identificatore"
})
@XmlRootElement(name = "ParametriRicercaAssegnazioniInoltriProtocollo")
public class ParametriRicercaAssegnazioniInoltriProtocollo {

    @XmlElement(name = "CredenzialiUtente", required = true)
    protected CredenzialiUtente credenzialiUtente;
    @XmlElement(name = "Identificatore", required = true)
    protected Identificatore identificatore;

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

}
