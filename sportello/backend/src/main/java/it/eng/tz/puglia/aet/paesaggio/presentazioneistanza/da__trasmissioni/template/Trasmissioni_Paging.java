package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template;

import java.io.Serializable;

public abstract class Trasmissioni_Paging implements Serializable
{
	private static final long serialVersionUID = 3256792155908943762L;
	
	private int pagina;
	private int limite;
	
	public Trasmissioni_Paging()
	{
		this.pagina = 1;
		this.limite = 200;
	}

	public int getPage() { 			    // modificato per FE
		return pagina;
	}

	public void setPage(int pagina) {   // modificato per FE
		this.pagina = pagina;
	}

	public int getLimit() { 		    // modificato per FE
		return limite;
	}

	public void setLimit(int limite) {  // modificato per FE
		this.limite = limite;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + limite;
		result = prime * result + pagina;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trasmissioni_Paging other = (Trasmissioni_Paging) obj;
		if (limite != other.limite)
			return false;
		if (pagina != other.pagina)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Paging [pagina=" + pagina + ", limite=" + limite + "]";
	}
	
}
