package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.geo.util.esri.EsriBBox;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.string.StringUtil;

public class PlainNumberValueLabelDeserializer extends JsonDeserializer<PlainNumberValueLabel>{

	/**
	 * logger
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(PlainNumberValueLabelDeserializer.class);

	private final ObjectMapper defaultMapper = new ObjectMapper();
	
	
	/**
	 * per fixare sui form stato - provincia - città in cui mi puo' tornare "" al posto di null...
	 * @author 
	 * @date 28/10/2020
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public PlainNumberValueLabel deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		 final JsonToken type = parser.currentToken();
	        switch (type) {
	            case VALUE_NULL:
	                return null;
	            case VALUE_STRING:
	                return null; // TODO: Should check whether it is empty
	            case START_OBJECT:
	                return ctxt.readValue(parser,PlainNumberValueLabel.class );
	            default:
	                throw new IllegalArgumentException("Unsupported JsonToken type: " + type);
	        }
	}
	

}


