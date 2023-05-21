package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.interfacce.UlterioreDocumentazioneAttribute;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.allegati
 */
public class AllegatiDTO implements Serializable,UlterioreDocumentazioneAttribute
{

	private static final long serialVersionUID = 6131712219L;
	// COLUMN id
	// @JsonIgnore
	private UUID id;
	// COLUMN nome_file
	private String nomeFile;
	// COLUMN formato_file
	private String formatoFile;
	// COLUMN data_caricamento
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateSqlDeserializer.class)
	private Date dataCaricamento;
	// COLUMN path_cms
	private String pathCms;
	// COLUMN id_cms
	@JsonIgnore
	private String idCms;
	// COLUMN pratica_id
	private UUID praticaId;
	// COLUMN descrizione
	private String descrizione;
	// COLUMN integrazione_id
	private Integer idIntegrazione;
	// COLUMN deleted
	private Boolean deleted;
	// COLUMN checksum
	//@JsonIgnore
	private String checksum;
	// COLUMN
	private Long size;
	// COLUMN titolo
	private String titolo;
	@JsonIgnore
	// COLUMN id_allegato_pre_protocollazione
	private UUID idAllegatoPreProtocollazione;
	@JsonIgnore
	// COLUMN id_diogene_scheduler 
	private String idDiogeneScheduler;
		
	
	private String type;
	@JsonIgnore
	private String protocollo;
	
	private String[] visualizzabiliDa;
	private String[] notificaA;
	private String ruolo;
	private String inseritoDa;
	private Boolean isSigned;

	// solo per invio tipi_contenuto multipli
	private List<String> tipiContenuto;

	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public UUID getIdAllegatoPreProtocollazione() {
		return idAllegatoPreProtocollazione;
	}
	public void setIdAllegatoPreProtocollazione(UUID idAllegatoPreProtocollazione) {
		this.idAllegatoPreProtocollazione = idAllegatoPreProtocollazione;
	}
	public String getChecksum()
	{
		return checksum;
	}
	public void setChecksum(String checksum)
	{
		this.checksum = checksum;
	}

	public Long getSize()
	{
		return size;
	}
	public void setSize(Long size)
	{
		this.size = size;
	}

	public UUID getId()
	{
		return this.id;
	}
	public void setId(UUID id)
	{
		this.id = id;
	}

	@JsonGetter("nome")
	public String getNomeFile()
	{
		return this.nomeFile;
	}
	@JsonSetter("nome")
	public void setNomeFile(String nomeFile)
	{
		this.nomeFile = nomeFile;
	}

	public String getFormatoFile()
	{
		return this.formatoFile;
	}
	public void setFormatoFile(String formatoFile)
	{
		this.formatoFile = formatoFile;
	}

	@JsonGetter("data")
	public Date getDataCaricamento()
	{
		return this.dataCaricamento;
	}
	@JsonSetter("data")
	public void setDataCaricamento(Date dataCaricamento)
	{
		this.dataCaricamento = dataCaricamento;
	}

	@JsonGetter("path")
	public String getPathCms()
	{
		return this.pathCms;
	}
	@JsonSetter("path")
	public void setPathCms(String pathCms)
	{
		this.pathCms = pathCms;
	}

	public String getIdCms()
	{
		return this.idCms;
	}
	public void setIdCms(String idCms)
	{
		this.idCms = idCms;
	}

	public UUID getPraticaId()
	{
		return this.praticaId;
	}
	public void setPraticaId(UUID praticaId)
	{
		this.praticaId = praticaId;
	}

	public String getDescrizione()
	{
		return this.descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}

	public List<String> getTipiContenuto()
	{
		return tipiContenuto;
	}
	public void setTipiContenuto(List<String> tipiContenuto)
	{
		this.tipiContenuto = tipiContenuto;
	}

	public Integer getIdIntegrazione()
	{
		return idIntegrazione;
	}
	public void setIdIntegrazione(Integer idIntegrazione)
	{
		this.idIntegrazione = idIntegrazione;
	}

	public Boolean getDeleted()
	{
		return deleted;
	}
	public void setDeleted(Boolean deleted)
	{
		this.deleted = deleted;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProtocollo() {
		return protocollo;
	}
	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}
	
	public String[] getNotificaA() {
		return notificaA;
	}
	public void setNotificaA(String[] notificaA) {
		this.notificaA = notificaA;
	}
	public String[] getVisualizzabiliDa() {
		return visualizzabiliDa;
	}
	public void setVisualizzabiliDa(String[] visualizzabiliDa) {
		this.visualizzabiliDa = visualizzabiliDa;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getInseritoDa() {
		return inseritoDa;
	}
	public void setInseritoDa(String inseritoDa) {
		this.inseritoDa = inseritoDa;
	}
	
	public String getIdDiogeneScheduler() {
		return idDiogeneScheduler;
	}
	public void setIdDiogeneScheduler(String idDiogeneScheduler) {
		this.idDiogeneScheduler = idDiogeneScheduler;
	}
	public Boolean isSigned() {
		return isSigned;
	}
	public void setSigned(Boolean isSigned) {
		this.isSigned = isSigned;
	}
	
	
	@Override
	public String titoloAttribute() {
		return titolo;
	}
	@Override
	public String descrizioneAttibute() {
		return descrizione;
	}
	
	@Override
	public String toString() {
		return "AllegatiDTO [id=" + id + ", nomeFile=" + nomeFile + ", formatoFile=" + formatoFile
				+ ", dataCaricamento=" + dataCaricamento + ", pathCms=" + pathCms + ", idCms=" + idCms + ", praticaId="
				+ praticaId + ", descrizione=" + descrizione + ", idIntegrazione=" + idIntegrazione + ", deleted="
				+ deleted + ", checksum=" + checksum + ", size=" + size + ", titolo=" + titolo
				+ ", idAllegatoPreProtocollazione=" + idAllegatoPreProtocollazione + ", idDiogeneScheduler="
				+ idDiogeneScheduler + ", type=" + type + ", protocollo=" + protocollo + ", visualizzabiliDa="
				+ Arrays.toString(visualizzabiliDa) + ", notificaA=" + Arrays.toString(notificaA) + ", ruolo=" + ruolo
				+ ", inseritoDa=" + inseritoDa + ", isSigned=" + isSigned + ", tipiContenuto=" + tipiContenuto + "]";
	}
	
	
	

}
