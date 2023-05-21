package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;

public class JasperUlterioreInformazioniDto
{
	private List<StringWrapper> bpParchiEReserve; 
	private List<StringWrapper> ucpPaesaggioRurale;
	private List<StringWrapper> bpImmobileAreePubblico;
	
	public List<StringWrapper> getBpParchiEReserve()
	{
		return bpParchiEReserve;
	}
	public void setBpParchiEReserve(List<StringWrapper> bpParchiEReserve)
	{
		this.bpParchiEReserve = bpParchiEReserve;
	}
	
	public List<StringWrapper> getUcpPaesaggioRurale()
	{
		return ucpPaesaggioRurale;
	}
	public void setUcpPaesaggioRurale(List<StringWrapper> ucpPaesaggioRurale)
	{
		this.ucpPaesaggioRurale = ucpPaesaggioRurale;
	}
	
	public List<StringWrapper> getBpImmobileAreePubblico()
	{
		return bpImmobileAreePubblico;
	}
	public void setBpImmobileAreePubblico(List<StringWrapper> bpImmobileAreePubblico)
	{
		this.bpImmobileAreePubblico = bpImmobileAreePubblico;
	}
}
