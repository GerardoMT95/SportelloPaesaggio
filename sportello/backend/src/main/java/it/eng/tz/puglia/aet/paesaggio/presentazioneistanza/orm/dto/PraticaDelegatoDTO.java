package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * DTO for table presentazione_istanza.pratica_delegato
 */
public class PraticaDelegatoDTO implements Serializable
{

    private static final long serialVersionUID = 1532537514L;
    // COLUMN id
    private UUID id;
    // COLUMN indice
    private short indice;
    // COLUMN cognome
    @NotBlank(message="Campo 'Cognome' non può essere vuoto")
    @Size(min = 1, max = 400, message="Il campo 'Cognome' deve avere un numero di caratteri compreso fra 1 e 400")
    private String cognome;
    // COLUMN nome
    @NotBlank(message="Campo 'Nome' non può essere vuoto")
    @Size(min = 1, max = 400, message="Il campo 'Nome' deve avere un numero di caratteri compreso fra 1 e 400")
    private String nome;
    // COLUMN codice_fiscale
    @NotBlank(message="Campo 'Codice fiscale' non può essere vuoto")
    @Size(min=16, max=16, message="Campo 'Codice fiscale' deve contenere 16 caratteri")
    @Pattern(regexp="[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]", message="Campo 'Codice fiscale' non valido")
    private String codiceFiscale;
    // COLUMN sesso
    @NotBlank(message="Campo 'Sesso' non può essere vuoto")
    private String sesso;
    // COLUMN data_nascita
    @NotNull(message="Campo 'Data di nascita' non può essere vuoto")
    private Timestamp dataNascita;
    // COLUMN id_nazione_nascita
    private Integer idNazioneNascita;
    // COLUMN nazione_nascita
    private String nazioneNascita;
    // COLUMN id_comune_nascita
    private Integer idComuneNascita;
    // COLUMN comune_nascita
    private String comuneNascita;
    // COLUMN comune_nascita_estero
    private String comuneNascitaEstero;
    // COLUMN id_nazione_residenza
    private Integer idNazioneResidenza;
    // COLUMN nazione_residenza
    private String nazioneResidenza;
    // COLUMN id_comune_residenza
    private Integer idComuneResidenza;
    // COLUMN comune_residenza
    private String comuneResidenza;
    // COLUMN comune_residenza_estero
    private String comuneResidenzaEstero;
    // COLUMN indirizzo_residenza
    private String indirizzoResidenza;
    // COLUMN civico_residenza
    private String civicoResidenza;
    // COLUMN cap_residenza
    private String capResidenza;
    // COLUMN pec
    private String pec;
    // COLUMN mail
    private String mail;
    // COLUMN date_create
    private Timestamp dateCreate;
    // COLUMN delgato_corrente
    private Boolean delgatoCorrente;
    // COLUMN provincia_nascita
    private String provinciaNascita;
    // COLUMN id_provincia_nascita
    private Integer idProvinciaNascita;
    // COLUMN provincia_residenza
    private String provinciaResidenza;
    // COLUMN id_provincia_residenza
    private Integer idProvinciaResidenza;
    
    private AllegatiDTO allegatoDelega;
    
    private AllegatiDTO allegatoSollevamento;

    public UUID getId()
    {
	return this.id;
    }
    public void setId(UUID id)
    {
	this.id = id;
    }

    public short getIndice()
    {
	return this.indice;
    }
    public void setIndice(short indice)
    {
	this.indice = indice;
    }

    public String getCognome()
    {
	return this.cognome;
    }
    public void setCognome(String cognome)
    {
	this.cognome = cognome;
    }

    public String getNome()
    {
	return this.nome;
    }
    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public String getCodiceFiscale()
    {
	return this.codiceFiscale;
    }
    public void setCodiceFiscale(String codiceFiscale)
    {
	this.codiceFiscale = codiceFiscale;
    }

    public String getSesso()
    {
	return this.sesso;
    }
    public void setSesso(String sesso)
    {
	this.sesso = sesso;
    }

    public Timestamp getDataNascita()
    {
	return this.dataNascita;
    }
    public void setDataNascita(Timestamp dataNascita)
    {
	this.dataNascita = dataNascita;
    }

    public Integer getIdNazioneNascita()
    {
	return this.idNazioneNascita;
    }
    public void setIdNazioneNascita(Integer idNazioneNascita)
    {
	this.idNazioneNascita = idNazioneNascita;
    }

    public String getNazioneNascita()
    {
	return this.nazioneNascita;
    }
    public void setNazioneNascita(String nazioneNascita)
    {
	this.nazioneNascita = nazioneNascita;
    }

    public Integer getIdComuneNascita()
    {
	return this.idComuneNascita;
    }
    public void setIdComuneNascita(Integer idComuneNascita)
    {
	this.idComuneNascita = idComuneNascita;
    }

    public String getComuneNascita()
    {
	return this.comuneNascita;
    }
    public void setComuneNascita(String comuneNascita)
    {
	this.comuneNascita = comuneNascita;
    }

    public String getComuneNascitaEstero()
    {
	return this.comuneNascitaEstero;
    }
    public void setComuneNascitaEstero(String comuneNascitaEstero)
    {
	this.comuneNascitaEstero = comuneNascitaEstero;
    }

    public Integer getIdNazioneResidenza()
    {
	return this.idNazioneResidenza;
    }
    public void setIdNazioneResidenza(Integer idNazioneResidenza)
    {
	this.idNazioneResidenza = idNazioneResidenza;
    }

    public String getNazioneResidenza()
    {
	return this.nazioneResidenza;
    }
    public void setNazioneResidenza(String nazioneResidenza)
    {
	this.nazioneResidenza = nazioneResidenza;
    }

    public Integer getIdComuneResidenza()
    {
	return this.idComuneResidenza;
    }
    public void setIdComuneResidenza(Integer idComuneResidenza)
    {
	this.idComuneResidenza = idComuneResidenza;
    }

    public String getComuneResidenza()
    {
	return this.comuneResidenza;
    }
    public void setComuneResidenza(String comuneResidenza)
    {
	this.comuneResidenza = comuneResidenza;
    }

    public String getComuneResidenzaEstero()
    {
	return this.comuneResidenzaEstero;
    }
    public void setComuneResidenzaEstero(String comuneResidenzaEstero)
    {
	this.comuneResidenzaEstero = comuneResidenzaEstero;
    }

    public String getIndirizzoResidenza()
    {
	return this.indirizzoResidenza;
    }
    public void setIndirizzoResidenza(String indirizzoResidenza)
    {
	this.indirizzoResidenza = indirizzoResidenza;
    }

    public String getCivicoResidenza()
    {
	return this.civicoResidenza;
    }
    public void setCivicoResidenza(String civicoResidenza)
    {
	this.civicoResidenza = civicoResidenza;
    }

    public String getCapResidenza()
    {
	return this.capResidenza;
    }
    public void setCapResidenza(String capResidenza)
    {
	this.capResidenza = capResidenza;
    }

    public String getPec()
    {
	return this.pec;
    }
    public void setPec(String pec)
    {
	this.pec = pec;
    }

    public String getMail()
    {
	return this.mail;
    }
    public void setMail(String mail)
    {
	this.mail = mail;
    }

    public Timestamp getDateCreate()
    {
	return this.dateCreate;
    }
    public void setDateCreate(Timestamp dateCreate)
    {
	this.dateCreate = dateCreate;
    }

    public Boolean getDelgatoCorrente()
    {
	return this.delgatoCorrente;
    }
    public void setDelgatoCorrente(Boolean delgatoCorrente)
    {
	this.delgatoCorrente = delgatoCorrente;
    }
    public String getProvinciaNascita()
    {
        return provinciaNascita;
    }
    public void setProvinciaNascita(String provinciaNascita)
    {
        this.provinciaNascita = provinciaNascita;
    }
    public Integer getIdProvinciaNascita()
    {
        return idProvinciaNascita;
    }
    public void setIdProvinciaNascita(Integer idProvinciaNascita)
    {
        this.idProvinciaNascita = idProvinciaNascita;
    }
    
    public String getProvinciaResidenza()
    {
        return provinciaResidenza;
    }
    public void setProvinciaResidenza(String provinciaResidenza)
    {
        this.provinciaResidenza = provinciaResidenza;
    }
    
    public Integer getIdProvinciaResidenza()
    {
        return idProvinciaResidenza;
    }
    public void setIdProvinciaResidenza(Integer idProvinciaResidenza)
    {
        this.idProvinciaResidenza = idProvinciaResidenza;
    }
    
    
	public AllegatiDTO getAllegatoDelega() {
		return allegatoDelega;
	}
	
	
	public void setAllegatoDelega(AllegatiDTO allegatoDelega) {
		this.allegatoDelega = allegatoDelega;
	}
	
	public AllegatiDTO getAllegatoSollevamento() {
		return allegatoSollevamento;
	}
	
	public void setAllegatoSollevamento(AllegatiDTO allegatoSollevamento) {
		this.allegatoSollevamento = allegatoSollevamento;
	}

    
}
