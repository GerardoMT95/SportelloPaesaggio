package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.Date;
import java.util.List;

public class JasperLocalizzazioneDto
{
	private String comune;
	private String sigla;
    private String indirizzo;
    private String numCivico;
    private String piano;
    private String interno;
    private String destUsoAtt;
    private String destUsoProg;
    private Date dataRiferimentoCatastale;    

    private List<JasperParticellaDto> particelle;    
    private List<JasperUlterioreInformazioniDto> ulterioriInformazioni;
    
    public JasperLocalizzazioneDto()
    {
    	super();
    }
    
	public String getComune()
	{
		return comune;
	}
	public void setComune(String comune)
	{
		this.comune = comune;
	}
	
	public String getSigla()
	{
		return sigla;
	}
	public void setSigla(String sigla)
	{
		this.sigla = sigla;
	}
	
	public String getIndirizzo()
	{
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo)
	{
		this.indirizzo = indirizzo;
	}
	
	public String getNumCivico()
	{
		return numCivico;
	}
	public void setNumCivico(String numCivico)
	{
		this.numCivico = numCivico;
	}
	
	public String getPiano()
	{
		return piano;
	}
	public void setPiano(String piano)
	{
		this.piano = piano;
	}
	
	public String getInterno()
	{
		return interno;
	}
	public void setInterno(String interno)
	{
		this.interno = interno;
	}
	
	public String getDestUsoAtt()
	{
		return destUsoAtt;
	}
	public void setDestUsoAtt(String destUsoAtt)
	{
		this.destUsoAtt = destUsoAtt;
	}
	
	public String getDestUsoProg()
	{
		return destUsoProg;
	}
	public void setDestUsoProg(String destUsoProg)
	{
		this.destUsoProg = destUsoProg;
	}
	
	public Date getDataRiferimentoCatastale()
	{
		return dataRiferimentoCatastale;
	}
	public void setDataRiferimentoCatastale(Date dataRiferimentoCatastale)
	{
		this.dataRiferimentoCatastale = dataRiferimentoCatastale;
	}
	
	public List<JasperParticellaDto> getParticelle()
	{
		return particelle;
	}
	public void setParticelle(List<JasperParticellaDto> particelle)
	{
		this.particelle = particelle;
	}
	public List<JasperUlterioreInformazioniDto> getUlterioriInformazioni()
	{
		return ulterioriInformazioni;
	}
	public void setUlterioriInformazioni(List<JasperUlterioreInformazioniDto> ulterioriInformazioni)
	{
		this.ulterioriInformazioni = ulterioriInformazioni;
	}
    
}
