/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.autpae.civilia.migration.dto.InfoAutPaesPptrAlfa;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrGruppoUfficio;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PraticaLavorazione;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.InfoAutPaesAlfaBean;
import it.eng.tz.puglia.autpae.civilia.migration.exception.FascicoloAlreadyExistsException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.FileSizeToLargeException;
import it.eng.tz.puglia.autpae.civilia.migration.service.IDatiPraticaCivService;
import it.eng.tz.puglia.autpae.civilia.migration.service.IMigratorService;
import it.eng.tz.puglia.autpae.civilia.migration.service.impl.MigratorService;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.autpae.repository.RicevutaFullRepository;
import it.eng.tz.puglia.autpae.repository.base.AllegatoBaseRepository;
import it.eng.tz.puglia.autpae.search.AllegatoSearch;
import it.eng.tz.puglia.autpae.search.RicevutaSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * @author Adriano Colaianni
 * @date 16 ago 2021
 */
@Component
@ConditionalOnProperty("datasource.civ.enableMigration")
public class MigratorePptr {
	@Autowired IDatiPraticaCivService datiPraticaSvc;
	@Autowired IMigratorService migratorSvc;
	@Autowired IHttpClientService httpclientSvc;
	@Autowired FascicoloService fascicoloService;
	@Autowired AllegatoBaseRepository allegatoDao;
	@Autowired RicevutaFullRepository ricevutaDao;
	
	private static final Logger log = LoggerFactory.getLogger(MigratorePptr.class);
	
	private int totAcquisite=0;
	private int totPreesistenti=0;
	private int totErrate=0;
	
	public void migraPraticheDaCodici(String codici) {
		if(datiPraticaSvc == null) return;
		List<InfoAutPaesPptrAlfa> listaPratiche=datiPraticaSvc.getPraticheByCodiciEnteDelegato(List.of(codici.split(",")));
		log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"Start migration by selected code {} pratiche totali {}", new Date(),listaPratiche.size());
		try
		{
			migraPaginaPratiche(listaPratiche,false,false);	
			log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"End migration {} , tot elaborate {}, tot acquisite {}, tot preesistenti {}, tot errate {}", new Date(),listaPratiche.size(),totAcquisite,totPreesistenti,totErrate);
		}
		catch(Exception e)
		{
			log.error("Errore durante la migrazione {}", e.getMessage(), e);
			throw e;
		}
	}
	
	public void migraMailPraticheDaCodici(String codici) {
		if(datiPraticaSvc == null) return;
		List<InfoAutPaesPptrAlfa> listaPratiche=datiPraticaSvc.getPraticheByCodiciEnteDelegato(List.of(codici.split(",")));
		log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"Start migration mail by selected code {} pratiche totali {}", new Date(),listaPratiche.size());
		try
		{
			migraPaginaPratiche(listaPratiche,false,true);	
			log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"End migration {} , tot elaborate {}, tot acquisite {}, tot preesistenti {}, tot errate {}", new Date(),listaPratiche.size(),totAcquisite,totPreesistenti,totErrate);
		}
		catch(Exception e)
		{
			log.error("Errore durante la migrazione mial {}", e.getMessage(), e);
			throw e;
		}
	}
	
	public void migraPratiche() {
		if(datiPraticaSvc == null) return;
		//job per ricostruzione dati pratica pubblicata:
		// - fetch da PSITPIANI.INFO_AUT_PAES_PPTR_ALFA (quelle visibili in area pubblica che comprendono anche quelle derivanti da sportello) 
		// - fetch del max progressivo pubblicazione
		// - fetch di AutPaesPptrPratica
		// - fetch del soggetto dichiarante (richiedente)
		// - fetch dei comuni ricavati dalla localizzazione dell'intervento
		long numPraticheTrasmesse=datiPraticaSvc.countPratichePptrTrasmesse();
		int maxPerPag=100;
		totAcquisite=0;
		totPreesistenti=0;
		totErrate=0;
		int totPagine=(int)numPraticheTrasmesse/maxPerPag;
		if((numPraticheTrasmesse % maxPerPag)>0) {
			totPagine++;
		}
		log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"Start migration {} pratiche totali {}", new Date(),numPraticheTrasmesse);
		try
		{
			for(int paginaCorrente=1;paginaCorrente<=(totPagine);paginaCorrente++){
				int fromRec=1+((paginaCorrente-1)*maxPerPag);
				int toRec=fromRec-1+maxPerPag; 
				 List<InfoAutPaesPptrAlfa> listaPratiche = datiPraticaSvc.getPratiche(fromRec,toRec);
				migraPaginaPratiche(listaPratiche,false,false);	
				log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"End pagina {} da record {} a record {}",paginaCorrente,fromRec,toRec); 
			}
			log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"End migration {} , tot elaborate {}, tot acquisite {}, tot preesistenti {}, tot errate {}", new Date(),numPraticheTrasmesse,totAcquisite,totPreesistenti,totErrate);
		}
		catch(Exception e)
		{
			log.error("Errore durante la migrazione {}", e.getMessage(), e);
			throw e;
		}
	}

	public static InfoAutPaesPptrAlfa fromPraticaLavorazioneToInfoAutPaesAlfa(PraticaLavorazione pratica) {
		//@see it.innovapuglia.bean.enteCS.trasmissione.ExportInfoAutPaesPptrAlfa.eseguiJob()
		InfoAutPaesPptrAlfa iia=new InfoAutPaesPptrAlfa();
		iia.setCodicePraticaAppptr(pratica.getCodicePraticaAppptr());
		iia.setCodicePraticaEnteDelegato(pratica.getCodicePraticaEnteDelegato());
		iia.settPraticaAppptrId(pratica.gettPraticaId());
		iia.setOggetto(pratica.gettPraticaDescrizione());
		iia.setDataCreazione(pratica.getDataAttivazione());
		iia.setIstatAmm(pratica.getUfficio().replace( "U" , "" ));
		iia.setProgPubblicazione(0L);
		return iia;
	}
	
	public void migraPraticheInLavorazione() {
		if(datiPraticaSvc == null) return;
		totAcquisite=0;
		totPreesistenti=0;
		totErrate=0;
		long numPraticheInLavorazione=datiPraticaSvc.countPratichePptrInLavorazione();
		int maxPerPag=100;
		int totPagine=(int)numPraticheInLavorazione/maxPerPag;
		if((numPraticheInLavorazione % maxPerPag)>0) {
			totPagine++;
		}
		log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"Start migration {} pratiche in lavorazione PPTR totali {}", new Date(),numPraticheInLavorazione);
		try
		{
			for(int paginaCorrente=1;paginaCorrente<=(totPagine);paginaCorrente++){
				int fromRec=1+((paginaCorrente-1)*maxPerPag);
				int toRec=fromRec-1+maxPerPag; 
				List<PraticaLavorazione> listaPratiche = datiPraticaSvc.getListaPraticheInLavorazione(fromRec,toRec);
				if(ListUtil.isNotEmpty(listaPratiche)) {
					migraPaginaPratiche(
							listaPratiche.stream().map(MigratorePptr::fromPraticaLavorazioneToInfoAutPaesAlfa).collect(Collectors.toList()),true,false);	
				}
				log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"End pagina {} da record {} a record {}",paginaCorrente,fromRec,toRec); 
			}
			log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"End migration {} , tot elaborate {}, tot acquisite {}, tot preesistenti {}, tot errate {}", new Date(),numPraticheInLavorazione,totAcquisite,totPreesistenti,totErrate);
		}
		catch(Exception e)
		{
			log.error("Errore durante la migrazione {}", e.getMessage(), e);
			throw e;
		}
	}
	
	private void migraPaginaPratiche(List<InfoAutPaesPptrAlfa> listaPratiche,boolean inLavorazione,boolean soloMail) {
		for(InfoAutPaesPptrAlfa pratica:listaPratiche) {
			try {
				if(soloMail) {
					migratorSvc.migraMailPraticaMigrataPptr(pratica);
				}else {
					migratorSvc.migraPratica(pratica,inLavorazione);
				}
				
				totAcquisite++;
				log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"acquisite {}",totAcquisite);
			}
			catch(FascicoloAlreadyExistsException e) {
				log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,
						"Pratica già esistente {}",pratica.getCodicePraticaEnteDelegato());
				totPreesistenti++;
			} catch (Exception e) {
				log.error(IMigratorService.LOG_MIGRAZIONE_MARKER,
				"Errore durante la migrazione della pratica {}",pratica.getCodicePraticaEnteDelegato(),e);
				totErrate++;
			}
		}
	}
	
	
//	@Test
//	public void testGetObjectByPath() throws CustomCmisException {
//		AlfrescoItemBean ret = httpclientSvc.getCmsObjectByPath("http://cms/explorer/getObjectByPath.pjson", "/autpae/AP30004-31-2020/Provvedimento/Provvedimento finale pubblico test_20201215165129025.pdf", "autpae", true);
//		System.out.println("id:" + ret.getId());
//	}
	
	public void migraEntiDelegati() throws Exception {
		if(datiPraticaSvc == null) return;
		List<PptrGruppoUfficio> listaUfficiGruppi = datiPraticaSvc.getUffici();
		for(PptrGruppoUfficio ufficioGruppo:listaUfficiGruppi) {
			try {
				migratorSvc.migraEnteDelegato(ufficioGruppo);
			}catch(Exception e) {
				log.error(IMigratorService.LOG_MIGRAZIONE_MARKER, "errore migrazione per:" + ufficioGruppo,e);
			}
		}
		
	}
	
	public void checkGruppiPM() throws Exception {
		if(datiPraticaSvc == null) return;
		migratorSvc.checkGruppiProfileManager();	
	}
	
	
	/**
	 * update riferimenti cmis
	 * @throws Exception 
	 * @autor Adriano Colaianni
	 * @date 20 lug 2021
	 */
	public void updateIdCmisAllegati() throws Exception {
		if (datiPraticaSvc == null)
			return;
		AllegatoSearch allSearch = new AllegatoSearch();
		log.info(MigratorService.LOG_MIGRAZIONE_MARKER,"Start updateIdCmisAllegati {}",new Date());
		final int pageLimit = 100;
		allSearch.setLimit(pageLimit);
		allSearch.setPage(1);
		allSearch.setContenuto(AllegatoService.PLACEHOLDERTEMPIDCMS);
		int pagineTot=0;
		PaginatedList<AllegatoDTO> allegatiPage = allegatoDao.search(allSearch);
		pagineTot = (allegatiPage.getCount() / pageLimit)+1;
		int totRecord=allegatiPage.getCount();
		int totMigrati=0;
		int totErrori=0;
		int totOversize=0;
		for (int paginaCorrente = 1; paginaCorrente <= pagineTot; paginaCorrente++) {
			allSearch.setPage(1);//prendo sempre dalla pagina 1 perchè man mano che le elaboro la search lavora sul set aggiornato
			allegatiPage = allegatoDao.search(allSearch);
			if (ListUtil.isNotEmpty(allegatiPage.getList())) {
				for (AllegatoDTO allegato : allegatiPage.getList()) {
					try {
						migratorSvc.aggiornaAlfrescoId(allegato);
						totMigrati++;
					} catch (FileSizeToLargeException e) {
						log.error(MigratorService.LOG_MIGRAZIONE_MARKER,
								"Allegato largo "+ allegato.getId() +" {}",e.getMessage());
						totOversize++;
					} catch (Exception e) {
						log.error(MigratorService.LOG_MIGRAZIONE_MARKER,
								"Errore durante la migrazione idCmis dell'allegato " + allegato.getId(), e);
						totErrori++;
					}
				}
			}
		}
		log.info(MigratorService.LOG_MIGRAZIONE_MARKER,
				"Totali {}  migrati {}  errore {} sizeOverride {}",totRecord,totMigrati,totErrori,totOversize);
		// ricevute
		RicevutaSearch ricSearch = new RicevutaSearch();
		ricSearch.setLimit(pageLimit);
		ricSearch.setPage(1);
		ricSearch.setIdCmsDatiCert(AllegatoService.PLACEHOLDERTEMPIDCMS);
		PaginatedList<RicevutaDTO> ricevutePage = ricevutaDao.search(ricSearch);
		totRecord=ricevutePage.getCount();
		totMigrati=0;
		totErrori=0;
		totOversize=0;
		pagineTot = (ricevutePage.getCount() / pageLimit)+1;
		for (int paginaCorrente = 1; paginaCorrente <= pagineTot; paginaCorrente++) {
			ricSearch.setPage(1);
			ricevutePage = ricevutaDao.search(ricSearch);
			if (ListUtil.isNotEmpty(ricevutePage.getList())) {
				for (RicevutaDTO ric : ricevutePage.getList()) {
					try {
						migratorSvc.aggiornaAlfrescoId(ric);
						totMigrati++;
					} catch (FileSizeToLargeException e) {
						log.error(MigratorService.LOG_MIGRAZIONE_MARKER,
								"File largo "+ ric.getId() +" {}",e.getMessage());
						totOversize++;
					} catch (Exception e) {
						log.error(MigratorService.LOG_MIGRAZIONE_MARKER,
								"Errore durante la migrazione idCmis della ricevuta " + ric.getId(), e);
						totErrori++;
					}
				}
			}
		}
		log.info(MigratorService.LOG_MIGRAZIONE_MARKER,
				"Ricevute mail:  Totali {}  migrati {}  errore {} sizeOverride {}",totRecord,totMigrati,totErrori,totOversize);
		log.info(MigratorService.LOG_MIGRAZIONE_MARKER,"End updateIdCmisAllegati {}",new Date());
	}

	
	/**
	 * migra le mail degli Enti destinatari della trasmissione (COMUNI PROVINCE)
	 * e le mail delle SBAP per comune
	 * @autor Adriano Colaianni
	 * @date 13 set 2021
	 * @throws Exception
	 */
	public void migraMailEntiESbap(boolean isProduzione) throws Exception {
		migratorSvc.migraMailEntiESbap(isProduzione);
	}
	
	
	public void updateAllegatiVuotiPptr() throws Exception {
		AllegatoSearch allSearch = new AllegatoSearch();
		log.info(MigratorService.LOG_MIGRAZIONE_MARKER, "Start updateAllegati a dimensione 0 {}", new Date());
		final int pageLimit = 100;
		allSearch.setLimit(pageLimit);
		allSearch.setPage(1);
		allSearch.setDimensione(0);
		int pagineTot = 0;
		PaginatedList<AllegatoDTO> allegatiPage = allegatoDao.search(allSearch);
		pagineTot = (allegatiPage.getCount() / pageLimit) + 1;
		int totRecord = allegatiPage.getCount();
		int totMigrati = 0;
		int totErrori = 0;
		int totOversize = 0;
		log.info(MigratorService.LOG_MIGRAZIONE_MARKER, "totali da elaborare {}", allegatiPage.getCount());
		for (int paginaCorrente = 1; paginaCorrente <= pagineTot; paginaCorrente++) {
			allSearch.setPage(1);// prendo sempre dalla pagina 1 perchè man mano che le elaboro la search lavora
									// sul set aggiornato
			allegatiPage = allegatoDao.search(allSearch);
			if (ListUtil.isNotEmpty(allegatiPage.getList())) {
				for (AllegatoDTO allegato : allegatiPage.getList()) {
					try {
						migratorSvc.rielaboraFileVuotoPptr(allegato);
						totMigrati++;
						log.info(MigratorService.LOG_MIGRAZIONE_MARKER, "tot scaricati {}", totMigrati);
					} catch (Exception e) {
						log.error(MigratorService.LOG_MIGRAZIONE_MARKER,
								"Errore durante la migrazione idCmis dell'allegato " + allegato.getId(), e);
						totErrori++;
					}
				}
			}
		}
		log.info(MigratorService.LOG_MIGRAZIONE_MARKER,
				"End download allegati vuoti Totali {}  migrati {}  errore {} sizeOverride {}", totRecord, totMigrati,
				totErrori, totOversize);
	}

}
