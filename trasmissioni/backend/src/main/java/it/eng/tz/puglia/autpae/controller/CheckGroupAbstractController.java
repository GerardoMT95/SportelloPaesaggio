package it.eng.tz.puglia.autpae.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.entity.ComuniCompetenzaDTO;
import it.eng.tz.puglia.autpae.search.ComuniCompetenzaSearch;
import it.eng.tz.puglia.autpae.service.interfacce.ComuniCompetenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.response.EnteResponseBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.response.ProfileUserResponseBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;

public abstract class CheckGroupAbstractController extends _RestController
{
	@Autowired protected GruppiRuoliService gruppiRuoliService;
	@Autowired private ComuniCompetenzaService comuniCompetenzaService;
	@Autowired private UserUtil userUtil;
	
	protected void check() throws Exception
	{
		check(userUtil.getGruppo_Id());
	}
	
	protected void check(String codiceGruppo) throws Exception
	{
		ProfileUserResponseBean bean = userUtil.getMyProfile();
		java.util.List<EnteResponseBean> gruppi = bean.getEnti().get("GRUPPI");
		if(gruppi == null || !gruppi.stream().anyMatch(p -> p.getCodiceGruppo().equals(codiceGruppo)))
		{
			logger.error("Errore: il gruppo {} non risulta fra quelli associati all'utente {}", codiceGruppo, bean.getUsername());
			logger.error("Errore: il gruppo " + codiceGruppo +" non risulta fra quelli associati all'utente " + bean.getUsername());
		}
		gruppiRuoliService.checkGruppo(codiceGruppo);
	}
	
//	protected void check(java.util.List<Long> entiId) throws Exception
//	{
//		check(userUtil.getGruppo_Id(), entiId);
//	}
//	
//	protected void check(String codiceGruppo, java.util.List<Long> entiId) throws Exception
//	{
//		ProfileUserResponseBean bean = userUtil.getMyProfile();
//		java.util.List<EnteResponseBean> gruppi = bean.getEnti().get("GRUPPI");
//		if(gruppi == null || !gruppi.stream().anyMatch(p -> p.getCodiceGruppo().equals(codiceGruppo)))
//		{
//			logger.error("Errore: il gruppo {} non risulta fra quelli associati all'utente {}", codiceGruppo, bean.getUsername());
//			logger.error("Errore: il gruppo " + codiceGruppo +" non risulta fra quelli associati all'utente " + bean.getUsername());
//		}
//		gruppiRuoliService.checkGruppo(codiceGruppo);
//		if(entiId != null && !entiId.isEmpty())
//		{
//			java.util.List<EnteBean> list = orgService.findCompetenze(userUtil.getLongId(codiceGruppo));
//			if(list == null | list.isEmpty())
//			{
//				logger.error("Il gruppo con codice {} non ha competenze territoriali registrate", codiceGruppo);
//				throw new Exception("Il gruppo con codice " + codiceGruppo + " non ha competenze territoriali registrate");
//			}
//			if(!list.stream().anyMatch(e -> e.getTipoEnte().equals(TipoEnte.regione))) //se regione puglia evito controlli su competenze
//			{
//				java.util.List<Long> entiValidi = list.stream().filter(f -> f.getDataTermine().after(new Date())).map(EnteBean::getIdEnte).collect(Collectors.toList());
//				if(!entiId.stream().allMatch(p -> entiValidi.stream().anyMatch(e -> e.equals(p))))
//				{
//					logger.error("Errore: sono presenti territori per cui il gruppo {} non ha competenze valide", codiceGruppo);
//					throw new Exception("Errore: sono presenti territori per cui il gruppo " + codiceGruppo + " non ha competenze valide");
//				}
//			}
//		}
//	}
	
	protected void check(Long praticaId, java.util.List<Long> entiId) throws Exception
	{
		check(userUtil.getGruppo_Id(), praticaId, entiId);
	}
	
	protected void check(String codiceGruppo, Long praticaId, java.util.List<Long> entiId) throws Exception
	{
		check(codiceGruppo);
		if(entiId != null && !entiId.isEmpty())
		{
			ComuniCompetenzaSearch search = new ComuniCompetenzaSearch();
			search.setPraticaId(praticaId);
			search.setCreazione(true);
			search.setEnti(entiId);//cerco solo quelli usati
			java.util.List<ComuniCompetenzaDTO> comuniCompetenza = comuniCompetenzaService.search(search).getList();
			
			if(comuniCompetenza == null || comuniCompetenza.isEmpty())
			{
				logger.error("Il gruppo con codice {} non ha competenze territoriali registrate", codiceGruppo);
				throw new CustomOperationToAdviceException("Il gruppo con codice " + codiceGruppo + " non ha competenze territoriali registrate");
			}		
			java.util.List<Long> ids = comuniCompetenza.stream().map(ComuniCompetenzaDTO::getEnteId).collect(Collectors.toList());
			if(!entiId.stream().allMatch(p -> ids.stream().anyMatch(e -> e.equals(p))))
			{
				logger.error("Errore: sono presenti territori per cui il gruppo {} non ha competenze valide", codiceGruppo);
				throw new CustomOperationToAdviceException("Errore: sono presenti territori per cui il gruppo " + codiceGruppo + " non ha competenze valide");
			}
		}
	}
}
