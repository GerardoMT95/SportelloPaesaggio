package it.eng.tz.puglia.autpae.utility;

public class Stringhe {
	
	private String stringa;
	
	public Stringhe(String stringa) {
		this.stringa = stringa;
	}

	public String getStringa() {
		return stringa;
	}

	public void setStringa(String stringa) {
		this.stringa = stringa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stringa == null) ? 0 : stringa.hashCode());
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
		Stringhe other = (Stringhe) obj;
		if (stringa == null) {
			if (other.stringa != null)
				return false;
		} else if (!stringa.equals(other.stringa))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Stringhe [stringa=" + stringa + "]";
	}

	
	public static String apicizza(String stringa) {
		return "'"+stringa+"'";
	}
	
}