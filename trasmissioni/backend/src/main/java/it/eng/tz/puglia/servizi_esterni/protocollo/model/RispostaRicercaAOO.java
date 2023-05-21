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
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}NumeroAOOTrovate"/&gt;
 *         &lt;element ref="{http://protocolloWS.egov.rupar.puglia.it/protocollo}AnagraficaAOO" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "numeroAOOTrovate",
    "anagraficaAOO"
})
@XmlRootElement(name = "RispostaRicercaAOO")
public class RispostaRicercaAOO {

    @XmlElement(name = "NumeroAOOTrovate")
    protected int numeroAOOTrovate;
    @XmlElement(name = "AnagraficaAOO")
    protected List<AnagraficaAOO> anagraficaAOO;

    /**
     * Recupera il valore della proprietà numeroAOOTrovate.
     * 
     */
    public int getNumeroAOOTrovate() {
        return numeroAOOTrovate;
    }

    /**
     * Imposta il valore della proprietà numeroAOOTrovate.
     * 
     */
    public void setNumeroAOOTrovate(int value) {
        this.numeroAOOTrovate = value;
    }

    /**
     * Gets the value of the anagraficaAOO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the anagraficaAOO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnagraficaAOO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnagraficaAOO }
     * 
     * 
     */
    public List<AnagraficaAOO> getAnagraficaAOO() {
        if (anagraficaAOO == null) {
            anagraficaAOO = new ArrayList<AnagraficaAOO>();
        }
        return this.anagraficaAOO;
    }

}
