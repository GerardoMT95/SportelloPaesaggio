//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.04.03 alle 04:26:48 PM CEST 
//


package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model;

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
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}Esito"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}SegnaturaProtocollo"/&gt;
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
    "esito",
    "segnaturaProtocollo"
})
@XmlRootElement(name = "RispostaProtocollo")
public class RispostaProtocollo {

    @XmlElement(name = "Esito", required = true)
    protected Esito esito;
    @XmlElement(name = "SegnaturaProtocollo", required = true)
    protected SegnaturaProtocollo segnaturaProtocollo;

    /**
     * Recupera il valore della proprietà esito.
     * 
     * @return
     *     possible object is
     *     {@link Esito }
     *     
     */
    public Esito getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     * @param value
     *     allowed object is
     *     {@link Esito }
     *     
     */
    public void setEsito(Esito value) {
        this.esito = value;
    }

    /**
     * Recupera il valore della proprietà segnaturaProtocollo.
     * 
     * @return
     *     possible object is
     *     {@link SegnaturaProtocollo }
     *     
     */
    public SegnaturaProtocollo getSegnaturaProtocollo() {
        return segnaturaProtocollo;
    }

    /**
     * Imposta il valore della proprietà segnaturaProtocollo.
     * 
     * @param value
     *     allowed object is
     *     {@link SegnaturaProtocollo }
     *     
     */
    public void setSegnaturaProtocollo(SegnaturaProtocollo value) {
        this.segnaturaProtocollo = value;
    }

}
