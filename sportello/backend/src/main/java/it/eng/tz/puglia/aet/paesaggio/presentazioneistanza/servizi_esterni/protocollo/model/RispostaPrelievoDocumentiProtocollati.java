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
 *         &lt;element name="Trovato" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="NumeroDocCartacei" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="NumeroDocTelematici" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="NumeroDocElettronici" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}MatchingDocumenti" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "numeroDocCartacei",
    "numeroDocTelematici",
    "numeroDocElettronici",
    "matchingDocumenti"
})
@XmlRootElement(name = "RispostaPrelievoDocumentiProtocollati")
public class RispostaPrelievoDocumentiProtocollati {

    @XmlElement(name = "Trovato")
    protected boolean trovato;
    @XmlElement(name = "NumeroDocCartacei")
    protected int numeroDocCartacei;
    @XmlElement(name = "NumeroDocTelematici")
    protected int numeroDocTelematici;
    @XmlElement(name = "NumeroDocElettronici")
    protected int numeroDocElettronici;
    @XmlElement(name = "MatchingDocumenti")
    protected List<MatchingDocumenti> matchingDocumenti;

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
     * Recupera il valore della proprietà numeroDocCartacei.
     * 
     */
    public int getNumeroDocCartacei() {
        return numeroDocCartacei;
    }

    /**
     * Imposta il valore della proprietà numeroDocCartacei.
     * 
     */
    public void setNumeroDocCartacei(int value) {
        this.numeroDocCartacei = value;
    }

    /**
     * Recupera il valore della proprietà numeroDocTelematici.
     * 
     */
    public int getNumeroDocTelematici() {
        return numeroDocTelematici;
    }

    /**
     * Imposta il valore della proprietà numeroDocTelematici.
     * 
     */
    public void setNumeroDocTelematici(int value) {
        this.numeroDocTelematici = value;
    }

    /**
     * Recupera il valore della proprietà numeroDocElettronici.
     * 
     */
    public int getNumeroDocElettronici() {
        return numeroDocElettronici;
    }

    /**
     * Imposta il valore della proprietà numeroDocElettronici.
     * 
     */
    public void setNumeroDocElettronici(int value) {
        this.numeroDocElettronici = value;
    }

    /**
     * Gets the value of the matchingDocumenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the matchingDocumenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMatchingDocumenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MatchingDocumenti }
     * 
     * 
     */
    public List<MatchingDocumenti> getMatchingDocumenti() {
        if (matchingDocumenti == null) {
            matchingDocumenti = new ArrayList<MatchingDocumenti>();
        }
        return this.matchingDocumenti;
    }

}
