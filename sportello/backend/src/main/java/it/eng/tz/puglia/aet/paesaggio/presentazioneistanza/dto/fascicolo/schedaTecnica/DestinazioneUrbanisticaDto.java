package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.SqlDateDeserializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinazioneUrbanisticaInterventoDTO;

public class DestinazioneUrbanisticaDto extends PraticaChild
{
	private static final long serialVersionUID = -1899187997086834484L;
	
//	private String titolo;	// nome del comune (inutile)
	private Long comuneId;
	private boolean mostraCoerenza;
	private Date coerenzaData;
	private String coerenzaAtto;
	
	private Integer strumentoUrbanistico;
	@JsonDeserialize(using=SqlDateDeserializer.class)
	@JsonSerialize(using=DateSerializer.class)
	private Date approvatoData;
	private String approvatoCon;
//	private String deliberazioneApprovazione;
	private String destinazioneUrbanistica;
	private String ulterioriTutele;
	private Boolean confermaCoerenza;
	
	private Integer strumentoInAdozione;
	@JsonDeserialize(using=SqlDateDeserializer.class)
	@JsonSerialize(using=DateSerializer.class)
	private Date adottatoData;
	private String adottatoCon;
	private String destinazioneUrbanisticaAdottato;
	private String ulterioriTuteleAdottato;
	private Boolean conformitaStrumentoUrbanistico;
	
	
	public DestinazioneUrbanisticaDto()
	{
		super();
	}
	
	public DestinazioneUrbanisticaDto(DestinazioneUrbanisticaInterventoDTO entity, boolean mostraCoerenza, Date coerenzaData, String coerenzaAtto)
	{
		this.comuneId = entity.getComuneId();
		this.mostraCoerenza = mostraCoerenza;
		this.coerenzaData = coerenzaData;
		this.coerenzaAtto = coerenzaAtto;
		
			 if (entity.getStrumUrbApprovato()==null) 		  this.strumentoUrbanistico = null;
		else if (entity.getStrumUrbApprovato().equals("PUG")) this.strumentoUrbanistico = 1;
		else if (entity.getStrumUrbApprovato().equals("PRG")) this.strumentoUrbanistico = 2;
		else if (entity.getStrumUrbApprovato().equals("Pdf")) this.strumentoUrbanistico = 3;
		else												  this.strumentoUrbanistico = null;
		this.approvatoData = entity.getStrumUrbApprovatoData();
		this.approvatoCon = entity.getStrumUrbApprovatoAtto();
		this.destinazioneUrbanistica = entity.getDestinAreaStrVig();
		this.ulterioriTutele = entity.getStrumApprovUltTutele();
		if (entity.getCheckPresaVisione()==null) 			 this.confermaCoerenza = null;
		else if (entity.getCheckPresaVisione().equals("S"))  this.confermaCoerenza = true;
		else if (entity.getCheckPresaVisione().equals("N"))  this.confermaCoerenza = false;
		else												 this.confermaCoerenza = null;
		
			 if (entity.getStrumUrbAdottato()==null) 		 this.strumentoInAdozione = null;
		else if (entity.getStrumUrbAdottato().equals("NO") ) this.strumentoInAdozione = 1;
		else if (entity.getStrumUrbAdottato().equals("PUG")) this.strumentoInAdozione = 2;
		else if (entity.getStrumUrbAdottato().equals("VAR")) this.strumentoInAdozione = 3;
		else												 this.strumentoInAdozione = null;
		this.adottatoData = entity.getStrumUrbAdottatoData();
		this.adottatoCon = entity.getStrumUrbAdottatoAtto();
		this.destinazioneUrbanisticaAdottato = entity.getDestinAreaStrAdott();
		this.ulterioriTuteleAdottato = entity.getStrumAdottUltTutele();
		if (entity.getConformeDisciplUrbVigente()==null) 			this.conformitaStrumentoUrbanistico = null;
		else if (entity.getConformeDisciplUrbVigente().equals("S")) this.conformitaStrumentoUrbanistico = true;
		else if (entity.getConformeDisciplUrbVigente().equals("N")) this.conformitaStrumentoUrbanistico = false;
		else														this.conformitaStrumentoUrbanistico = null;
	}
	
	
//	public String getTitolo() {
//		return titolo;
//	}
//	public void setTitolo(String titolo) {
//		this.titolo = titolo;
//	}
	public Long getComuneId() {
		return comuneId;
	}
	public void setComuneId(Long comuneId) {
		this.comuneId = comuneId;
	}
	public boolean isMostraCoerenza() {
		return mostraCoerenza;
	}
	public void setMostraCoerenza(boolean mostraCoerenza) {
		this.mostraCoerenza = mostraCoerenza;
	}
	public Integer getStrumentoUrbanistico() {
		return strumentoUrbanistico;
	}
	public void setStrumentoUrbanistico(Integer strumentoUrbanistico) {
		this.strumentoUrbanistico = strumentoUrbanistico;
	}
	public Date getApprovatoData() {
		return approvatoData;
	}
	public void setApprovatoData(Date approvatoData) {
		this.approvatoData = approvatoData;
	}
	public String getApprovatoCon() {
		return approvatoCon;
	}
	public void setApprovatoCon(String approvatoCon) {
		this.approvatoCon = approvatoCon;
	}
	public String getDestinazioneUrbanistica() {
		return destinazioneUrbanistica;
	}
	public void setDestinazioneUrbanistica(String destinazioneUrbanistica) {
		this.destinazioneUrbanistica = destinazioneUrbanistica;
	}
	public String getUlterioriTutele() {
		return ulterioriTutele;
	}
	public void setUlterioriTutele(String ulterioriTutele) {
		this.ulterioriTutele = ulterioriTutele;
	}
	public Boolean getConfermaCoerenza() {
		return confermaCoerenza;
	}
	public void setConfermaCoerenza(Boolean confermaCoerenza) {
		this.confermaCoerenza = confermaCoerenza;
	}
	public Integer getStrumentoInAdozione() {
		return strumentoInAdozione;
	}
	public void setStrumentoInAdozione(Integer strumentoInAdozione) {
		this.strumentoInAdozione = strumentoInAdozione;
	}
	public Date getAdottatoData() {
		return adottatoData;
	}
	public void setAdottatoData(Date adottatoData) {
		this.adottatoData = adottatoData;
	}
	public String getAdottatoCon() {
		return adottatoCon;
	}
	public void setAdottatoCon(String adottatoCon) {
		this.adottatoCon = adottatoCon;
	}
	public String getDestinazioneUrbanisticaAdottato() {
		return destinazioneUrbanisticaAdottato;
	}
	public void setDestinazioneUrbanisticaAdottato(String destinazioneUrbanisticaAdottato) {
		this.destinazioneUrbanisticaAdottato = destinazioneUrbanisticaAdottato;
	}
	public String getUlterioriTuteleAdottato() {
		return ulterioriTuteleAdottato;
	}
	public void setUlterioriTuteleAdottato(String ulterioriTuteleAdottato) {
		this.ulterioriTuteleAdottato = ulterioriTuteleAdottato;
	}
	public Boolean getConformitaStrumentoUrbanistico() {
		return conformitaStrumentoUrbanistico;
	}
	public void setConformitaStrumentoUrbanistico(Boolean conformitaStrumentoUrbanistico) {
		this.conformitaStrumentoUrbanistico = conformitaStrumentoUrbanistico;
	}
	public Date getCoerenzaData() {
		return coerenzaData;
	}
	public void setCoerenzaData(Date coerenzaData) {
		this.coerenzaData = coerenzaData;
	}
	public String getCoerenzaAtto() {
		return coerenzaAtto;
	}
	public void setCoerenzaAtto(String coerenzaAtto) {
		this.coerenzaAtto = coerenzaAtto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((adottatoCon == null) ? 0 : adottatoCon.hashCode());
		result = prime * result + ((adottatoData == null) ? 0 : adottatoData.hashCode());
		result = prime * result + ((approvatoCon == null) ? 0 : approvatoCon.hashCode());
		result = prime * result + ((approvatoData == null) ? 0 : approvatoData.hashCode());
		result = prime * result + ((coerenzaAtto == null) ? 0 : coerenzaAtto.hashCode());
		result = prime * result + ((coerenzaData == null) ? 0 : coerenzaData.hashCode());
		result = prime * result + ((comuneId == null) ? 0 : comuneId.hashCode());
		result = prime * result + ((confermaCoerenza == null) ? 0 : confermaCoerenza.hashCode());
		result = prime * result	+ ((conformitaStrumentoUrbanistico == null) ? 0 : conformitaStrumentoUrbanistico.hashCode());
		result = prime * result + ((destinazioneUrbanistica == null) ? 0 : destinazioneUrbanistica.hashCode());
		result = prime * result	+ ((destinazioneUrbanisticaAdottato == null) ? 0 : destinazioneUrbanisticaAdottato.hashCode());
		result = prime * result + (mostraCoerenza ? 1231 : 1237);
		result = prime * result + ((strumentoInAdozione == null) ? 0 : strumentoInAdozione.hashCode());
		result = prime * result + ((strumentoUrbanistico == null) ? 0 : strumentoUrbanistico.hashCode());
//		result = prime * result + ((titolo == null) ? 0 : titolo.hashCode());
		result = prime * result + ((ulterioriTutele == null) ? 0 : ulterioriTutele.hashCode());
		result = prime * result + ((ulterioriTuteleAdottato == null) ? 0 : ulterioriTuteleAdottato.hashCode());
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
		DestinazioneUrbanisticaDto other = (DestinazioneUrbanisticaDto) obj;
		if (adottatoCon == null) {
			if (other.adottatoCon != null)
				return false;
		} else if (!adottatoCon.equals(other.adottatoCon))
			return false;
		if (adottatoData == null) {
			if (other.adottatoData != null)
				return false;
		} else if (!adottatoData.equals(other.adottatoData))
			return false;
		if (approvatoCon == null) {
			if (other.approvatoCon != null)
				return false;
		} else if (!approvatoCon.equals(other.approvatoCon))
			return false;
		if (approvatoData == null) {
			if (other.approvatoData != null)
				return false;
		} else if (!approvatoData.equals(other.approvatoData))
			return false;
		if (coerenzaAtto == null) {
			if (other.coerenzaAtto != null)
				return false;
		} else if (!coerenzaAtto.equals(other.coerenzaAtto))
			return false;
		if (coerenzaData == null) {
			if (other.coerenzaData != null)
				return false;
		} else if (!coerenzaData.equals(other.coerenzaData))
			return false;
		if (comuneId == null) {
			if (other.comuneId != null)
				return false;
		} else if (!comuneId.equals(other.comuneId))
			return false;
		if (confermaCoerenza == null) {
			if (other.confermaCoerenza != null)
				return false;
		} else if (!confermaCoerenza.equals(other.confermaCoerenza))
			return false;
		if (conformitaStrumentoUrbanistico == null) {
			if (other.conformitaStrumentoUrbanistico != null)
				return false;
		} else if (!conformitaStrumentoUrbanistico.equals(other.conformitaStrumentoUrbanistico))
			return false;
		if (destinazioneUrbanistica == null) {
			if (other.destinazioneUrbanistica != null)
				return false;
		} else if (!destinazioneUrbanistica.equals(other.destinazioneUrbanistica))
			return false;
		if (destinazioneUrbanisticaAdottato == null) {
			if (other.destinazioneUrbanisticaAdottato != null)
				return false;
		} else if (!destinazioneUrbanisticaAdottato.equals(other.destinazioneUrbanisticaAdottato))
			return false;
		if (mostraCoerenza != other.mostraCoerenza)
			return false;
		if (strumentoInAdozione == null) {
			if (other.strumentoInAdozione != null)
				return false;
		} else if (!strumentoInAdozione.equals(other.strumentoInAdozione))
			return false;
		if (strumentoUrbanistico == null) {
			if (other.strumentoUrbanistico != null)
				return false;
		} else if (!strumentoUrbanistico.equals(other.strumentoUrbanistico))
			return false;
//		if (titolo == null) {
//			if (other.titolo != null)
//				return false;
//		} else if (!titolo.equals(other.titolo))
//			return false;
		if (ulterioriTutele == null) {
			if (other.ulterioriTutele != null)
				return false;
		} else if (!ulterioriTutele.equals(other.ulterioriTutele))
			return false;
		if (ulterioriTuteleAdottato == null) {
			if (other.ulterioriTuteleAdottato != null)
				return false;
		} else if (!ulterioriTuteleAdottato.equals(other.ulterioriTuteleAdottato))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DestinazioneUrbanisticaDto [comuneId=" + comuneId + ", mostraCoerenza="
				+ mostraCoerenza + ", coerenzaData=" + coerenzaData + ", coerenzaAtto=" + coerenzaAtto
				+ ", strumentoUrbanistico=" + strumentoUrbanistico + ", approvatoData=" + approvatoData
				+ ", approvatoCon=" + approvatoCon + ", destinazioneUrbanistica=" + destinazioneUrbanistica
				+ ", ulterioriTutele=" + ulterioriTutele + ", confermaCoerenza=" + confermaCoerenza
				+ ", strumentoInAdozione=" + strumentoInAdozione + ", adottatoData=" + adottatoData + ", adottatoCon="
				+ adottatoCon + ", destinazioneUrbanisticaAdottato=" + destinazioneUrbanisticaAdottato
				+ ", ulterioriTuteleAdottato=" + ulterioriTuteleAdottato + ", conformitaStrumentoUrbanistico="
				+ conformitaStrumentoUrbanistico + "]";
	}
	
}