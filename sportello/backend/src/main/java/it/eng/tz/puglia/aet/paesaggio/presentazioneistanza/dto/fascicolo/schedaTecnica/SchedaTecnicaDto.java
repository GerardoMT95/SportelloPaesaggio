package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class SchedaTecnicaDto extends PraticaChild
{
	private static final long serialVersionUID = 3713781621648392287L;
	
	private SchedaTecnicaDescrizioneDto descrizione;
	private List<DestinazioneUrbanisticaDto> destinazione;
	private LegittimitaDto legittimita;
	private ProcedureEdilizieDto procedureEdilizie;
	private InquadramentoDto inquadramento;
	private EffettiMitigazioneDto effetiMitigazione;
    private ProcedimentoContenziosoDto eventualiProcedimenti;
    private DichiarazioneDto dichiarazione;
    private PptrDto pptr;
    private PareriAttiDto parreriEAtti;
    private VincolisticaDto vincolistica;
//  private boolean dichiara;
    private Boolean valida;
    
    public SchedaTecnicaDto()
    {
    	super();
    }

    @JsonProperty(access=Access.WRITE_ONLY)
    @Override
    public UUID getIdPratica()
    {
    	return super.getIdPratica();
    }

	public SchedaTecnicaDescrizioneDto getDescrizione()
	{
		return descrizione;
	}
	public void setDescrizione(SchedaTecnicaDescrizioneDto descrizione)
	{
		this.descrizione = descrizione;
	}

	public List<DestinazioneUrbanisticaDto> getDestinazione()
	{
		return destinazione;
	}
	public void setDestinazione(List<DestinazioneUrbanisticaDto> destinazione)
	{
		this.destinazione = destinazione;
	}

	public LegittimitaDto getLegittimita()
	{
		return legittimita;
	}
	public void setLegittimita(LegittimitaDto legittimita)
	{
		this.legittimita = legittimita;
	}

	public ProcedureEdilizieDto getProcedureEdilizie()
	{
		return procedureEdilizie;
	}
	public void setProcedureEdilizie(ProcedureEdilizieDto procedureEdilizie)
	{
		this.procedureEdilizie = procedureEdilizie;
	}

	public PptrDto getPptr()
	{
		return pptr;
	}
	public void setPptr(PptrDto pptr)
	{
		this.pptr = pptr;
	}

	public InquadramentoDto getInquadramento()
	{
		return inquadramento;
	}
	public void setInquadramento(InquadramentoDto inquadramento)
	{
		this.inquadramento = inquadramento;
	}

	public EffettiMitigazioneDto getEffetiMitigazione()
	{
		return effetiMitigazione;
	}
	public void setEffetiMitigazione(EffettiMitigazioneDto effetiMitigazione)
	{
		this.effetiMitigazione = effetiMitigazione;
	}

	public ProcedimentoContenziosoDto getEventualiProcedimenti()
	{
		return eventualiProcedimenti;
	}
	public void setEventualiProcedimenti(ProcedimentoContenziosoDto eventualiProcedimenti)
	{
		this.eventualiProcedimenti = eventualiProcedimenti;
	}

	public PareriAttiDto getParreriEAtti() {
		return parreriEAtti;
	}

	public void setParreriEAtti(PareriAttiDto parreriEAtti) {
		this.parreriEAtti = parreriEAtti;
	}

	public VincolisticaDto getVincolistica()
	{
		return vincolistica;
	}
	public void setVincolistica(VincolisticaDto vincolistica)
	{
		this.vincolistica = vincolistica;
	}

//	public boolean isDichiara()
//	{
//		return dichiara;
//	}
//	public void setDichiara(boolean dichiara)
//	{
//		this.dichiara = dichiara;
//	}

	public Boolean getValida()
	{
		return valida;
	}
	public void setValida(Boolean valida)
	{
		this.valida = valida;
	}

	public DichiarazioneDto getDichiarazione() {
		return dichiarazione;
	}

	public void setDichiarazione(DichiarazioneDto dichiarazione) {
		this.dichiarazione = dichiarazione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((destinazione == null) ? 0 : destinazione.hashCode());
//		result = prime * result + (dichiara ? 1231 : 1237);
		result = prime * result + ((dichiarazione == null) ? 0 : dichiarazione.hashCode());
		result = prime * result + ((effetiMitigazione == null) ? 0 : effetiMitigazione.hashCode());
		result = prime * result + ((eventualiProcedimenti == null) ? 0 : eventualiProcedimenti.hashCode());
		result = prime * result + ((inquadramento == null) ? 0 : inquadramento.hashCode());
		result = prime * result + ((legittimita == null) ? 0 : legittimita.hashCode());
		result = prime * result + ((parreriEAtti == null) ? 0 : parreriEAtti.hashCode());
		result = prime * result + ((pptr == null) ? 0 : pptr.hashCode());
		result = prime * result + ((procedureEdilizie == null) ? 0 : procedureEdilizie.hashCode());
		result = prime * result + ((valida == null) ? 0 : valida.hashCode());
		result = prime * result + ((vincolistica == null) ? 0 : vincolistica.hashCode());
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
		SchedaTecnicaDto other = (SchedaTecnicaDto) obj;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (destinazione == null) {
			if (other.destinazione != null)
				return false;
		} else if (!destinazione.equals(other.destinazione))
			return false;
//		if (dichiara != other.dichiara)
//			return false;
		if (dichiarazione == null) {
			if (other.dichiarazione != null)
				return false;
		} else if (!dichiarazione.equals(other.dichiarazione))
			return false;
		if (effetiMitigazione == null) {
			if (other.effetiMitigazione != null)
				return false;
		} else if (!effetiMitigazione.equals(other.effetiMitigazione))
			return false;
		if (eventualiProcedimenti == null) {
			if (other.eventualiProcedimenti != null)
				return false;
		} else if (!eventualiProcedimenti.equals(other.eventualiProcedimenti))
			return false;
		if (inquadramento == null) {
			if (other.inquadramento != null)
				return false;
		} else if (!inquadramento.equals(other.inquadramento))
			return false;
		if (legittimita == null) {
			if (other.legittimita != null)
				return false;
		} else if (!legittimita.equals(other.legittimita))
			return false;
		if (parreriEAtti == null) {
			if (other.parreriEAtti != null)
				return false;
		} else if (!parreriEAtti.equals(other.parreriEAtti))
			return false;
		if (pptr == null) {
			if (other.pptr != null)
				return false;
		} else if (!pptr.equals(other.pptr))
			return false;
		if (procedureEdilizie == null) {
			if (other.procedureEdilizie != null)
				return false;
		} else if (!procedureEdilizie.equals(other.procedureEdilizie))
			return false;
		if (valida == null) {
			if (other.valida != null)
				return false;
		} else if (!valida.equals(other.valida))
			return false;
		if (vincolistica == null) {
			if (other.vincolistica != null)
				return false;
		} else if (!vincolistica.equals(other.vincolistica))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SchedaTecnicaDto [descrizione=" + descrizione + ", destinazione=" + destinazione + ", legittimita="
				+ legittimita + ", procedureEdilizie=" + procedureEdilizie + ", inquadramento=" + inquadramento
				+ ", effetiMitigazione=" + effetiMitigazione + ", eventualiProcedimenti=" + eventualiProcedimenti
				+ ", dichiarazione=" + dichiarazione + ", pptr=" + pptr + ", parreriEAtti=" + parreriEAtti
				+ ", vincolistica=" + vincolistica + ", valida=" + valida + "]";
	}
    
}