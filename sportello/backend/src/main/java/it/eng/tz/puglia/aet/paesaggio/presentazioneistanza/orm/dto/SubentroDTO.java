package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SubentroDto;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.subentro
 */
public class SubentroDTO implements Serializable{

    private static final long serialVersionUID = 7888483516L;
    //COLUMN id
    private UUID id;
    //COLUMN id_pratica
    private UUID idPratica;
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
    //COLUMN pec
    private String pec;
    //COLUMN mail
    private String mail;
    //COLUMN date_create
    private Timestamp dateCreate;
    //COLUMN date_update
    private Timestamp dateUpdate;
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
    private Integer idProvinciaNascita;
    //COLUMN provincia_nascita
    private String provinciaNascita;
    //COLUMN id_provincia_residenza
    private Integer idProvinciaResidenza;
    //COLUMN provincia_residenza
    private String provinciaResidenza;

    public UUID getId(){
        return this.id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public UUID getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(UUID idPratica){
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

    public Timestamp getDateCreate(){
        return this.dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate){
        this.dateCreate = dateCreate;
    }

    public Timestamp getDateUpdate(){
        return this.dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate){
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
    
    public Integer getIdProvinciaNascita(){
        return this.idProvinciaNascita;
    }

    public void setIdProvinciaNascita(Integer idProvinciaNascita){
        this.idProvinciaNascita = idProvinciaNascita;
    }

    public String getProvinciaNascita(){
        return this.provinciaNascita;
    }

    public void setProvinciaNascita(String provinciaNascita){
        this.provinciaNascita = provinciaNascita;
    }
    
    public Integer getIdProvinciaResidenza(){
        return this.idProvinciaResidenza;
    }

    public void setIdProvinciaResidenza(Integer idProvinciaResidenza){
        this.idProvinciaResidenza = idProvinciaResidenza;
    }

    public String getProvinciaResidenza(){
        return this.provinciaResidenza;
    }

    public void setProvinciaResidenza(String provinciaResidenza){
        this.provinciaResidenza = provinciaResidenza;
    }

    public SubentroDTO() {}
    
    public SubentroDTO(SubentroDto entity) {
		this.setIdPratica(entity.getId());
		this.setCognome(entity.getCognome());
		this.setNome(entity.getNome());
		this.setCodiceFiscale(entity.getCodiceFiscale());
		this.setSesso(entity.getSesso());
		this.setDataNascita(entity.getDataNascita());
		this.setIdNazioneNascita(entity.getIdNazioneNascita());
		this.setNazioneNascita(entity.getNazioneNascita());
		this.setIdComuneNascita(entity.getIdComuneNascita());
		this.setComuneNascita(entity.getComuneNascita());
		this.setComuneNascitaEstero(entity.getComuneNascitaEstero());
		this.setIdNazioneResidenza(entity.getIdNazioneResidenza());
		this.setNazioneResidenza(entity.getNazioneResidenza());
		this.setIdComuneResidenza(entity.getIdComuneResidenza());
		this.setComuneResidenza(entity.getComuneResidenza());
		this.setComuneResidenzaEstero(entity.getComuneResidenzaEstero());
		this.setIndirizzoResidenza(entity.getIndirizzoResidenza());
		this.setCivicoResidenza(entity.getCivicoResidenza());
		this.setCapResidenza(entity.getCapResidenza());
		this.setPec(entity.getPec());
		this.setMail(entity.getMail());
		this.setDateCreate(entity.getDateCreate());
		this.setCmisIdModulo(entity.getCmisIdModulo());
		this.setFileNameModulo(entity.getFileNameModulo());
		this.setHashModulo(entity.getHashModulo());
		this.setCmisIdSollevamento(entity.getCmisIdSollevamento());
		this.setFileNameSollevamento(entity.getFileNameSollevamento());
		this.setHashSollevamento(entity.getHashSollevamento());
		this.setProvinciaNascita(entity.getProvinciaNascita());
		this.setIdProvinciaNascita(entity.getIdProvinciaNascita());
		this.setProvinciaResidenza(entity.getProvinciaResidenza());
		this.setIdProvinciaResidenza(entity.getIdProvinciaResidenza());
	}


}
