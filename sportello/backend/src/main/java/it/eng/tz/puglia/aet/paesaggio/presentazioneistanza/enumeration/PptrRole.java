package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

public enum PptrRole {
		RICHIEDENTE("RICHIEDENTE"),
		EMPTY("");
		
		String feRole;
		
		private PptrRole(String role) {
			feRole = role;
		}

		public static String getPptrRole(String feRole) {
			switch (feRole) {
			case "RICHIEDENTE":
				return PptrRole.RICHIEDENTE.name();
			default:
				return PptrRole.EMPTY.name();
			}
		}
}

