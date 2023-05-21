package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;

public class AllegatoCustomDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	private UUID id;
	private String nome;
	private String nrProtocolloIn;
	private String nrProtocolloOut;
	private Date dataProtocolloIn;
	private Date dataProtocolloOut;
	private Long dimensione;    // potrei cancellarlo perchè non serve al FE
	private	String tipo;
	private List<String> tipi;
	private String mimeType;       // potrei cancellarlo perchè non serve al FE
	private Date dataCaricamento;
	private Boolean obbligatorio = false;
	private Boolean multiplo;
	private String checksum;
	
	
	public AllegatoCustomDTO() { }
	
	public AllegatoCustomDTO(AllegatiDTO allegatoDTO) {
		this.id = allegatoDTO.getId();
		this.nome = allegatoDTO.getNomeFile();
		//this.tipo = allegatoDTO.getTipiContenuto();
		this.dimensione = allegatoDTO.getSize();
		this.dataCaricamento = allegatoDTO.getDataCaricamento();
		this.mimeType = allegatoDTO.getFormatoFile();
		this.checksum = allegatoDTO.getChecksum();
		//this.nrProtocolloIn = allegatoDTO.get
		//this.nrProtocolloOut = allegatoDTO.getNumeroProtocolloOut();
		//this.dataProtocolloIn = allegatoDTO.getDataProtocolloIn();
		//this.dataProtocolloOut = allegatoDTO.getDataProtocolloOut();
//		this.obbligatorio = ?????????;
//		this.multiplo = ?????????;
	}
	
	public AllegatoCustomDTO(String tipo) {
		this.tipo = tipo;
	}
	
	public AllegatoCustomDTO(List<String> tipi) {
		this.tipi = tipi;
	}
	
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
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
	public Long getDimensione() {
		return dimensione;
	}
	public void setDimensione(Long dimensione) {
		this.dimensione = dimensione;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public List<String> getTipi() {
		return tipi;
	}
	public void setTipi(List<String> tipi) {
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
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AllegatoCustomDTO [id=" + id + ", nome=" + nome + ", nrProtocolloIn=" + nrProtocolloIn
				+ ", nrProtocolloOut=" + nrProtocolloOut + ", dataProtocolloIn=" + dataProtocolloIn
				+ ", dataProtocolloOut=" + dataProtocolloOut + ", dimensione=" + dimensione + ", tipo=" + tipo
				+ ", tipi=" + tipi + ", mimeType=" + mimeType + ", dataCaricamento=" + dataCaricamento
				+ ", obbligatorio=" + obbligatorio + ", multiplo=" + multiplo + ", checksum=" + checksum + "]";
	}
	
	
}