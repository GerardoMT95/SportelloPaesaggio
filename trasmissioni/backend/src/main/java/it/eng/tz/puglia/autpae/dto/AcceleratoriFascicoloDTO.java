package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

public class AcceleratoriFascicoloDTO implements Serializable
{
	private static final long serialVersionUID = 1007265991533749011L;
	private long nWorking;
	private long nTrasmitted;
	private long nCancelled;
	private long nSelected;
	private long nFinished;
	private long nOnModify;
	
	public AcceleratoriFascicoloDTO()
	{
		super();
	}
	
	public long getnWorking()
	{
		return nWorking;
	}
	public void setnWorking(long nWorking)
	{
		this.nWorking = nWorking;
	}
	
	public long getnTrasmitted()
	{
		return nTrasmitted;
	}
	public void setnTrasmitted(long nTrasmitted)
	{
		this.nTrasmitted = nTrasmitted;
	}
	
	public long getnCancelled()
	{
		return nCancelled;
	}
	public void setnCancelled(long nCancelled)
	{
		this.nCancelled = nCancelled;
	}
	
	public long getnSelected()
	{
		return nSelected;
	}
	public void setnSelected(long nSelected)
	{
		this.nSelected = nSelected;
	}
	
	public long getnFinished()
	{
		return nFinished;
	}
	public void setnFinished(long nFinished)
	{
		this.nFinished = nFinished;
	}
	
	public long getnOnModify()
	{
		return nOnModify;
	}
	public void setnOnModify(long nOnModify)
	{
		this.nOnModify = nOnModify;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (nCancelled ^ (nCancelled >>> 32));
		result = prime * result + (int) (nFinished ^ (nFinished >>> 32));
		result = prime * result + (int) (nOnModify ^ (nOnModify >>> 32));
		result = prime * result + (int) (nSelected ^ (nSelected >>> 32));
		result = prime * result + (int) (nTrasmitted ^ (nTrasmitted >>> 32));
		result = prime * result + (int) (nWorking ^ (nWorking >>> 32));
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
		AcceleratoriFascicoloDTO other = (AcceleratoriFascicoloDTO) obj;
		if (nCancelled != other.nCancelled)
			return false;
		if (nFinished != other.nFinished)
			return false;
		if (nOnModify != other.nOnModify)
			return false;
		if (nSelected != other.nSelected)
			return false;
		if (nTrasmitted != other.nTrasmitted)
			return false;
		if (nWorking != other.nWorking)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "AcceleratoriFascicoloDTO [nWorking=" + nWorking + ", nTrasmitted=" + nTrasmitted + ", nCancelled="
				+ nCancelled + ", nSelected=" + nSelected + ", nFinished=" + nFinished + ", nOnModify=" + nOnModify
				+ "]";
	}
}
