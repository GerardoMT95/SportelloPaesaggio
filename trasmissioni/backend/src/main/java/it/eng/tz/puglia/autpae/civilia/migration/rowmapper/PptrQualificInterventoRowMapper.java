/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrQualificIntervento;

/**
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class PptrQualificInterventoRowMapper implements RowMapper<PptrQualificIntervento>{

	@Override
	public PptrQualificIntervento mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrQualificIntervento result=new PptrQualificIntervento();
		if (rs.getObject("T_PRATICA_APPTR_ID") != null) result.settPraticaApptrId(rs.getLong("T_PRATICA_APPTR_ID"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("D_LGS_42_167_CHK_A") != null) result.setdLgs42167ChkA(rs.getString("D_LGS_42_167_CHK_A"));
		if (rs.getObject("D_LGS_42_167_CHK_B") != null) result.setdLgs42167ChkB(rs.getString("D_LGS_42_167_CHK_B"));
		if (rs.getObject("D_LGS_42_167_CHK_C") != null) result.setdLgs42167ChkC(rs.getString("D_LGS_42_167_CHK_C"));
		if (rs.getObject("D_LGS_42_91_RB_LIEVE_ENTITA") != null) result.setdLgs4291RbLieveEntita(rs.getString("D_LGS_42_91_RB_LIEVE_ENTITA"));
		if (rs.getObject("DPR_139_91_CHK_01") != null) result.setDpr13991Chk1(rs.getString("DPR_139_91_CHK_01"));
		if (rs.getObject("DPR_139_91_CHK_02") != null) result.setDpr13991Chk2(rs.getString("DPR_139_91_CHK_02"));
		if (rs.getObject("DPR_139_91_CHK_03") != null) result.setDpr13991Chk3(rs.getString("DPR_139_91_CHK_03"));
		if (rs.getObject("DPR_139_91_CHK_04") != null) result.setDpr13991Chk4(rs.getString("DPR_139_91_CHK_04"));
		if (rs.getObject("DPR_139_91_CHK_05") != null) result.setDpr13991Chk5(rs.getString("DPR_139_91_CHK_05"));
		if (rs.getObject("DPR_139_91_CHK_06") != null) result.setDpr13991Chk6(rs.getString("DPR_139_91_CHK_06"));
		if (rs.getObject("DPR_139_91_CHK_07") != null) result.setDpr13991Chk7(rs.getString("DPR_139_91_CHK_07"));
		if (rs.getObject("DPR_139_91_CHK_08") != null) result.setDpr13991Chk8(rs.getString("DPR_139_91_CHK_08"));
		if (rs.getObject("DPR_139_91_CHK_09") != null) result.setDpr13991Chk9(rs.getString("DPR_139_91_CHK_09"));
		if (rs.getObject("DPR_139_91_CHK_10") != null) result.setDpr13991Chk10(rs.getString("DPR_139_91_CHK_10"));
		if (rs.getObject("DPR_139_91_CHK_11") != null) result.setDpr13991Chk11(rs.getString("DPR_139_91_CHK_11"));
		if (rs.getObject("DPR_139_91_CHK_12") != null) result.setDpr13991Chk12(rs.getString("DPR_139_91_CHK_12"));
		if (rs.getObject("DPR_139_91_CHK_13") != null) result.setDpr13991Chk13(rs.getString("DPR_139_91_CHK_13"));
		if (rs.getObject("DPR_139_91_CHK_14") != null) result.setDpr13991Chk14(rs.getString("DPR_139_91_CHK_14"));
		if (rs.getObject("DPR_139_91_CHK_15") != null) result.setDpr13991Chk15(rs.getString("DPR_139_91_CHK_15"));
		if (rs.getObject("DPR_139_91_CHK_16") != null) result.setDpr13991Chk16(rs.getString("DPR_139_91_CHK_16"));
		if (rs.getObject("DPR_139_91_CHK_17") != null) result.setDpr13991Chk17(rs.getString("DPR_139_91_CHK_17"));
		if (rs.getObject("DPR_139_91_CHK_18") != null) result.setDpr13991Chk18(rs.getString("DPR_139_91_CHK_18"));
		if (rs.getObject("DPR_139_91_CHK_19") != null) result.setDpr13991Chk19(rs.getString("DPR_139_91_CHK_19"));
		if (rs.getObject("DPR_139_91_CHK_20") != null) result.setDpr13991Chk20(rs.getString("DPR_139_91_CHK_20"));
		if (rs.getObject("DPR_139_91_CHK_21") != null) result.setDpr13991Chk21(rs.getString("DPR_139_91_CHK_21"));
		if (rs.getObject("DPR_139_91_CHK_22") != null) result.setDpr13991Chk22(rs.getString("DPR_139_91_CHK_22"));
		if (rs.getObject("DPR_139_91_CHK_23") != null) result.setDpr13991Chk23(rs.getString("DPR_139_91_CHK_23"));
		if (rs.getObject("DPR_139_91_CHK_24") != null) result.setDpr13991Chk24(rs.getString("DPR_139_91_CHK_24"));
		if (rs.getObject("DPR_139_91_CHK_25") != null) result.setDpr13991Chk25(rs.getString("DPR_139_91_CHK_25"));
		if (rs.getObject("DPR_139_91_CHK_26") != null) result.setDpr13991Chk26(rs.getString("DPR_139_91_CHK_26"));
		if (rs.getObject("DPR_139_91_CHK_27") != null) result.setDpr13991Chk27(rs.getString("DPR_139_91_CHK_27"));
		if (rs.getObject("DPR_139_91_CHK_28") != null) result.setDpr13991Chk28(rs.getString("DPR_139_91_CHK_28"));
		if (rs.getObject("DPR_139_91_CHK_29") != null) result.setDpr13991Chk29(rs.getString("DPR_139_91_CHK_29"));
		if (rs.getObject("DPR_139_91_CHK_30") != null) result.setDpr13991Chk30(rs.getString("DPR_139_91_CHK_30"));
		if (rs.getObject("DPR_139_91_CHK_31") != null) result.setDpr13991Chk31(rs.getString("DPR_139_91_CHK_31"));
		if (rs.getObject("DPR_139_91_CHK_32") != null) result.setDpr13991Chk32(rs.getString("DPR_139_91_CHK_32"));
		if (rs.getObject("DPR_139_91_CHK_33") != null) result.setDpr13991Chk33(rs.getString("DPR_139_91_CHK_33"));
		if (rs.getObject("DPR_139_91_CHK_34") != null) result.setDpr13991Chk34(rs.getString("DPR_139_91_CHK_34"));
		if (rs.getObject("DPR_139_91_CHK_35") != null) result.setDpr13991Chk35(rs.getString("DPR_139_91_CHK_35"));
		if (rs.getObject("DPR_139_91_CHK_36") != null) result.setDpr13991Chk36(rs.getString("DPR_139_91_CHK_36"));
		if (rs.getObject("DPR_139_91_CHK_37") != null) result.setDpr13991Chk37(rs.getString("DPR_139_91_CHK_37"));
		if (rs.getObject("DPR_139_91_CHK_38") != null) result.setDpr13991Chk38(rs.getString("DPR_139_91_CHK_38"));
		if (rs.getObject("DPR_139_91_CHK_39") != null) result.setDpr13991Chk39(rs.getString("DPR_139_91_CHK_39"));
		if (rs.getObject("D_LGS_42_146_RB_01") != null) result.setdLgs42146Rb01(rs.getString("D_LGS_42_146_RB_01"));
		if (rs.getObject("DPR_31_90_CHK_1") != null) result.setDpr3190Chk1(rs.getString("DPR_31_90_CHK_1"));
		if (rs.getObject("DPR_31_90_CHK_2") != null) result.setDpr3190Chk2(rs.getString("DPR_31_90_CHK_2"));
		if (rs.getObject("DPR_31_90_CHK_2_1") != null) result.setDpr3190Chk2_1(rs.getString("DPR_31_90_CHK_2_1"));
		if (rs.getObject("DPR_31_90_CHK_2_2") != null) result.setDpr3190Chk2_2(rs.getString("DPR_31_90_CHK_2_2"));
		if (rs.getObject("DPR_31_90_CHK_2_3") != null) result.setDpr3190Chk2_3(rs.getString("DPR_31_90_CHK_2_3"));
		if (rs.getObject("DPR_31_90_CHK_2_4") != null) result.setDpr3190Chk2_4(rs.getString("DPR_31_90_CHK_2_4"));
		if (rs.getObject("DPR_31_90_CHK_2_5") != null) result.setDpr3190Chk2_5(rs.getString("DPR_31_90_CHK_2_5"));
		if (rs.getObject("DPR_31_90_CHK_2_6") != null) result.setDpr3190Chk2_6(rs.getString("DPR_31_90_CHK_2_6"));
		if (rs.getObject("DPR_31_90_CHK_2_7") != null) result.setDpr3190Chk2_7(rs.getString("DPR_31_90_CHK_2_7"));
		if (rs.getObject("DPR_31_90_CHK_2_8") != null) result.setDpr3190Chk2_8(rs.getString("DPR_31_90_CHK_2_8"));
		if (rs.getObject("DPR_31_90_CHK_2_9") != null) result.setDpr3190Chk2_9(rs.getString("DPR_31_90_CHK_2_9"));
		if (rs.getObject("DPR_31_90_CHK_2_10") != null) result.setDpr3190Chk2_10(rs.getString("DPR_31_90_CHK_2_10"));
		if (rs.getObject("DPR_31_90_CHK_2_11") != null) result.setDpr3190Chk2_11(rs.getString("DPR_31_90_CHK_2_11"));
		if (rs.getObject("DPR_31_90_CHK_2_12") != null) result.setDpr3190Chk2_12(rs.getString("DPR_31_90_CHK_2_12"));
		if (rs.getObject("DPR_31_90_CHK_2_13") != null) result.setDpr3190Chk2_13(rs.getString("DPR_31_90_CHK_2_13"));
		if (rs.getObject("DPR_31_90_CHK_2_14") != null) result.setDpr3190Chk2_14(rs.getString("DPR_31_90_CHK_2_14"));
		if (rs.getObject("DPR_31_90_CHK_2_15") != null) result.setDpr3190Chk2_15(rs.getString("DPR_31_90_CHK_2_15"));
		if (rs.getObject("DPR_31_90_CHK_2_16") != null) result.setDpr3190Chk2_16(rs.getString("DPR_31_90_CHK_2_16"));
		if (rs.getObject("DPR_31_90_CHK_2_17") != null) result.setDpr3190Chk2_17(rs.getString("DPR_31_90_CHK_2_17"));
		if (rs.getObject("DPR_31_90_CHK_2_18") != null) result.setDpr3190Chk2_18(rs.getString("DPR_31_90_CHK_2_18"));
		if (rs.getObject("DPR_31_90_CHK_2_19") != null) result.setDpr3190Chk2_19(rs.getString("DPR_31_90_CHK_2_19"));
		if (rs.getObject("DPR_31_90_CHK_2_20") != null) result.setDpr3190Chk2_20(rs.getString("DPR_31_90_CHK_2_20"));
		if (rs.getObject("DPR_31_90_CHK_2_21") != null) result.setDpr3190Chk2_21(rs.getString("DPR_31_90_CHK_2_21"));
		if (rs.getObject("DPR_31_90_CHK_2_22") != null) result.setDpr3190Chk2_22(rs.getString("DPR_31_90_CHK_2_22"));
		if (rs.getObject("DPR_31_90_CHK_2_23") != null) result.setDpr3190Chk2_23(rs.getString("DPR_31_90_CHK_2_23"));
		if (rs.getObject("DPR_31_90_CHK_2_24") != null) result.setDpr3190Chk2_24(rs.getString("DPR_31_90_CHK_2_24"));
		if (rs.getObject("DPR_31_90_CHK_2_25") != null) result.setDpr3190Chk2_25(rs.getString("DPR_31_90_CHK_2_25"));
		if (rs.getObject("DPR_31_90_CHK_2_26") != null) result.setDpr3190Chk2_26(rs.getString("DPR_31_90_CHK_2_26"));
		if (rs.getObject("DPR_31_90_CHK_2_27") != null) result.setDpr3190Chk2_27(rs.getString("DPR_31_90_CHK_2_27"));
		if (rs.getObject("DPR_31_90_CHK_2_28") != null) result.setDpr3190Chk2_28(rs.getString("DPR_31_90_CHK_2_28"));
		if (rs.getObject("DPR_31_90_CHK_2_29") != null) result.setDpr3190Chk2_29(rs.getString("DPR_31_90_CHK_2_29"));
		if (rs.getObject("DPR_31_90_CHK_2_30") != null) result.setDpr3190Chk2_30(rs.getString("DPR_31_90_CHK_2_30"));
		if (rs.getObject("DPR_31_90_CHK_2_31") != null) result.setDpr3190Chk2_31(rs.getString("DPR_31_90_CHK_2_31"));
		if (rs.getObject("DPR_31_90_CHK_2_32") != null) result.setDpr3190Chk2_32(rs.getString("DPR_31_90_CHK_2_32"));
		if (rs.getObject("DPR_31_90_CHK_2_33") != null) result.setDpr3190Chk2_33(rs.getString("DPR_31_90_CHK_2_33"));
		if (rs.getObject("DPR_31_90_CHK_2_34") != null) result.setDpr3190Chk2_34(rs.getString("DPR_31_90_CHK_2_34"));
		if (rs.getObject("DPR_31_90_CHK_2_35") != null) result.setDpr3190Chk2_35(rs.getString("DPR_31_90_CHK_2_35"));
		if (rs.getObject("DPR_31_90_CHK_2_36") != null) result.setDpr3190Chk2_36(rs.getString("DPR_31_90_CHK_2_36"));
		if (rs.getObject("DPR_31_90_CHK_2_37") != null) result.setDpr3190Chk2_37(rs.getString("DPR_31_90_CHK_2_37"));
		if (rs.getObject("DPR_31_90_CHK_2_38") != null) result.setDpr3190Chk2_38(rs.getString("DPR_31_90_CHK_2_38"));
		if (rs.getObject("DPR_31_90_CHK_2_39") != null) result.setDpr3190Chk2_39(rs.getString("DPR_31_90_CHK_2_39"));
		if (rs.getObject("DPR_31_90_CHK_2_40") != null) result.setDpr3190Chk2_40(rs.getString("DPR_31_90_CHK_2_40"));
		if (rs.getObject("DPR_31_90_CHK_2_41") != null) result.setDpr3190Chk2_41(rs.getString("DPR_31_90_CHK_2_41"));
		if (rs.getObject("DPR_31_90_CHK_2_42") != null) result.setDpr3190Chk2_42(rs.getString("DPR_31_90_CHK_2_42"));
		return result;
	}
	

}
