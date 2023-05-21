package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

public class JasperAltroTitolareDto extends JasperRichiedenteDto
{
	private String titolaritaInQualitaDi;
	private String descrizioneAltro;
	
	public String getTitolaritaInQualitaDi()
	{
		return titolaritaInQualitaDi;
	}
	public void setTitolaritaInQualitaDi(String titolaritaInQualitaDi)
	{
		this.titolaritaInQualitaDi = titolaritaInQualitaDi;
	}
	
	public String getDescrizioneAltro()
	{
		return descrizioneAltro;
	}
	public void setDescrizioneAltro(String descrizioneAltro)
	{
		this.descrizioneAltro = descrizioneAltro;
	}
}
