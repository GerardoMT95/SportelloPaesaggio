//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.04.03 alle 04:26:48 PM CEST 
//


package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model;

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
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}NumeroElementiTrovati1"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}MatchingProtocollo" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "numeroElementiTrovati1",
    "matchingProtocollo"
})
@XmlRootElement(name = "RispostaRicercaProtocollo")
public class RispostaRicercaProtocollo {

    @XmlElement(name = "NumeroElementiTrovati1")
    protected int numeroElementiTrovati1;
    @XmlElement(name = "MatchingProtocollo")
    protected List<MatchingProtocollo> matchingProtocollo;

    /**
     * Recupera il valore della proprietà numeroElementiTrovati1.
     * 
     */
    public int getNumeroElementiTrovati1() {
        return numeroElementiTrovati1;
    }

    /**
     * Imposta il valore della proprietà numeroElementiTrovati1.
     * 
     */
    public void setNumeroElementiTrovati1(int value) {
        this.numeroElementiTrovati1 = value;
    }

    /**
     * Gets the value of the matchingProtocollo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the matchingProtocollo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMatchingProtocollo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MatchingProtocollo }
     * 
     * 
     */
    public List<MatchingProtocollo> getMatchingProtocollo() {
        if (matchingProtocollo == null) {
            matchingProtocollo = new ArrayList<MatchingProtocollo>();
        }
        return this.matchingProtocollo;
    }

}
