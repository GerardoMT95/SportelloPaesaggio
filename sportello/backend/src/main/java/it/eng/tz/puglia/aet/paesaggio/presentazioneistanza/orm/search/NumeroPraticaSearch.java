package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.numero_pratica
 */
public class NumeroPraticaSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 4584203851L;
    //COLUMN id
    private String id;
    //COLUMN anno
    private String anno;
    //COLUMN numero
    private String numero;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getAnno(){
        return this.anno;
    }

    public void setAnno(String anno){
        this.anno = anno;
    }

    public String getNumero(){
        return this.numero;
    }

    public void setNumero(String numero){
        this.numero = numero;
    }


}
