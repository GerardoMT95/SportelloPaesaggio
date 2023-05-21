package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;

public class AllegatoCustomDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	private Long id;
	private String nome;
	private String nrProtocolloIn;
	private String nrProtocolloOut;
	private Date dataProtocolloIn;
	private Date dataProtocolloOut;
	private Integer dimensione;    // potrei cancellarlo perchè non serve al FE
	private 	 TipoAllegato  tipo;
	private List<TipoAllegato> tipi;
	private String mimeType;       // potrei cancellarlo perchè non serve al FE
	private Date dataCaricamento;
	private Boolean obbligatorio = false;
	private Boolean multiplo;
	private String checksum;
	private Boolean isUrl; //settato in caso di allegati mail prelevato da allegato_corrispondenza
	private Long idRichiestaPostTrasmissione;
	private Long idAnnotazioneInterna;
	
	public AllegatoCustomDTO() { }
	
	public AllegatoCustomDTO(AllegatoDTO allegatoDTO) {
		this.id = allegatoDTO.getId();
		this.nome = allegatoDTO.getNome();
		this.tipo = allegatoDTO.getTipo();
		this.dimensione = allegatoDTO.getDimensione();
		this.dataCaricamento = allegatoDTO.getDataCaricamento();
		this.mimeType = allegatoDTO.getMimeType();
		this.nrProtocolloIn = allegatoDTO.getNumeroProtocolloIn();
		this.nrProtocolloOut = allegatoDTO.getNumeroProtocolloOut();
		this.dataProtocolloIn = allegatoDTO.getDataProtocolloIn();
		this.dataProtocolloOut = allegatoDTO.getDataProtocolloOut();
		this.setChecksum(allegatoDTO.getChecksum());
		this.setIdRichiestaPostTrasmissione(allegatoDTO.getIdRichiestaPostTrasmissione());
//		this.obbligatorio = ?????????;
//		this.multiplo = ?????????;
	}
	
	public AllegatoCustomDTO(TipoAllegato tipo) {
		this.tipo = tipo;
	}
	
	public AllegatoCustomDTO(List<TipoAllegato> tipi) {
		this.tipi = tipi;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNrProtocolloIn() {
		return nrProtocolloIn;
	}
	public void setNrProtocolloIn(String nrProtocolloIn) {
		this.nrProtocolloIn = nrProtocolloIn;
	}
	public String getNrProtocolloOut() {
		return nrProtocolloOut;
	}
	public void setNrProtocolloOut(String nrProtocolloOut) {
		this.nrProtocolloOut = nrProtocolloOut;
	}
	public Date getDataProtocolloIn() {
		return dataProtocolloIn;
	}
	public void setDataProtocolloIn(Date dataProtocolloIn) {
		this.dataProtocolloIn = dataProtocolloIn;
	}
	public Date getDataProtocolloOut() {
		return dataProtocolloOut;
	}
	public void setDataProtocolloOut(Date dataProtocolloOut) {
		this.dataProtocolloOut = dataProtocolloOut;
	}
	public Integer getDimensione() {
		return dimensione;
	}
	public void setDimensione(Integer dimensione) {
		this.dimensione = dimensione;
	}
	public TipoAllegato getTipo() {
		return tipo;
	}
	public void setTipo(TipoAllegato tipo) {
		this.tipo = tipo;
	}
	public List<TipoAllegato> getTipi() {
		return tipi;
	}
	public void setTipi(List<TipoAllegato> tipi) {
		this.tipi = tipi;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public Date getDataCaricamento() {
		return dataCaricamento;
	}
	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}
	public Boolean getObbligatorio() {
		return obbligatorio;
	}
	public void setObbligatorio(Boolean obbligatorio) {
		this.obbligatorio = obbligatorio;
	}
	public Boolean getMultiplo() {
		return multiplo;
	}
	public void setMultiplo(Boolean multiplo) {
		this.multiplo = multiplo;
	}
	
	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public Boolean getIsUrl() {
		return isUrl;
	}

	public void setIsUrl(Boolean isUrl) {
		this.isUrl = isUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checksum == null) ? 0 : checksum.hashCode());
		result = prime * result + ((dataCaricamento == null) ? 0 : dataCaricamento.hashCode());
		result = prime * result + ((dataProtocolloIn == null) ? 0 : dataProtocolloIn.hashCode());
		result = prime * result + ((dataProtocolloOut == null) ? 0 : dataProtocolloOut.hashCode());
		result = prime * result + ((dimensione == null) ? 0 : dimensione.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isUrl == null) ? 0 : isUrl.hashCode());
		result = prime * result + ((mimeType == null) ? 0 : mimeType.hashCode());
		result = prime * result + ((multiplo == null) ? 0 : multiplo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((nrProtocolloIn == null) ? 0 : nrProtocolloIn.hashCode());
		result = prime * result + ((nrProtocolloOut == null) ? 0 : nrProtocolloOut.hashCode());
		result = prime * result + ((obbligatorio == null) ? 0 : obbligatorio.hashCode());
		result = prime * result + ((tipi == null) ? 0 : tipi.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AllegatoCustomDTO other = (AllegatoCustomDTO) obj;
		if (checksum == null) {
			if (other.checksum != null)
				return false;
		} else if (!checksum.equals(other.checksum))
			return false;
		if (dataCaricamento == null) {
			if (other.dataCaricamento != null)
				return false;
		} else if (!dataCaricamento.equals(other.dataCaricamento))
			return false;
		if (dataProtocolloIn == null) {
			if (other.dataProtocolloIn != null)
				return false;
		} else if (!dataProtocolloIn.equals(other.dataProtocolloIn))
			return false;
		if (dataProtocolloOut == null) {
			if (other.dataProtocolloOut != null)
				return false;
		} else if (!dataProtocolloOut.equals(other.dataProtocolloOut))
			return false;
		if (dimensione == null) {
			if (other.dimensione != null)
				return false;
		} else if (!dimensione.equals(other.dimensione))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isUrl == null) {
			if (other.isUrl != null)
				return false;
		} else if (!isUrl.equals(other.isUrl))
			return false;
		if (mimeType == null) {
			if (other.mimeType != null)
				return false;
		} else if (!mimeType.equals(other.mimeType))
			return false;
		if (multiplo == null) {
			if (other.multiplo != null)
				return false;
		} else if (!multiplo.equals(other.multiplo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (nrProtocolloIn == null) {
			if (other.nrProtocolloIn != null)
				return false;
		} else if (!nrProtocolloIn.equals(other.nrProtocolloIn))
			return false;
		if (nrProtocolloOut == null) {
			if (other.nrProtocolloOut != null)
				return false;
		} else if (!nrProtocolloOut.equals(other.nrProtocolloOut))
			return false;
		if (obbligatorio == null) {
			if (other.obbligatorio != null)
				return false;
		} else if (!obbligatorio.equals(other.obbligatorio))
			return false;
		if (tipi == null) {
			if (other.tipi != null)
				return false;
		} else if (!tipi.equals(other.tipi))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AllegatoCustomDTO [id=" + id + ", nome=" + nome + ", nrProtocolloIn=" + nrProtocolloIn
				+ ", nrProtocolloOut=" + nrProtocolloOut + ", dataProtocolloIn=" + dataProtocolloIn
				+ ", dataProtocolloOut=" + dataProtocolloOut + ", dimensione=" + dimensione + ", tipo=" + tipo
				+ ", tipi=" + tipi + ", mimeType=" + mimeType + ", dataCaricamento=" + dataCaricamento
				+ ", obbligatorio=" + obbligatorio + ", multiplo=" + multiplo + ", checksum=" + checksum + ", isUrl="
				+ isUrl + "]";
	}

	public Long getIdRichiestaPostTrasmissione() {
		return idRichiestaPostTrasmissione;
	}

	public void setIdRichiestaPostTrasmissione(Long idRichiestaPostTrasmissione) {
		this.idRichiestaPostTrasmissione = idRichiestaPostTrasmissione;
	}
	
	public Long getIdAnnotazioneInterna() {
		return idAnnotazioneInterna;
	}

	public void setIdAnnotazioneInterna(Long idAnnotazioneInterna) {
		this.idAnnotazioneInterna = idAnnotazioneInterna;
	}
	
	
}