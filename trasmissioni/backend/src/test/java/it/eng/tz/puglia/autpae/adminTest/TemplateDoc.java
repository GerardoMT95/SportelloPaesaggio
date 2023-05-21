package it.eng.tz.puglia.autpae.adminTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.eng.tz.puglia.autpae.entity.TemplateDocDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoSezioneDoc;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDocService;

@RunWith(SpringRunner.class)
@SpringBootTest

public class TemplateDoc {
		private static final Logger LOGGER = LoggerFactory.getLogger(TemplateDoc.class);
		
		@Autowired 
		TemplateDocService templateDocSvc;
		
		@Test
		public void testCrud() throws Exception {
			List<TemplateDocDTO> lista = templateDocSvc.getAll();
			lista.forEach(template->{
				template.getSezioni().forEach(sezione->{
					if(!sezione.getTipoSezione().equals(TipoSezioneDoc.IMAGE)) {
						sezione.setValore(sezione.getValore()+"addedd");
					}
				});
			});
			lista.forEach(template->{
				this.templateDocSvc.salva(template);
			});
			lista.forEach(template->{
				try {
					this.templateDocSvc.reset(template.getNome());
				} catch (Exception e) {
					System.out.println("Errore durante il reset:");
					e.printStackTrace();
				}
			});
		}


}
