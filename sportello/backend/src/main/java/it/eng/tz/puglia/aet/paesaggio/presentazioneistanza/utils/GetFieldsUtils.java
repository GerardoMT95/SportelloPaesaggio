package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils;

import java.lang.reflect.Field;

public class GetFieldsUtils
{
	public static Object getField(Object value, String name)
	{
		Object toreturn = null;
		try
		{
			Field field = value.getClass().getDeclaredField(name);
			field.setAccessible(true);
			toreturn = field.get(value);
		}catch(Exception e)
		{
			//....
		}
		return toreturn;
	}
}
