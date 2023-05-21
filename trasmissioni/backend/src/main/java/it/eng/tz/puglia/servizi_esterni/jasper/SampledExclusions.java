package it.eng.tz.puglia.servizi_esterni.jasper;

public enum SampledExclusions {
	ALL("listAll"),
	SELECTED("listSelected"),
	NOT_SELECTED("listNotSelected");
	
	private String jasperMApKey;
	private SampledExclusions(String mapKey) {
		jasperMApKey = mapKey;
	}
	
	/**
	 * Map keys used by jasper report compiler
	 * @return
	 */
	public String getMapKey() {
		return jasperMApKey;
	}
}
