package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils;

import java.util.Comparator;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.GenericPlainValueLabel;

public class CustomPlainValueLabelComparator<T extends GenericPlainValueLabel<?>> implements Comparator<T>
{
	@Override
	public int compare(T o1, T o2)
	{
		return o1.getDescription().compareTo(o2.getDescription());
	}
}
