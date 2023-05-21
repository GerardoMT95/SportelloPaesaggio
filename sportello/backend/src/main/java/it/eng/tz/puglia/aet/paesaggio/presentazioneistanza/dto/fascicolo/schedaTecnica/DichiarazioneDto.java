package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DichiarazioneDTO;

public class DichiarazioneDto extends PraticaChild
{
	private static final long serialVersionUID = 6419748386310737591L;
	
    private String testo;
    private String labelCb;
    private boolean accettata;
    
    
    public DichiarazioneDto() { }
    
    public DichiarazioneDto(DichiarazioneDTO entity, boolean accettata) {
    	this.testo = entity.getTesto();
    	this.labelCb = entity.getLabelCb();
    	this.accettata = accettata;
    }
    
    
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public String getLabelCb() {
		return labelCb;
	}
	public void setLabelCb(String labelCb) {
		this.labelCb = labelCb;
	}
	public boolean isAccettata() {
		return accettata;
	}
	public void setAccettata(boolean accettata) {
		this.accettata = accettata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (accettata ? 1231 : 1237);
		result = prime * result + ((labelCb == null) ? 0 : labelCb.hashCode());
		result = prime * result + ((testo == null) ? 0 : testo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DichiarazioneDto other = (DichiarazioneDto) obj;
		if (accettata != other.accettata)
			return false;
		if (labelCb == null) {
			if (other.labelCb != null)
				return false;
		} else if (!labelCb.equals(other.labelCb))
			return false;
		if (testo == null) {
			if (other.testo != null)
				return false;
		} else if (!testo.equals(other.testo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "DichiarazioneDto [testo=" + testo + ", labelCb=" + labelCb + ", accettata=" + accettata + "]";
	}
    
}