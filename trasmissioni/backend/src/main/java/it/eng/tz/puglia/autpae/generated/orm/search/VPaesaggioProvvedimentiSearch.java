package it.eng.tz.puglia.autpae.generated.orm.search;

import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table procedimenti_ambientali.v_paesaggio_provvedimenti
 */
public class VPaesaggioProvvedimentiSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 7025248389L;
    //COLUMN id_doc
    private String idDoc;
    //COLUMN id_pratica
    private String idPratica;
    //COLUMN codice_trasmissione
    private String codiceTrasmissione;
    //COLUMN oggetto
    private String oggetto;
    //COLUMN id_tipo_carica_documento
    private String idTipoCaricaDocumento;
    //COLUMN numero_protocollo_esterno
    private String numeroProtocolloEsterno;
    //COLUMN data_protocollo_esterno
    private String dataProtocolloEsterno;
    //COLUMN cmis_id
    private String cmisId;
    //COLUMN file_name
    private String fileName;
    //COLUMN hash_file
    private String hashFile;
    //COLUMN codice_fiscale_ente
    private String codiceFiscaleEnte;
    //COLUMN ente_id
    private String enteId;
    //COLUMN tipo
    private String tipo;
    //COLUMN id
    private String id;
    //COLUMN indice
    private String indice;
    //COLUMN cognome
    private String cognome;
    //COLUMN nome
    private String nome;
    //COLUMN codice_fiscale
    private String codiceFiscale;
    //COLUMN sesso
    private String sesso;
    //COLUMN data_nascita
    private String dataNascita;
    //COLUMN id_nazione_nascita
    private String idNazioneNascita;
    //COLUMN nazione_nascita
    private String nazioneNascita;
    //COLUMN id_comune_nascita
    private String idComuneNascita;
    //COLUMN comune_nascita
    private String comuneNascita;
    //COLUMN comune_nascita_estero
    private String comuneNascitaEstero;
    //COLUMN id_nazione_residenza
    private String idNazioneResidenza;
    //COLUMN nazione_residenza
    private String nazioneResidenza;
    //COLUMN id_comune_residenza
    private String idComuneResidenza;
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
    private String ditta;
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
    private String dataRilascioDocumento;
    //COLUMN data_scadenza_documento
    private String dataScadenzaDocumento;
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
    private String dataDocumentoIdentita;
    //COLUMN ente_rilascio_documento
    private String enteRilascioDocumento;
    //COLUMN date_create
    private String dateCreate;
    //COLUMN id_tipo_azienda
    private String idTipoAzienda;
    //COLUMN tipo_azienda
    private String tipoAzienda;
    //COLUMN c_ipa
    private String cIpa;
    //COLUMN c_uo
    private String cUo;
    //COLUMN id_tipo_doc
    private String idTipoDoc;
    
    private String idFascicoloPaesaggio;
    
    private List<UUID> idTipoCaricaDocumentoAmmessi;
    
    private boolean soloNonUtilizzati=true;

    public boolean isSoloNonUtilizzati() {
		return soloNonUtilizzati;
	}

	public void setSoloNonUtilizzati(boolean soloNonUtilizzati) {
		this.soloNonUtilizzati = soloNonUtilizzati;
	}

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

    public String getIdTipoCaricaDocumento(){
        return this.idTipoCaricaDocumento;
    }

    public void setIdTipoCaricaDocumento(String idTipoCaricaDocumento){
        this.idTipoCaricaDocumento = idTipoCaricaDocumento;
    }

    public String getNumeroProtocolloEsterno(){
        return this.numeroProtocolloEsterno;
    }

    public void setNumeroProtocolloEsterno(String numeroProtocolloEsterno){
        this.numeroProtocolloEsterno = numeroProtocolloEsterno;
    }

    public String getDataProtocolloEsterno(){
        return this.dataProtocolloEsterno;
    }

    public void setDataProtocolloEsterno(String dataProtocolloEsterno){
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

    public String getEnteId(){
        return this.enteId;
    }

    public void setEnteId(String enteId){
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

    public String getIndice(){
        return this.indice;
    }

    public void setIndice(String indice){
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

    public String getDataNascita(){
        return this.dataNascita;
    }

    public void setDataNascita(String dataNascita){
        this.dataNascita = dataNascita;
    }

    public String getIdNazioneNascita(){
        return this.idNazioneNascita;
    }

    public void setIdNazioneNascita(String idNazioneNascita){
        this.idNazioneNascita = idNazioneNascita;
    }

    public String getNazioneNascita(){
        return this.nazioneNascita;
    }

    public void setNazioneNascita(String nazioneNascita){
        this.nazioneNascita = nazioneNascita;
    }

    public String getIdComuneNascita(){
        return this.idComuneNascita;
    }

    public void setIdComuneNascita(String idComuneNascita){
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

    public String getIdNazioneResidenza(){
        return this.idNazioneResidenza;
    }

    public void setIdNazioneResidenza(String idNazioneResidenza){
        this.idNazioneResidenza = idNazioneResidenza;
    }

    public String getNazioneResidenza(){
        return this.nazioneResidenza;
    }

    public void setNazioneResidenza(String nazioneResidenza){
        this.nazioneResidenza = nazioneResidenza;
    }

    public String getIdComuneResidenza(){
        return this.idComuneResidenza;
    }

    public void setIdComuneResidenza(String idComuneResidenza){
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

    public String getDitta(){
        return this.ditta;
    }

    public void setDitta(String ditta){
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

    public String getDataRilascioDocumento(){
        return this.dataRilascioDocumento;
    }

    public void setDataRilascioDocumento(String dataRilascioDocumento){
        this.dataRilascioDocumento = dataRilascioDocumento;
    }

    public String getDataScadenzaDocumento(){
        return this.dataScadenzaDocumento;
    }

    public void setDataScadenzaDocumento(String dataScadenzaDocumento){
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

    public String getDataDocumentoIdentita(){
        return this.dataDocumentoIdentita;
    }

    public void setDataDocumentoIdentita(String dataDocumentoIdentita){
        this.dataDocumentoIdentita = dataDocumentoIdentita;
    }

    public String getEnteRilascioDocumento(){
        return this.enteRilascioDocumento;
    }

    public void setEnteRilascioDocumento(String enteRilascioDocumento){
        this.enteRilascioDocumento = enteRilascioDocumento;
    }

    public String getDateCreate(){
        return this.dateCreate;
    }

    public void setDateCreate(String dateCreate){
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

	public String getIdTipoDoc() {
		return idTipoDoc;
	}

	public void setIdTipoDoc(String idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}

	public String getIdFascicoloPaesaggio() {
		return idFascicoloPaesaggio;
	}

	public void setIdFascicoloPaesaggio(String idFascicoloPaesaggio) {
		this.idFascicoloPaesaggio = idFascicoloPaesaggio;
	}

	public List<UUID> getIdTipoCaricaDocumentoAmmessi() {
		return idTipoCaricaDocumentoAmmessi;
	}

	public void setIdTipoCaricaDocumentoAmmessi(List<UUID> idTipoCaricaDocumentoAmmessi) {
		this.idTipoCaricaDocumentoAmmessi = idTipoCaricaDocumentoAmmessi;
	}


}
