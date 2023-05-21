package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DTO for table presentazione_istanza.disclaimer_pratica
 */
public class DisclaimerPraticaDTO implements Serializable{

    private static final long serialVersionUID = 4172022274L;
    //COLUMN disclaimer_id
    private int disclaimerId;
    //COLUMN flag
    private String flag;
    //COLUMN pratica_id
    private UUID praticaId;

    
    
    public DisclaimerPraticaDTO() {}

	public DisclaimerPraticaDTO(int disclaimerId, String flag, UUID praticaId) {
		super();
		this.disclaimerId = disclaimerId;
		this.flag = flag;
		this.praticaId = praticaId;
	}

	
    public int getDisclaimerId(){
        return this.disclaimerId;
    }

    public void setDisclaimerId(int disclaimerId){
        this.disclaimerId = disclaimerId;
    }

    public String getFlag(){
        return this.flag;
    }

    public void setFlag(String flag){
        this.flag = flag;
    }

    public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(UUID praticaId){
        this.praticaId = praticaId;
    }

    public static List<DisclaimerPraticaDTO> fromOptions(UUID praticaId,List<Integer> altreOpzioni){
    	List<DisclaimerPraticaDTO> r=null;
    	if(altreOpzioni!=null && altreOpzioni.size()>0) {
    		final List<DisclaimerPraticaDTO> ret=new ArrayList<DisclaimerPraticaDTO>();
    		altreOpzioni.forEach(el->{
    			ret.add(new DisclaimerPraticaDTO(el,"S",praticaId));
    		});
    		r=ret;
    	}
		return r;
    }

}
