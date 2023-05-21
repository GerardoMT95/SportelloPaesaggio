package it.eng.tz.puglia.aet.test;

public class Column {
	
	private final String name; 
	private final String javaType;
	private final String sqlType;
	private final boolean isPk;
	private final boolean isSerial;
	private final boolean isNullable;
	private final boolean hasDefaultValue;
	
	public Column(String name, String sqlType, String javaType, boolean isPk, boolean isSerial, boolean isNullable, boolean hasDefaultValue) {
		super();
		this.name = name;
		this.javaType = javaType;
		this.sqlType = sqlType;
		this.isPk = isPk;
		this.isSerial = isSerial;
		this.isNullable = isNullable;
		this.hasDefaultValue = hasDefaultValue;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the javaType
	 */
	public String getJavaType() {
		return javaType;
	}
	/**
	 * @return the isPk
	 */
	public boolean isPk() {
		return isPk;
	}
	/**
	 * @return the isSerial
	 */
	public boolean isSerial() {
		return isSerial;
	}

	/**
	 * @return the isNullable
	 */
	public boolean isNullable() {
		return isNullable;
	}

	/**
	 * @return the sqlType
	 */
	public String getSqlType() {
		return sqlType;
	}
	/**
	 * @return isGeometry
	 */
	public boolean isGeometry() {
		return "geometry".equalsIgnoreCase(sqlType);
	}

	/**
	 * @return the hasDefaultValue
	 */
	public boolean isHasDefaultValue() {
		return hasDefaultValue;
	}

}
