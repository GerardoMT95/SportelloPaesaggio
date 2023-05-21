package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.TipoProcedimentoDTO;

public class FascicoloTabDTO extends FascicoloDTO implements Serializable
{
	private static final long serialVersionUID = 6985990040232694483L;
	
	private List<TipologicaDTO> uffici;
	private List<TipoProcedimentoDTO> tipiProcedimento;
	private String avviso;
	
	
	public FascicoloTabDTO() {
		super();
		this.uffici = new ArrayList<TipologicaDTO>();
		this.tipiProcedimento = new ArrayList<TipoProcedimentoDTO>();
	}

	public FascicoloTabDTO(List<TipologicaDTO> uffici, List<TipoProcedimentoDTO> tipiProcedimento) {
		super();
		this.uffici = uffici;
		this.tipiProcedimento = tipiProcedimento;
	}
	
	public FascicoloTabDTO(FascicoloDTO fascicoloDTO) {
		super(fascicoloDTO);
		this.uffici = new ArrayList<TipologicaDTO>();
		this.tipiProcedimento = new ArrayList<TipoProcedimentoDTO>();
	}
	
	public FascicoloTabDTO(FascicoloTabDTO fascicoloTabDTO) {
		super(fascicoloTabDTO);
		this.uffici = fascicoloTabDTO.getUffici();
		this.tipiProcedimento = fascicoloTabDTO.getTipiProcedimento();
		this.avviso = fascicoloTabDTO.getAvviso();
	}

	public List<TipologicaDTO> getUffici() {
		return uffici;
	}

	public void setUffici(List<TipologicaDTO> uffici) {
		this.uffici = uffici;
	}

	public List<TipoProcedimentoDTO> getTipiProcedimento() {
		return tipiProcedimento;
	}

	public void setTipiProcedimento(List<TipoProcedimentoDTO> tipiProcedimento) {
		this.tipiProcedimento = tipiProcedimento;
	}

	public String getAvviso() {
		return avviso;
	}

	public void setAvviso(String avviso) {
		this.avviso = avviso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((avviso == null) ? 0 : avviso.hashCode());
		result = prime * result + ((tipiProcedimento == null) ? 0 : tipiProcedimento.hashCode());
		result = prime * result + ((uffici == null) ? 0 : uffici.hashCode());
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
		FascicoloTabDTO other = (FascicoloTabDTO) obj;
		if (avviso == null) {
			if (other.avviso != null)
				return false;
		} else if (!avviso.equals(other.avviso))
			return false;
		if (tipiProcedimento == null) {
			if (other.tipiProcedimento != null)
				return false;
		} else if (!tipiProcedimento.equals(other.tipiProcedimento))
			return false;
		if (uffici == null) {
			if (other.uffici != null)
				return false;
		} else if (!uffici.equals(other.uffici))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FascicoloTabDTO [uffici=" + uffici + ", tipiProcedimento=" + tipiProcedimento + ", avviso=" + avviso + "]";
	}

}
