package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.tipo_ruolo_ditta
 */
public class TipoRuoloDittaSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 2084960516L;
    //COLUMN id
    private String id;
    //COLUMN nome
    private String nome;
    //COLUMN order
    private String order;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getOrder(){
        return this.order;
    }

    public void setOrder(String order){
        this.order = order;
    }


}
