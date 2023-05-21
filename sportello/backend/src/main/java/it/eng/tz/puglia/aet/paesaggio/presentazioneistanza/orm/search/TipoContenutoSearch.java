package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.List;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.tipo_contenuto
 */
public class TipoContenutoSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 3344277111L;
    //COLUMN id
    private String id;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN descrizione_estesa
    private String descrizioneEstesa;
    //COLUMN sezione
    private String sezione;
    
    private List<Integer> ids;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public String getDescrizioneEstesa(){
        return this.descrizioneEstesa;
    }

    public void setDescrizioneEstesa(String descrizioneEstesa){
        this.descrizioneEstesa = descrizioneEstesa;
    }

    public String getSezione(){
        return this.sezione;
    }

    public void setSezione(String sezione){
        this.sezione = sezione;
    }

	public List<Integer> getIds()
	{
		return ids;
	}
	public void setIds(List<Integer> ids)
	{
		this.ids = ids;
	}


}
