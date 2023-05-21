package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import it.eng.tz.puglia.autpae.enumeratori.ProtocolloMailIn;
import it.eng.tz.puglia.autpae.enumeratori.ProtocolloMailOut;
import it.eng.tz.regione.puglia.webmail.be.dto.CasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigMailInDto;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigMailOutDto;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.enumerations.MailInEnum;
import it.eng.tz.regione.puglia.webmail.be.enumerations.MailOutEnum;

public class ConfigurazionePECBean implements Serializable
{
	private static final long serialVersionUID = 4878853180167330354L;
	
	private Integer id;

	@NotNull(message="Il campo 'Indirizzo PEC' non può essere lasciato in bianco")
	@NotBlank(message="Il campo 'Indirizzo PEC' non può essere lasciato in bianco")
	@Length(min=1, max=100, message="Il campo 'indirizzo PEC' deve essere popolato e non deve superare i 100 caratteri")
	private String pecIndirizzo;
	@NotNull(message="Il campo 'Nome PEC' non può essere lasciato in bianco")
	@NotBlank(message="Il campo 'Nome PEC' non può essere lasciato in bianco")
	@Length(min=1, max=100, message="Il campo 'Nome PEC' deve essere popolato e non deve superare i 100 caratteri")
	private String pecNome;
	@NotNull(message="Il campo 'Username PEC' non può essere lasciato in bianco")
	@NotBlank(message="Il campo 'Username PEC' non può essere lasciato in bianco")
	@Length(min=1, max=100, message="Il campo 'Username PEC' deve essere popolato e non deve superare i 100 caratteri")
	private String pecUsername;
	@NotNull(message="Il campo 'Password PEC' non può essere lasciato in bianco")
	@NotBlank(message="Il campo 'Password PEC' non può essere lasciato in bianco")
	@Length(min=1, max=100, message="Il campo 'Password PEC' deve essere popolato e non deve superare i 100 caratteri")
	private String pecPassword;
	@NotNull(message="Il campo 'Host in PEC' non può essere lasciato in bianco")
	@NotBlank(message="Il campo 'Host in PEC' non può essere lasciato in bianco")
	@Length(min=1, max=200, message="Il campo 'Host in PEC' deve essere popolato e non deve superare i 200 caratteri")
	private String pecHostIn;
	@NotNull(message="Il campo 'Host out PEC' non può essere lasciato in bianco")
	@NotBlank(message="Il campo 'Host out PEC' non può essere lasciato in bianco")
	@Length(min=1, max=200, message="Il campo 'Host out PEC' deve essere popolato e non deve superare i 200 caratteri")
	private String pecHostOut;
	private Boolean pecProtocolloIn;
	private Boolean pecProtocolloOut;
	@NotNull(message="Il campo 'pecTipoProtocolloIn' non può essere nullo")
	private ProtocolloMailIn pecTipoProtocolloIn;
	@NotNull(message="Il campo 'pecTipoProtocolloOut' non può essere nullo")
	private ProtocolloMailOut pecTipoProtocolloOut;
	private Boolean pecSslIn;
	private Boolean pecSslOut;
	private Boolean pecTlsIn;
	private Boolean pecTlsOut;
	@Min(value=0, message="Il campo 'Porta SSl in' non può essere negativo")
	private Integer pecPortaSslIn;
	@Min(value=0, message="Il campo 'Porta SSl out' non può essere negativo")
	private Integer pecPortaSslOut;
	@Min(value=0, message="Il campo 'Porta TLS in' non può essere negativo")
	private Integer pecPortaTlsIn;
	@Min(value=0, message="Il campo 'Porta TLS in' non può essere negativo")
	private Integer pecPortaTlsOut;
	private Boolean pecStartTlsIn;
	private Boolean pecStartTlsOut;
	private Boolean pecAutenticazioneIn;
	private Boolean pecAutenticazioneOut;
	
	public ConfigurazionePECBean() {}
	
	public ConfigurazionePECBean(ConfigurazioneCasellaPostaleDto other)
	{
		if(other != null)
		{
			if(other.getCasellaPostale() != null)
			{
				CasellaPostaleDto c = other.getCasellaPostale();
				setPecIndirizzo(c.getIndirizzoMail());
				setPecNome(c.getNomeVisualizzato());
				setPecUsername(c.getUsername());
				setPecPassword(c.getPassword());
			}
			if(other.getConfigurazioneMailIngresso() != null)
			{
				ConfigMailInDto c = other.getConfigurazioneMailIngresso();
				setPecHostIn(c.getHost());
				//setPecProtocolloIn(c.getProt());
				setPecTipoProtocolloIn(ProtocolloMailIn.valueOf(c.getProtocollo().toString()));
				setPecSslIn(c.isRichiedeSsl());
				//setPecTlsIn(???);
				setPecPortaSslIn(c.getPorta());
				//setPecPortaTlsIn(???);
				setPecStartTlsIn(c.isRichiedeStarttsl());
				setPecAutenticazioneIn(c.isRichiedeAutenticazione());
			}
			if(other.getConfigurazioneMailUscita() != null)
			{
				ConfigMailOutDto c = other.getConfigurazioneMailUscita();
				setPecHostOut(c.getHost());
				//setPecProtocolloOut(c.getProt());
				setPecTipoProtocolloOut(ProtocolloMailOut.valueOf(c.getProtocollo().toString()));
				setPecSslOut(c.isRichiedeSsl());
				//setPecTlsIn(???);
				setPecPortaSslOut(c.getPortaSsl());
				setPecPortaTlsOut(c.getPortaTls());
				setPecStartTlsOut(c.isRichiedeStarttsl());
				setPecAutenticazioneOut(c.isRichiedeAutenticazione());
			}
		}
	}
	
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public String getPecIndirizzo()
	{
		return pecIndirizzo;
	}
	public void setPecIndirizzo(String pecIndirizzo)
	{
		this.pecIndirizzo = pecIndirizzo;
	}
	
	public String getPecNome()
	{
		return pecNome;
	}
	public void setPecNome(String pecNome)
	{
		this.pecNome = pecNome;
	}
	
	public String getPecUsername()
	{
		return pecUsername;
	}
	public void setPecUsername(String pecUsername)
	{
		this.pecUsername = pecUsername;
	}
	
	public String getPecPassword()
	{
		return pecPassword;
	}
	public void setPecPassword(String pecPassword)
	{
		this.pecPassword = pecPassword;
	}
	
	public String getPecHostIn()
	{
		return pecHostIn;
	}
	public void setPecHostIn(String pecHostIn)
	{
		this.pecHostIn = pecHostIn;
	}
	
	public String getPecHostOut()
	{
		return pecHostOut;
	}
	public void setPecHostOut(String pecHostOut)
	{
		this.pecHostOut = pecHostOut;
	}
	
	public Boolean getPecProtocolloIn()
	{
		return pecProtocolloIn;
	}
	public void setPecProtocolloIn(Boolean pecProtocolloIn)
	{
		this.pecProtocolloIn = pecProtocolloIn;
	}
	
	public Boolean getPecProtocolloOut()
	{
		return pecProtocolloOut;
	}
	public void setPecProtocolloOut(Boolean pecProtocolloOut)
	{
		this.pecProtocolloOut = pecProtocolloOut;
	}
	
	public ProtocolloMailIn getPecTipoProtocolloIn()
	{
		return pecTipoProtocolloIn;
	}
	public void setPecTipoProtocolloIn(ProtocolloMailIn pecTipoProtocolloIn)
	{
		this.pecTipoProtocolloIn = pecTipoProtocolloIn;
	}
	
	public ProtocolloMailOut getPecTipoProtocolloOut()
	{
		return pecTipoProtocolloOut;
	}
	public void setPecTipoProtocolloOut(ProtocolloMailOut pecTipoProtocolloOut)
	{
		this.pecTipoProtocolloOut = pecTipoProtocolloOut;
	}
	
	public Boolean getPecSslIn()
	{
		return pecSslIn;
	}
	public void setPecSslIn(Boolean pecSslIn)
	{
		this.pecSslIn = pecSslIn;
	}
	
	public Boolean getPecSslOut()
	{
		return pecSslOut;
	}
	public void setPecSslOut(Boolean pecSslOut)
	{
		this.pecSslOut = pecSslOut;
	}
	
	public Boolean getPecTlsIn()
	{
		return pecTlsIn;
	}
	public void setPecTlsIn(Boolean pecTlsIn)
	{
		this.pecTlsIn = pecTlsIn;
	}
	
	public Boolean getPecTlsOut()
	{
		return pecTlsOut;
	}
	public void setPecTlsOut(Boolean pecTlsOut)
	{
		this.pecTlsOut = pecTlsOut;
	}
	
	public Integer getPecPortaSslIn()
	{
		return pecPortaSslIn;
	}
	public void setPecPortaSslIn(Integer pecPortaSslIn)
	{
		this.pecPortaSslIn = pecPortaSslIn;
	}
	
	public Integer getPecPortaSslOut()
	{
		return pecPortaSslOut;
	}
	public void setPecPortaSslOut(Integer pecPortaSslOut)
	{
		this.pecPortaSslOut = pecPortaSslOut;
	}
	
	public Integer getPecPortaTlsIn()
	{
		return pecPortaTlsIn;
	}
	public void setPecPortaTlsIn(Integer pecPortaTlsIn)
	{
		this.pecPortaTlsIn = pecPortaTlsIn;
	}
	
	public Integer getPecPortaTlsOut()
	{
		return pecPortaTlsOut;
	}
	public void setPecPortaTlsOut(Integer pecPortaTlsOut)
	{
		this.pecPortaTlsOut = pecPortaTlsOut;
	}
	
	public Boolean getPecStartTlsIn()
	{
		return pecStartTlsIn;
	}
	public void setPecStartTlsIn(Boolean pecStartTlsIn)
	{
		this.pecStartTlsIn = pecStartTlsIn;
	}
	
	public Boolean getPecStartTlsOut()
	{
		return pecStartTlsOut;
	}
	public void setPecStartTlsOut(Boolean pecStartTlsOut)
	{
		this.pecStartTlsOut = pecStartTlsOut;
	}
	
	public Boolean getPecAutenticazioneIn()
	{
		return pecAutenticazioneIn;
	}
	public void setPecAutenticazioneIn(Boolean pecAutenticazioneIn)
	{
		this.pecAutenticazioneIn = pecAutenticazioneIn;
	}
	
	public Boolean getPecAutenticazioneOut()
	{
		return pecAutenticazioneOut;
	}
	public void setPecAutenticazioneOut(Boolean pecAutenticazioneOut)
	{
		this.pecAutenticazioneOut = pecAutenticazioneOut;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pecAutenticazioneIn == null) ? 0 : pecAutenticazioneIn.hashCode());
		result = prime * result + ((pecAutenticazioneOut == null) ? 0 : pecAutenticazioneOut.hashCode());
		result = prime * result + ((pecHostIn == null) ? 0 : pecHostIn.hashCode());
		result = prime * result + ((pecHostOut == null) ? 0 : pecHostOut.hashCode());
		result = prime * result + ((pecIndirizzo == null) ? 0 : pecIndirizzo.hashCode());
		result = prime * result + ((pecNome == null) ? 0 : pecNome.hashCode());
		result = prime * result + ((pecPassword == null) ? 0 : pecPassword.hashCode());
		result = prime * result + ((pecPortaSslIn == null) ? 0 : pecPortaSslIn.hashCode());
		result = prime * result + ((pecPortaSslOut == null) ? 0 : pecPortaSslOut.hashCode());
		result = prime * result + ((pecPortaTlsIn == null) ? 0 : pecPortaTlsIn.hashCode());
		result = prime * result + ((pecPortaTlsOut == null) ? 0 : pecPortaTlsOut.hashCode());
		result = prime * result + ((pecSslIn == null) ? 0 : pecSslIn.hashCode());
		result = prime * result + ((pecSslOut == null) ? 0 : pecSslOut.hashCode());
		result = prime * result + ((pecStartTlsIn == null) ? 0 : pecStartTlsIn.hashCode());
		result = prime * result + ((pecStartTlsOut == null) ? 0 : pecStartTlsOut.hashCode());
		result = prime * result + ((pecTipoProtocolloIn == null) ? 0 : pecTipoProtocolloIn.hashCode());
		result = prime * result + ((pecTipoProtocolloOut == null) ? 0 : pecTipoProtocolloOut.hashCode());
		result = prime * result + ((pecTlsIn == null) ? 0 : pecTlsIn.hashCode());
		result = prime * result + ((pecTlsOut == null) ? 0 : pecTlsOut.hashCode());
		result = prime * result + ((pecUsername == null) ? 0 : pecUsername.hashCode());
		result = prime * result + ((pecProtocolloIn == null) ? 0 : pecProtocolloIn.hashCode());
		result = prime * result + ((pecProtocolloOut == null) ? 0 : pecProtocolloOut.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfigurazionePECBean other = (ConfigurazionePECBean) obj;
		if (pecAutenticazioneIn == null)
		{
			if (other.pecAutenticazioneIn != null)
				return false;
		} else if (!pecAutenticazioneIn.equals(other.pecAutenticazioneIn))
			return false;
		if (pecAutenticazioneOut == null)
		{
			if (other.pecAutenticazioneOut != null)
				return false;
		} else if (!pecAutenticazioneOut.equals(other.pecAutenticazioneOut))
			return false;
		if (pecHostIn == null)
		{
			if (other.pecHostIn != null)
				return false;
		} else if (!pecHostIn.equals(other.pecHostIn))
			return false;
		if (pecHostOut == null)
		{
			if (other.pecHostOut != null)
				return false;
		} else if (!pecHostOut.equals(other.pecHostOut))
			return false;
		if (pecIndirizzo == null)
		{
			if (other.pecIndirizzo != null)
				return false;
		} else if (!pecIndirizzo.equals(other.pecIndirizzo))
			return false;
		if (pecNome == null)
		{
			if (other.pecNome != null)
				return false;
		} else if (!pecNome.equals(other.pecNome))
			return false;
		if (pecPassword == null)
		{
			if (other.pecPassword != null)
				return false;
		} else if (!pecPassword.equals(other.pecPassword))
			return false;
		if (pecPortaSslIn == null)
		{
			if (other.pecPortaSslIn != null)
				return false;
		} else if (!pecPortaSslIn.equals(other.pecPortaSslIn))
			return false;
		if (pecPortaSslOut == null)
		{
			if (other.pecPortaSslOut != null)
				return false;
		} else if (!pecPortaSslOut.equals(other.pecPortaSslOut))
			return false;
		if (pecPortaTlsIn == null)
		{
			if (other.pecPortaTlsIn != null)
				return false;
		} else if (!pecPortaTlsIn.equals(other.pecPortaTlsIn))
			return false;
		if (pecPortaTlsOut == null)
		{
			if (other.pecPortaTlsOut != null)
				return false;
		} else if (!pecPortaTlsOut.equals(other.pecPortaTlsOut))
			return false;
		if (pecSslIn == null)
		{
			if (other.pecSslIn != null)
				return false;
		} else if (!pecSslIn.equals(other.pecSslIn))
			return false;
		if (pecSslOut == null)
		{
			if (other.pecSslOut != null)
				return false;
		} else if (!pecSslOut.equals(other.pecSslOut))
			return false;
		if (pecStartTlsIn == null)
		{
			if (other.pecStartTlsIn != null)
				return false;
		} else if (!pecStartTlsIn.equals(other.pecStartTlsIn))
			return false;
		if (pecStartTlsOut == null)
		{
			if (other.pecStartTlsOut != null)
				return false;
		} else if (!pecStartTlsOut.equals(other.pecStartTlsOut))
			return false;
		if (pecTipoProtocolloIn == null)
		{
			if (other.pecTipoProtocolloIn != null)
				return false;
		} else if (!pecTipoProtocolloIn.equals(other.pecTipoProtocolloIn))
			return false;
		if (pecTipoProtocolloOut == null)
		{
			if (other.pecTipoProtocolloOut != null)
				return false;
		} else if (!pecTipoProtocolloOut.equals(other.pecTipoProtocolloOut))
			return false;
		if (pecTlsIn == null)
		{
			if (other.pecTlsIn != null)
				return false;
		} else if (!pecTlsIn.equals(other.pecTlsIn))
			return false;
		if (pecTlsOut == null)
		{
			if (other.pecTlsOut != null)
				return false;
		} else if (!pecTlsOut.equals(other.pecTlsOut))
			return false;
		if (pecUsername == null)
		{
			if (other.pecUsername != null)
				return false;
		} else if (!pecUsername.equals(other.pecUsername))
			return false;
		if (pecProtocolloIn == null)
		{
			if (other.pecProtocolloIn != null)
				return false;
		} else if (!pecProtocolloIn.equals(other.pecProtocolloIn))
			return false;
		if (pecProtocolloOut == null)
		{
			if (other.pecProtocolloOut != null)
				return false;
		} else if (!pecProtocolloOut.equals(other.pecProtocolloOut))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "ConfigurazionePECBean [pecIndirizzo=" + pecIndirizzo + ", pecNome=" + pecNome + ", pecUsername="
				+ pecUsername + ", pecPassword=" + pecPassword + ", pecHostIn=" + pecHostIn + ", pecHostOut="
				+ pecHostOut + ", pecProtocolloIn=" + pecProtocolloIn + ", pecProtocolloOut=" + pecProtocolloOut
				+ ", pecTipoProtocolloIn=" + pecTipoProtocolloIn + ", pecTipoProtocolloOut=" + pecTipoProtocolloOut
				+ ", pecSslIn=" + pecSslIn + ", pecSslOut=" + pecSslOut + ", pecTlsIn=" + pecTlsIn + ", pecTlsOut="
				+ pecTlsOut + ", pecPortaSslIn=" + pecPortaSslIn + ", pecPortaSslOut=" + pecPortaSslOut
				+ ", pecPortaTlsIn=" + pecPortaTlsIn + ", pecPortaTlsOut=" + pecPortaTlsOut + ", pecStartTlsIn="
				+ pecStartTlsIn + ", pecStartTlsOut=" + pecStartTlsOut + ", pecAutenticazioneIn=" + pecAutenticazioneIn
				+ ", pecAutenticazioneOut=" + pecAutenticazioneOut + "]";
	}

	
	public ConfigurazioneCasellaPostaleDto toCasellaPostaleDTO()
	{
		ConfigurazioneCasellaPostaleDto other = new ConfigurazioneCasellaPostaleDto();
		
		CasellaPostaleDto caspost = new CasellaPostaleDto();
		caspost.setIndirizzoMail(getPecIndirizzo());
		caspost.setNomeVisualizzato(getPecNome());
		caspost.setUsername(getPecUsername());
		caspost.setPassword(getPecPassword());
		caspost.setPec(true);
		
		ConfigMailInDto confIn = new ConfigMailInDto();
		confIn.setHost(getPecHostIn());
		confIn.setProtocollo(MailInEnum.valueOf(getPecTipoProtocolloIn().toString()));
		confIn.setRichiedeSsl(getPecSslIn());
		confIn.setPorta(getPecPortaSslIn());
		confIn.setRichiedeStarttsl(getPecSslIn());
		confIn.setRichiedeAutenticazione(getPecAutenticazioneIn());
		confIn.setImap(!confIn.getProtocollo().equals(MailInEnum.POP3));
		
		ConfigMailOutDto confOut = new ConfigMailOutDto();
		confOut.setHost(getPecHostOut());
		confOut.setProtocollo(MailOutEnum.valueOf(getPecTipoProtocolloOut().toString()));
		confOut.setRichiedeSsl(getPecSslOut());
		confOut.setPortaSsl(getPecPortaSslOut());
		confOut.setPortaTls(getPecPortaTlsOut());
		confOut.setRichiedeStarttsl(getPecSslOut());
		confOut.setRichiedeAutenticazione(getPecAutenticazioneOut());
	
		other.setCasellaPostale(caspost);
		other.setConfigurazioneMailIngresso(confIn);
		other.setConfigurazioneMailUscita(confOut);
		
		return other;
	}
	
}
