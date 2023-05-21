package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Inquadramento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.InquadramentoDTO;

public class InquadramentoDto extends PraticaChild
{
	private static final long serialVersionUID = -2096596410346039322L;

	private Integer destinazioneUso;
    private String  destinazioneUsoSpecifica;
	private Integer contestoPaesaggInterv;
    private String  contestoPaesaggIntervSpecifica;
	private Integer morfologiaConPaesag;
    private String  morfologiaConPaesagSpecifica;
    
    
    public InquadramentoDto()
    {
    	super();
    }

    public InquadramentoDto(InquadramentoDTO entity) {
    	
    		 if (entity.getInqDestUsoInterv()==null) 															this.destinazioneUso = null;
    	else if (Inquadramento.valueOf(entity.getInqDestUsoInterv())   ==Inquadramento.RESIDENZIALE) 		    this.destinazioneUso = 1;
    	else if (Inquadramento.valueOf(entity.getInqDestUsoInterv())   ==Inquadramento.RICETTIVA_TURISTICA)     this.destinazioneUso = 2;
    	else if (Inquadramento.valueOf(entity.getInqDestUsoInterv())   ==Inquadramento.INDUSTRIALE_ARTIGIANALE) this.destinazioneUso = 3;
    	else if (Inquadramento.valueOf(entity.getInqDestUsoInterv())   ==Inquadramento.AGRICOLO) 			    this.destinazioneUso = 4;
    	else if (Inquadramento.valueOf(entity.getInqDestUsoInterv())   ==Inquadramento.COMMERCIALE_DIREZIONALE) this.destinazioneUso = 5;
    	else if (Inquadramento.valueOf(entity.getInqDestUsoInterv())   ==Inquadramento.ALTRO) 				    this.destinazioneUso = 6;
    	else 																									this.destinazioneUso = null;
    	
    		 if (entity.getInqContestoPaesag()==null)															this.contestoPaesaggInterv = null;
    	else if (Inquadramento.valueOf(entity.getInqContestoPaesag())  ==Inquadramento.CENTRO_E_NUCLEO_STORICO) this.contestoPaesaggInterv = 1;
    	else if (Inquadramento.valueOf(entity.getInqContestoPaesag())  ==Inquadramento.AREA_URBANA) 			this.contestoPaesaggInterv = 2;
    	else if (Inquadramento.valueOf(entity.getInqContestoPaesag())  ==Inquadramento.AREA_PERIURBANA) 		this.contestoPaesaggInterv = 3;
    	else if (Inquadramento.valueOf(entity.getInqContestoPaesag())  ==Inquadramento.AREA_AGRICOLA) 			this.contestoPaesaggInterv = 4;
    	else if (Inquadramento.valueOf(entity.getInqContestoPaesag())  ==Inquadramento.INSEDIAMENTO_RURALE) 	this.contestoPaesaggInterv = 5;
    	else if (Inquadramento.valueOf(entity.getInqContestoPaesag())  ==Inquadramento.AREA_NATURALE) 		    this.contestoPaesaggInterv = 6;
    	else if (Inquadramento.valueOf(entity.getInqContestoPaesag())  ==Inquadramento.AREA_BOSCHIVA) 		    this.contestoPaesaggInterv = 7;
    	else if (Inquadramento.valueOf(entity.getInqContestoPaesag())  ==Inquadramento.AMBITO_FLUVIALE) 		this.contestoPaesaggInterv = 8;
    	else if (Inquadramento.valueOf(entity.getInqContestoPaesag())  ==Inquadramento.AMBITO_LACUSTRE) 		this.contestoPaesaggInterv = 9;
    	else if (Inquadramento.valueOf(entity.getInqContestoPaesag())  ==Inquadramento.ALTRO) 				    this.contestoPaesaggInterv = 10;
    	else 																									this.contestoPaesaggInterv = null;
    	
    		 if (entity.getInqMorfologiaPaesag()==null)															this.morfologiaConPaesag = null;
    	else if (Inquadramento.valueOf(entity.getInqMorfologiaPaesag())==Inquadramento.COSTA) 				    this.morfologiaConPaesag = 1;
    	else if (Inquadramento.valueOf(entity.getInqMorfologiaPaesag())==Inquadramento.CRINALE) 				this.morfologiaConPaesag = 2;
    	else if (Inquadramento.valueOf(entity.getInqMorfologiaPaesag())==Inquadramento.PIANURA) 				this.morfologiaConPaesag = 3;
    	else if (Inquadramento.valueOf(entity.getInqMorfologiaPaesag())==Inquadramento.VERSANTE) 			    this.morfologiaConPaesag = 4;
    	else if (Inquadramento.valueOf(entity.getInqMorfologiaPaesag())==Inquadramento.ALTOPIANO_PROMONTORIO)   this.morfologiaConPaesag = 5;
    	else if (Inquadramento.valueOf(entity.getInqMorfologiaPaesag())==Inquadramento.PIANA_VALLIVA) 		    this.morfologiaConPaesag = 6;
    	else if (Inquadramento.valueOf(entity.getInqMorfologiaPaesag())==Inquadramento.ALTRO) 				    this.morfologiaConPaesag = 7;
    	else 																									this.morfologiaConPaesag = null;
    	
    	this.destinazioneUsoSpecifica       = entity.getInqDestUsoIntervAltro();
    	this.contestoPaesaggIntervSpecifica = entity.getInqContestoPaesagAltro();
    	this.morfologiaConPaesagSpecifica 	= entity.getInqMorfologiaPaesagAltro();
    }
    
    
	public Integer getDestinazioneUso() {
		return destinazioneUso;
	}

	public void setDestinazioneUso(Integer destinazioneUso) {
		this.destinazioneUso = destinazioneUso;
	}

	public String getDestinazioneUsoSpecifica() {
		return destinazioneUsoSpecifica;
	}

	public void setDestinazioneUsoSpecifica(String destinazioneUsoSpecifica) {
		this.destinazioneUsoSpecifica = destinazioneUsoSpecifica;
	}

	public Integer getContestoPaesaggInterv() {
		return contestoPaesaggInterv;
	}

	public void setContestoPaesaggInterv(Integer contestoPaesaggInterv) {
		this.contestoPaesaggInterv = contestoPaesaggInterv;
	}

	public String getContestoPaesaggIntervSpecifica() {
		return contestoPaesaggIntervSpecifica;
	}

	public void setContestoPaesaggIntervSpecifica(String contestoPaesaggIntervSpecifica) {
		this.contestoPaesaggIntervSpecifica = contestoPaesaggIntervSpecifica;
	}

	public Integer getMorfologiaConPaesag() {
		return morfologiaConPaesag;
	}

	public void setMorfologiaConPaesag(Integer morfologiaConPaesag) {
		this.morfologiaConPaesag = morfologiaConPaesag;
	}

	public String getMorfologiaConPaesagSpecifica() {
		return morfologiaConPaesagSpecifica;
	}

	public void setMorfologiaConPaesagSpecifica(String morfologiaConPaesagSpecifica) {
		this.morfologiaConPaesagSpecifica = morfologiaConPaesagSpecifica;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((contestoPaesaggInterv == null) ? 0 : contestoPaesaggInterv.hashCode());
		result = prime * result
				+ ((contestoPaesaggIntervSpecifica == null) ? 0 : contestoPaesaggIntervSpecifica.hashCode());
		result = prime * result + ((destinazioneUso == null) ? 0 : destinazioneUso.hashCode());
		result = prime * result + ((destinazioneUsoSpecifica == null) ? 0 : destinazioneUsoSpecifica.hashCode());
		result = prime * result + ((morfologiaConPaesag == null) ? 0 : morfologiaConPaesag.hashCode());
		result = prime * result
				+ ((morfologiaConPaesagSpecifica == null) ? 0 : morfologiaConPaesagSpecifica.hashCode());
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
		InquadramentoDto other = (InquadramentoDto) obj;
		if (contestoPaesaggInterv == null) {
			if (other.contestoPaesaggInterv != null)
				return false;
		} else if (!contestoPaesaggInterv.equals(other.contestoPaesaggInterv))
			return false;
		if (contestoPaesaggIntervSpecifica == null) {
			if (other.contestoPaesaggIntervSpecifica != null)
				return false;
		} else if (!contestoPaesaggIntervSpecifica.equals(other.contestoPaesaggIntervSpecifica))
			return false;
		if (destinazioneUso == null) {
			if (other.destinazioneUso != null)
				return false;
		} else if (!destinazioneUso.equals(other.destinazioneUso))
			return false;
		if (destinazioneUsoSpecifica == null) {
			if (other.destinazioneUsoSpecifica != null)
				return false;
		} else if (!destinazioneUsoSpecifica.equals(other.destinazioneUsoSpecifica))
			return false;
		if (morfologiaConPaesag == null) {
			if (other.morfologiaConPaesag != null)
				return false;
		} else if (!morfologiaConPaesag.equals(other.morfologiaConPaesag))
			return false;
		if (morfologiaConPaesagSpecifica == null) {
			if (other.morfologiaConPaesagSpecifica != null)
				return false;
		} else if (!morfologiaConPaesagSpecifica.equals(other.morfologiaConPaesagSpecifica))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InquadramentoDto [destinazioneUso=" + destinazioneUso + ", destinazioneUsoSpecifica="
				+ destinazioneUsoSpecifica + ", contestoPaesaggInterv=" + contestoPaesaggInterv
				+ ", contestoPaesaggIntervSpecifica=" + contestoPaesaggIntervSpecifica + ", morfologiaConPaesag="
				+ morfologiaConPaesag + ", morfologiaConPaesagSpecifica=" + morfologiaConPaesagSpecifica + "]";
	}

}