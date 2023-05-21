/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.InfoAutPaesAlfaBean;
import it.eng.tz.puglia.autpae.civilia.migration.exception.FascicoloAlreadyExistsException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.FileSizeToLargeException;
import it.eng.tz.puglia.autpae.civilia.migration.service.IDatiPraticaCivService;
import it.eng.tz.puglia.autpae.civilia.migration.service.IMigratorService;
import it.eng.tz.puglia.autpae.civilia.migration.service.PuttMigrationService;
import it.eng.tz.puglia.autpae.civilia.migration.service.impl.MigratorService;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.autpae.repository.RicevutaFullRepository;
import it.eng.tz.puglia.autpae.repository.base.AllegatoBaseRepository;
import it.eng.tz.puglia.autpae.search.AllegatoSearch;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
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
public class MigratorePutt {

	@Autowired
	IDatiPraticaCivService datiPraticaSvc;
	@Autowired
	IMigratorService migratorSvc;
	@Autowired
	IHttpClientService httpclientSvc;
	@Autowired
	PuttMigrationService puttMigrationService;
	@Autowired
	FascicoloService fascicoloService;
	@Autowired
	AllegatoBaseRepository allegatoDao;
	@Autowired
	RicevutaFullRepository ricevutaDao;
	private int totAcquisite = 0;
	private int totPreesistenti = 0;
	private int totErrate = 0;

	private static final Logger log = LoggerFactory.getLogger(MigratorePutt.class);

	public void testaMigrazione() throws Exception {
		totAcquisite = 0;
		totPreesistenti = 0;
		totErrate = 0;
		if (datiPraticaSvc == null)
			return;
		long numPraticheTrasmesse = puttMigrationService.countPuttPraticheTrasmesse();
		int maxPerPag = 100;
		int totPagine = (int) numPraticheTrasmesse / maxPerPag;
		if ((numPraticheTrasmesse % maxPerPag) > 0) {
			totPagine++;
		}
		log.info(IMigratorService.LOG_MIGRAZIONE_MARKER, "Start migration {}", new Date());
		try {
			for (int paginaCorrente = 1; paginaCorrente <= (totPagine); paginaCorrente++) {
				int fromRec = 1 + ((paginaCorrente - 1) * maxPerPag);
				int toRec = fromRec - 1 + maxPerPag;
				List<InfoAutPaesAlfaBean> pratiche = puttMigrationService.getPuttPratiche(fromRec, toRec);
				migraElencoPutt(pratiche);
				log.info(IMigratorService.LOG_MIGRAZIONE_MARKER, "End pagina {} da record {} a record {}",
						paginaCorrente, fromRec, toRec);
			}
			log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,
					"End migration {} , tot elaborate {}, tot acquisite {}, tot preesistenti {}, tot errate {}",
					new Date(), numPraticheTrasmesse, totAcquisite, totPreesistenti, totErrate);
		} catch (Exception e) {
			log.error(IMigratorService.LOG_MIGRAZIONE_MARKER, "Errore durante la migrazione {}", e.getMessage(), e);
			throw e;
		}
	}

	public void testaMigrazionePuttListaCodici(String codici) throws Exception {
		totAcquisite = 0;
		totPreesistenti = 0;
		totErrate = 0;
		if (datiPraticaSvc == null)
			return;
		try {
			List<String> listaCodici = List.of(codici.split(","));
			List<InfoAutPaesAlfaBean> pratiche = puttMigrationService.getPuttPraticheFromCodes(listaCodici);
			migraElencoPutt(pratiche);
		} catch (Exception e) {
			log.error(IMigratorService.LOG_MIGRAZIONE_MARKER, "Errore durante la migrazione {}", e.getMessage(), e);
			throw e;
		}
	}

	public void testaMigrazioneMailPuttListaCodici(String codici) throws Exception {
		totAcquisite = 0;
		totPreesistenti = 0;
		totErrate = 0;
		if (datiPraticaSvc == null)
			return;
		try {
			List<String> listaCodici = List.of(codici.split(","));
			List<InfoAutPaesAlfaBean> pratiche = puttMigrationService.getPuttPraticheFromCodes(listaCodici);
			migraElencoPuttMail(pratiche);
		} catch (Exception e) {
			log.error(IMigratorService.LOG_MIGRAZIONE_MARKER, "Errore durante la migrazione {}", e.getMessage(), e);
			throw e;
		}
	}

	private void migraElencoPuttMail(List<InfoAutPaesAlfaBean> pratiche) throws Exception {
		for (InfoAutPaesAlfaBean p : pratiche) {
			try {
				migratorSvc.migraMailPraticaMigrataPutt(p);
				totAcquisite++;
				log.info(IMigratorService.LOG_MIGRAZIONE_MARKER, "Migrate {} ", totAcquisite);
			}  catch (Exception e) {
				log.error(IMigratorService.LOG_MIGRAZIONE_MARKER, "Errore durante la migrazione dell mail della pratica {}",
						p.getCodicePratica(), e);
				totErrate++;
			}
		}
	}
	
	private void migraElencoPutt(List<InfoAutPaesAlfaBean> pratiche) throws Exception {
		for (InfoAutPaesAlfaBean p : pratiche) {
			try {
				migratorSvc.migraPraticaPutt(p);
				totAcquisite++;
				log.info(IMigratorService.LOG_MIGRAZIONE_MARKER, "Migrate {} ", totAcquisite);
			} catch (FascicoloAlreadyExistsException e) {
				log.info(IMigratorService.LOG_MIGRAZIONE_MARKER, "Pratica già esistente {}", p.getCodicePratica());
				totPreesistenti++;
			} catch (Exception e) {
				log.error(IMigratorService.LOG_MIGRAZIONE_MARKER, "Errore durante la migrazione della pratica {}",
						p.getCodicePratica(), e);
				totErrate++;
			}
		}
	}

	/**
	 * update riferimenti cmis
	 * 
	 * @throws Exception
	 * @autor Adriano Colaianni
	 * @date 20 lug 2021
	 */
	public void updateIdCmisAllegati() throws Exception {
		if (datiPraticaSvc == null)
			return;
		AllegatoSearch allSearch = new AllegatoSearch();
		log.info(MigratorService.LOG_MIGRAZIONE_MARKER, "Start updateIdCmisAllegati {}", new Date());
		final int pageLimit = 100;
		allSearch.setLimit(pageLimit);
		allSearch.setPage(1);
		allSearch.setContenuto(AllegatoService.PLACEHOLDERTEMPIDCMS);
		int pagineTot = 0;
		PaginatedList<AllegatoDTO> allegatiPage = allegatoDao.search(allSearch);
		pagineTot = (allegatiPage.getCount() / pageLimit) + 1;
		int totRecord = allegatiPage.getCount();
		int totMigrati = 0;
		int totErrori = 0;
		int totOversize = 0;
		for (int paginaCorrente = 1; paginaCorrente <= pagineTot; paginaCorrente++) {
			allSearch.setPage(1);// prendo sempre dalla pagina 1 perchè man mano che le elaboro la search lavora
									// sul set aggiornato
			allegatiPage = allegatoDao.search(allSearch);
			if (ListUtil.isNotEmpty(allegatiPage.getList())) {
				for (AllegatoDTO allegato : allegatiPage.getList()) {
					try {
						migratorSvc.aggiornaAlfrescoId(allegato);
						totMigrati++;
					} catch (FileSizeToLargeException e) {
						log.error(MigratorService.LOG_MIGRAZIONE_MARKER, "Allegato largo " + allegato.getId() + " {}",
								e.getMessage());
						totOversize++;
					} catch (Exception e) {
						log.error(MigratorService.LOG_MIGRAZIONE_MARKER,
								"Errore durante la migrazione idCmis dell'allegato " + allegato.getId(), e);
						totErrori++;
					}
				}
			}
		}
		log.info(MigratorService.LOG_MIGRAZIONE_MARKER, "Totali {}  migrati {}  errore {} sizeOverride {}", totRecord,
				totMigrati, totErrori, totOversize);
		// ricevute
		RicevutaSearch ricSearch = new RicevutaSearch();
		ricSearch.setLimit(pageLimit);
		ricSearch.setPage(1);
		ricSearch.setIdCmsDatiCert(AllegatoService.PLACEHOLDERTEMPIDCMS);
		PaginatedList<RicevutaDTO> ricevutePage = ricevutaDao.search(ricSearch);
		totRecord = ricevutePage.getCount();
		totMigrati = 0;
		totErrori = 0;
		totOversize = 0;
		pagineTot = (ricevutePage.getCount() / pageLimit) + 1;
		for (int paginaCorrente = 1; paginaCorrente <= pagineTot; paginaCorrente++) {
			ricSearch.setPage(1);
			ricevutePage = ricevutaDao.search(ricSearch);
			if (ListUtil.isNotEmpty(ricevutePage.getList())) {
				for (RicevutaDTO ric : ricevutePage.getList()) {
					try {
						migratorSvc.aggiornaAlfrescoId(ric);
						totMigrati++;
					} catch (FileSizeToLargeException e) {
						log.error(MigratorService.LOG_MIGRAZIONE_MARKER, "File largo " + ric.getId() + " {}",
								e.getMessage());
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
				"Ricevute mail:  Totali {}  migrati {}  errore {} sizeOverride {}", totRecord, totMigrati, totErrori,
				totOversize);
		log.info(MigratorService.LOG_MIGRAZIONE_MARKER, "End updateIdCmisAllegati {}", new Date());
	}

	/**
	 * effettua delete su alfresco applicativo, e rieffettua download da alfresco
	 * SITPUGLIA
	 * 
	 * @autor Adriano Colaianni
	 * @date 28 nov 2021
	 * @throws Exception
	 */
	public void updateAllegatiVuoti() throws Exception {
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
						migratorSvc.downloadFromAlfresco(allegato);
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
