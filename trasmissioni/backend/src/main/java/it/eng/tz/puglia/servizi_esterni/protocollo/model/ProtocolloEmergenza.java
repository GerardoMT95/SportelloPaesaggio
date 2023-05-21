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
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}NumeroProtEmergenza"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}DataProtEmergenza"/&gt;
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
    "numeroProtEmergenza",
    "dataProtEmergenza"
})
@XmlRootElement(name = "ProtocolloEmergenza")
public class ProtocolloEmergenza {

    @XmlElement(name = "NumeroProtEmergenza", required = true)
    protected String numeroProtEmergenza;
    @XmlElement(name = "DataProtEmergenza", required = true)
    protected String dataProtEmergenza;

    /**
     * Recupera il valore della proprietà numeroProtEmergenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtEmergenza() {
        return numeroProtEmergenza;
    }

    /**
     * Imposta il valore della proprietà numeroProtEmergenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtEmergenza(String value) {
        this.numeroProtEmergenza = value;
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

}
