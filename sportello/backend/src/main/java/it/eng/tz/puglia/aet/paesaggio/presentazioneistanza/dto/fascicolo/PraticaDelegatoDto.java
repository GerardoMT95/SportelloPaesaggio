package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.RichiedenteInfo;
import it.eng.tz.puglia.util.json.DateDeserializer;
import it.eng.tz.puglia.util.json.DateSerializer;

public class PraticaDelegatoDto implements Serializable
{
    private static final long serialVersionUID = 5971645978256258078L;
    
    private Long id;
    private String capResidenza;
    private String civicoResidenza;
    @NotBlank(message="Il campo 'codice fiscale' non può essere vuoto")
    @Pattern(regexp="[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]", message="codice fiscale non valido")
    private String codiceFiscale;
    @NotBlank(message="Il campo 'cognome' non può essere vuoto")
    private String cognome;
    private Long idNazioneNascita;
    private Long idNazioneResidenza;
    private Long idProvinciaNascita;
    private String provinciaNascita;
    private Long idComuneNascita;
    private String comuneNascita;
    private String comuneNascitaEstero;
    private Long idComuneResidenza;
    private String comuneResidenza;
    private Long idProvinciaResidenza;
    private String provinciaResidenza;
    private String comuneResidenzaEstero;
    @NotNull(message="Il campo 'dataNascita' non può essere nullo")
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataNascita;
    @NotBlank(message = "Il campo 'mail' non può essere vuoto",groups = {RichiedenteInfo.class})
    @Email(message = "Il contenuto del campo 'mail' non è valido")
    private String mail;
    private String nazioneNascita;
    private String nazioneResidenza;
    private String indirizzoResidenza;
    @NotBlank(message="Il campo 'nome' non può essere vuoto")
    private String nome;
    private String pec;
    @NotBlank(message="Il campo 'sesso' non può essere vuoto")
    private String sesso;
    
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getCapResidenza()
    {
        return capResidenza;
    }
    public void setCapResidenza(String capResidenza)
    {
        this.capResidenza = capResidenza;
    }
    
    public String getCivicoResidenza()
    {
        return civicoResidenza;
    }
    public void setCivicoResidenza(String civicoResidenza)
    {
        this.civicoResidenza = civicoResidenza;
    }
    
    public String getCodiceFiscale()
    {
        return codiceFiscale;
    }
    public void setCodiceFiscale(String codiceFiscale)
    {
        this.codiceFiscale = codiceFiscale;
    }
    
    public String getCognome()
    {
        return cognome;
    }
    public void setCognome(String cognome)
    {
        this.cognome = cognome;
    }
    
    public Long getIdNazioneNascita()
    {
        return idNazioneNascita;
    }
    public void setIdNazioneNascita(Long idNazioneNascita)
    {
        this.idNazioneNascita = idNazioneNascita;
    }
    
    public Long getIdNazioneResidenza()
    {
        return idNazioneResidenza;
    }
    public void setIdNazioneResidenza(Long idNazioneResidenza)
    {
        this.idNazioneResidenza = idNazioneResidenza;
    }
    
    public Long getIdProvinciaNascita()
    {
        return idProvinciaNascita;
    }
    public void setIdProvinciaNascita(Long idProvinciaNascita)
    {
        this.idProvinciaNascita = idProvinciaNascita;
    }
    
    public String getProvinciaNascita()
    {
        return provinciaNascita;
    }
    public void setProvinciaNascita(String provinciaNascita)
    {
        this.provinciaNascita = provinciaNascita;
    }
    
    public Long getIdComuneNascita()
    {
        return idComuneNascita;
    }
    public void setIdComuneNascita(Long idComuneNascita)
    {
        this.idComuneNascita = idComuneNascita;
    }
    
    public String getComuneNascita()
    {
        return comuneNascita;
    }
    public void setComuneNascita(String comuneNascita)
    {
        this.comuneNascita = comuneNascita;
    }
    
    public String getComuneNascitaEstero()
    {
        return comuneNascitaEstero;
    }
    public void setComuneNascitaEstero(String comuneNascitaEstero)
    {
        this.comuneNascitaEstero = comuneNascitaEstero;
    }
    
    public Long getIdComuneResidenza()
    {
        return idComuneResidenza;
    }
    public void setIdComuneResidenza(Long idComuneResidenza)
    {
        this.idComuneResidenza = idComuneResidenza;
    }
    
    public String getComuneResidenza()
    {
        return comuneResidenza;
    }
    public void setComuneResidenza(String comuneResidenza)
    {
        this.comuneResidenza = comuneResidenza;
    }
    
    public Long getIdProvinciaResidenza()
    {
        return idProvinciaResidenza;
    }
    public void setIdProvinciaResidenza(Long idProvinciaResidenza)
    {
        this.idProvinciaResidenza = idProvinciaResidenza;
    }
    
    public String getProvinciaResidenza()
    {
        return provinciaResidenza;
    }
    public void setProvinciaResidenza(String provinciaResidenza)
    {
        this.provinciaResidenza = provinciaResidenza;
    }
    
    public String getComuneResidenzaEstero()
    {
        return comuneResidenzaEstero;
    }
    public void setComuneResidenzaEstero(String comuneResidenzaEstero)
    {
        this.comuneResidenzaEstero = comuneResidenzaEstero;
    }
    
    public Date getDataNascita()
    {
        return dataNascita;
    }
    public void setDataNascita(Date dataNascita)
    {
        this.dataNascita = dataNascita;
    }
    
    public String getMail()
    {
        return mail;
    }
    public void setMail(String mail)
    {
        this.mail = mail;
    }
    
    public String getNazioneNascita()
    {
        return nazioneNascita;
    }
    public void setNazioneNascita(String nazioneNascita)
    {
        this.nazioneNascita = nazioneNascita;
    }
    
    public String getNazioneResidenza()
    {
        return nazioneResidenza;
    }
    public void setNazioneResidenza(String nazioneResidenza)
    {
        this.nazioneResidenza = nazioneResidenza;
    }
    
    public String getIndirizzoResidenza()
    {
        return indirizzoResidenza;
    }
    public void setIndirizzoResidenza(String indirizzoResidenza)
    {
        this.indirizzoResidenza = indirizzoResidenza;
    }
    
    public String getNome()
    {
        return nome;
    }
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    
    public String getPec()
    {
        return pec;
    }
    public void setPec(String pec)
    {
        this.pec = pec;
    }
    
    public String getSesso()
    {
        return sesso;
    }
    public void setSesso(String sesso)
    {
        this.sesso = sesso;
    }
    
    
}
