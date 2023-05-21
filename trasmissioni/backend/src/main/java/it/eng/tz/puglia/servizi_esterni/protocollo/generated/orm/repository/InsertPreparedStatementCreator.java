package it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;

/**
 * Implementation of {@link PreparedStatementCreator} for insert and return value
 * @author Antonio La Gatta
 * @date 20 gen 2020
 */
public class InsertPreparedStatementCreator implements PreparedStatementCreator{

	private final Logger LOGGER = LoggerFactory.getLogger(InsertPreparedStatementCreator.class);
	
	private final String sql;
	private final List<Object> parameters;
	private final String idColumn;
	
	public InsertPreparedStatementCreator(String sql, List<Object> parameters, String idColumn) {
		super();
		this.sql = sql;
		this.parameters = parameters;
		this.idColumn = idColumn;
	}

	/**
	 * @author Antonio La Gatta
	 * @date 20 gen 2020
	 * @see org.springframework.jdbc.core.PreparedStatementCreator#createPreparedStatement(java.sql.Connection)
	 */
	@Override
	public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		LOGGER.info("Start createPreparedStatement");
		final PreparedStatement ps = connection.prepareStatement(this.sql,new String[] {this.idColumn});
        int idx = 1;
        for(Iterator<Object> iterator = this.parameters.iterator(); iterator.hasNext(); ) {
        	Object parameter = iterator.next();
        	      if(parameter instanceof String            ) {ps.setString    (idx++, (String            )parameter);
        	}else if(parameter instanceof Byte              ) {ps.setByte      (idx++, (Byte              )parameter);
        	}else if(parameter instanceof Short             ) {ps.setShort     (idx++, (Short             )parameter);
        	}else if(parameter instanceof Integer           ) {ps.setInt       (idx++, (Integer           )parameter);
        	}else if(parameter instanceof Long              ) {ps.setLong      (idx++, (Long              )parameter);
        	}else if(parameter instanceof Float             ) {ps.setFloat     (idx++, (Float             )parameter);
        	}else if(parameter instanceof Double            ) {ps.setDouble    (idx++, (Double            )parameter);
        	}else if(parameter instanceof Boolean           ) {ps.setBoolean   (idx++, (Boolean           )parameter);
        	}else if(parameter instanceof BigDecimal        ) {ps.setBigDecimal(idx++, (BigDecimal        )parameter);
        	}else if(parameter instanceof java.sql.Date     ) {ps.setDate      (idx++, (java.sql.Date     )parameter);
        	}else if(parameter instanceof java.sql.Time     ) {ps.setTime      (idx++, (java.sql.Time     )parameter);
        	}else if(parameter instanceof java.sql.Timestamp) {ps.setTimestamp (idx++, (java.sql.Timestamp)parameter);
        	}else if(parameter instanceof java.util.Date    ) {ps.setDate      (idx++, new java.sql.Date(((java.util.Date)parameter).getTime()));
        	}else if(parameter instanceof Calendar          ) {ps.setTimestamp (idx++, new java.sql.Timestamp(((Calendar)parameter).getTimeInMillis()));
        	}else if(parameter instanceof BigInteger        ) {ps.setLong      (idx++, ((BigInteger)parameter).longValue());
        	}else {ps.setObject(idx++, parameter);
        	}
        }
        LOGGER.info("End createPreparedStatement");
        return ps;
	}

}
