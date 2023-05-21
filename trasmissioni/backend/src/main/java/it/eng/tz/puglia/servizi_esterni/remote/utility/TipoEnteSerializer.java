package it.eng.tz.puglia.servizi_esterni.remote.utility;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TipoEnteSerializer extends JsonSerializer<TipoEnte>
{
	@Override
	public void serialize(TipoEnte value, JsonGenerator gen, SerializerProvider serializers) throws IOException
	{
		if(value == null)
			gen.writeNull();
		else
			gen.writeString(value.getCodice());
	}

}
