package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.VCdsAttivitaRowMapper;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SelectParentItemDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SelectParentItemSearchDto;

@Repository
public class VCdsAttivitaRepository extends GenericCrudDao<SelectParentItemDto, SelectParentItemSearchDto>{
	
	private final VCdsAttivitaRowMapper rowMapper = new VCdsAttivitaRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"value\"")
                                     .append(",\"label\"")
                                     .append(" from \"presentazione_istanza\".\"v_cds_attivita\"")
                                     .toString();
	@Override
	public List<SelectParentItemDto> select() {
		// TODO
		return super.queryForListTxRead(selectAll, this.rowMapper);
	}
	@Override
	public long count() {
		// TODO
		return 0;
	}
	@Override
	public SelectParentItemDto find(SelectParentItemDto pk) {
		// TODO
		return null;
	}
	@Override
	public long insert(SelectParentItemDto entity) {
		// TODO
		return 0;
	}
	@Override
	public int update(SelectParentItemDto entity) {
		// TODO
		return 0;
	}
	@Override
	public int delete(SelectParentItemDto entity) {
		// TODO
		return 0;
	}
	@Override
	public PaginatedList<SelectParentItemDto> search(SelectParentItemSearchDto bean) {
		// TODO
		return null;
	}
    

}
