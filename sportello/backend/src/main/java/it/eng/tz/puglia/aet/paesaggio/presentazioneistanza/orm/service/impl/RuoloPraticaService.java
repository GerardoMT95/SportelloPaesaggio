package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service CRUD for table presentazione_istanza.ruolo_pratica
 */
@Service
public class RuoloPraticaService  implements IRuoloPraticaService{

    /**
     * dao
     */
    @Autowired
    private RuoloPraticaRepository dao;

  
    @Override
    @Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, timeout=60000)
    public List<PlainStringValueLabel> select(){
    	return dao.select()
    			.stream()
    			.map(rp->new PlainStringValueLabel(rp.getId(), rp.getDescrizione())).collect(Collectors.toList());
    }
}
