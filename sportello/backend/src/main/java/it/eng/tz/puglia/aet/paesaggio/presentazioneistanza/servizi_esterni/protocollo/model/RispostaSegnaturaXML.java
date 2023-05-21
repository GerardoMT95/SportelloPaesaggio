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
 *         &lt;element name="Trovato" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="SegnaturaXML" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "trovato",
    "segnaturaXML"
})
@XmlRootElement(name = "RispostaSegnaturaXML")
public class RispostaSegnaturaXML {

    @XmlElement(name = "Trovato")
    protected boolean trovato;
    @XmlElement(name = "SegnaturaXML")
    protected String segnaturaXML;

    /**
     * Recupera il valore della proprietà trovato.
     * 
     */
    public boolean isTrovato() {
        return trovato;
    }

    /**
     * Imposta il valore della proprietà trovato.
     * 
     */
    public void setTrovato(boolean value) {
        this.trovato = value;
    }

    /**
     * Recupera il valore della proprietà segnaturaXML.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegnaturaXML() {
        return segnaturaXML;
    }

    /**
     * Imposta il valore della proprietà segnaturaXML.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegnaturaXML(String value) {
        this.segnaturaXML = value;
    }

}
