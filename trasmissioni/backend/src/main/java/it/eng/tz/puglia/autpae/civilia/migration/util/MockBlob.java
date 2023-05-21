/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.tz.puglia.autpae.service.AllegatoServiceImpl;

/**
 * 
 * classe Mock che implementa solo alcuni metodi:
 * lenght
 * getBinaryStream
 *  del Blob utilizzata per la sola migrazione.
 * @author Adriano Colaianni
 * @date 21 lug 2021
 */
public class MockBlob implements Blob{
	private static final Logger log = LoggerFactory.getLogger(MockBlob.class);
	private File file;
	
	public MockBlob(File file) {
		this.file=file;
	}

	@Override
	public long length() throws SQLException {
		return file.length();
	}

	@Override
	public byte[] getBytes(long pos, int length) throws SQLException {
		throw new SQLException("Not implemented!!");
	}

	@Override
	public InputStream getBinaryStream() throws SQLException {
		InputStream is;
		try {
			is = new FileInputStream(this.file);
		} catch (FileNotFoundException e) {
			throw new SQLException("File non trovato!"+file.getAbsolutePath());
		}
		return is;
	}

	@Override
	public long position(byte[] pattern, long start) throws SQLException {
		return 0;
	}

	@Override
	public long position(Blob pattern, long start) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setBytes(long pos, byte[] bytes) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OutputStream setBinaryStream(long pos) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void truncate(long len) throws SQLException {
	}

	@Override
	public void free() throws SQLException {
		if(this.file!=null && this.file.exists()) {
			try {
			this.file.delete();
			}catch(Exception e) {
				log.error("Errore nella rimozione del file in MockBlob "+this.file.getName(),e);
			}
		}
	}

	@Override
	public InputStream getBinaryStream(long pos, long length) throws SQLException {
		return null;
	}

}
