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
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}NumeroElementiTrovati"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}MatchingAssegnazioniInoltri" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "numeroElementiTrovati",
    "matchingAssegnazioniInoltri"
})
@XmlRootElement(name = "RispostaRicercaAssegnazioniInoltriProtocollo")
public class RispostaRicercaAssegnazioniInoltriProtocollo {

    @XmlElement(name = "NumeroElementiTrovati")
    protected int numeroElementiTrovati;
    @XmlElement(name = "MatchingAssegnazioniInoltri")
    protected List<MatchingAssegnazioniInoltri> matchingAssegnazioniInoltri;

    /**
     * Recupera il valore della proprietà numeroElementiTrovati.
     * 
     */
    public int getNumeroElementiTrovati() {
        return numeroElementiTrovati;
    }

    /**
     * Imposta il valore della proprietà numeroElementiTrovati.
     * 
     */
    public void setNumeroElementiTrovati(int value) {
        this.numeroElementiTrovati = value;
    }

    /**
     * Gets the value of the matchingAssegnazioniInoltri property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the matchingAssegnazioniInoltri property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMatchingAssegnazioniInoltri().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MatchingAssegnazioniInoltri }
     * 
     * 
     */
    public List<MatchingAssegnazioniInoltri> getMatchingAssegnazioniInoltri() {
        if (matchingAssegnazioniInoltri == null) {
            matchingAssegnazioniInoltri = new ArrayList<MatchingAssegnazioniInoltri>();
        }
        return this.matchingAssegnazioniInoltri;
    }

}
