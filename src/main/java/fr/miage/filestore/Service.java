/**
 * 
 */
package fr.miage.filestore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @author Maxime BLAISE
 *
 */
public class Service implements BinaryStoreService {

	HashMap<String, InputStream> storage = new HashMap<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.miage.filestore.BinaryStoreService#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String key) throws BinaryStoreServiceException {
		if (storage != null) {
			return this.storage.containsKey(key);
		} else {
			throw new BinaryStoreServiceException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.miage.filestore.BinaryStoreService#put(java.io.InputStream)
	 */
	@Override
	public String put(InputStream is) throws BinaryStoreServiceException {
		String content = "";
		try {
			content = convertStreamToString(is);
			InputStream is2 = new ByteArrayInputStream(content.getBytes());
			if (!this.exists(content)) {
				this.storage.put(content, is2);
			} else {
				this.storage.replace(content, is2);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return content;
	}

	static String convertStreamToString(java.io.InputStream in) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		org.apache.commons.io.IOUtils.copy(in, baos);
		byte[] bytes = baos.toByteArray();
		
		return new String(bytes);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.miage.filestore.BinaryStoreService#get(java.lang.String)
	 */
	@Override
	public InputStream get(String key) throws BinaryStoreServiceException, BinaryStreamNotFoundException {
		if (this.exists(key)) {
			return this.storage.get(key);
		} else {
			throw new BinaryStreamNotFoundException();
		}
	}

}
