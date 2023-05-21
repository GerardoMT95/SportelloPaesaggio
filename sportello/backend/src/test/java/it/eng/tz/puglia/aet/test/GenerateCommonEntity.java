package it.eng.tz.puglia.aet.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;



public abstract class GenerateCommonEntity {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Value("${jdbc.driver}")
	private String driver;
	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String password;
	@Value("${jdbc.schema}")
	private String schema;
	@Value("${jdbc.tables}")
	private String tables;
	@Value("${pathFile}")
	private String path;
	@Value("${packageString}")
	private String stringpackage;
	@Value("${onlyRepo:}")
	private boolean onlyRepo; 
	
	private static final String SQL_DEFAULT_VALUE = "SELECT column_default FROM information_schema.columns WHERE table_schema || '.' || table_name = ? and column_name = ?"; 
	
	protected String getDefaultSchema() {
		return null;
	}
	protected final String getSchema(Table table) {
		return StringUtil.isEmpty(this.getDefaultSchema()) 
				? (StringUtil.concateneString("\\\"",table.getSchema(),"\\\".\\\"")) 
				: this.getDefaultSchema()
				;
	}
	protected String getCrudServiceClassName() {
		return "it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.GenericCrudService";
	}
	protected String getCrudServiceSimpleClassName() {
		return extractSimpleClassName(this.getCrudServiceClassName());
	}
	
	private final static String extractSimpleClassName(String className) {
		String[] classNameSplit = className.split("\\.");
		return classNameSplit[classNameSplit.length - 1];
	}
	
	public void commonGenerate() throws Exception{
		logger.info("Start test");
		try {
			Class.forName(this.driver);
			String basePackagePath           = StringUtil.concateneString(path, path.endsWith("/") ? "" : "/",stringpackage.replace(".", "/"),"/");
			String dtoBasePath               = StringUtil.concateneString(basePackagePath ,"dto/"         );
			String rowMapperBasePath         = StringUtil.concateneString(basePackagePath ,"rowmapper/"   );
			String searchBasePath            = StringUtil.concateneString(basePackagePath ,"search/"      );
			String repositoryBasePath        = StringUtil.concateneString(basePackagePath ,"repository/"  );
			String serviceMapperBasePath     = StringUtil.concateneString(basePackagePath ,"service/"     );
			String serviceImplMapperBasePath = StringUtil.concateneString(basePackagePath ,"service/impl/");
			String controllerBasePath        = StringUtil.concateneString(basePackagePath ,"controller/"  );
			
			new File(dtoBasePath              ).mkdirs();
			new File(rowMapperBasePath        ).mkdirs();
			new File(repositoryBasePath       ).mkdirs();
			new File(serviceMapperBasePath    ).mkdirs();
			new File(serviceImplMapperBasePath).mkdirs();
			new File(searchBasePath           ).mkdirs();
			new File(controllerBasePath       ).mkdirs();
			
			
			try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password);){
				DatabaseMetaData database = connection.getMetaData();
				List<Table>      tabelle  = new ArrayList<>();
				try(ResultSet rs = database.getTables(null, StringUtil.isEmpty(this.schema) ? null : this.schema, StringUtil.isEmpty(this.tables) ? null : this.tables, new String[] {"TABLE"});){
					while(rs.next()) {
						tabelle.add(new Table(rs.getString("TABLE_SCHEM"), rs.getString("TABLE_NAME")));
					}
				}
				for(Table table : tabelle) {
					try(ResultSet rs   = database.getColumns    (null, table.getSchema(), table.getTableName(), null);
					    ResultSet rsPK = database.getPrimaryKeys(null, table.getSchema(), table.getTableName());
					){
						List<String> columnsPk = new ArrayList<>();
						while(rsPK.next()) {
							columnsPk.add(rsPK.getString("COLUMN_NAME"));
						}
						while(rs.next()) {
							String  columnName    = rs.getString("COLUMN_NAME");
							
							
							
							String  typeName      = rs.getString("TYPE_NAME");
							int     sqlType       = rs.getInt   ("DATA_TYPE");
							boolean autoincrement = rs.getString("IS_AUTOINCREMENT").equalsIgnoreCase("YES");
							boolean isPk          = columnsPk.contains(columnName);
							boolean isNullable    = rs.getString("IS_NULLABLE").equalsIgnoreCase("YES");
							
							table.getColonne().add(new Column(columnName
									                         ,typeName
									                         ,this.getJavaType(sqlType, isNullable)
									                         ,isPk
									                         ,autoincrement
									                         ,isNullable
									                         ,this.columnHasDefaultValue(columnName
									                        		                    ,table.getTableName()
									                        		                    ,table.getSchema()
									                        		                    ,connection
									                        		                    )
									                         )
									              );
						}
					}
				}
				for(Table table : tabelle) {
					logger.info(StringUtil.concateneString("Start generate table: ", table.getTableName()));
					this.generateDTO        (table, dtoBasePath              );
					this.generateSearch     (table, searchBasePath           );
					this.generateRowMapper  (table, rowMapperBasePath        );
					this.generateRepository (table, repositoryBasePath       );
					if(!onlyRepo) {
						this.generateService    (table, serviceMapperBasePath    );
						this.generateServiceImpl(table, serviceImplMapperBasePath);
						this.generateController (table, controllerBasePath       );
					}
					logger.info(StringUtil.concateneString("End generate table: ", table.getTableName()));
				}
			}
			
		}catch(Exception e) {
			logger.error("Error in test", e);
		}
		logger.info("End test");
	}
	
	protected boolean columnHasDefaultValue(String column, String tableName, String schemaName, Connection connection) throws SQLException {
		boolean result = false;
		try(PreparedStatement ps = connection.prepareStatement(SQL_DEFAULT_VALUE)){
			ps.setString(1, StringUtil.concateneString(schemaName, ".", tableName));
			ps.setString(2, column);
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					String defaultValue = rs.getString(1);
					result = defaultValue != null
							&& defaultValue.startsWith("NULL::") == false
							;
				}
			}
		}
		return result;
	}
	
	/**
	 * Generate dto 
	 */
	private void generateDTO(Table table, String dtoBasePath) throws Exception{
		String className = this.tableToClass(table);
		PrintStream ps = new PrintStream(new FileOutputStream(StringUtil.concateneString(dtoBasePath
				                                                                        ,className
				                                                                        ,"DTO.java"
				                                                                        )
				                                              )
				                         );
		ps.println(StringUtil.concateneString("package ", stringpackage , ".dto;"));
		ps.println("");
		ps.println("import java.io.Serializable;");
		ps.println("import java.sql.*;");
		ps.println("import java.math.*;");
		ps.println("import java.util.*;");
		ps.println("import java.util.Date;");
		ps.println("import it.eng.tz.puglia.util.json.*;");
		ps.println("import com.fasterxml.jackson.databind.annotation.JsonDeserialize;");
		ps.println("import com.fasterxml.jackson.databind.annotation.JsonSerialize;");
		ps.println("");
		ps.println("/**");
		ps.println(StringUtil.concateneString(" * DTO for table ", table.getSchema(), ".", table.getTableName()));
		ps.println(" */");
		ps.println(StringUtil.concateneString("public class ", className, "DTO implements Serializable{"));
		ps.println(StringUtil.concateneString(""));
		long serializableId = (long)(Math.random() * Math.pow(10, 10));
		ps.println(StringUtil.concateneString("    private static final long serialVersionUID = ", serializableId, "L;"));
		for(Column column : table.getColonne()) {
			ps.println(StringUtil.concateneString("    //COLUMN ", column.getName()));
			if(column.getJavaType().equals("Time")) {
				ps.println(StringUtil.concateneString("    @JsonDeserialize(using = DateTimeDeserializer.class)"));
				ps.println(StringUtil.concateneString("    @JsonSerialize(using = DateSerializer.class)"));
			}else if(column.getJavaType().equals("Date")) {
				ps.println(StringUtil.concateneString("    @JsonDeserialize(using = DateSqlDeserializer.class)"));
				ps.println(StringUtil.concateneString("    @JsonSerialize(using = DateSerializer.class)"));
			}else if(column.getJavaType().equals("timestamp")) {
				ps.println(StringUtil.concateneString("    @JsonDeserialize(using = DateTimestampDeserializer.class)"));
				ps.println(StringUtil.concateneString("    @JsonSerialize(using = DateSerializer.class)"));
			}
			
			ps.println(StringUtil.concateneString("    private ", column.getJavaType(), " ",  this.columnToProperty(column), ";"));
		}
		ps.println("");
		for(Column column : table.getColonne()) {
			String fieldName = this.columnToProperty(column);
			ps.println(StringUtil.concateneString("    public ", column.getJavaType(), " ",  this.columnToGetMethod(column), "{"));
			ps.println(StringUtil.concateneString("        return this.", fieldName, ";"));
			ps.println(StringUtil.concateneString("    }"));
			ps.println("");
			ps.println(StringUtil.concateneString("    public void ",  this.columnToSetMethod(column), "(", column.getJavaType(), " ", fieldName, "){"));
			ps.println(StringUtil.concateneString("        this.", fieldName, " = ", fieldName, ";"));
			ps.println(StringUtil.concateneString("    }"));
			ps.println("");
		}
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("}"));
		ps.flush();
		ps.close();
	}
	/**
	 * Generate dto 
	 */
	private void generateSearch(Table table, String searchBasePath) throws Exception{
		String className = this.tableToClass(table);
		PrintStream ps = new PrintStream(new FileOutputStream(StringUtil.concateneString(searchBasePath
				                                                                        ,className
				                                                                        ,"Search.java"
				                                                                        )
				                                             )
				                       );
		ps.println(StringUtil.concateneString("package ", stringpackage , ".search;"));
		ps.println("");
		ps.println("import it.eng.tz.puglia.bean.BaseSearchRequestBean;");
		ps.println("");
		ps.println("/**");
		ps.println(StringUtil.concateneString(" * Search for table ", table.getSchema(), ".", table.getTableName()));
		ps.println(" */");
		ps.println(StringUtil.concateneString("public class ", className, "Search extends BaseSearchRequestBean{"));
		ps.println(StringUtil.concateneString(""));
		long serializableId = (long)(Math.random() * Math.pow(10, 10));
		ps.println(StringUtil.concateneString("    private static final long serialVersionUID = ", serializableId, "L;"));
		for(Column column : table.getColonne()) {
			ps.println(StringUtil.concateneString("    //COLUMN ", column.getName()));
			ps.println(StringUtil.concateneString("    private String ",  this.columnToProperty(column), ";"));
		}
		ps.println("");
		for(Column column : table.getColonne()) {
			String fieldName = this.columnToProperty(column);
			ps.println(StringUtil.concateneString("    public String ",  this.columnToGetMethod(column), "{"));
			ps.println(StringUtil.concateneString("        return this.", fieldName, ";"));
			ps.println(StringUtil.concateneString("    }"));
			ps.println("");
			ps.println(StringUtil.concateneString("    public void ",  this.columnToSetMethod(column), "(String ", fieldName, "){"));
			ps.println(StringUtil.concateneString("        this.", fieldName, " = ", fieldName, ";"));
			ps.println(StringUtil.concateneString("    }"));
			ps.println("");
		}
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("}"));
		ps.flush();
		ps.close();
	}
	/**
	 * Generate rowmapper 
	 */
	private void generateRowMapper(Table table, String rowMapperBasePath) throws Exception{
		String className = this.tableToClass(table);
		PrintStream ps = new PrintStream(new FileOutputStream(StringUtil.concateneString(rowMapperBasePath
				                                                                        ,className
				                                                                        ,"RowMapper.java"
				                                                                        )
				                                             )
				                       );
		ps.println(StringUtil.concateneString("package ", stringpackage , ".rowmapper;"));
		ps.println("");
		ps.println("import java.sql.*;");
		ps.println("import java.math.*;");
		ps.println(StringUtil.concateneString("import ", stringpackage, ".dto.*;"));
		ps.println("import org.springframework.jdbc.core.RowMapper;");
		ps.println("");
		ps.println("/**");
		ps.println(StringUtil.concateneString(" * Row Mapper for table ", table.getSchema(), ".", table.getTableName()));
		ps.println(" */");
		ps.println(StringUtil.concateneString("public class ", className, "RowMapper implements RowMapper<", className, "DTO>{"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    public ", className, "DTO mapRow(ResultSet rs, int rowNum) throws SQLException{"));
		ps.println(StringUtil.concateneString("        final ", className, "DTO result = new ", className, "DTO();"));
		for(Column column : table.getColonne()) {
			String setMethod = this.columnToSetMethod(column);
			String setRow    = null;
			if(StringUtils.equalsIgnoreCase(column.getJavaType(), "UUID")) {
				setRow = StringUtil.concateneString("        result.", setMethod, "( (java.util.UUID) rs.getObject" , "(\"", column.getName(), "\"));");
			} else {
			setRow    = StringUtil.concateneString("        result.", setMethod, "(rs.get", column.getJavaType().equals("Integer") || column.getJavaType().equals("int") ? "Int" : StringUtil.camelCase(column.getJavaType(), true) , "(\"", column.getName(), "\"));");
			}
			if(column.isNullable()) {
				ps.println(StringUtil.concateneString("        if (rs.getObject(\"", column.getName() , "\") != null)"));
				ps.println(StringUtil.concateneString("    ", setRow));
			}else {
				ps.println(StringUtil.concateneString(setRow));
			}
		}
		ps.println(StringUtil.concateneString("        return result;"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString("}"));
		ps.flush();
		ps.close();
	}

	/**
	 * Generate repository 
	 */
	private void generateRepository(Table table, String repositoryMapperBasePath) throws Exception{
		String columnSerial = null;
		for(Iterator<Column> iterator = table.getColonne().iterator(); columnSerial == null && iterator.hasNext(); ) {
			Column column = iterator.next();
			if(column.isSerial())
				columnSerial = column.getName();
		}
		String className = this.tableToClass(table);
		String sep = "";
		PrintStream ps = new PrintStream(new FileOutputStream(StringUtil.concateneString(repositoryMapperBasePath
				                                                                        ,className
				                                                                        ,"Repository.java"
				                                                                        )
				                                             )
				                        );
		ps.println(StringUtil.concateneString("package ", stringpackage , ".repository;"));
		ps.println("");
		ps.println("import java.sql.*;");
		ps.println("import java.math.*;");
		ps.println("import java.util.List;");
		ps.println("import java.util.HashMap;");
		ps.println("import java.util.ArrayList;");
		ps.println("import it.eng.tz.puglia.bean.PaginatedList;");
		ps.println("import org.springframework.stereotype.Repository;");
		ps.println("import it.eng.tz.puglia.util.string.StringUtil;");
		ps.println(StringUtil.concateneString("import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;"));
		ps.println(StringUtil.concateneString("import ", stringpackage, ".dto.*;"));
		ps.println(StringUtil.concateneString("import ", stringpackage, ".search.*;"));
		ps.println(StringUtil.concateneString("import ", stringpackage, ".rowmapper.*;"));
		ps.println("");
		ps.println("/**");
		ps.println(StringUtil.concateneString(" * Repository for table ", table.getSchema(), ".", table.getTableName()));
		ps.println(" */");
		ps.println(StringUtil.concateneString("@Repository"));
		ps.println(StringUtil.concateneString("public class ", className, "Repository extends GenericCrudDao<", className, "DTO, ", className, "Search>{"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    private final ",className , "RowMapper rowMapper = new ",className , "RowMapper();"));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * select all method"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("        final String selectAll = new StringBuilder(\"select\")"));
		sep = " ";
		for(Column column : table.getColonne()) {
			if(column.isGeometry())
				ps.println(StringUtil.concateneString("                                     .append(\"", sep, "ST_AsText(\\\"", column.getName(), "\\\") as \\\"",column.getName(),"\\\"\")"));
			else
				ps.println(StringUtil.concateneString("                                     .append(\"", sep, "\\\"", column.getName(), "\\\"\")"));
			sep = ",";
			
		}
		ps.println(StringUtil.concateneString("                                     .append(\" from ", this.getSchema(table), table.getTableName(), "\\\"\")"));
		ps.println(StringUtil.concateneString("                                     .toString();"));
		ps.println(StringUtil.concateneString("    @Override"));
		ps.println(StringUtil.concateneString("    public List<", className, "DTO> select(){"));
		ps.println(StringUtil.concateneString("        return super.queryForListTxRead(selectAll, this.rowMapper);"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * count all method"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @Override"));
		ps.println(StringUtil.concateneString("    public long count(){"));
		ps.println(StringUtil.concateneString("        final String sql = new StringBuilder(\"select count(*)\")"));
		ps.println(StringUtil.concateneString("                                     .append(\" from ", this.getSchema(table), table.getTableName(), "\\\"\")"));
		ps.println(StringUtil.concateneString("                                     .toString();"));
		ps.println(StringUtil.concateneString("        return super.queryForObjectTxRead(sql, Long.class);"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * find by pk method"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @Override"));
		ps.println(StringUtil.concateneString("    public ", className, "DTO find(", className, "DTO pk){"));
		ps.println(StringUtil.concateneString("        final String sql = new StringBuilder(selectAll)"));
		sep = " where ";
		for(Column column : table.getColonne()) {
			if(column.isPk()) {
				ps.println(StringUtil.concateneString("                                     .append(\"", sep, "\\\"", column.getName(), "\\\" = ?\")"));
				sep = " and ";
			}
			
		}
		ps.println(StringUtil.concateneString("                                     .toString();"));
		ps.println(StringUtil.concateneString("        final List<Object> parameters = new ArrayList<Object>();"));
		for(Column column : table.getColonne()) {
			if(column.isPk()) {
				ps.println(StringUtil.concateneString("        parameters.add(pk.", this.columnToGetMethod(column), ");"));
			}
			
		}
		ps.println(StringUtil.concateneString("        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * search method"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @Override"));
		ps.println(StringUtil.concateneString("    public PaginatedList<", className, "DTO> search(", className, "Search search){"));
		ps.println(StringUtil.concateneString("        final List<Object>  parameters = new ArrayList<Object>();"));
		ps.println(StringUtil.concateneString("        String              sep        = \" where \";"));
		ps.println(StringUtil.concateneString("        final StringBuilder sql        = new StringBuilder(selectAll);"));
		sep = " ";
		for(Column column : table.getColonne()) {
			ps.println(StringUtil.concateneString("        if(StringUtil.isNotEmpty(search.", this.columnToGetMethod(column), ")){"));
			ps.println(StringUtil.concateneString("            sql.append(sep).append(\"lower(\\\"", column.getName(), "\\\"::text) like ?\");"));
			ps.println(StringUtil.concateneString("            parameters.add(StringUtil.convertLike(search.", this.columnToGetMethod(column), ").toLowerCase());"));
			ps.println(StringUtil.concateneString("            sep = \" and \";"));
			ps.println(StringUtil.concateneString("        }"));
			
		}
		ps.println(StringUtil.concateneString("        if(StringUtil.isNotEmpty(search.getSortBy())){"));
		ps.println(StringUtil.concateneString("            String sortType = search.getSortType() == null ? \"\" : StringUtil.concateneString(\" \", search.getSortType());"));
		ps.println(StringUtil.concateneString("            switch (search.getSortBy()) {"));
		for(Column column : table.getColonne()) {
			ps.println(StringUtil.concateneString("            case \"", this.columnToProperty(column), "\":"));
			ps.println(StringUtil.concateneString("                    sql.append(\" order by \\\"", column.getName(), "\\\" \").append(sortType);"));
			ps.println(StringUtil.concateneString("                	   break;"));
		}
		ps.println(StringUtil.concateneString("            default:"));
		ps.println(StringUtil.concateneString("            	    logger.warn(StringUtil.concateneString(\"value \", search.getSortBy(), \" not valid for sort by\"));"));
		ps.println(StringUtil.concateneString("            	    break;"));
		ps.println(StringUtil.concateneString("            }"));
		ps.println(StringUtil.concateneString("        }"));
		ps.println(StringUtil.concateneString("        return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * insert all method"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @Override"));
		ps.println(StringUtil.concateneString("    public long insert(", className, "DTO entity){"));
		ps.println(StringUtil.concateneString("        final String sql = new StringBuilder(\"insert into ", this.getSchema(table), table.getTableName() ,"\\\"\")"));
		sep = "(";
		for(Column column : table.getColonne()) {
			if(column.isSerial() == false) {
				ps.println(StringUtil.concateneString("                                     .append(\"", sep, "\\\"", column.getName(), "\\\"\")"));
				sep = ",";
			}
			
		}
		ps.println(StringUtil.concateneString("                                     .append(\") values \")"));
		sep = "(";
		for(Column column : table.getColonne()) {
			if(column.isSerial() == false) {
				if(column.isGeometry())
					ps.println(StringUtil.concateneString("                                     .append(\"", sep, "ST_GeomFromText(?,\",DEFAULT_SRID,\")\")"));
				else
					ps.println(StringUtil.concateneString("                                     .append(\"", sep, "?\")"));
				sep = ",";
			}
			
		}
		ps.println(StringUtil.concateneString("                                     .append(\")\")"));
		ps.println(StringUtil.concateneString("                                     .toString();"));
		ps.println(StringUtil.concateneString("        final List<Object> parameters = new ArrayList<Object>();"));
		for(Column column : table.getColonne()) {
			if(column.isSerial() == false) 
				ps.println(StringUtil.concateneString("        parameters.add(entity.", this.columnToGetMethod(column), ");"));
			
		}
		if(columnSerial == null)
			ps.println(StringUtil.concateneString("        return super.update(sql, parameters);"));
		else {
			ps.println(StringUtil.concateneString("        return super.insertAndGetAutoincrementValue(sql, parameters, \"", columnSerial, "\");"));
			
		}
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * update by pk method"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @Override"));
		ps.println(StringUtil.concateneString("    public int update(", className, "DTO entity){"));
		ps.println(StringUtil.concateneString("        final String sql = new StringBuilder(\"update ", this.getSchema(table), table.getTableName() ,"\\\"\")"));
		sep = " set ";
		for(Column column : table.getColonne()) {
			if(column.isPk() == false) {
				if(column.isGeometry())
					ps.println(StringUtil.concateneString("                                     .append(\"", sep, "\\\"", column.getName(), "\\\" = ST_GeomFromText(?,\",DEFAULT_SRID,\")\")"));
				else
					ps.println(StringUtil.concateneString("                                     .append(\"", sep, "\\\"", column.getName(), "\\\" = ?\")"));
				sep = ", ";
			}
			
		}
		sep = " where ";
		for(Column column : table.getColonne()) {
			if(column.isPk()) {
				ps.println(StringUtil.concateneString("                                     .append(\"", sep, "\\\"", column.getName(), "\\\" = ?\")"));
				sep = " and ";
			}
			
		}
		ps.println(StringUtil.concateneString("                                     .toString();"));
		ps.println(StringUtil.concateneString("        final List<Object> parameters = new ArrayList<Object>();"));
		for(Column column : table.getColonne()) {
			if(column.isPk() == false)
				ps.println(StringUtil.concateneString("        parameters.add(entity.", this.columnToGetMethod(column), ");"));
			
		}
		for(Column column : table.getColonne()) {
			if(column.isPk())
				ps.println(StringUtil.concateneString("        parameters.add(entity.", this.columnToGetMethod(column), ");"));
			
		}
		ps.println(StringUtil.concateneString("        return super.update(sql, parameters);"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * delete by pk method"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @Override"));
		ps.println(StringUtil.concateneString("    public int delete(", className, "DTO entity){"));
		ps.println(StringUtil.concateneString("        final String sql = new StringBuilder(\"delete from ", this.getSchema(table), table.getTableName() ,"\\\"\")"));
		sep = " where ";
		for(Column column : table.getColonne()) {
			if(column.isPk()) {
				ps.println(StringUtil.concateneString("                                     .append(\"", sep, "\\\"", column.getName(), "\\\" = ?\")"));
				sep = " and ";
			}
			
		}
		ps.println(StringUtil.concateneString("                                     .toString();"));
		ps.println(StringUtil.concateneString("        final List<Object> parameters = new ArrayList<Object>();"));
		for(Column column : table.getColonne()) {
			if(column.isPk())
				ps.println(StringUtil.concateneString("        parameters.add(entity.", this.columnToGetMethod(column), ");"));
			
		}
		ps.println(StringUtil.concateneString("        return super.update(sql, parameters);"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("}"));
		ps.flush();
		ps.close();
	}
	/**
	 * Generate Service Impl
	 */
	private void generateService(Table table, String serviceBasePath) throws Exception{
		String className = this.tableToClass(table);
		PrintStream ps = new PrintStream(new FileOutputStream(StringUtil.concateneString(serviceBasePath
				                                                                        ,"I"
				                                                                        ,className
				                                                                        ,"Service.java"
				                                                                        )
				                                             )
				                        );
		ps.println(StringUtil.concateneString("package ", stringpackage , ".service;"));
		ps.println("");
		ps.println(StringUtil.concateneString("import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;"));
		ps.println(StringUtil.concateneString("import ", stringpackage, ".dto.*;"));
		ps.println(StringUtil.concateneString("import ", stringpackage, ".search.*;"));
		ps.println(StringUtil.concateneString("import ", stringpackage, ".repository.*;"));
		ps.println(StringUtil.concateneString("import java.sql.SQLException;"));
		ps.println("");
		ps.println("/**");
		ps.println(StringUtil.concateneString(" * Service interface CRUD for table ", table.getSchema(), ".", table.getTableName()));
		ps.println(" */");
		ps.println(StringUtil.concateneString("public interface I", className, "Service extends ICrudService<", className,"DTO, ", className,"Search, ", className,"Repository>{"));
		ps.println("");
		ps.println(StringUtil.concateneString("}"));
		ps.flush();
		ps.close();
	}
	/**
	/**
	 * Generate Service Impl
	 */
	private void generateServiceImpl(Table table, String serviceBasePath) throws Exception{
		String className = this.tableToClass(table);
		PrintStream ps = new PrintStream(new FileOutputStream(StringUtil.concateneString(serviceBasePath
				                                                                        ,className
				                                                                        ,"Service.java"
				                                                                        )
				                                             )
				                        );
		ps.println(StringUtil.concateneString("package ", stringpackage , ".service.impl;"));
		ps.println("");
		ps.println(StringUtil.concateneString("import ", stringpackage, ".dto.*;"));
		ps.println(StringUtil.concateneString("import ", stringpackage, ".repository.*;"));
		ps.println(StringUtil.concateneString("import ", stringpackage, ".search.*;"));
		ps.println(StringUtil.concateneString("import ", stringpackage, ".service.*;"));
		ps.println(StringUtil.concateneString("import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;"));
		ps.println("import org.springframework.beans.factory.annotation.Autowired;");
		ps.println("import org.springframework.stereotype.Service;");
		ps.println("import it.eng.tz.puglia.error.exception.CustomValidationException;");
		ps.println("");
		ps.println("/**");
		ps.println(StringUtil.concateneString(" * Service CRUD for table ", table.getSchema(), ".", table.getTableName()));
		ps.println(" */");
		ps.println(StringUtil.concateneString("@Service"));
		ps.println(StringUtil.concateneString("public class ", className, "Service extends GenericCrudService<", className,"DTO, ", className,"Search, ", className,"Repository> implements I", className, "Service{"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * dao"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @Autowired"));
		ps.println(StringUtil.concateneString("    private ", className, "Repository dao;"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * get dao"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @Override"));
		ps.println(StringUtil.concateneString("    protected ", className, "Repository getDao(){"));
		ps.println(StringUtil.concateneString("        return this.dao;"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * validate dto for insert"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @Override"));
		ps.println(StringUtil.concateneString("    protected void validateInsertDTO(final ", className, "DTO entity) throws CustomValidationException{"));
		table.getColonne().forEach(column ->{
			if(column.isNullable() == false
			&& column.isHasDefaultValue() == false
			&& Character.isUpperCase(column.getJavaType().charAt(0))
			){
				ps.println(StringUtil.concateneString("        if(entity.", this.columnToGetMethod(column), " == null){"));
				ps.println(StringUtil.concateneString("            throw new CustomValidationException(\"",  this.columnToProperty(column), " can't be null\");"));
				ps.println(StringUtil.concateneString("        }"));
			}
		});
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * validate dto for update"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @Override"));
		ps.println(StringUtil.concateneString("    protected void validateUpdateDTO(final ", className, "DTO entity) throws CustomValidationException{"));
		table.getColonne().forEach(column ->{
			if(column.isNullable() == false && Character.isUpperCase(column.getJavaType().charAt(0))){
				ps.println(StringUtil.concateneString("        if(entity.", this.columnToGetMethod(column), " == null){"));
				ps.println(StringUtil.concateneString("            throw new CustomValidationException(\"",  this.columnToProperty(column), " can't be null\");"));
				ps.println(StringUtil.concateneString("        }"));
			}
		});
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("}"));
		ps.flush();
		ps.close();
	}

	private void generateController(Table table, String controllerBasePath) throws Exception{
		String className = this.tableToClass(table);
		PrintStream ps = new PrintStream(new FileOutputStream(StringUtil.concateneString(controllerBasePath
				                                                                        ,className
				                                                                        ,"Controller.java"
				                                                                        )
				                                             )
				                       );
		ps.println(StringUtil.concateneString("package ", stringpackage , ".controller;"));
		ps.println("");
		ps.println(StringUtil.concateneString("import ", stringpackage, ".dto.*;"));
		ps.println(StringUtil.concateneString("import ", stringpackage, ".search.*;"));
		ps.println(StringUtil.concateneString("import ", stringpackage, ".service.*;"));
		ps.println("import java.util.List;");
		ps.println("import org.slf4j.Logger;");
		ps.println("import org.slf4j.LoggerFactory;");
		ps.println("import org.springframework.beans.factory.annotation.Autowired;");
		ps.println("import org.springframework.http.ResponseEntity;");
		ps.println("import org.springframework.util.StopWatch;");
		ps.println("import org.springframework.stereotype.Controller;");
		ps.println("import org.springframework.web.bind.annotation.GetMapping;");
		ps.println("import org.springframework.web.bind.annotation.PostMapping;");
		ps.println("import org.springframework.web.bind.annotation.RequestBody;");
		ps.println("import org.springframework.web.bind.annotation.RequestMapping;");
		ps.println("import org.springframework.web.bind.annotation.RequestParam;");
		ps.println("import org.springframework.web.bind.annotation.RestController;");
		ps.println("import it.eng.tz.puglia.bean.BaseRestResponse;");
		ps.println("import it.eng.tz.puglia.bean.PaginatedList;");
		ps.println("import it.eng.tz.puglia.util.log.LogUtil;");
		ps.println("import it.eng.tz.puglia.controller.BaseRestController;");

		ps.println("");
		ps.println("/**");
		ps.println(StringUtil.concateneString(" * Controller CRUD for table ", table.getSchema(), ".", table.getTableName()));
		ps.println(" */");
		ps.println(StringUtil.concateneString("@Controller"));
		ps.println(StringUtil.concateneString("@RequestMapping(\"/", className, "\")"));
		ps.println(StringUtil.concateneString("public class ", className, "Controller extends BaseRestController{"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * logger"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    private static final Logger LOGGER = LoggerFactory.getLogger(", className, "Controller.class);"));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * service"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @Autowired"));
		ps.println(StringUtil.concateneString("    private I", className, "Service service;"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * select all"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @GetMapping(value=\"/select.pjson\", produces=MEDIA_TYPE)"));
		ps.println(StringUtil.concateneString("    public ResponseEntity<BaseRestResponse<List<", className, "DTO>>> select(){"));
		ps.println(StringUtil.concateneString("        LOGGER.info(\"Start select\");"));
		ps.println(StringUtil.concateneString("        final StopWatch sw = LogUtil.startLog(\"select\");"));
		ps.println(StringUtil.concateneString("        try {"));
		ps.println(StringUtil.concateneString("            return super.okResponse(this.service.select());"));
		ps.println(StringUtil.concateneString("        }catch(Exception e) {"));
		ps.println(StringUtil.concateneString("            LOGGER.error(\"Error in select\", e);"));
		ps.println(StringUtil.concateneString("            return super.koResponse(e, new BaseRestResponse<>());"));
		ps.println(StringUtil.concateneString("        }finally {"));
		ps.println(StringUtil.concateneString("            LOGGER.info(LogUtil.stopLog(sw));"));
		ps.println(StringUtil.concateneString("        }"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * count all"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @GetMapping(value=\"/count.pjson\", produces=MEDIA_TYPE)"));
		ps.println(StringUtil.concateneString("    public ResponseEntity<BaseRestResponse<Long>> count(){"));
		ps.println(StringUtil.concateneString("        LOGGER.info(\"Start select\");"));
		ps.println(StringUtil.concateneString("        final StopWatch sw = LogUtil.startLog(\"select\");"));
		ps.println(StringUtil.concateneString("        try {"));
		ps.println(StringUtil.concateneString("            return super.okResponse(this.service.count());"));
		ps.println(StringUtil.concateneString("        }catch(Exception e) {"));
		ps.println(StringUtil.concateneString("            LOGGER.error(\"Error in count\", e);"));
		ps.println(StringUtil.concateneString("            return super.koResponse(e, new BaseRestResponse<>());"));
		ps.println(StringUtil.concateneString("        }finally {"));
		ps.println(StringUtil.concateneString("            LOGGER.info(LogUtil.stopLog(sw));"));
		ps.println(StringUtil.concateneString("        }"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * find by pk"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @PostMapping(value=\"/find.pjson\", produces=MEDIA_TYPE)"));
		ps.println(StringUtil.concateneString("    public ResponseEntity<BaseRestResponse<", className, "DTO>> find(@RequestBody ", className, "DTO pk){"));
		ps.println(StringUtil.concateneString("        LOGGER.info(\"Start find\");"));
		ps.println(StringUtil.concateneString("        final StopWatch sw = LogUtil.startLog(\"find\");"));
		ps.println(StringUtil.concateneString("        try {"));
		ps.println(StringUtil.concateneString("            return super.okResponse(this.service.find(pk));"));
		ps.println(StringUtil.concateneString("        }catch(Exception e) {"));
		ps.println(StringUtil.concateneString("            LOGGER.error(\"Error in find\", e);"));
		ps.println(StringUtil.concateneString("            return super.koResponse(e, new BaseRestResponse<>());"));
		ps.println(StringUtil.concateneString("        }finally {"));
		ps.println(StringUtil.concateneString("            LOGGER.info(LogUtil.stopLog(sw));"));
		ps.println(StringUtil.concateneString("        }"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * search"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @PostMapping(value=\"/search.pjson\", produces=MEDIA_TYPE)"));
		ps.println(StringUtil.concateneString("    public ResponseEntity<BaseRestResponse<PaginatedList<", className, "DTO>>> search(@RequestBody ", className, "Search search){"));
		ps.println(StringUtil.concateneString("        LOGGER.info(\"Start search\");"));
		ps.println(StringUtil.concateneString("        final StopWatch sw = LogUtil.startLog(\"search\");"));
		ps.println(StringUtil.concateneString("        try {"));
		ps.println(StringUtil.concateneString("            return super.okResponse(this.service.search(search));"));
		ps.println(StringUtil.concateneString("        }catch(Exception e) {"));
		ps.println(StringUtil.concateneString("            LOGGER.error(\"Error in search\", e);"));
		ps.println(StringUtil.concateneString("            return super.koResponse(e, new BaseRestResponse<>());"));
		ps.println(StringUtil.concateneString("        }finally {"));
		ps.println(StringUtil.concateneString("            LOGGER.info(LogUtil.stopLog(sw));"));
		ps.println(StringUtil.concateneString("        }"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * insert"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @PostMapping(value=\"/insert.pjson\", produces=MEDIA_TYPE)"));
		ps.println(StringUtil.concateneString("    public ResponseEntity<BaseRestResponse<Long>> insert(@RequestBody ", className, "DTO dto){"));
		ps.println(StringUtil.concateneString("        LOGGER.info(\"Start insert\");"));
		ps.println(StringUtil.concateneString("        final StopWatch sw = LogUtil.startLog(\"insert\");"));
		ps.println(StringUtil.concateneString("        try {"));
		ps.println(StringUtil.concateneString("            return super.okResponse(this.service.insert(dto));"));
		ps.println(StringUtil.concateneString("        }catch(Exception e) {"));
		ps.println(StringUtil.concateneString("            LOGGER.error(\"Error in insert\", e);"));
		ps.println(StringUtil.concateneString("            return super.koResponse(e, new BaseRestResponse<>());"));
		ps.println(StringUtil.concateneString("        }finally {"));
		ps.println(StringUtil.concateneString("            LOGGER.info(LogUtil.stopLog(sw));"));
		ps.println(StringUtil.concateneString("        }"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * update"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @PostMapping(value=\"/update.pjson\", produces=MEDIA_TYPE)"));
		ps.println(StringUtil.concateneString("    public ResponseEntity<BaseRestResponse<Integer>> update(@RequestBody ", className, "DTO dto){"));
		ps.println(StringUtil.concateneString("        LOGGER.info(\"Start update\");"));
		ps.println(StringUtil.concateneString("        final StopWatch sw = LogUtil.startLog(\"update\");"));
		ps.println(StringUtil.concateneString("        try {"));
		ps.println(StringUtil.concateneString("            return super.okResponse(this.service.update(dto));"));
		ps.println(StringUtil.concateneString("        }catch(Exception e) {"));
		ps.println(StringUtil.concateneString("            LOGGER.error(\"Error in update\", e);"));
		ps.println(StringUtil.concateneString("            return super.koResponse(e, new BaseRestResponse<>());"));
		ps.println(StringUtil.concateneString("        }finally {"));
		ps.println(StringUtil.concateneString("            LOGGER.info(LogUtil.stopLog(sw));"));
		ps.println(StringUtil.concateneString("        }"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("    /**"));
		ps.println(StringUtil.concateneString("     * delete"));
		ps.println(StringUtil.concateneString("     */"));
		ps.println(StringUtil.concateneString("    @PostMapping(value=\"/delete.pjson\", produces=MEDIA_TYPE)"));
		ps.println(StringUtil.concateneString("    public ResponseEntity<BaseRestResponse<Integer>> delete(@RequestBody ", className, "DTO dto){"));
		ps.println(StringUtil.concateneString("        LOGGER.info(\"Start delete\");"));
		ps.println(StringUtil.concateneString("        final StopWatch sw = LogUtil.startLog(\"delete\");"));
		ps.println(StringUtil.concateneString("        try {"));
		ps.println(StringUtil.concateneString("            return super.okResponse(this.service.delete(dto));"));
		ps.println(StringUtil.concateneString("        }catch(Exception e) {"));
		ps.println(StringUtil.concateneString("            LOGGER.error(\"Error in delete\", e);"));
		ps.println(StringUtil.concateneString("            return super.koResponse(e, new BaseRestResponse<>());"));
		ps.println(StringUtil.concateneString("        }finally {"));
		ps.println(StringUtil.concateneString("            LOGGER.info(LogUtil.stopLog(sw));"));
		ps.println(StringUtil.concateneString("        }"));
		ps.println(StringUtil.concateneString("    }"));
		ps.println(StringUtil.concateneString(""));
		ps.println(StringUtil.concateneString("}"));
		ps.flush();
		ps.close();
	}
	/**
	 * @return java type from column type
	 */
	private String getJavaType(int sqlType, boolean isNullable) {
		switch (sqlType) {
		case java.sql.Types.BIGINT                 : return !isNullable ? "long"    : "Long"   ;
		case java.sql.Types.DECIMAL                : return !isNullable ? "double"  : "Double" ;
		case java.sql.Types.NUMERIC                : return !isNullable ? "double"  : "Double" ;
		case java.sql.Types.DOUBLE                 : return !isNullable ? "double"  : "Double" ;
		case java.sql.Types.FLOAT                  : return !isNullable ? "float"   : "Float"  ;
		case java.sql.Types.INTEGER                : return !isNullable ? "int"     : "Integer";
		case java.sql.Types.SMALLINT               : return !isNullable ? "short"   : "Short"  ;
		case java.sql.Types.TINYINT                : return !isNullable ? "byte"    : "Byte"   ;
		case java.sql.Types.BOOLEAN                : return !isNullable ? "boolean" : "Boolean";
		case java.sql.Types.BIT                    : return !isNullable ? "boolean" : "Boolean";
		case java.sql.Types.CHAR                   : return "String";
		case java.sql.Types.VARCHAR                : return "String";
		case java.sql.Types.DATE                   : return "Date";
		case java.sql.Types.TIME                   : return "Time";
		case java.sql.Types.TIME_WITH_TIMEZONE     : return "Time";
		case java.sql.Types.TIMESTAMP              : return "Timestamp";
		case java.sql.Types.TIMESTAMP_WITH_TIMEZONE: return "Timestamp";
		case 1111								   : return "UUID";
		default:
			System.out.println(sqlType + " non gestito");
			return "String";
		}
	}

	private String columnToProperty(Column column) {
		return this.columnToProperty(column.getName());
	}

	private String columnToProperty(String column) {
		 return StringUtil.camelCase(column, false);
	}

	private String columnToGetMethod(Column column) {
		return StringUtil.concateneString(StringUtil.camelCase(StringUtil.concateneString("get_",column.getName()), false), "()");
	}

	private String columnToSetMethod(Column column) {
		return StringUtil.camelCase(StringUtil.concateneString("set_",column.getName()), false);
	}

	private String tableToClass(Table table) {
		return this.tableToClass(table.getTableName());
	}
	private String tableToClass(String tableName) {
		return StringUtil.camelCase(tableName, true);
	}

}
