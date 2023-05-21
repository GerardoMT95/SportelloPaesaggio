package it.eng.tz.puglia.autpae.service;
import java.sql.Timestamp;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.puglia.autpae.generated.orm.dto.ConfigurazioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.repository.ConfigurazioneRepository;
import it.eng.tz.puglia.autpae.service.interfacce.IConfigurazioneService;
import it.eng.tz.puglia.util.date.DateUtil;



@Service
public class ConfigurazioneService implements IConfigurazioneService{
	
	/**
	 * quando deserializza nel caso di nuove properties che non vengono trovate nelle vecchie versioni del bean, le setterà a null
	 */
	private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
	@Autowired
	private ConfigurazioneRepository dao;

	
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public <T> T findConfigurazioneCorrente(Date data,Class<T> tipo) throws Exception
    {
		ConfigurazioneDTO conf = dao.findByChiaveEData(tipo.getCanonicalName(), data);
    	return mapper.readValue(conf.getValore(), tipo);
    }


	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public <T> ConfigurazioneDTO upsertConfigurazione(T objConf, Class<T> tipo,String userUpdated) throws Exception {
		Timestamp current = DateUtil.currentTimestamp();
		Timestamp nextTimestamp = new Timestamp(current.getTime()+1);
		ConfigurazioneDTO conf = dao.findByChiaveLast(tipo.getCanonicalName());
		conf.setUserUpdate(userUpdated);
		conf.setEndDate(current);
		dao.update(conf);
		//setto per nuovo inserimento
		conf.setStartDate(nextTimestamp);
		conf.setEndDate(null);
		conf.setUserCreate(userUpdated);
		conf.setUserUpdate(null);
		conf.setValore(mapper.writeValueAsString(objConf));
		long newId = dao.insert(conf);
		conf.setId(newId);
		return conf;
	}
	
}
