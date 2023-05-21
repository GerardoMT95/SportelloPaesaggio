package it.eng.tz.puglia.autpae.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.civilia.migration.repository.PraticaRepository;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.dbMapping.FascicoloIntervento;
import it.eng.tz.puglia.autpae.dto.InterventoTabDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloInterventoDTO;
import it.eng.tz.puglia.autpae.entity.ProcedimentoQualificazioniDTO;
import it.eng.tz.puglia.autpae.entity.TipiEQualificazioniDTO;
import it.eng.tz.puglia.autpae.enumeratori.ErroriValidazioneBE;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.repository.FascicoloFullRepository;
import it.eng.tz.puglia.autpae.search.FascicoloInterventoSearch;
import it.eng.tz.puglia.autpae.search.ProcedimentoQualificazioniSearch;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloInterventoService;
import it.eng.tz.puglia.autpae.service.interfacce.InterventoService;
import it.eng.tz.puglia.autpae.service.interfacce.ProcedimentoQualificazioniService;
import it.eng.tz.puglia.autpae.service.interfacce.TipiEQualificazioniService;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class InterventoServiceImpl implements InterventoService {

	private static final Logger log = LoggerFactory.getLogger(InterventoServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private FascicoloInterventoService fascicoloInterventoService;
	@Autowired	private TipiEQualificazioniService tipiEQualificazioniService;
	@Autowired	private ProcedimentoQualificazioniService procedimentoQualificazioniService;
	@Autowired  private ApplicationProperties props;
	@Autowired	private FascicoloFullRepository fascicoloDao;

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, timeout=60000, readOnly=true, rollbackFor=Exception.class)
	public InterventoTabDTO tabIntervento(TipoProcedimento tipoProcedimento,Long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<TipiEQualificazioniDTO> selezioniPossibili =null;
		FascicoloDTO fascicoloDto=null;
		if(idFascicolo!=null) {
			try{
				 fascicoloDto = fascicoloDao.find(idFascicolo);
			}catch(EmptyResultDataAccessException e) {}
			//va aggiunta selezione già effettuata
			if(fascicoloDto!=null && fascicoloDto.gettPraticaId()!=null) {
				selezioniPossibili = tipiEQualificazioniService.selectByCodiceTipoProcedimento(tipoProcedimento,new Date(),null);	
			}
		}
		if(fascicoloDto!=null) {
			selezioniPossibili = tipiEQualificazioniService.selectByCodiceTipoProcedimento(tipoProcedimento,fascicoloDto.getDataCreazione(),fascicoloDto.getUfficio());
		}
		return new InterventoTabDTO(selezioniPossibili);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, timeout=60000, readOnly=true, rollbackFor=Exception.class)
	public List<Long> datiIntervento(Long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

//		InterventoTabDTO interventoTab = new InterventoTabDTO();
		List<Long> intervento = new ArrayList<Long>();
		
		FascicoloInterventoSearch search = new FascicoloInterventoSearch();
		search.setIdFascicolo(idFascicolo);
		//ordino sempre le selezioni...
		search.setColonna(FascicoloIntervento.id_tipi_qualificazioni);
		List<FascicoloInterventoDTO> selezionati = fascicoloInterventoService.search(search).getList();
		
//		if (selezionati!=null && selezionati.size()>0) {
//			selezionati.forEach(elem -> {
//				for (int i=0; i<interventoTab.getLista().size(); i++) {
//					if (interventoTab.getLista().get(i).getId() == elem.getIdTipiQualificazioni()) {
//						interventoTab.getLista().add(new TipiEQualificazioniDTO(elem.getIdTipiQualificazioni()));
//					    break;
//					}
//				}
//			});
//		}
		
		if (selezionati!=null && selezionati.size()>0) {
			selezionati.forEach(elem -> {
				intervento.add(elem.getIdTipiQualificazioni());
			});
		}
		
//		return interventoTab;
		return intervento;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public void salva(List<Long> interventi, Long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		// qua ci va un unico blocco try-catch fino alla fine dell'insert
		
		FascicoloInterventoSearch filter = new FascicoloInterventoSearch();
		filter.setIdFascicolo(idFascicolo);
		fascicoloInterventoService.delete(filter);
		if (!interventi.isEmpty())
			fascicoloInterventoService.insertMultiple(interventi, idFascicolo);
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<ErroriValidazioneBE> validazione(List<Long> interventoSelezionati, Long idFascicolo, TipoProcedimento tipoProcedimento,String interventoDettaglio,Date dataCreazioneFascicolo,String gruppoOwner) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<ErroriValidazioneBE> erroriRilevati=new ArrayList<ErroriValidazioneBE>();
		// eventualmente inserire una validazione preliminare, prima delle singole validazioni dei tab,
		// che controlla se esiste id e codiceTipoProcedimento, ....

		// List<Long> listaIdTipiQualificazioni = this.fromTabDTOtoList(interventoTQ);
		Gruppi tipoOrganizzazionOwner = Gruppi.fromCodiceGruppoPM(gruppoOwner);
		boolean isEti=tipoOrganizzazionOwner!=null && tipoOrganizzazionOwner.equals(Gruppi.ETI_);
		this.ripulisciTipoProcedimento(interventoSelezionati, tipoProcedimento,dataCreazioneFascicolo,isEti);
		
		InterventoTabDTO interventoTab = new InterventoTabDTO();
		interventoTab.setLista(tipiEQualificazioniService.selectByCodiceTipoProcedimento(tipoProcedimento,dataCreazioneFascicolo,gruppoOwner));
		
		// PARTE A
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		int count=0;
		boolean validoA = false;
		for (int i = 0; i < interventoTab.getLista().size(); i++) {
			if (interventoTab.getLista().get(i).getRaggruppamento().equals("a")) {
				for (int j = 0; j < interventoSelezionati.size(); j++) {
					if (interventoTab.getLista().get(i).getId().equals(interventoSelezionati.get(j))) {
						count++;
						break;
					}
				}
			}
		}
		if (count==1) // essendo rb
			validoA = true;
		
		if (validoA==false) 
		{
			erroriRilevati.add(ErroriValidazioneBE.INTERVENTO_TIPOLOGIA);
			return erroriRilevati;
		}
			
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		//qui spezzo il job in caso di pareri...
		if(props.isPareri()) {
			//testo solo se ha scelto "Altro .. id=172 allora deve essere pieno interventoDettaglio....
			boolean validoPareri = validoA && 
					interventoSelezionati.contains(Long.valueOf(172))?
							StringUtil.isNotEmpty(interventoDettaglio):
								true;
			if(!validoPareri) {
				erroriRilevati.add(ErroriValidazioneBE.INTERVENTO_PARERI_DETTAGLIO);
			}
			return erroriRilevati;
		}
		
		// PARTE B
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		count=0;
		boolean validoB = false;
		for (int i = 0; i < interventoTab.getLista().size(); i++) {
			if (interventoTab.getLista().get(i).getRaggruppamento().equals("b")) {
				for (int j = 0; j < interventoSelezionati.size(); j++) {
					if (interventoTab.getLista().get(i).getId().equals(interventoSelezionati.get(j))) {
						count++;
						break;
					}
				}
			}
		}
		if (count>=1) // essendo cb
			validoB = true;
		
		if (validoB==false)
		{
			erroriRilevati.add(ErroriValidazioneBE.INTERVENTO_CARATTERIZZAZIONE);
			return erroriRilevati;
			//return validoB;
		}
		if(interventoSelezionati.contains(Long.valueOf(6))) {
			erroriRilevati.add(ErroriValidazioneBE.INTERVENTO_CARATTERIZZAZIONE_RIMESSA_IN_RIPRISTINO_DETTAGLIARE);
		} //Rimessa in ripristino
		if(interventoSelezionati.contains(Long.valueOf(19))) {
			erroriRilevati.add(ErroriValidazioneBE.INTERVENTO_CARATTERIZZAZIONE_ALTRO_SPECIFICARE);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		
		// PARTE C
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		count=0;
		boolean validoC = false;
		for (int i = 0; i < interventoTab.getLista().size(); i++) {
			if (interventoTab.getLista().get(i).getRaggruppamento().equals("c")) {
				for (int j = 0; j < interventoSelezionati.size(); j++) {
					if (interventoTab.getLista().get(i).getId().equals(interventoSelezionati.get(j))) {
						count++;
						break;
					}
				}
			}
		}
		//per PUTT la sezione C non esiste
		if (count==1 || props.isPutt()) // essendo rb
			validoC = true;
		
		if (validoC==false)
		{
			erroriRilevati.add(ErroriValidazioneBE.INTERVENTO_TEMP_PERM);
			return erroriRilevati;
		}
			
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		// caso A: solo radioButton
		// caso B: solo checkBox
		// caso C: radioButton + checkBox
		// caso D: checkBox + checkBox
		
		
		// PARTE D
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		boolean caso_A = false;
		boolean caso_B = false;
		boolean caso_C = false;
		boolean caso_D = false;

		for (int i = 0; i < interventoTab.getLista().size(); i++) {
			if (interventoTab.getLista().get(i).getZona()==(4*100)) {
				if (interventoTab.getLista().get(i).getStile().equals("checkbox"))
					caso_D = true;
				if (interventoTab.getLista().get(i).getStile().equals("radiobutton"))
					caso_C = true;
				break;
			}
		}
		if (!(caso_D || caso_C)) {
			for (int i = 0; i < interventoTab.getLista().size(); i++) {

				if (interventoTab.getLista().get(i).getZona()==4) {
					if (interventoTab.getLista().get(i).getStile().equals("checkbox"))
						caso_B = true;
					if (interventoTab.getLista().get(i).getStile().equals("radiobutton"))
						caso_A = true;
				}
			}
		}
		if (!caso_A && !caso_B && !caso_C && !caso_D) {  // se nessuno dei casi possibili è identificato
			erroriRilevati.add(ErroriValidazioneBE.INTERVENTO_QUALIFICAZIONE_ERRATA);
			log.error("Impossibile identificare il caso specifico del Tab Intervento!");
			return erroriRilevati;
			//throw new Exception("Impossibile identificare il caso specifico del Tab Intervento!");
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		boolean validoD = false;
		
		// caso A:
		if (caso_A==true) {
		    count=0;
			for (int i = 0; i < interventoTab.getLista().size(); i++) {
				if (interventoTab.getLista().get(i).getZona() == 4) {
					for (int j = 0; j < interventoSelezionati.size(); j++) {
						if (interventoTab.getLista().get(i).getId().equals(interventoSelezionati.get(j))) {
							count++;
							break;
						}
					}
				}
			}
			if (count==1) // essendo rb
				validoD = true;
		}
		
		// caso B:
		else if (caso_B==true) {
			count=0;
			for (int i = 0; i < interventoTab.getLista().size(); i++) {
				if (interventoTab.getLista().get(i).getZona() == 4) {
					for (int j = 0; j < interventoSelezionati.size(); j++) {
						if (interventoTab.getLista().get(i).getId().equals(interventoSelezionati.get(j))) {
							count++;
							break;
						}
					}
				}
			}
			if (count>=1) // essendo cb
				validoD = true;
		}
		
		// caso C:
		else if (caso_C==true) {
		    count=0;
		    boolean validoParziale = false;
			boolean subScelte = false;
			for (int i = 0; i < interventoTab.getLista().size(); i++) {
				if (interventoTab.getLista().get(i).getZona() == 4) {
					for (int j = 0; j < interventoSelezionati.size(); j++) {
						if (interventoTab.getLista().get(i).getId().equals(interventoSelezionati.get(j))) {
							count++;
							break;
						}
					}
				}
			}
			for (int i = 0; i < interventoTab.getLista().size(); i++) {
				if (interventoTab.getLista().get(i).getZona() == 400) {
					for (int j = 0; j < interventoSelezionati.size(); j++) {
						if (interventoTab.getLista().get(i).getId().equals(interventoSelezionati.get(j))) {
							subScelte=true;
							count++;
							break;
						}
					}
				}
			}
			if (count==1) { // essendo rb
				validoParziale = true;
				validoD = true;
			}
				
			if (validoParziale==true && subScelte==true) {
				count=0;
				for (int i = 0; i < interventoTab.getLista().size(); i++) {
					if (interventoTab.getLista().get(i).getZona() == (4*100*100)) {
						for (int j = 0; j < interventoSelezionati.size(); j++) {
							if (interventoTab.getLista().get(i).getId().equals(interventoSelezionati.get(j))) {
								count++;
								break;
							}
						}
					}
				}
				if (count<1) // essendo cb
					validoD = false;
			}
		}
		
		// caso D:
		else if (caso_D==true) {
				    count=0;
				    boolean validoParziale = false;
					boolean subScelte = false;
					for (int i = 0; i < interventoTab.getLista().size(); i++) {
						if (interventoTab.getLista().get(i).getZona() == 4) {
							for (int j = 0; j < interventoSelezionati.size(); j++) {
								if (interventoTab.getLista().get(i).getId().equals(interventoSelezionati.get(j))) {
									count++;
									break;
								}
							}
						}
					}
					for (int i = 0; i < interventoTab.getLista().size(); i++) {
						if (interventoTab.getLista().get(i).getZona() == 400) {
							for (int j = 0; j < interventoSelezionati.size(); j++) {
								if (interventoTab.getLista().get(i).getId().equals(interventoSelezionati.get(j))) {
									subScelte=true;
									count++;
									break;
								}
							}
						}
					}
					if (count>=1) { // essendo cb
						validoParziale = true;
						validoD = true;
					}
					
					if (validoParziale==true && subScelte==true) {
						count=0;
						for (int i = 0; i < interventoTab.getLista().size(); i++) {
							if (interventoTab.getLista().get(i).getZona() == (4*100*100)) {
								for (int j = 0; j < interventoSelezionati.size(); j++) {
									if (interventoTab.getLista().get(i).getId().equals(interventoSelezionati.get(j))) {
										count++;
										break;
									}
								}
							}
						}
						if (count<1) // essendo cb
							validoD = false;
					}
				}
		if(!validoD) {
			erroriRilevati.add(ErroriValidazioneBE.INTERVENTO_QUALIFICAZIONE_ERRATA);
		}
		return erroriRilevati;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
/*	private ArrayList<Long> fromTabDTOtoList(InterventoTabDTO intervento) {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		Set<Long> lista = new HashSet<Long>();
		
		if (intervento!=null && intervento.getLista()!=null) {
			intervento.getLista().forEach(elem -> {
				lista.add(elem.getId());
			});
		}
		return new ArrayList<Long>(lista);
	}	*/
	
	private void ripulisciTipoProcedimento(List<Long> idSelezionati, TipoProcedimento tipoProcedimento,Date dataRifConfigurazioneProcedimentoQualificazioni,boolean isEti) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		ProcedimentoQualificazioniSearch searchTipoProc=new ProcedimentoQualificazioniSearch(tipoProcedimento.name(), null);
		searchTipoProc.setDataRiferimento(dataRifConfigurazioneProcedimentoQualificazioni);
		if(isEti) {
			searchTipoProc.setEsclusoEti(true);
		}
		List<ProcedimentoQualificazioniDTO> idPossibili = procedimentoQualificazioniService.search(searchTipoProc).getList();
		
		List<Long> idConsentiti = new ArrayList<>();
		
		if (idSelezionati!=null) {
			idSelezionati.forEach(idS -> {
				boolean consentito = false;
				for (ProcedimentoQualificazioniDTO poss : idPossibili) {
					if (poss.getIdTipiQualificazioni()==idS)
						consentito=true;
				};
				if (consentito==true)
					idConsentiti.add(idS);
			});
		}
		idSelezionati = idConsentiti;
	}
	
}