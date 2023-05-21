package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.VincArchDTO;

public class VincolisticaDto extends PraticaChild
{
	private static final long serialVersionUID = 6419748386310737591L;
	
	private Boolean sottopostoATutela;
    private List<TipologicaFE> specificaVincolo;
    private String altriVincolo;
    
    
    public VincolisticaDto()
    {
    	super();
    }

    public VincolisticaDto(VincArchDTO entity)
    {
    		 if (entity.getVincArcNoTutela()==null)	   	  this.sottopostoATutela = null;
    	else if (entity.getVincArcNoTutela().equals("S")) this.sottopostoATutela = true;
    	else if (entity.getVincArcNoTutela().equals("N")) this.sottopostoATutela = false;
    	else										  	  this.sottopostoATutela = null;
    	
    	this.specificaVincolo = new ArrayList<>();
    	if (entity.getVincArcMonumDiretto   ()!=null && entity.getVincArcMonumDiretto   ().equals("S")) this.specificaVincolo.add(new TipologicaFE(1,""));
    	if (entity.getVincArcMonumIndiretto ()!=null && entity.getVincArcMonumIndiretto ().equals("S")) this.specificaVincolo.add(new TipologicaFE(2,""));
    	if (entity.getVincArcArcheoDiretto  ()!=null && entity.getVincArcArcheoDiretto  ().equals("S")) this.specificaVincolo.add(new TipologicaFE(3,""));
    	if (entity.getVincArcArcheoIndiretto()!=null && entity.getVincArcArcheoIndiretto().equals("S")) this.specificaVincolo.add(new TipologicaFE(4,""));
    	
    	this.altriVincolo = entity.getAltriVincoli();
    }

    
	public Boolean getSottopostoATutela() {
		return sottopostoATutela;
	}
	public void setSottopostoATutela(Boolean sottopostoATutela) {
		this.sottopostoATutela = sottopostoATutela;
	}
	public List<TipologicaFE> getSpecificaVincolo() {
		return specificaVincolo;
	}
	public void setSpecificaVincolo(List<TipologicaFE> specificaVincolo) {
		this.specificaVincolo = specificaVincolo;
	}
	public String getAltriVincolo() {
		return altriVincolo;
	}
	public void setAltriVincolo(String altriVincolo) {
		this.altriVincolo = altriVincolo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((altriVincolo == null) ? 0 : altriVincolo.hashCode());
		result = prime * result + ((sottopostoATutela == null) ? 0 : sottopostoATutela.hashCode());
		result = prime * result + ((specificaVincolo == null) ? 0 : specificaVincolo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		VincolisticaDto other = (VincolisticaDto) obj;
		if (altriVincolo == null) {
			if (other.altriVincolo != null)
				return false;
		} else if (!altriVincolo.equals(other.altriVincolo))
			return false;
		if (sottopostoATutela == null) {
			if (other.sottopostoATutela != null)
				return false;
		} else if (!sottopostoATutela.equals(other.sottopostoATutela))
			return false;
		if (specificaVincolo == null) {
			if (other.specificaVincolo != null)
				return false;
		} else if (!specificaVincolo.equals(other.specificaVincolo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VincolisticaDto [sottopostoATutela=" + sottopostoATutela + ", specificaVincolo=" + specificaVincolo
				+ ", altriVincolo=" + altriVincolo + "]";
	}
    
}