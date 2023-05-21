package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SelectParentItemDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SelectParentItemSearchDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.VCdsAzioneRowMapper;
import it.eng.tz.puglia.bean.PaginatedList;

@Repository
public class VCdsAzioneRepository extends GenericCrudDao<SelectParentItemDto, SelectParentItemSearchDto>{
	
	private final VCdsAzioneRowMapper rowMapper = new VCdsAzioneRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"v_cds_azione\".\"value\"")
                                     .append(",CONCAT(\"v_cds_attivita\".\"label\", ' --- ', \"v_cds_azione\".\"label\") as label")
                                     .append(",\"v_cds_azione\".\"attivita\"")
                                     .append(" from \"presentazione_istanza\".\"v_cds_azione\"")
                                     .append(" inner join \"presentazione_istanza\".\"v_cds_attivita\"")
                                     .append(" on \"v_cds_attivita\".\"value\" = \"v_cds_azione\".\"attivita\"")
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
