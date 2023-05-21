/**
 * 
 */
package it.eng.tz.puglia.autpae.repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.autpae.dto.AllegatoDiogeneInfoDTO;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.generated.orm.repository.GenericDao;
import it.eng.tz.puglia.autpae.rowmapper.custom.AllegatoDiogeneInfoRowMapper;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.bean.PaginatedList;

/**
 * @author Adriano Colaianni
 * @date 13 ott 2021
 */
@Repository
public class AllegatoDiogeneInfoRepository  extends GenericDao{
	
	final String SQL_FILE_TO_SEND =
			StringUtil.concateneString(
	 " select a.id,a.nome ,"  
	," a.contenuto ,a.numero_protocollo_in ,a.data_protocollo_in, "   
	," a.numero_protocollo_out ,a.data_protocollo_out , "   
	," STRING_AGG(af.type ,',') as types, "   
	," f.codice, "   
	," f.org_creazione,  "
	," f.data_trasmissione  "   
	," from  "   
	," allegato a,allegato_fascicolo af ,fascicolo f  "   
	," where "   
	," f.id =af.id_fascicolo and  "   
	," af.id_allegato=a.id  "   
	," and f.stato in (:statiAmmessi) "   
	," and not f.codice is null "   
	," and not f.data_trasmissione is null  "   
	," and a.id_diogene is null  "
	," and a.last_send_diogene is null  "
	," and not a.contenuto is null  "
	," and a.deletable = true "
	," and a.contenuto<> :placeholderMigrazione " 
	," and af.type in (:tipiAmmessi) "
	," group by "   
	," a.id,a.nome ,a.contenuto ,a.numero_protocollo_in , "   
	," a.data_protocollo_in, "   
	," a.numero_protocollo_out ,a.data_protocollo_out, "   
	," f.codice, "
	," f.org_creazione, "   
	," f.data_trasmissione "); 
	
	AllegatoDiogeneInfoRowMapper rowMapper=new AllegatoDiogeneInfoRowMapper();
	
	public PaginatedList<AllegatoDiogeneInfoDTO> getListaFileDaInviare(int page,int limit){
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("statiAmmessi",Arrays.asList(StatoFascicolo.statiAmmessiDiogene()).stream().map(el->el.name()).collect(Collectors.toList()));
		paramMap.put("tipiAmmessi",TipoAllegato.tipiAmmessiDiogene()
				.stream().map(el->el.name()).collect(Collectors.toList()));
		paramMap.put("placeholderMigrazione",AllegatoService.PLACEHOLDERTEMPIDCMS);
		return super.paginatedList(SQL_FILE_TO_SEND, paramMap, rowMapper, page, limit);
	}
	
	
}
