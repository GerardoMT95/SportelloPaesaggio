package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.referenti
 */
public class ReferentiDTO implements Serializable{

    private static final long serialVersionUID = 5870421355L;
    //COLUMN id
    private long id;
    //COLUMN tipo_referente
    private String tipoReferente;
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN cognome
    private String cognome;
    //COLUMN nome
    private String nome;
    //COLUMN codice_fiscale
    private String codiceFiscale;
    //COLUMN comune_nascita
    private Integer comuneNascita;
    //COLUMN provincia_nascita
    private Integer provinciaNascita;
    //COLUMN nazionalita_nascita
    private Integer nazionalitaNascita;
    //COLUMN comune_nascita_estero
    private String comuneNascitaEstero;
    //COLUMN provincia_nascita_estero
    private String provinciaNascitaEstero;
    //COLUMN data_nascita
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataNascita;
    //COLUMN sesso
    private String sesso;
    //COLUMN indirizzo_residenza
    private String indirizzoResidenza;
    //COLUMN civico_residenza
    private String civicoResidenza;
    //COLUMN cap_residenza
    private String capResidenza;
    //COLUMN comune_residenza
    private Integer comuneResidenza;
    //COLUMN provincia_residenza
    private Integer provinciaResidenza;
    //COLUMN nazionalita_residenza
    private Integer nazionalitaResidenza;
    //COLUMN comune_residenza_estero
    private String comuneResidenzaEstero;
    //COLUMN provincia_residenza_estero
    private String provinciaResidenzaEstero;
    //COLUMN pec
    private String pec;
    //COLUMN mail
    private String mail;
    //COLUMN telefono
    private String telefono;
    //COLUMN cellulare
    private String cellulare;
    //COLUMN fax
    private String fax;
    //COLUMN ditta
    private Boolean ditta;
    //COLUMN ditta_ente
    private String dittaEnte;
    //COLUMN ditta_cf
    private String dittaCf;
    //COLUMN ditta_partita_iva
    private String dittaPartitaIva;
    //COLUMN ditta_qualita_di
    private Integer dittaQualitaDi;
    //COLUMN ditta_qualita_altro
    private String dittaQualitaAltro;
    //COLUMN tecnico_studio_indirizzo
    private String tecnicoStudioIndirizzo;
    //COLUMN tecnico_studio_civico
    private String tecnicoStudioCivico;
    //COLUMN tecnico_studio_cap
    private String tecnicoStudioCap;
    //COLUMN tecnico_studio_comune
    private Integer tecnicoStudioComune;
    //COLUMN tecnico_studio_provincia
    private Integer tecnicoStudioProvincia;
    //COLUMN tecnico_studio_nazionalita
    private Integer tecnicoStudioNazionalita;
    //COLUMN tecnico_studio_comune_estero
    private String tecnicoStudioComuneEstero;
    //COLUMN tecnico_studio_provincia_estero
    private String tecnicoStudioProvinciaEstero;
    //COLUMN tecnico_ord_collegio
    private String tecnicoOrdCollegio;
    //COLUMN tecnico_collegio_sede
    private String tecnicoCollegioSede;
    //COLUMN tecnico_collegio_n_iscr
    private String tecnicoCollegioNIscr;
    //COLUMN nazionalita_residenza_name
    private String nazionalitaResidenzaName;
    //COLUMN provincia_residenza_name
    private String provinciaResidenzaName;
    //COLUMN comune_residenza_name
    private String comuneResidenzaName;
    //COLUMN nazionalita_nascita_name
    private String nazionalitaNascitaName;
    //COLUMN provincia_nascita_name
    private String provinciaNascitaName;
    //COLUMN comune_nascita_name
    private String comuneNascitaName;
    //COLUMN tecnico_studio_nazionalita_name
    private String tecnicoStudioNazionalitaName;
    //COLUMN tecnico_studio_provincia_name
    private String tecnicoStudioProvinciaName;
    //COLUMN tecnico_studio_comune_name
    private String tecnicoStudioComuneName;
    //COLUMN titolarita_id
    private Integer titolaritaId;
    //COLUMN specifica_titolarita
    private String specificaTitolarita;
    //COLUMN titolarita_esclusiva
    private String titolaritaEsclusiva;

    private String codiceIpa;
    private String idTipoAzienda;
    private String tipoAzienda;
    private String dittaCodiceUO;
    
    

    public Integer getTitolaritaId() {
		return this.titolaritaId;
	}

	public void setTitolaritaId(final Integer titolaritaId) {
		this.titolaritaId = titolaritaId;
	}

	public String getSpecificaTitolarita() {
		return this.specificaTitolarita;
	}

	public void setSpecificaTitolarita(final String specificaTitolarita) {
		this.specificaTitolarita = specificaTitolarita;
	}

	public String getTitolaritaEsclusiva() {
		return this.titolaritaEsclusiva;
	}

	public void setTitolaritaEsclusiva(final String titolaritaEsclusiva) {
		this.titolaritaEsclusiva = titolaritaEsclusiva;
	}

	public long getId(){
        return this.id;
    }

    public void setId(final long id){
        this.id = id;
    }

    public String getTipoReferente(){
        return this.tipoReferente;
    }

    public void setTipoReferente(final String tipoReferente){
        this.tipoReferente = tipoReferente;
    }

    public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(final UUID praticaId){
        this.praticaId = praticaId;
    }

    public String getCognome(){
        return this.cognome;
    }

    public void setCognome(final String cognome){
        this.cognome = cognome;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(final String nome){
        this.nome = nome;
    }

    public String getCodiceFiscale(){
        return this.codiceFiscale;
    }

    public void setCodiceFiscale(final String codiceFiscale){
        this.codiceFiscale = codiceFiscale;
    }

    public Integer getComuneNascita(){
        return this.comuneNascita;
    }

    public void setComuneNascita(final Integer comuneNascita){
        this.comuneNascita = comuneNascita;
    }

    public Integer getProvinciaNascita(){
        return this.provinciaNascita;
    }

    public void setProvinciaNascita(final Integer provinciaNascita){
        this.provinciaNascita = provinciaNascita;
    }

    public Integer getNazionalitaNascita(){
        return this.nazionalitaNascita;
    }

    public void setNazionalitaNascita(final Integer nazionalitaNascita){
        this.nazionalitaNascita = nazionalitaNascita;
    }

    public String getComuneNascitaEstero(){
        return this.comuneNascitaEstero;
    }

    public void setComuneNascitaEstero(final String comuneNascitaEstero){
        this.comuneNascitaEstero = comuneNascitaEstero;
    }

    public String getProvinciaNascitaEstero(){
        return this.provinciaNascitaEstero;
    }

    public void setProvinciaNascitaEstero(final String provinciaNascitaEstero){
        this.provinciaNascitaEstero = provinciaNascitaEstero;
    }

    public Date getDataNascita(){
        return this.dataNascita;
    }

    public void setDataNascita(final Date dataNascita){
        this.dataNascita = dataNascita;
    }

    public String getSesso(){
        return this.sesso;
    }

    public void setSesso(final String sesso){
        this.sesso = sesso;
    }

    public String getIndirizzoResidenza(){
        return this.indirizzoResidenza;
    }

    public void setIndirizzoResidenza(final String indirizzoResidenza){
        this.indirizzoResidenza = indirizzoResidenza;
    }

    public String getCivicoResidenza(){
        return this.civicoResidenza;
    }

    public void setCivicoResidenza(final String civicoResidenza){
        this.civicoResidenza = civicoResidenza;
    }

    public String getCapResidenza(){
        return this.capResidenza;
    }

    public void setCapResidenza(final String capResidenza){
        this.capResidenza = capResidenza;
    }

    public Integer getComuneResidenza(){
        return this.comuneResidenza;
    }

    public void setComuneResidenza(final Integer comuneResidenza){
        this.comuneResidenza = comuneResidenza;
    }

    public Integer getProvinciaResidenza(){
        return this.provinciaResidenza;
    }

    public void setProvinciaResidenza(final Integer provinciaResidenza){
        this.provinciaResidenza = provinciaResidenza;
    }

    public Integer getNazionalitaResidenza(){
        return this.nazionalitaResidenza;
    }

    public void setNazionalitaResidenza(final Integer nazionalitaResidenza){
        this.nazionalitaResidenza = nazionalitaResidenza;
    }

    public String getComuneResidenzaEstero(){
        return this.comuneResidenzaEstero;
    }

    public void setComuneResidenzaEstero(final String comuneResidenzaEstero){
        this.comuneResidenzaEstero = comuneResidenzaEstero;
    }

    public String getProvinciaResidenzaEstero(){
        return this.provinciaResidenzaEstero;
    }

    public void setProvinciaResidenzaEstero(final String provinciaResidenzaEstero){
        this.provinciaResidenzaEstero = provinciaResidenzaEstero;
    }

    public String getPec(){
        return this.pec;
    }

    public void setPec(final String pec){
        this.pec = pec;
    }

    public String getMail(){
        return this.mail;
    }

    public void setMail(final String mail){
        this.mail = mail;
    }

    public String getTelefono(){
        return this.telefono;
    }

    public void setTelefono(final String telefono){
        this.telefono = telefono;
    }

    public String getCellulare(){
        return this.cellulare;
    }

    public void setCellulare(final String cellulare){
        this.cellulare = cellulare;
    }

    public String getFax(){
        return this.fax;
    }

    public void setFax(final String fax){
        this.fax = fax;
    }

    public Boolean getDitta(){
        return this.ditta;
    }

    public void setDitta(final Boolean ditta){
        this.ditta = ditta;
    }

    public String getDittaEnte(){
        return this.dittaEnte;
    }

    public void setDittaEnte(final String dittaEnte){
        this.dittaEnte = dittaEnte;
    }

    public String getDittaCf(){
        return this.dittaCf;
    }

    public void setDittaCf(final String dittaCf){
        this.dittaCf = dittaCf;
    }

    public String getDittaPartitaIva(){
        return this.dittaPartitaIva;
    }

    public void setDittaPartitaIva(final String dittaPartitaIva){
        this.dittaPartitaIva = dittaPartitaIva;
    }

    public Integer getDittaQualitaDi(){
        return this.dittaQualitaDi;
    }

    public void setDittaQualitaDi(final Integer dittaQualitaDi){
        this.dittaQualitaDi = dittaQualitaDi;
    }

    public String getDittaQualitaAltro(){
        return this.dittaQualitaAltro;
    }

    public void setDittaQualitaAltro(final String dittaQualitaAltro){
        this.dittaQualitaAltro = dittaQualitaAltro;
    }

    public String getTecnicoStudioIndirizzo(){
        return this.tecnicoStudioIndirizzo;
    }

    public void setTecnicoStudioIndirizzo(final String tecnicoStudioIndirizzo){
        this.tecnicoStudioIndirizzo = tecnicoStudioIndirizzo;
    }

    public String getTecnicoStudioCivico(){
        return this.tecnicoStudioCivico;
    }

    public void setTecnicoStudioCivico(final String tecnicoStudioCivico){
        this.tecnicoStudioCivico = tecnicoStudioCivico;
    }

    public String getTecnicoStudioCap(){
        return this.tecnicoStudioCap;
    }

    public void setTecnicoStudioCap(final String tecnicoStudioCap){
        this.tecnicoStudioCap = tecnicoStudioCap;
    }

    public Integer getTecnicoStudioComune(){
        return this.tecnicoStudioComune;
    }

    public void setTecnicoStudioComune(final Integer tecnicoStudioComune){
        this.tecnicoStudioComune = tecnicoStudioComune;
    }

    public Integer getTecnicoStudioProvincia(){
        return this.tecnicoStudioProvincia;
    }

    public void setTecnicoStudioProvincia(final Integer tecnicoStudioProvincia){
        this.tecnicoStudioProvincia = tecnicoStudioProvincia;
    }

    public Integer getTecnicoStudioNazionalita(){
        return this.tecnicoStudioNazionalita;
    }

    public void setTecnicoStudioNazionalita(final Integer tecnicoStudioNazionalita){
        this.tecnicoStudioNazionalita = tecnicoStudioNazionalita;
    }

    public String getTecnicoStudioComuneEstero(){
        return this.tecnicoStudioComuneEstero;
    }

    public void setTecnicoStudioComuneEstero(final String tecnicoStudioComuneEstero){
        this.tecnicoStudioComuneEstero = tecnicoStudioComuneEstero;
    }

    public String getTecnicoStudioProvinciaEstero(){
        return this.tecnicoStudioProvinciaEstero;
    }

    public void setTecnicoStudioProvinciaEstero(final String tecnicoStudioProvinciaEstero){
        this.tecnicoStudioProvinciaEstero = tecnicoStudioProvinciaEstero;
    }

    public String getTecnicoOrdCollegio(){
        return this.tecnicoOrdCollegio;
    }

    public void setTecnicoOrdCollegio(final String tecnicoOrdCollegio){
        this.tecnicoOrdCollegio = tecnicoOrdCollegio;
    }

    public String getTecnicoCollegioSede(){
        return this.tecnicoCollegioSede;
    }

    public void setTecnicoCollegioSede(final String tecnicoCollegioSede){
        this.tecnicoCollegioSede = tecnicoCollegioSede;
    }

    public String getTecnicoCollegioNIscr(){
        return this.tecnicoCollegioNIscr;
    }

    public void setTecnicoCollegioNIscr(final String tecnicoCollegioNIscr){
        this.tecnicoCollegioNIscr = tecnicoCollegioNIscr;
    }

    public String getNazionalitaResidenzaName(){
        return this.nazionalitaResidenzaName;
    }

    public void setNazionalitaResidenzaName(final String nazionalitaResidenzaName){
        this.nazionalitaResidenzaName = nazionalitaResidenzaName;
    }

    public String getProvinciaResidenzaName(){
        return this.provinciaResidenzaName;
    }

    public void setProvinciaResidenzaName(final String provinciaResidenzaName){
        this.provinciaResidenzaName = provinciaResidenzaName;
    }

    public String getComuneResidenzaName(){
        return this.comuneResidenzaName;
    }

    public void setComuneResidenzaName(final String comuneResidenzaName){
        this.comuneResidenzaName = comuneResidenzaName;
    }

    public String getNazionalitaNascitaName(){
        return this.nazionalitaNascitaName;
    }

    public void setNazionalitaNascitaName(final String nazionalitaNascitaName){
        this.nazionalitaNascitaName = nazionalitaNascitaName;
    }

    public String getProvinciaNascitaName(){
        return this.provinciaNascitaName;
    }

    public void setProvinciaNascitaName(final String provinciaNascitaName){
        this.provinciaNascitaName = provinciaNascitaName;
    }

    public String getComuneNascitaName(){
        return this.comuneNascitaName;
    }

    public void setComuneNascitaName(final String comuneNascitaName){
        this.comuneNascitaName = comuneNascitaName;
    }

    public String getTecnicoStudioNazionalitaName(){
        return this.tecnicoStudioNazionalitaName;
    }

    public void setTecnicoStudioNazionalitaName(final String tecnicoStudioNazionalitaName){
        this.tecnicoStudioNazionalitaName = tecnicoStudioNazionalitaName;
    }

    public String getTecnicoStudioProvinciaName(){
        return this.tecnicoStudioProvinciaName;
    }

    public void setTecnicoStudioProvinciaName(final String tecnicoStudioProvinciaName){
        this.tecnicoStudioProvinciaName = tecnicoStudioProvinciaName;
    }

    public String getTecnicoStudioComuneName(){
        return this.tecnicoStudioComuneName;
    }

    public void setTecnicoStudioComuneName(final String tecnicoStudioComuneName){
        this.tecnicoStudioComuneName = tecnicoStudioComuneName;
    }

	/**
	 * @return the codiceIpa
	 */
	public String getCodiceIpa() {
		return this.codiceIpa;
	}

	/**
	 * @param codiceIpa the codiceIpa to set
	 */
	public void setCodiceIpa(final String codiceIpa) {
		this.codiceIpa = codiceIpa;
	}

	/**
	 * @return the idTipoAzienda
	 */
	public String getIdTipoAzienda() {
		return this.idTipoAzienda;
	}

	/**
	 * @param idTipoAzienda the idTipoAzienda to set
	 */
	public void setIdTipoAzienda(final String idTipoAzienda) {
		this.idTipoAzienda = idTipoAzienda;
	}

	/**
	 * @return the tipoAzienda
	 */
	public String getTipoAzienda() {
		return tipoAzienda;
	}

	/**
	 * @param tipoAzienda the tipoAzienda to set
	 */
	public void setTipoAzienda(String tipoAzienda) {
		this.tipoAzienda = tipoAzienda;
	}

	public String getDittaCodiceUO()
	{
	    return dittaCodiceUO;
	}

	public void setDittaCodiceUO(String dittaCodiceUO)
	{
	    this.dittaCodiceUO = dittaCodiceUO;
	}


}
