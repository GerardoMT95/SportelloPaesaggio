/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

/**
 * CIVILIA_CS.TNO_PPTR_QUALIFIC_INTERVENTO
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class PptrQualificIntervento {
	
	//@Column(name="T_PRATICA_APPTR_ID")
	private long tPraticaApptrId;

	//tipo procedimento 3
	//@Column(name="D_LGS_42_167_CHK_A")
	private String dLgs42167ChkA;
	//tipo procedimento 3
	//@Column(name="D_LGS_42_167_CHK_B")
	private String dLgs42167ChkB;
	//tipo procedimento 3
	//@Column(name="D_LGS_42_167_CHK_C")
	private String dLgs42167ChkC;
	
	//@Column(name="D_LGS_42_91_RB_LIEVE_ENTITA")  null 1,2
	/**tipo procedimento 4
	 * 1			=Non ricade tra gli interventi di lieve entit&agrave come definiti dal DPR 139/2010.
	   2   =Ricade tra gli interventi di lieve entita' come definiti dal DPR 139/2010 in quanto:
	   
	   tipo_procedimento 6 
	   1=non ricadono tra gli interventi di lieve entit&agrave di cui all'allegato B al DPR 31/2017;
       2=ricadono tra gli interventi di lieve entit&agrave di cui all'allegato B al DPR 31/2017 in quanto:
	 */
	private String dLgs4291RbLieveEntita;
	
	//tipo procedimento 2 AUT SEMPLIFICATA 139/2010
	//@Column(name="DPR_139_91_CHK_01")
	private String dpr13991Chk1;

	//@Column(name="DPR_139_91_CHK_02")
	private String dpr13991Chk2;
	
	//@Column(name="DPR_139_91_CHK_03")
	private String dpr13991Chk3;
	
	//@Column(name="DPR_139_91_CHK_04")
	private String dpr13991Chk4;
	
	//@Column(name="DPR_139_91_CHK_05")
	private String dpr13991Chk5;
	
	//@Column(name="DPR_139_91_CHK_06")
	private String dpr13991Chk6;
	
	//@Column(name="DPR_139_91_CHK_07")
	private String dpr13991Chk7;
	
	//@Column(name="DPR_139_91_CHK_08")
	private String dpr13991Chk8;
	
	//@Column(name="DPR_139_91_CHK_09")
	private String dpr13991Chk9;
	
	//@Column(name="DPR_139_91_CHK_10")
	private String dpr13991Chk10;
	
	//@Column(name="DPR_139_91_CHK_11")
	private String dpr13991Chk11;
	
	//@Column(name="DPR_139_91_CHK_12")
	private String dpr13991Chk12;
	
	//@Column(name="DPR_139_91_CHK_13")
	private String dpr13991Chk13;
	
	//@Column(name="DPR_139_91_CHK_14")
	private String dpr13991Chk14;
	
	//@Column(name="DPR_139_91_CHK_15")
	private String dpr13991Chk15;
	
	//@Column(name="DPR_139_91_CHK_16")
	private String dpr13991Chk16;
	
	//@Column(name="DPR_139_91_CHK_17")
	private String dpr13991Chk17;
	
	//@Column(name="DPR_139_91_CHK_18")
	private String dpr13991Chk18;
	
	//@Column(name="DPR_139_91_CHK_19")
	private String dpr13991Chk19;
	
	//@Column(name="DPR_139_91_CHK_20")
	private String dpr13991Chk20;
	
	//@Column(name="DPR_139_91_CHK_21")
	private String dpr13991Chk21;
	
	//@Column(name="DPR_139_91_CHK_22")
	private String dpr13991Chk22;
	
	//@Column(name="DPR_139_91_CHK_23")
	private String dpr13991Chk23;
	
	//@Column(name="DPR_139_91_CHK_24")
	private String dpr13991Chk24;
	
	//@Column(name="DPR_139_91_CHK_25")
	private String dpr13991Chk25;
	
	//@Column(name="DPR_139_91_CHK_26")
	private String dpr13991Chk26;
	
	//@Column(name="DPR_139_91_CHK_27")
	private String dpr13991Chk27;
	
	//@Column(name="DPR_139_91_CHK_28")
	private String dpr13991Chk28;
	
	//@Column(name="DPR_139_91_CHK_29")
	private String dpr13991Chk29;
	
	//@Column(name="DPR_139_91_CHK_30")
	private String dpr13991Chk30;
	
	//@Column(name="DPR_139_91_CHK_31")
	private String dpr13991Chk31;
	
	//@Column(name="DPR_139_91_CHK_32")
	private String dpr13991Chk32;
	
	//@Column(name="DPR_139_91_CHK_33")
	private String dpr13991Chk33;
	
	//@Column(name="DPR_139_91_CHK_34")
	private String dpr13991Chk34;
	
	//@Column(name="DPR_139_91_CHK_35")
	private String dpr13991Chk35;
	
	//@Column(name="DPR_139_91_CHK_36")
	private String dpr13991Chk36;
	
	//@Column(name="DPR_139_91_CHK_37")
	private String dpr13991Chk37;
	
	//@Column(name="DPR_139_91_CHK_38")
	private String dpr13991Chk38;
	
	//@Column(name="DPR_139_91_CHK_39")
	private String dpr13991Chk39;

	//@Column(name="D_LGS_42_146_RB_01") 1,2,3 (tipo 1 ordinaria)
	//1=ordinari
	//2=di grande impegno territoriale a carattere areale<sup>(1)</sup>
	//3=di grande impegno territoriale a carattere lineare o a rete<sup>(2)</sup>
	private String dLgs42146Rb01;
	
	//tipo procedimento 5
	//@Column(name="DPR_31_90_CHK_1") 1=Istanza di rinnovo di autorizzazione paesaggistica anche rilasciata ai sensi dell\u2019art 146 del codice scaduta da non pi&ugrave di un anno e relative ad interventi in tutto o in parte non eseguiti, per progetti conformi a quanto in precedenza autorizzato e alle specifiche prescrizioni di tutela eventualmente sopravvenute
	private String dpr3190Chk1;
	//tipo procedimento 5
	//@Column(name="DPR_31_90_CHK_2") 1=Le opere rientrano tra gli interventi di lieve entit&agrave di cui all'allegato B del d.P.R. 31/2017:
	private String dpr3190Chk2;
	
	//@Column(name="DPR_31_90_CHK_2_1")
	private String dpr3190Chk2_1;
	
	//@Column(name="DPR_31_90_CHK_2_2")
	private String dpr3190Chk2_2;
	
	//@Column(name="DPR_31_90_CHK_2_3")
	private String dpr3190Chk2_3;
	
	//@Column(name="DPR_31_90_CHK_2_4")
	private String dpr3190Chk2_4;
	
	//@Column(name="DPR_31_90_CHK_2_5")
	private String dpr3190Chk2_5;
	
	//@Column(name="DPR_31_90_CHK_2_6")
	private String dpr3190Chk2_6;
	
	//@Column(name="DPR_31_90_CHK_2_7")
	private String dpr3190Chk2_7;
	
	//@Column(name="DPR_31_90_CHK_2_8")
	private String dpr3190Chk2_8;
	
	//@Column(name="DPR_31_90_CHK_2_9")
	private String dpr3190Chk2_9;
	
	//@Column(name="DPR_31_90_CHK_2_10")
	private String dpr3190Chk2_10;
	
	//@Column(name="DPR_31_90_CHK_2_11")
	private String dpr3190Chk2_11;
	
	//@Column(name="DPR_31_90_CHK_2_12")
	private String dpr3190Chk2_12;
	
	//@Column(name="DPR_31_90_CHK_2_13")
	private String dpr3190Chk2_13;
	
	//@Column(name="DPR_31_90_CHK_2_14")
	private String dpr3190Chk2_14;
	
	//@Column(name="DPR_31_90_CHK_2_15")
	private String dpr3190Chk2_15;
	
	//@Column(name="DPR_31_90_CHK_2_16")
	private String dpr3190Chk2_16;
	
	//@Column(name="DPR_31_90_CHK_2_17")
	private String dpr3190Chk2_17;
	
	//@Column(name="DPR_31_90_CHK_2_18")
	private String dpr3190Chk2_18;
	
	//@Column(name="DPR_31_90_CHK_2_19")
	private String dpr3190Chk2_19;
	
	//@Column(name="DPR_31_90_CHK_2_20")
	private String dpr3190Chk2_20;
	
	//@Column(name="DPR_31_90_CHK_2_21")
	private String dpr3190Chk2_21;
	
	//@Column(name="DPR_31_90_CHK_2_22")
	private String dpr3190Chk2_22;
	
	//@Column(name="DPR_31_90_CHK_2_23")
	private String dpr3190Chk2_23;
	
	//@Column(name="DPR_31_90_CHK_2_24")
	private String dpr3190Chk2_24;
	
	//@Column(name="DPR_31_90_CHK_2_25")
	private String dpr3190Chk2_25;
	
	//@Column(name="DPR_31_90_CHK_2_26")
	private String dpr3190Chk2_26;
	
	//@Column(name="DPR_31_90_CHK_2_27")
	private String dpr3190Chk2_27;
	
	//@Column(name="DPR_31_90_CHK_2_28")
	private String dpr3190Chk2_28;
	
	//@Column(name="DPR_31_90_CHK_2_29")
	private String dpr3190Chk2_29;
	
	//@Column(name="DPR_31_90_CHK_2_30")
	private String dpr3190Chk2_30;

	//@Column(name="DPR_31_90_CHK_2_31")
	private String dpr3190Chk2_31;
	
	//@Column(name="DPR_31_90_CHK_2_32")
	private String dpr3190Chk2_32;
	
	//@Column(name="DPR_31_90_CHK_2_33")
	private String dpr3190Chk2_33;
	
	//@Column(name="DPR_31_90_CHK_2_34")
	private String dpr3190Chk2_34;
	
	//@Column(name="DPR_31_90_CHK_2_35")
	private String dpr3190Chk2_35;
	
	//@Column(name="DPR_31_90_CHK_2_36")
	private String dpr3190Chk2_36;
	
	//@Column(name="DPR_31_90_CHK_2_37")
	private String dpr3190Chk2_37;
	
	//@Column(name="DPR_31_90_CHK_2_38")
	private String dpr3190Chk2_38;
	
	//@Column(name="DPR_31_90_CHK_2_39")
	private String dpr3190Chk2_39;
	
	//@Column(name="DPR_31_90_CHK_2_40")
	private String dpr3190Chk2_40;

	//@Column(name="DPR_31_90_CHK_2_41")
	private String dpr3190Chk2_41;
	
	//@Column(name="DPR_31_90_CHK_2_42")
	private String dpr3190Chk2_42;
	
	//@Column(name="PROG")
	private long prog;

	public long gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(long tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
	}

	public String getdLgs42167ChkA() {
		return dLgs42167ChkA;
	}

	public void setdLgs42167ChkA(String dLgs42167ChkA) {
		this.dLgs42167ChkA = dLgs42167ChkA;
	}

	public String getdLgs42167ChkB() {
		return dLgs42167ChkB;
	}

	public void setdLgs42167ChkB(String dLgs42167ChkB) {
		this.dLgs42167ChkB = dLgs42167ChkB;
	}

	public String getdLgs42167ChkC() {
		return dLgs42167ChkC;
	}

	public void setdLgs42167ChkC(String dLgs42167ChkC) {
		this.dLgs42167ChkC = dLgs42167ChkC;
	}

	public String getdLgs4291RbLieveEntita() {
		return dLgs4291RbLieveEntita;
	}

	public void setdLgs4291RbLieveEntita(String dLgs4291RbLieveEntita) {
		this.dLgs4291RbLieveEntita = dLgs4291RbLieveEntita;
	}

	public String getDpr13991Chk1() {
		return dpr13991Chk1;
	}

	public void setDpr13991Chk1(String dpr13991Chk1) {
		this.dpr13991Chk1 = dpr13991Chk1;
	}

	public String getDpr13991Chk2() {
		return dpr13991Chk2;
	}

	public void setDpr13991Chk2(String dpr13991Chk2) {
		this.dpr13991Chk2 = dpr13991Chk2;
	}

	public String getDpr13991Chk3() {
		return dpr13991Chk3;
	}

	public void setDpr13991Chk3(String dpr13991Chk3) {
		this.dpr13991Chk3 = dpr13991Chk3;
	}

	public String getDpr13991Chk4() {
		return dpr13991Chk4;
	}

	public void setDpr13991Chk4(String dpr13991Chk4) {
		this.dpr13991Chk4 = dpr13991Chk4;
	}

	public String getDpr13991Chk5() {
		return dpr13991Chk5;
	}

	public void setDpr13991Chk5(String dpr13991Chk5) {
		this.dpr13991Chk5 = dpr13991Chk5;
	}

	public String getDpr13991Chk6() {
		return dpr13991Chk6;
	}

	public void setDpr13991Chk6(String dpr13991Chk6) {
		this.dpr13991Chk6 = dpr13991Chk6;
	}

	public String getDpr13991Chk7() {
		return dpr13991Chk7;
	}

	public void setDpr13991Chk7(String dpr13991Chk7) {
		this.dpr13991Chk7 = dpr13991Chk7;
	}

	public String getDpr13991Chk8() {
		return dpr13991Chk8;
	}

	public void setDpr13991Chk8(String dpr13991Chk8) {
		this.dpr13991Chk8 = dpr13991Chk8;
	}

	public String getDpr13991Chk9() {
		return dpr13991Chk9;
	}

	public void setDpr13991Chk9(String dpr13991Chk9) {
		this.dpr13991Chk9 = dpr13991Chk9;
	}

	public String getDpr13991Chk10() {
		return dpr13991Chk10;
	}

	public void setDpr13991Chk10(String dpr13991Chk10) {
		this.dpr13991Chk10 = dpr13991Chk10;
	}

	public String getDpr13991Chk11() {
		return dpr13991Chk11;
	}

	public void setDpr13991Chk11(String dpr13991Chk11) {
		this.dpr13991Chk11 = dpr13991Chk11;
	}

	public String getDpr13991Chk12() {
		return dpr13991Chk12;
	}

	public void setDpr13991Chk12(String dpr13991Chk12) {
		this.dpr13991Chk12 = dpr13991Chk12;
	}

	public String getDpr13991Chk13() {
		return dpr13991Chk13;
	}

	public void setDpr13991Chk13(String dpr13991Chk13) {
		this.dpr13991Chk13 = dpr13991Chk13;
	}

	public String getDpr13991Chk14() {
		return dpr13991Chk14;
	}

	public void setDpr13991Chk14(String dpr13991Chk14) {
		this.dpr13991Chk14 = dpr13991Chk14;
	}

	public String getDpr13991Chk15() {
		return dpr13991Chk15;
	}

	public void setDpr13991Chk15(String dpr13991Chk15) {
		this.dpr13991Chk15 = dpr13991Chk15;
	}

	public String getDpr13991Chk16() {
		return dpr13991Chk16;
	}

	public void setDpr13991Chk16(String dpr13991Chk16) {
		this.dpr13991Chk16 = dpr13991Chk16;
	}

	public String getDpr13991Chk17() {
		return dpr13991Chk17;
	}

	public void setDpr13991Chk17(String dpr13991Chk17) {
		this.dpr13991Chk17 = dpr13991Chk17;
	}

	public String getDpr13991Chk18() {
		return dpr13991Chk18;
	}

	public void setDpr13991Chk18(String dpr13991Chk18) {
		this.dpr13991Chk18 = dpr13991Chk18;
	}

	public String getDpr13991Chk19() {
		return dpr13991Chk19;
	}

	public void setDpr13991Chk19(String dpr13991Chk19) {
		this.dpr13991Chk19 = dpr13991Chk19;
	}

	public String getDpr13991Chk20() {
		return dpr13991Chk20;
	}

	public void setDpr13991Chk20(String dpr13991Chk20) {
		this.dpr13991Chk20 = dpr13991Chk20;
	}

	public String getDpr13991Chk21() {
		return dpr13991Chk21;
	}

	public void setDpr13991Chk21(String dpr13991Chk21) {
		this.dpr13991Chk21 = dpr13991Chk21;
	}

	public String getDpr13991Chk22() {
		return dpr13991Chk22;
	}

	public void setDpr13991Chk22(String dpr13991Chk22) {
		this.dpr13991Chk22 = dpr13991Chk22;
	}

	public String getDpr13991Chk23() {
		return dpr13991Chk23;
	}

	public void setDpr13991Chk23(String dpr13991Chk23) {
		this.dpr13991Chk23 = dpr13991Chk23;
	}

	public String getDpr13991Chk24() {
		return dpr13991Chk24;
	}

	public void setDpr13991Chk24(String dpr13991Chk24) {
		this.dpr13991Chk24 = dpr13991Chk24;
	}

	public String getDpr13991Chk25() {
		return dpr13991Chk25;
	}

	public void setDpr13991Chk25(String dpr13991Chk25) {
		this.dpr13991Chk25 = dpr13991Chk25;
	}

	public String getDpr13991Chk26() {
		return dpr13991Chk26;
	}

	public void setDpr13991Chk26(String dpr13991Chk26) {
		this.dpr13991Chk26 = dpr13991Chk26;
	}

	public String getDpr13991Chk27() {
		return dpr13991Chk27;
	}

	public void setDpr13991Chk27(String dpr13991Chk27) {
		this.dpr13991Chk27 = dpr13991Chk27;
	}

	public String getDpr13991Chk28() {
		return dpr13991Chk28;
	}

	public void setDpr13991Chk28(String dpr13991Chk28) {
		this.dpr13991Chk28 = dpr13991Chk28;
	}

	public String getDpr13991Chk29() {
		return dpr13991Chk29;
	}

	public void setDpr13991Chk29(String dpr13991Chk29) {
		this.dpr13991Chk29 = dpr13991Chk29;
	}

	public String getDpr13991Chk30() {
		return dpr13991Chk30;
	}

	public void setDpr13991Chk30(String dpr13991Chk30) {
		this.dpr13991Chk30 = dpr13991Chk30;
	}

	public String getDpr13991Chk31() {
		return dpr13991Chk31;
	}

	public void setDpr13991Chk31(String dpr13991Chk31) {
		this.dpr13991Chk31 = dpr13991Chk31;
	}

	public String getDpr13991Chk32() {
		return dpr13991Chk32;
	}

	public void setDpr13991Chk32(String dpr13991Chk32) {
		this.dpr13991Chk32 = dpr13991Chk32;
	}

	public String getDpr13991Chk33() {
		return dpr13991Chk33;
	}

	public void setDpr13991Chk33(String dpr13991Chk33) {
		this.dpr13991Chk33 = dpr13991Chk33;
	}

	public String getDpr13991Chk34() {
		return dpr13991Chk34;
	}

	public void setDpr13991Chk34(String dpr13991Chk34) {
		this.dpr13991Chk34 = dpr13991Chk34;
	}

	public String getDpr13991Chk35() {
		return dpr13991Chk35;
	}

	public void setDpr13991Chk35(String dpr13991Chk35) {
		this.dpr13991Chk35 = dpr13991Chk35;
	}

	public String getDpr13991Chk36() {
		return dpr13991Chk36;
	}

	public void setDpr13991Chk36(String dpr13991Chk36) {
		this.dpr13991Chk36 = dpr13991Chk36;
	}

	public String getDpr13991Chk37() {
		return dpr13991Chk37;
	}

	public void setDpr13991Chk37(String dpr13991Chk37) {
		this.dpr13991Chk37 = dpr13991Chk37;
	}

	public String getDpr13991Chk38() {
		return dpr13991Chk38;
	}

	public void setDpr13991Chk38(String dpr13991Chk38) {
		this.dpr13991Chk38 = dpr13991Chk38;
	}

	public String getDpr13991Chk39() {
		return dpr13991Chk39;
	}

	public void setDpr13991Chk39(String dpr13991Chk39) {
		this.dpr13991Chk39 = dpr13991Chk39;
	}

	public String getdLgs42146Rb01() {
		return dLgs42146Rb01;
	}

	public void setdLgs42146Rb01(String dLgs42146Rb01) {
		this.dLgs42146Rb01 = dLgs42146Rb01;
	}

	public String getDpr3190Chk1() {
		return dpr3190Chk1;
	}

	public void setDpr3190Chk1(String dpr3190Chk1) {
		this.dpr3190Chk1 = dpr3190Chk1;
	}

	public String getDpr3190Chk2() {
		return dpr3190Chk2;
	}

	public void setDpr3190Chk2(String dpr3190Chk2) {
		this.dpr3190Chk2 = dpr3190Chk2;
	}

	public String getDpr3190Chk2_1() {
		return dpr3190Chk2_1;
	}

	public void setDpr3190Chk2_1(String dpr3190Chk2_1) {
		this.dpr3190Chk2_1 = dpr3190Chk2_1;
	}

	public String getDpr3190Chk2_2() {
		return dpr3190Chk2_2;
	}

	public void setDpr3190Chk2_2(String dpr3190Chk2_2) {
		this.dpr3190Chk2_2 = dpr3190Chk2_2;
	}

	public String getDpr3190Chk2_3() {
		return dpr3190Chk2_3;
	}

	public void setDpr3190Chk2_3(String dpr3190Chk2_3) {
		this.dpr3190Chk2_3 = dpr3190Chk2_3;
	}

	public String getDpr3190Chk2_4() {
		return dpr3190Chk2_4;
	}

	public void setDpr3190Chk2_4(String dpr3190Chk2_4) {
		this.dpr3190Chk2_4 = dpr3190Chk2_4;
	}

	public String getDpr3190Chk2_5() {
		return dpr3190Chk2_5;
	}

	public void setDpr3190Chk2_5(String dpr3190Chk2_5) {
		this.dpr3190Chk2_5 = dpr3190Chk2_5;
	}

	public String getDpr3190Chk2_6() {
		return dpr3190Chk2_6;
	}

	public void setDpr3190Chk2_6(String dpr3190Chk2_6) {
		this.dpr3190Chk2_6 = dpr3190Chk2_6;
	}

	public String getDpr3190Chk2_7() {
		return dpr3190Chk2_7;
	}

	public void setDpr3190Chk2_7(String dpr3190Chk2_7) {
		this.dpr3190Chk2_7 = dpr3190Chk2_7;
	}

	public String getDpr3190Chk2_8() {
		return dpr3190Chk2_8;
	}

	public void setDpr3190Chk2_8(String dpr3190Chk2_8) {
		this.dpr3190Chk2_8 = dpr3190Chk2_8;
	}

	public String getDpr3190Chk2_9() {
		return dpr3190Chk2_9;
	}

	public void setDpr3190Chk2_9(String dpr3190Chk2_9) {
		this.dpr3190Chk2_9 = dpr3190Chk2_9;
	}

	public String getDpr3190Chk2_10() {
		return dpr3190Chk2_10;
	}

	public void setDpr3190Chk2_10(String dpr3190Chk2_10) {
		this.dpr3190Chk2_10 = dpr3190Chk2_10;
	}

	public String getDpr3190Chk2_11() {
		return dpr3190Chk2_11;
	}

	public void setDpr3190Chk2_11(String dpr3190Chk2_11) {
		this.dpr3190Chk2_11 = dpr3190Chk2_11;
	}

	public String getDpr3190Chk2_12() {
		return dpr3190Chk2_12;
	}

	public void setDpr3190Chk2_12(String dpr3190Chk2_12) {
		this.dpr3190Chk2_12 = dpr3190Chk2_12;
	}

	public String getDpr3190Chk2_13() {
		return dpr3190Chk2_13;
	}

	public void setDpr3190Chk2_13(String dpr3190Chk2_13) {
		this.dpr3190Chk2_13 = dpr3190Chk2_13;
	}

	public String getDpr3190Chk2_14() {
		return dpr3190Chk2_14;
	}

	public void setDpr3190Chk2_14(String dpr3190Chk2_14) {
		this.dpr3190Chk2_14 = dpr3190Chk2_14;
	}

	public String getDpr3190Chk2_15() {
		return dpr3190Chk2_15;
	}

	public void setDpr3190Chk2_15(String dpr3190Chk2_15) {
		this.dpr3190Chk2_15 = dpr3190Chk2_15;
	}

	public String getDpr3190Chk2_16() {
		return dpr3190Chk2_16;
	}

	public void setDpr3190Chk2_16(String dpr3190Chk2_16) {
		this.dpr3190Chk2_16 = dpr3190Chk2_16;
	}

	public String getDpr3190Chk2_17() {
		return dpr3190Chk2_17;
	}

	public void setDpr3190Chk2_17(String dpr3190Chk2_17) {
		this.dpr3190Chk2_17 = dpr3190Chk2_17;
	}

	public String getDpr3190Chk2_18() {
		return dpr3190Chk2_18;
	}

	public void setDpr3190Chk2_18(String dpr3190Chk2_18) {
		this.dpr3190Chk2_18 = dpr3190Chk2_18;
	}

	public String getDpr3190Chk2_19() {
		return dpr3190Chk2_19;
	}

	public void setDpr3190Chk2_19(String dpr3190Chk2_19) {
		this.dpr3190Chk2_19 = dpr3190Chk2_19;
	}

	public String getDpr3190Chk2_20() {
		return dpr3190Chk2_20;
	}

	public void setDpr3190Chk2_20(String dpr3190Chk2_20) {
		this.dpr3190Chk2_20 = dpr3190Chk2_20;
	}

	public String getDpr3190Chk2_21() {
		return dpr3190Chk2_21;
	}

	public void setDpr3190Chk2_21(String dpr3190Chk2_21) {
		this.dpr3190Chk2_21 = dpr3190Chk2_21;
	}

	public String getDpr3190Chk2_22() {
		return dpr3190Chk2_22;
	}

	public void setDpr3190Chk2_22(String dpr3190Chk2_22) {
		this.dpr3190Chk2_22 = dpr3190Chk2_22;
	}

	public String getDpr3190Chk2_23() {
		return dpr3190Chk2_23;
	}

	public void setDpr3190Chk2_23(String dpr3190Chk2_23) {
		this.dpr3190Chk2_23 = dpr3190Chk2_23;
	}

	public String getDpr3190Chk2_24() {
		return dpr3190Chk2_24;
	}

	public void setDpr3190Chk2_24(String dpr3190Chk2_24) {
		this.dpr3190Chk2_24 = dpr3190Chk2_24;
	}

	public String getDpr3190Chk2_25() {
		return dpr3190Chk2_25;
	}

	public void setDpr3190Chk2_25(String dpr3190Chk2_25) {
		this.dpr3190Chk2_25 = dpr3190Chk2_25;
	}

	public String getDpr3190Chk2_26() {
		return dpr3190Chk2_26;
	}

	public void setDpr3190Chk2_26(String dpr3190Chk2_26) {
		this.dpr3190Chk2_26 = dpr3190Chk2_26;
	}

	public String getDpr3190Chk2_27() {
		return dpr3190Chk2_27;
	}

	public void setDpr3190Chk2_27(String dpr3190Chk2_27) {
		this.dpr3190Chk2_27 = dpr3190Chk2_27;
	}

	public String getDpr3190Chk2_28() {
		return dpr3190Chk2_28;
	}

	public void setDpr3190Chk2_28(String dpr3190Chk2_28) {
		this.dpr3190Chk2_28 = dpr3190Chk2_28;
	}

	public String getDpr3190Chk2_29() {
		return dpr3190Chk2_29;
	}

	public void setDpr3190Chk2_29(String dpr3190Chk2_29) {
		this.dpr3190Chk2_29 = dpr3190Chk2_29;
	}

	public String getDpr3190Chk2_30() {
		return dpr3190Chk2_30;
	}

	public void setDpr3190Chk2_30(String dpr3190Chk2_30) {
		this.dpr3190Chk2_30 = dpr3190Chk2_30;
	}

	public String getDpr3190Chk2_31() {
		return dpr3190Chk2_31;
	}

	public void setDpr3190Chk2_31(String dpr3190Chk2_31) {
		this.dpr3190Chk2_31 = dpr3190Chk2_31;
	}

	public String getDpr3190Chk2_32() {
		return dpr3190Chk2_32;
	}

	public void setDpr3190Chk2_32(String dpr3190Chk2_32) {
		this.dpr3190Chk2_32 = dpr3190Chk2_32;
	}

	public String getDpr3190Chk2_33() {
		return dpr3190Chk2_33;
	}

	public void setDpr3190Chk2_33(String dpr3190Chk2_33) {
		this.dpr3190Chk2_33 = dpr3190Chk2_33;
	}

	public String getDpr3190Chk2_34() {
		return dpr3190Chk2_34;
	}

	public void setDpr3190Chk2_34(String dpr3190Chk2_34) {
		this.dpr3190Chk2_34 = dpr3190Chk2_34;
	}

	public String getDpr3190Chk2_35() {
		return dpr3190Chk2_35;
	}

	public void setDpr3190Chk2_35(String dpr3190Chk2_35) {
		this.dpr3190Chk2_35 = dpr3190Chk2_35;
	}

	public String getDpr3190Chk2_36() {
		return dpr3190Chk2_36;
	}

	public void setDpr3190Chk2_36(String dpr3190Chk2_36) {
		this.dpr3190Chk2_36 = dpr3190Chk2_36;
	}

	public String getDpr3190Chk2_37() {
		return dpr3190Chk2_37;
	}

	public void setDpr3190Chk2_37(String dpr3190Chk2_37) {
		this.dpr3190Chk2_37 = dpr3190Chk2_37;
	}

	public String getDpr3190Chk2_38() {
		return dpr3190Chk2_38;
	}

	public void setDpr3190Chk2_38(String dpr3190Chk2_38) {
		this.dpr3190Chk2_38 = dpr3190Chk2_38;
	}

	public String getDpr3190Chk2_39() {
		return dpr3190Chk2_39;
	}

	public void setDpr3190Chk2_39(String dpr3190Chk2_39) {
		this.dpr3190Chk2_39 = dpr3190Chk2_39;
	}

	public String getDpr3190Chk2_40() {
		return dpr3190Chk2_40;
	}

	public void setDpr3190Chk2_40(String dpr3190Chk2_40) {
		this.dpr3190Chk2_40 = dpr3190Chk2_40;
	}

	public String getDpr3190Chk2_41() {
		return dpr3190Chk2_41;
	}

	public void setDpr3190Chk2_41(String dpr3190Chk2_41) {
		this.dpr3190Chk2_41 = dpr3190Chk2_41;
	}

	public String getDpr3190Chk2_42() {
		return dpr3190Chk2_42;
	}

	public void setDpr3190Chk2_42(String dpr3190Chk2_42) {
		this.dpr3190Chk2_42 = dpr3190Chk2_42;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

	@Override
	public String toString() {
		return "PptrQualificIntervento [tPraticaApptrId=" + tPraticaApptrId + ", dLgs42167ChkA=" + dLgs42167ChkA
				+ ", dLgs42167ChkB=" + dLgs42167ChkB + ", dLgs42167ChkC=" + dLgs42167ChkC + ", dLgs4291RbLieveEntita="
				+ dLgs4291RbLieveEntita + ", dpr13991Chk1=" + dpr13991Chk1 + ", dpr13991Chk2=" + dpr13991Chk2
				+ ", dpr13991Chk3=" + dpr13991Chk3 + ", dpr13991Chk4=" + dpr13991Chk4 + ", dpr13991Chk5=" + dpr13991Chk5
				+ ", dpr13991Chk6=" + dpr13991Chk6 + ", dpr13991Chk7=" + dpr13991Chk7 + ", dpr13991Chk8=" + dpr13991Chk8
				+ ", dpr13991Chk9=" + dpr13991Chk9 + ", dpr13991Chk10=" + dpr13991Chk10 + ", dpr13991Chk11="
				+ dpr13991Chk11 + ", dpr13991Chk12=" + dpr13991Chk12 + ", dpr13991Chk13=" + dpr13991Chk13
				+ ", dpr13991Chk14=" + dpr13991Chk14 + ", dpr13991Chk15=" + dpr13991Chk15 + ", dpr13991Chk16="
				+ dpr13991Chk16 + ", dpr13991Chk17=" + dpr13991Chk17 + ", dpr13991Chk18=" + dpr13991Chk18
				+ ", dpr13991Chk19=" + dpr13991Chk19 + ", dpr13991Chk20=" + dpr13991Chk20 + ", dpr13991Chk21="
				+ dpr13991Chk21 + ", dpr13991Chk22=" + dpr13991Chk22 + ", dpr13991Chk23=" + dpr13991Chk23
				+ ", dpr13991Chk24=" + dpr13991Chk24 + ", dpr13991Chk25=" + dpr13991Chk25 + ", dpr13991Chk26="
				+ dpr13991Chk26 + ", dpr13991Chk27=" + dpr13991Chk27 + ", dpr13991Chk28=" + dpr13991Chk28
				+ ", dpr13991Chk29=" + dpr13991Chk29 + ", dpr13991Chk30=" + dpr13991Chk30 + ", dpr13991Chk31="
				+ dpr13991Chk31 + ", dpr13991Chk32=" + dpr13991Chk32 + ", dpr13991Chk33=" + dpr13991Chk33
				+ ", dpr13991Chk34=" + dpr13991Chk34 + ", dpr13991Chk35=" + dpr13991Chk35 + ", dpr13991Chk36="
				+ dpr13991Chk36 + ", dpr13991Chk37=" + dpr13991Chk37 + ", dpr13991Chk38=" + dpr13991Chk38
				+ ", dpr13991Chk39=" + dpr13991Chk39 + ", dLgs42146Rb01=" + dLgs42146Rb01 + ", dpr3190Chk1="
				+ dpr3190Chk1 + ", dpr3190Chk2=" + dpr3190Chk2 + ", dpr3190Chk2_1=" + dpr3190Chk2_1 + ", dpr3190Chk2_2="
				+ dpr3190Chk2_2 + ", dpr3190Chk2_3=" + dpr3190Chk2_3 + ", dpr3190Chk2_4=" + dpr3190Chk2_4
				+ ", dpr3190Chk2_5=" + dpr3190Chk2_5 + ", dpr3190Chk2_6=" + dpr3190Chk2_6 + ", dpr3190Chk2_7="
				+ dpr3190Chk2_7 + ", dpr3190Chk2_8=" + dpr3190Chk2_8 + ", dpr3190Chk2_9=" + dpr3190Chk2_9
				+ ", dpr3190Chk2_10=" + dpr3190Chk2_10 + ", dpr3190Chk2_11=" + dpr3190Chk2_11 + ", dpr3190Chk2_12="
				+ dpr3190Chk2_12 + ", dpr3190Chk2_13=" + dpr3190Chk2_13 + ", dpr3190Chk2_14=" + dpr3190Chk2_14
				+ ", dpr3190Chk2_15=" + dpr3190Chk2_15 + ", dpr3190Chk2_16=" + dpr3190Chk2_16 + ", dpr3190Chk2_17="
				+ dpr3190Chk2_17 + ", dpr3190Chk2_18=" + dpr3190Chk2_18 + ", dpr3190Chk2_19=" + dpr3190Chk2_19
				+ ", dpr3190Chk2_20=" + dpr3190Chk2_20 + ", dpr3190Chk2_21=" + dpr3190Chk2_21 + ", dpr3190Chk2_22="
				+ dpr3190Chk2_22 + ", dpr3190Chk2_23=" + dpr3190Chk2_23 + ", dpr3190Chk2_24=" + dpr3190Chk2_24
				+ ", dpr3190Chk2_25=" + dpr3190Chk2_25 + ", dpr3190Chk2_26=" + dpr3190Chk2_26 + ", dpr3190Chk2_27="
				+ dpr3190Chk2_27 + ", dpr3190Chk2_28=" + dpr3190Chk2_28 + ", dpr3190Chk2_29=" + dpr3190Chk2_29
				+ ", dpr3190Chk2_30=" + dpr3190Chk2_30 + ", dpr3190Chk2_31=" + dpr3190Chk2_31 + ", dpr3190Chk2_32="
				+ dpr3190Chk2_32 + ", dpr3190Chk2_33=" + dpr3190Chk2_33 + ", dpr3190Chk2_34=" + dpr3190Chk2_34
				+ ", dpr3190Chk2_35=" + dpr3190Chk2_35 + ", dpr3190Chk2_36=" + dpr3190Chk2_36 + ", dpr3190Chk2_37="
				+ dpr3190Chk2_37 + ", dpr3190Chk2_38=" + dpr3190Chk2_38 + ", dpr3190Chk2_39=" + dpr3190Chk2_39
				+ ", dpr3190Chk2_40=" + dpr3190Chk2_40 + ", dpr3190Chk2_41=" + dpr3190Chk2_41 + ", dpr3190Chk2_42="
				+ dpr3190Chk2_42 + ", prog=" + prog + "]";
	}

	
}
