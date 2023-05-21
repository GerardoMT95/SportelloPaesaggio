package it.eng.tz.puglia.autpae.generated.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;
import it.eng.tz.puglia.util.json.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * DTO for table procedimenti_ambientali.v_paesaggio_provvedimenti
 */
public class VPaesaggioProvvedimentiDTO implements Serializable{

    private static final long serialVersionUID = 4684840619L;
    //COLUMN id_doc
    private String idDoc;
    //COLUMN id_pratica
    private String idPratica;
	//COLUMN nome_procedimento,
    private String nomeProcedimento;
	//COLUMN nome_famiglia,
    private String nomeFamiglia;
	//COLUMN nome_autorita_procedente,
    private String nomeAutoritaProcedente;
	//COLUMN nome_soggetto_coinvolto,
    private String nomeSoggettoCoinvolto;
    //COLUMN descrizione_tipo_doc
    private String descrizioneTipoDoc;
    //COLUMN id_tipo_doc
    private String idTipoDoc;
    //COLUMN codice_trasmissione
    private String codiceTrasmissione;
    //COLUMN oggetto
    private String oggetto;
    //COLUMN id_tipo_carica_documento
    private UUID idTipoCaricaDocumento;
    //COLUMN numero_protocollo_esterno
    private String numeroProtocolloEsterno;
    //COLUMN data_protocollo_esterno
    private Timestamp dataProtocolloEsterno;
    //COLUMN cmis_id
    private String cmisId;
    //COLUMN file_name
    private String fileName;
    //COLUMN hash_file
    private String hashFile;
    //COLUMN codice_fiscale_ente
    private String codiceFiscaleEnte;
    //COLUMN ente_id
    private Integer enteId;
    //COLUMN tipo
    private String tipo;
    //COLUMN id
    private String id;
    //COLUMN indice
    private Short indice;
    //COLUMN cognome
    private String cognome;
    //COLUMN nome
    private String nome;
    //COLUMN codice_fiscale
    private String codiceFiscale;
    //COLUMN sesso
    private String sesso;
    //COLUMN data_nascita
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataNascita;
    //COLUMN id_nazione_nascita
    private Integer idNazioneNascita;
    //COLUMN nazione_nascita
    private String nazioneNascita;
    //COLUMN id_comune_nascita
    private Integer idComuneNascita;
    //COLUMN comune_nascita
    private String comuneNascita;
    //COLUMN comune_nascita_estero
    private String comuneNascitaEstero;
    //COLUMN id_nazione_residenza
    private Integer idNazioneResidenza;
    //COLUMN nazione_residenza
    private String nazioneResidenza;
    //COLUMN id_comune_residenza
    private Integer idComuneResidenza;
    //COLUMN comune_residenza
    private String comuneResidenza;
    //COLUMN comune_residenza_estero
    private String comuneResidenzaEstero;
    //COLUMN indirizzo_residenza
    private String indirizzoResidenza;
    //COLUMN civico_residenza
    private String civicoResidenza;
    //COLUMN cap_residenza
    private String capResidenza;
    //COLUMN telefono
    private String telefono;
    //COLUMN pec
    private String pec;
    //COLUMN mail
    private String mail;
    //COLUMN ditta
    private Boolean ditta;
    //COLUMN id_ruolo_azienda
    private String idRuoloAzienda;
    //COLUMN nome_ruolo_azienda
    private String nomeRuoloAzienda;
    //COLUMN altro_ruolo_azienda
    private String altroRuoloAzienda;
    //COLUMN piva_azienda
    private String pivaAzienda;
    //COLUMN codice_fiscale_azienda
    private String codiceFiscaleAzienda;
    //COLUMN id_tipo_documento
    private String idTipoDocumento;
    //COLUMN numero_documento
    private String numeroDocumento;
    //COLUMN data_rilascio_documento
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataRilascioDocumento;
    //COLUMN data_scadenza_documento
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataScadenzaDocumento;
    //COLUMN cmis_documento
    private String cmisDocumento;
    //COLUMN nome_documento
    private String nomeDocumento;
    //COLUMN titolarita
    private String titolarita;
    //COLUMN titolarita_altro
    private String titolaritaAltro;
    //COLUMN hash_documento
    private String hashDocumento;
    //COLUMN azienda
    private String azienda;
    //COLUMN data_documento_identita
    private Timestamp dataDocumentoIdentita;
    //COLUMN ente_rilascio_documento
    private String enteRilascioDocumento;
    //COLUMN date_create
    private Timestamp dateCreate;
    //COLUMN id_tipo_azienda
    private String idTipoAzienda;
    //COLUMN tipo_azienda
    private String tipoAzienda;
    //COLUMN c_ipa
    private String cIpa;
    //COLUMN c_uo
    private String cUo;

    public String getIdDoc(){
        return this.idDoc;
    }

    public void setIdDoc(String idDoc){
        this.idDoc = idDoc;
    }

    public String getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(String idPratica){
        this.idPratica = idPratica;
    }

    public String getCodiceTrasmissione(){
        return this.codiceTrasmissione;
    }

    public void setCodiceTrasmissione(String codiceTrasmissione){
        this.codiceTrasmissione = codiceTrasmissione;
    }

    public String getOggetto(){
        return this.oggetto;
    }

    public void setOggetto(String oggetto){
        this.oggetto = oggetto;
    }

    public UUID getIdTipoCaricaDocumento(){
        return this.idTipoCaricaDocumento;
    }

    public void setIdTipoCaricaDocumento(UUID idTipoCaricaDocumento){
        this.idTipoCaricaDocumento = idTipoCaricaDocumento;
    }

    public String getNumeroProtocolloEsterno(){
        return this.numeroProtocolloEsterno;
    }

    public void setNumeroProtocolloEsterno(String numeroProtocolloEsterno){
        this.numeroProtocolloEsterno = numeroProtocolloEsterno;
    }

    public Timestamp getDataProtocolloEsterno(){
        return this.dataProtocolloEsterno;
    }

    public void setDataProtocolloEsterno(Timestamp dataProtocolloEsterno){
        this.dataProtocolloEsterno = dataProtocolloEsterno;
    }

    public String getCmisId(){
        return this.cmisId;
    }

    public void setCmisId(String cmisId){
        this.cmisId = cmisId;
    }

    public String getFileName(){
        return this.fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getHashFile(){
        return this.hashFile;
    }

    public void setHashFile(String hashFile){
        this.hashFile = hashFile;
    }

    public String getCodiceFiscaleEnte(){
        return this.codiceFiscaleEnte;
    }

    public void setCodiceFiscaleEnte(String codiceFiscaleEnte){
        this.codiceFiscaleEnte = codiceFiscaleEnte;
    }

    public Integer getEnteId(){
        return this.enteId;
    }

    public void setEnteId(Integer enteId){
        this.enteId = enteId;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public Short getIndice(){
        return this.indice;
    }

    public void setIndice(Short indice){
        this.indice = indice;
    }

    public String getCognome(){
        return this.cognome;
    }

    public void setCognome(String cognome){
        this.cognome = cognome;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCodiceFiscale(){
        return this.codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale){
        this.codiceFiscale = codiceFiscale;
    }

    public String getSesso(){
        return this.sesso;
    }

    public void setSesso(String sesso){
        this.sesso = sesso;
    }

    public Date getDataNascita(){
        return this.dataNascita;
    }

    public void setDataNascita(Date dataNascita){
        this.dataNascita = dataNascita;
    }

    public Integer getIdNazioneNascita(){
        return this.idNazioneNascita;
    }

    public void setIdNazioneNascita(Integer idNazioneNascita){
        this.idNazioneNascita = idNazioneNascita;
    }

    public String getNazioneNascita(){
        return this.nazioneNascita;
    }

    public void setNazioneNascita(String nazioneNascita){
        this.nazioneNascita = nazioneNascita;
    }

    public Integer getIdComuneNascita(){
        return this.idComuneNascita;
    }

    public void setIdComuneNascita(Integer idComuneNascita){
        this.idComuneNascita = idComuneNascita;
    }

    public String getComuneNascita(){
        return this.comuneNascita;
    }

    public void setComuneNascita(String comuneNascita){
        this.comuneNascita = comuneNascita;
    }

    public String getComuneNascitaEstero(){
        return this.comuneNascitaEstero;
    }

    public void setComuneNascitaEstero(String comuneNascitaEstero){
        this.comuneNascitaEstero = comuneNascitaEstero;
    }

    public Integer getIdNazioneResidenza(){
        return this.idNazioneResidenza;
    }

    public void setIdNazioneResidenza(Integer idNazioneResidenza){
        this.idNazioneResidenza = idNazioneResidenza;
    }

    public String getNazioneResidenza(){
        return this.nazioneResidenza;
    }

    public void setNazioneResidenza(String nazioneResidenza){
        this.nazioneResidenza = nazioneResidenza;
    }

    public Integer getIdComuneResidenza(){
        return this.idComuneResidenza;
    }

    public void setIdComuneResidenza(Integer idComuneResidenza){
        this.idComuneResidenza = idComuneResidenza;
    }

    public String getComuneResidenza(){
        return this.comuneResidenza;
    }

    public void setComuneResidenza(String comuneResidenza){
        this.comuneResidenza = comuneResidenza;
    }

    public String getComuneResidenzaEstero(){
        return this.comuneResidenzaEstero;
    }

    public void setComuneResidenzaEstero(String comuneResidenzaEstero){
        this.comuneResidenzaEstero = comuneResidenzaEstero;
    }

    public String getIndirizzoResidenza(){
        return this.indirizzoResidenza;
    }

    public void setIndirizzoResidenza(String indirizzoResidenza){
        this.indirizzoResidenza = indirizzoResidenza;
    }

    public String getCivicoResidenza(){
        return this.civicoResidenza;
    }

    public void setCivicoResidenza(String civicoResidenza){
        this.civicoResidenza = civicoResidenza;
    }

    public String getCapResidenza(){
        return this.capResidenza;
    }

    public void setCapResidenza(String capResidenza){
        this.capResidenza = capResidenza;
    }

    public String getTelefono(){
        return this.telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public String getPec(){
        return this.pec;
    }

    public void setPec(String pec){
        this.pec = pec;
    }

    public String getMail(){
        return this.mail;
    }

    public void setMail(String mail){
        this.mail = mail;
    }

    public Boolean getDitta(){
        return this.ditta;
    }

    public void setDitta(Boolean ditta){
        this.ditta = ditta;
    }

    public String getIdRuoloAzienda(){
        return this.idRuoloAzienda;
    }

    public void setIdRuoloAzienda(String idRuoloAzienda){
        this.idRuoloAzienda = idRuoloAzienda;
    }

    public String getNomeRuoloAzienda(){
        return this.nomeRuoloAzienda;
    }

    public void setNomeRuoloAzienda(String nomeRuoloAzienda){
        this.nomeRuoloAzienda = nomeRuoloAzienda;
    }

    public String getAltroRuoloAzienda(){
        return this.altroRuoloAzienda;
    }

    public void setAltroRuoloAzienda(String altroRuoloAzienda){
        this.altroRuoloAzienda = altroRuoloAzienda;
    }

    public String getPivaAzienda(){
        return this.pivaAzienda;
    }

    public void setPivaAzienda(String pivaAzienda){
        this.pivaAzienda = pivaAzienda;
    }

    public String getCodiceFiscaleAzienda(){
        return this.codiceFiscaleAzienda;
    }

    public void setCodiceFiscaleAzienda(String codiceFiscaleAzienda){
        this.codiceFiscaleAzienda = codiceFiscaleAzienda;
    }

    public String getIdTipoDocumento(){
        return this.idTipoDocumento;
    }

    public void setIdTipoDocumento(String idTipoDocumento){
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNumeroDocumento(){
        return this.numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento){
        this.numeroDocumento = numeroDocumento;
    }

    public Date getDataRilascioDocumento(){
        return this.dataRilascioDocumento;
    }

    public void setDataRilascioDocumento(Date dataRilascioDocumento){
        this.dataRilascioDocumento = dataRilascioDocumento;
    }

    public Date getDataScadenzaDocumento(){
        return this.dataScadenzaDocumento;
    }

    public void setDataScadenzaDocumento(Date dataScadenzaDocumento){
        this.dataScadenzaDocumento = dataScadenzaDocumento;
    }

    public String getCmisDocumento(){
        return this.cmisDocumento;
    }

    public void setCmisDocumento(String cmisDocumento){
        this.cmisDocumento = cmisDocumento;
    }

    public String getNomeDocumento(){
        return this.nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento){
        this.nomeDocumento = nomeDocumento;
    }

    public String getTitolarita(){
        return this.titolarita;
    }

    public void setTitolarita(String titolarita){
        this.titolarita = titolarita;
    }

    public String getTitolaritaAltro(){
        return this.titolaritaAltro;
    }

    public void setTitolaritaAltro(String titolaritaAltro){
        this.titolaritaAltro = titolaritaAltro;
    }

    public String getHashDocumento(){
        return this.hashDocumento;
    }

    public void setHashDocumento(String hashDocumento){
        this.hashDocumento = hashDocumento;
    }

    public String getAzienda(){
        return this.azienda;
    }

    public void setAzienda(String azienda){
        this.azienda = azienda;
    }

    public Timestamp getDataDocumentoIdentita(){
        return this.dataDocumentoIdentita;
    }

    public void setDataDocumentoIdentita(Timestamp dataDocumentoIdentita){
        this.dataDocumentoIdentita = dataDocumentoIdentita;
    }

    public String getEnteRilascioDocumento(){
        return this.enteRilascioDocumento;
    }

    public void setEnteRilascioDocumento(String enteRilascioDocumento){
        this.enteRilascioDocumento = enteRilascioDocumento;
    }

    public Timestamp getDateCreate(){
        return this.dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate){
        this.dateCreate = dateCreate;
    }

    public String getIdTipoAzienda(){
        return this.idTipoAzienda;
    }

    public void setIdTipoAzienda(String idTipoAzienda){
        this.idTipoAzienda = idTipoAzienda;
    }

    public String getTipoAzienda(){
        return this.tipoAzienda;
    }

    public void setTipoAzienda(String tipoAzienda){
        this.tipoAzienda = tipoAzienda;
    }

    public String getCIpa(){
        return this.cIpa;
    }

    public void setCIpa(String cIpa){
        this.cIpa = cIpa;
    }

    public String getCUo(){
        return this.cUo;
    }

    public void setCUo(String cUo){
        this.cUo = cUo;
    }

	public String getNomeProcedimento() {
		return nomeProcedimento;
	}

	public void setNomeProcedimento(String nomeProcedimento) {
		this.nomeProcedimento = nomeProcedimento;
	}

	public String getNomeFamiglia() {
		return nomeFamiglia;
	}

	public void setNomeFamiglia(String nomeFamiglia) {
		this.nomeFamiglia = nomeFamiglia;
	}

	public String getNomeAutoritaProcedente() {
		return nomeAutoritaProcedente;
	}

	public void setNomeAutoritaProcedente(String nomeAutoritaProcedente) {
		this.nomeAutoritaProcedente = nomeAutoritaProcedente;
	}

	public String getNomeSoggettoCoinvolto() {
		return nomeSoggettoCoinvolto;
	}

	public void setNomeSoggettoCoinvolto(String nomeSoggettoCoinvolto) {
		this.nomeSoggettoCoinvolto = nomeSoggettoCoinvolto;
	}

	public String getDescrizioneTipoDoc() {
		return descrizioneTipoDoc;
	}

	public void setDescrizioneTipoDoc(String descrizioneTipoDoc) {
		this.descrizioneTipoDoc = descrizioneTipoDoc;
	}

	public String getIdTipoDoc() {
		return idTipoDoc;
	}

	public void setIdTipoDoc(String idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}


}
