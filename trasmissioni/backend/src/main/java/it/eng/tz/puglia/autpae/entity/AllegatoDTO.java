package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.util.Date;

import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;

/**
 * DTO for table public.allegato
 */
public class AllegatoDTO implements Serializable {  // implements Comparable<AllegatoDTO>

	private static final long serialVersionUID = 6693642738L;
	
	protected Long id;
	protected String nome;
	protected String titolo;
	protected String descrizione;
	protected String mimeType;
	protected Date dataCaricamento;
	protected String contenuto;
	protected String checksum;
	protected Boolean deleted;
	protected Integer dimensione;
	protected String utenteInserimento;
	protected String usernameInserimento;
	protected String numeroProtocolloIn;
	protected String numeroProtocolloOut;
	protected Date dataProtocolloIn;
	protected Date dataProtocolloOut;
	protected String pathCms;
	protected Long idRichiestaPostTrasmissione;
	protected Long idAnnotazioneInterna;
	protected Boolean deletable=true;
	
	
	public String getPathCms() {
		return pathCms;
	}
	public void setPathCms(String pathCms) {
		this.pathCms = pathCms;
	}


	protected TipoAllegato tipo; // non fa parte della tabella
	
	
	public AllegatoDTO() { }
	public AllegatoDTO(AllegatoCustomDTO allegatoDTO) {
		this.id = allegatoDTO.getId();
		this.nome = allegatoDTO.getNome();
		this.tipo = allegatoDTO.getTipo();
		this.dimensione = allegatoDTO.getDimensione();
		this.dataCaricamento = allegatoDTO.getDataCaricamento();
		this.mimeType = allegatoDTO.getMimeType();
		this.numeroProtocolloIn = allegatoDTO.getNrProtocolloIn();
		this.numeroProtocolloOut = allegatoDTO.getNrProtocolloOut();
		this.dataProtocolloIn = allegatoDTO.getDataProtocolloIn();
		this.dataProtocolloOut = allegatoDTO.getDataProtocolloOut();
		this.idRichiestaPostTrasmissione = allegatoDTO.getIdRichiestaPostTrasmissione();
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
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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
	public String getContenuto() {
		return contenuto;
	}
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	public String getChecksum() {
		return checksum;
	}
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Integer getDimensione() {
		return dimensione;
	}
	public void setDimensione(Integer dimensione) {
		this.dimensione = dimensione;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public TipoAllegato getTipo() {
		return tipo;
	}
	public void setTipo(TipoAllegato tipo) {
		this.tipo = tipo;
	}
	public String getUtenteInserimento() {
		return utenteInserimento;
	}
	public void setUtenteInserimento(String utenteInserimento) {
		this.utenteInserimento = utenteInserimento;
	}
	public String getUsernameInserimento() {
		return usernameInserimento;
	}
	public void setUsernameInserimento(String usernameInserimento) {
		this.usernameInserimento = usernameInserimento;
	}
	public String getNumeroProtocolloIn() {
		return numeroProtocolloIn;
	}
	public void setNumeroProtocolloIn(String numeroProtocolloIn) {
		this.numeroProtocolloIn = numeroProtocolloIn;
	}
	public String getNumeroProtocolloOut() {
		return numeroProtocolloOut;
	}
	public void setNumeroProtocolloOut(String numeroProtocolloOut) {
		this.numeroProtocolloOut = numeroProtocolloOut;
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
	public Boolean getDeletable() {
		return deletable;
	}
	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
	}
	@Override
	public String toString() {
		return "AllegatoDTO [id=" + id + ", nome=" + nome + ", titolo=" + titolo + ", descrizione=" + descrizione
				+ ", mimeType=" + mimeType + ", dataCaricamento=" + dataCaricamento + ", contenuto=" + contenuto
				+ ", checksum=" + checksum + ", deleted=" + deleted + ", dimensione=" + dimensione
				+ ", utenteInserimento=" + utenteInserimento + ", usernameInserimento=" + usernameInserimento
				+ ", numeroProtocolloIn=" + numeroProtocolloIn + ", numeroProtocolloOut=" + numeroProtocolloOut
				+ ", dataProtocolloIn=" + dataProtocolloIn + ", dataProtocolloOut=" + dataProtocolloOut + ", pathCms="
				+ pathCms + ", idRichiestaPostTrasmissione=" + idRichiestaPostTrasmissione + ", idAnnotazioneInterna="
				+ idAnnotazioneInterna + ", deletable=" + deletable + ", tipo=" + tipo + "]";
	}
	

}
