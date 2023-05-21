package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.referenti
 */
public class ReferentiSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 7274196091L;
    //COLUMN id
    private String id;
    //COLUMN tipo_referente
    private String tipoReferente;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN cognome
    private String cognome;
    //COLUMN nome
    private String nome;
    //COLUMN codice_fiscale
    private String codiceFiscale;
    //COLUMN comune_nascita
    private String comuneNascita;
    //COLUMN provincia_nascita
    private String provinciaNascita;
    //COLUMN nazionalita_nascita
    private String nazionalitaNascita;
    //COLUMN comune_nascita_estero
    private String comuneNascitaEstero;
    //COLUMN provincia_nascita_estero
    private String provinciaNascitaEstero;
    //COLUMN data_nascita
    private String dataNascita;
    //COLUMN sesso
    private String sesso;
    //COLUMN indirizzo_residenza
    private String indirizzoResidenza;
    //COLUMN civico_residenza
    private String civicoResidenza;
    //COLUMN cap_residenza
    private String capResidenza;
    //COLUMN comune_residenza
    private String comuneResidenza;
    //COLUMN provincia_residenza
    private String provinciaResidenza;
    //COLUMN nazionalita_residenza
    private String nazionalitaResidenza;
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
    private String ditta;
    //COLUMN ditta_ente
    private String dittaEnte;
    //COLUMN ditta_cf
    private String dittaCf;
    //COLUMN ditta_partita_iva
    private String dittaPartitaIva;
    //COLUMN ditta_qualita_di
    private String dittaQualitaDi;
    //COLUMN ditta_qualita_altro
    private String dittaQualitaAltro;
    //COLUMN tecnico_studio_indirizzo
    private String tecnicoStudioIndirizzo;
    //COLUMN tecnico_studio_civico
    private String tecnicoStudioCivico;
    //COLUMN tecnico_studio_cap
    private String tecnicoStudioCap;
    //COLUMN tecnico_studio_comune
    private String tecnicoStudioComune;
    //COLUMN tecnico_studio_provincia
    private String tecnicoStudioProvincia;
    //COLUMN tecnico_studio_nazionalita
    private String tecnicoStudioNazionalita;
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

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTipoReferente(){
        return this.tipoReferente;
    }

    public void setTipoReferente(String tipoReferente){
        this.tipoReferente = tipoReferente;
    }

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
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

    public String getComuneNascita(){
        return this.comuneNascita;
    }

    public void setComuneNascita(String comuneNascita){
        this.comuneNascita = comuneNascita;
    }

    public String getProvinciaNascita(){
        return this.provinciaNascita;
    }

    public void setProvinciaNascita(String provinciaNascita){
        this.provinciaNascita = provinciaNascita;
    }

    public String getNazionalitaNascita(){
        return this.nazionalitaNascita;
    }

    public void setNazionalitaNascita(String nazionalitaNascita){
        this.nazionalitaNascita = nazionalitaNascita;
    }

    public String getComuneNascitaEstero(){
        return this.comuneNascitaEstero;
    }

    public void setComuneNascitaEstero(String comuneNascitaEstero){
        this.comuneNascitaEstero = comuneNascitaEstero;
    }

    public String getProvinciaNascitaEstero(){
        return this.provinciaNascitaEstero;
    }

    public void setProvinciaNascitaEstero(String provinciaNascitaEstero){
        this.provinciaNascitaEstero = provinciaNascitaEstero;
    }

    public String getDataNascita(){
        return this.dataNascita;
    }

    public void setDataNascita(String dataNascita){
        this.dataNascita = dataNascita;
    }

    public String getSesso(){
        return this.sesso;
    }

    public void setSesso(String sesso){
        this.sesso = sesso;
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

    public String getComuneResidenza(){
        return this.comuneResidenza;
    }

    public void setComuneResidenza(String comuneResidenza){
        this.comuneResidenza = comuneResidenza;
    }

    public String getProvinciaResidenza(){
        return this.provinciaResidenza;
    }

    public void setProvinciaResidenza(String provinciaResidenza){
        this.provinciaResidenza = provinciaResidenza;
    }

    public String getNazionalitaResidenza(){
        return this.nazionalitaResidenza;
    }

    public void setNazionalitaResidenza(String nazionalitaResidenza){
        this.nazionalitaResidenza = nazionalitaResidenza;
    }

    public String getComuneResidenzaEstero(){
        return this.comuneResidenzaEstero;
    }

    public void setComuneResidenzaEstero(String comuneResidenzaEstero){
        this.comuneResidenzaEstero = comuneResidenzaEstero;
    }

    public String getProvinciaResidenzaEstero(){
        return this.provinciaResidenzaEstero;
    }

    public void setProvinciaResidenzaEstero(String provinciaResidenzaEstero){
        this.provinciaResidenzaEstero = provinciaResidenzaEstero;
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

    public String getTelefono(){
        return this.telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public String getCellulare(){
        return this.cellulare;
    }

    public void setCellulare(String cellulare){
        this.cellulare = cellulare;
    }

    public String getFax(){
        return this.fax;
    }

    public void setFax(String fax){
        this.fax = fax;
    }

    public String getDitta(){
        return this.ditta;
    }

    public void setDitta(String ditta){
        this.ditta = ditta;
    }

    public String getDittaEnte(){
        return this.dittaEnte;
    }

    public void setDittaEnte(String dittaEnte){
        this.dittaEnte = dittaEnte;
    }

    public String getDittaCf(){
        return this.dittaCf;
    }

    public void setDittaCf(String dittaCf){
        this.dittaCf = dittaCf;
    }

    public String getDittaPartitaIva(){
        return this.dittaPartitaIva;
    }

    public void setDittaPartitaIva(String dittaPartitaIva){
        this.dittaPartitaIva = dittaPartitaIva;
    }

    public String getDittaQualitaDi(){
        return this.dittaQualitaDi;
    }

    public void setDittaQualitaDi(String dittaQualitaDi){
        this.dittaQualitaDi = dittaQualitaDi;
    }

    public String getDittaQualitaAltro(){
        return this.dittaQualitaAltro;
    }

    public void setDittaQualitaAltro(String dittaQualitaAltro){
        this.dittaQualitaAltro = dittaQualitaAltro;
    }

    public String getTecnicoStudioIndirizzo(){
        return this.tecnicoStudioIndirizzo;
    }

    public void setTecnicoStudioIndirizzo(String tecnicoStudioIndirizzo){
        this.tecnicoStudioIndirizzo = tecnicoStudioIndirizzo;
    }

    public String getTecnicoStudioCivico(){
        return this.tecnicoStudioCivico;
    }

    public void setTecnicoStudioCivico(String tecnicoStudioCivico){
        this.tecnicoStudioCivico = tecnicoStudioCivico;
    }

    public String getTecnicoStudioCap(){
        return this.tecnicoStudioCap;
    }

    public void setTecnicoStudioCap(String tecnicoStudioCap){
        this.tecnicoStudioCap = tecnicoStudioCap;
    }

    public String getTecnicoStudioComune(){
        return this.tecnicoStudioComune;
    }

    public void setTecnicoStudioComune(String tecnicoStudioComune){
        this.tecnicoStudioComune = tecnicoStudioComune;
    }

    public String getTecnicoStudioProvincia(){
        return this.tecnicoStudioProvincia;
    }

    public void setTecnicoStudioProvincia(String tecnicoStudioProvincia){
        this.tecnicoStudioProvincia = tecnicoStudioProvincia;
    }

    public String getTecnicoStudioNazionalita(){
        return this.tecnicoStudioNazionalita;
    }

    public void setTecnicoStudioNazionalita(String tecnicoStudioNazionalita){
        this.tecnicoStudioNazionalita = tecnicoStudioNazionalita;
    }

    public String getTecnicoStudioComuneEstero(){
        return this.tecnicoStudioComuneEstero;
    }

    public void setTecnicoStudioComuneEstero(String tecnicoStudioComuneEstero){
        this.tecnicoStudioComuneEstero = tecnicoStudioComuneEstero;
    }

    public String getTecnicoStudioProvinciaEstero(){
        return this.tecnicoStudioProvinciaEstero;
    }

    public void setTecnicoStudioProvinciaEstero(String tecnicoStudioProvinciaEstero){
        this.tecnicoStudioProvinciaEstero = tecnicoStudioProvinciaEstero;
    }

    public String getTecnicoOrdCollegio(){
        return this.tecnicoOrdCollegio;
    }

    public void setTecnicoOrdCollegio(String tecnicoOrdCollegio){
        this.tecnicoOrdCollegio = tecnicoOrdCollegio;
    }

    public String getTecnicoCollegioSede(){
        return this.tecnicoCollegioSede;
    }

    public void setTecnicoCollegioSede(String tecnicoCollegioSede){
        this.tecnicoCollegioSede = tecnicoCollegioSede;
    }

    public String getTecnicoCollegioNIscr(){
        return this.tecnicoCollegioNIscr;
    }

    public void setTecnicoCollegioNIscr(String tecnicoCollegioNIscr){
        this.tecnicoCollegioNIscr = tecnicoCollegioNIscr;
    }

    public String getNazionalitaResidenzaName(){
        return this.nazionalitaResidenzaName;
    }

    public void setNazionalitaResidenzaName(String nazionalitaResidenzaName){
        this.nazionalitaResidenzaName = nazionalitaResidenzaName;
    }

    public String getProvinciaResidenzaName(){
        return this.provinciaResidenzaName;
    }

    public void setProvinciaResidenzaName(String provinciaResidenzaName){
        this.provinciaResidenzaName = provinciaResidenzaName;
    }

    public String getComuneResidenzaName(){
        return this.comuneResidenzaName;
    }

    public void setComuneResidenzaName(String comuneResidenzaName){
        this.comuneResidenzaName = comuneResidenzaName;
    }

    public String getNazionalitaNascitaName(){
        return this.nazionalitaNascitaName;
    }

    public void setNazionalitaNascitaName(String nazionalitaNascitaName){
        this.nazionalitaNascitaName = nazionalitaNascitaName;
    }

    public String getProvinciaNascitaName(){
        return this.provinciaNascitaName;
    }

    public void setProvinciaNascitaName(String provinciaNascitaName){
        this.provinciaNascitaName = provinciaNascitaName;
    }

    public String getComuneNascitaName(){
        return this.comuneNascitaName;
    }

    public void setComuneNascitaName(String comuneNascitaName){
        this.comuneNascitaName = comuneNascitaName;
    }

    public String getTecnicoStudioNazionalitaName(){
        return this.tecnicoStudioNazionalitaName;
    }

    public void setTecnicoStudioNazionalitaName(String tecnicoStudioNazionalitaName){
        this.tecnicoStudioNazionalitaName = tecnicoStudioNazionalitaName;
    }

    public String getTecnicoStudioProvinciaName(){
        return this.tecnicoStudioProvinciaName;
    }

    public void setTecnicoStudioProvinciaName(String tecnicoStudioProvinciaName){
        this.tecnicoStudioProvinciaName = tecnicoStudioProvinciaName;
    }

    public String getTecnicoStudioComuneName(){
        return this.tecnicoStudioComuneName;
    }

    public void setTecnicoStudioComuneName(String tecnicoStudioComuneName){
        this.tecnicoStudioComuneName = tecnicoStudioComuneName;
    }


}
