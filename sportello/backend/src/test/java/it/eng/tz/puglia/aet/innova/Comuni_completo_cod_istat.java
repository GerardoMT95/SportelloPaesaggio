package it.eng.tz.puglia.aet.innova;

import java.io.Serializable;


//@Entity
//@Table(name="Comuni_completo_cod_istat")
public class Comuni_completo_cod_istat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@Id	
	//@Column(name="OBJECTID")
	private long objectid;
	
	//@Column(name="COMUNE")
	private String comune;
	
	//@Column(name="PROVINCIA")
	private String provincia;	            
	              
	//@Column(name="CODICE_CATASTALE")
	private String codiceCatastale;
	
	//@Column(name="ISTAT_6_PROV")
	private String istat6province;

	public long getObjectid() {
		return objectid;
	}

	public void setObjectid(long objectid) {
		this.objectid = objectid;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCodiceCatastale() {
		return codiceCatastale;
	}

	public void setCodiceCatastale(String codiceCatastale) {
		this.codiceCatastale = codiceCatastale;
	}

	public String getIstat6province() {
		return istat6province;
	}

	public void setIstat6province(String istat6province) {
		this.istat6province = istat6province;
	}
	
	public String getXmlItem()
	{
		String return_ = "";
		return_ = return_ + "<item>\n";
		if (comune != null)
			return_ = return_ + "<element key=\"comune\" value=\"" + this.getComune() + "\"/>\n";
		else 
			return_ = return_ + "<element key=\"comune\" value=\"\"/>\n";
		if (provincia != null)
			return_ = return_ + "<element key=\"provincia\" value=\"" + this.getProvincia()+ "\"/>\n";
		else
			return_ = return_ + "<element key=\"provincia\" value=\"\"/>\n";
		if (codiceCatastale != null)
			return_ = return_ + "<element key=\"codiceCatastale\" value=\"" + this.getCodiceCatastale()+ "\"/>\n";
		else
			return_ = return_ + "<element key=\"codiceCatastale\" value=\"\"/>\n";
		if (istat6province != null)
			return_ = return_ + "<element key=\"istat\" value=\"" + this.getIstat6province()+ "\"/>\n";
		else 
			return_ = return_ + "<element key=\"istat\" value=\"\"/>\n";
		return_= return_ + "</item>\n";
        return return_;
	}
	
	/**
	 * Costruttore senza parametri
	 */
	public Comuni_completo_cod_istat() {}
	
	
	/**
	 * Costruttore con parametri nel seguente ordine 
	 * OBJECTID , COMUNE , PROVINCIA , CODICE_CATASTALE , ISTAT_6_PROV
	 * 
	 * @param obj
	 */
	public Comuni_completo_cod_istat( Object[] obj ) 
	{
		try 
		{
			String tmp = obj[0].toString();
			if( tmp != null )
			{
				this.setObjectid( Long.valueOf( tmp ).longValue() );  
			}
			
			tmp = obj[1].toString();
			if( tmp != null )
			{
				this.setComune( tmp );  
			}
			
			tmp = obj[2].toString();
			if( tmp != null )
			{
				this.setProvincia( tmp );  
			}
			
			tmp = obj[3].toString();
			if( tmp != null )
			{
				this.setCodiceCatastale( tmp );  
			}
			
			tmp = obj[4].toString();
			if( tmp != null )
			{
				this.setIstat6province( tmp );  
			}
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}		
	}
}


