package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.subentro
 */
public class SubentroSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 6804208818L;
    //COLUMN id
    private String id;
    //COLUMN id_pratica
    private String idPratica;
    //COLUMN stato
    private String stato;
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
    //COLUMN pec
    private String pec;
    //COLUMN mail
    private String mail;
    //COLUMN date_create
    private String dateCreate;
    //COLUMN date_update
    private String dateUpdate;
    //COLUMN cmis_id_modulo
    private String cmisIdModulo;
    //COLUMN file_name_modulo
    private String fileNameModulo;
    //COLUMN cmis_id_sollevamento
    private String cmisIdSollevamento;
    //COLUMN file_name_sollevamento
    private String fileNameSollevamento;
    //COLUMN hash_modulo
    private String hashModulo;
    //COLUMN hash_sollevamento
    private String hashSollevamento;
    //COLUMN id_provincia_nascita
    private String idProvinciaNascita;
    //COLUMN provincia_nascita
    private String provinciaNascita;
    //COLUMN id_provincia_residenza
    private String idProvinciaResidenza;
    //COLUMN provincia_residenza
    private String provinciaResidenza;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(String idPratica){
        this.idPratica = idPratica;
    }

    public String getStato(){
        return this.stato;
    }

    public void setStato(String stato){
        this.stato = stato;
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

    public String getDateCreate(){
        return this.dateCreate;
    }

    public void setDateCreate(String dateCreate){
        this.dateCreate = dateCreate;
    }

    public String getDateUpdate(){
        return this.dateUpdate;
    }

    public void setDateUpdate(String dateUpdate){
        this.dateUpdate = dateUpdate;
    }

    public String getCmisIdModulo(){
        return this.cmisIdModulo;
    }

    public void setCmisIdModulo(String cmisIdModulo){
        this.cmisIdModulo = cmisIdModulo;
    }

    public String getFileNameModulo(){
        return this.fileNameModulo;
    }

    public void setFileNameModulo(String fileNameModulo){
        this.fileNameModulo = fileNameModulo;
    }

    public String getCmisIdSollevamento(){
        return this.cmisIdSollevamento;
    }

    public void setCmisIdSollevamento(String cmisIdSollevamento){
        this.cmisIdSollevamento = cmisIdSollevamento;
    }

    public String getFileNameSollevamento(){
        return this.fileNameSollevamento;
    }

    public void setFileNameSollevamento(String fileNameSollevamento){
        this.fileNameSollevamento = fileNameSollevamento;
    }

    public String getHashModulo(){
        return this.hashModulo;
    }

    public void setHashModulo(String hashModulo){
        this.hashModulo = hashModulo;
    }

    public String getHashSollevamento(){
        return this.hashSollevamento;
    }

    public void setHashSollevamento(String hashSollevamento){
        this.hashSollevamento = hashSollevamento;
    }
    
    public String getIdProvinciaNascita(){
        return this.idProvinciaNascita;
    }

    public void setIdProvinciaNascita(String idProvinciaNascita){
        this.idProvinciaNascita = idProvinciaNascita;
    }

    public String getProvinciaNascita(){
        return this.provinciaNascita;
    }

    public void setProvinciaNascita(String provinciaNascita){
        this.provinciaNascita = provinciaNascita;
    }
    
    public String getIdProvinciaResidenza(){
        return this.idProvinciaResidenza;
    }

    public void setIdProvinciaResidenza(String idProvinciaResidenza){
        this.idProvinciaResidenza = idProvinciaResidenza;
    }

    public String getProvinciaResidenza(){
        return this.provinciaResidenza;
    }

    public void setProvinciaResidenza(String provinciaResidenza){
        this.provinciaResidenza = provinciaResidenza;
    }
}
