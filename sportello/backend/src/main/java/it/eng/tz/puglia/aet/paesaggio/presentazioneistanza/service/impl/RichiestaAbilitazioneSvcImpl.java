package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.RichiestaAbilitazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.RichiestaAbilitazioneRichiedenteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AllowedMimeType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.impl.RemoteSchemaServiceImpl;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.RichiestaAbilitazioneSvc;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.service.http.IHttpAbilitazioneClientService;
import it.eng.tz.puglia.service.http.bean.DatiRichiestaAbilitazione;
import it.eng.tz.puglia.service.http.bean.PropertyType;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class RichiestaAbilitazioneSvcImpl  implements RichiestaAbilitazioneSvc{
	
	private static final Logger log = LoggerFactory.getLogger(RichiestaAbilitazioneSvcImpl.class);

	@Value("${richiesta.abilitazione.maxSizeAllegato:1048576}")
	private long maxSizeAllegato;
	
	@Value("${pm.codice.applicazione}")
	private String codiceApplicazionePM;
	
	@Autowired
	IHttpAbilitazioneClientService abilitSvc;
	@Autowired
	RemoteSchemaService commonSvc;
	
	private void addField(List<DatiRichiestaAbilitazione> formData,String label,PropertyType tipo,Object value) {
		DatiRichiestaAbilitazione field=new DatiRichiestaAbilitazione();
		field.setLabel(label);
		field.setType(tipo);
		field.setValue(value);
		formData.add(field);
	}
	
	@Override
	public void sendRichiesta(RichiestaAbilitazioneRichiedenteDto richData,MultipartFile file) throws Exception {
		List<DatiRichiestaAbilitazione> formData=new ArrayList<DatiRichiestaAbilitazione>();
		addField(formData,"Nazionalità residenza",PropertyType.TEXT,richData.getStato());
		addField(formData,"comune residenza",PropertyType.TEXT,richData.getComune());
		addField(formData,"provincia residenza",PropertyType.TEXT,richData.getProvincia());
		addField(formData,"città estera residenza",PropertyType.TEXT,richData.getCitta());
		addField(formData,"indirizzo residenza",PropertyType.TEXT,richData.getVia());
		addField(formData,"n.civico residenza",PropertyType.TEXT,richData.getN());
		addField(formData,"CAP residenza",PropertyType.TEXT,richData.getCap());
		addField(formData,"PEC",PropertyType.TEXT,richData.getPec());
		addField(formData,"Tipo documento riconoscimento",PropertyType.TEXT,richData.getTipo());
		addField(formData,"Numero documento riconoscimento",PropertyType.TEXT,richData.getNumero());
		addField(formData,"Data rilascio documento ric.",PropertyType.DATE,richData.getRilasciatoIl());
		addField(formData,"Ente rilascio documento ric.",PropertyType.TEXT,richData.getRilasciatoDa());
		addField(formData,"Data scadenza documento ric.",PropertyType.DATE,richData.getDataScadenza());
		addField(formData,"Allegato",PropertyType.FILE,file);
		abilitSvc.nuovaRichiesta(List.of("RICHIEDENTI"), formData);
	}
	
	@Override
	public void sendRichiesta(RichiestaAbilitazioneDto richData,MultipartFile file) throws Exception {
		List<DatiRichiestaAbilitazione> formData=new ArrayList<DatiRichiestaAbilitazione>();
		addField(formData,"Nome",PropertyType.TEXT,richData.getNome());
		addField(formData,"Cognome",PropertyType.TEXT,richData.getCognome());
		addField(formData,"Email",PropertyType.TEXT,richData.getEmail());
		addField(formData,"Telefono",PropertyType.TEXT,richData.getTelefono());
		addField(formData,"Fax",PropertyType.TEXT,richData.getFax());
		addField(formData,"Nome Responsabile ",PropertyType.TEXT,richData.getCapoNome());
		addField(formData,"Cognome Responsabile ",PropertyType.TEXT,richData.getCapoCognome());
		addField(formData,"Email Responsabile ",PropertyType.TEXT,richData.getCapoEmail());
		addField(formData,"Telefono Responsabile ",PropertyType.TEXT,richData.getCapoTelefono());
		addField(formData,"titolarita responsabile (in qualità di) ",PropertyType.TEXT,richData.getInQualitaDi());
		addField(formData,"dichiarazione privacy accettata",PropertyType.BOOLEAN,richData.getPrivacyAccettato());
		addField(formData,"presa visione dichiarazioni mendaci ",PropertyType.BOOLEAN,richData.getDichMendaciAccettato());
		addField(formData,"Allegato attestazione del responsabile",PropertyType.FILE,file);
		List<String> codiciGruppi =new ArrayList<>();
		codiciGruppi.add(richData.getOrganizzazione()+"_"+richData.getRuolo());
		Long ret=abilitSvc.nuovaRichiesta(codiciGruppi, formData);
		if(ret!=null) {
			//salvo l'utenza in common.utente e in utente_attribute l'id della richiesta di abilitazione
			try {
				this.commonSvc.aggiornaUtenteCommon(ret, codiceApplicazionePM, SecurityUtil.getUsername(),richData.getEmail(),richData.getTelefono(),richData.getFax());
			}catch(Exception e) {
				log.error("Errore durante l'aggiornamento utente in common",e);
			}
		}
		
	}
	
	@Override
	public void validaRichiesta(RichiestaAbilitazioneDto richData,MultipartFile file) throws CustomOperationToAdviceException{
		List<String> campiErrati=new ArrayList<>();
		if(StringUtil.isEmpty(richData.getNome())) {
			campiErrati.add("Nome");
		}
		if(StringUtil.isEmpty(richData.getCognome())) {
			campiErrati.add("Cognome");
		}
		if(StringUtil.isEmpty(richData.getEmail())) {
			campiErrati.add("Email");
		}
		if(StringUtil.isEmpty(richData.getTelefono())) {
			campiErrati.add("Telefono");
		}
		if(StringUtil.isEmpty(richData.getCapoNome())) {
			campiErrati.add("Nome Responsabile");
		}
		if(StringUtil.isEmpty(richData.getCapoCognome())) {
			campiErrati.add("Cognome Responsabile");
		}
		if(StringUtil.isEmpty(richData.getCapoEmail())) {
			campiErrati.add("Email Responsabile");
		}
		if(StringUtil.isEmpty(richData.getCapoTelefono())) {
			campiErrati.add("Telefono Responsabile");
		}
		if(StringUtil.isEmpty(richData.getInQualitaDi())) {
			campiErrati.add("titolarita responsabile (in qualità di) ");
		}
		if(!AllowedMimeType.isMimeTypeSolaLettura(file.getContentType())) {
			campiErrati.add("File allegato di tipo non ammesso (PDF,PNG,JPEG)");
		}
		if(file.getSize()>maxSizeAllegato) {
			campiErrati.add("L'allegato supera la dimensione massima ammessa di "+maxSizeAllegato+" byte");
    	}
		if(campiErrati.size()>0) {
			throw new CustomOperationToAdviceException("Campi invalidi: "+campiErrati);
		}
	}
	
}
