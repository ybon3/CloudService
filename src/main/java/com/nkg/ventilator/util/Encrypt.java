package com.nkg.ventilator.util;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encrypt {

	public static final String ENC_KEY = "hGbSIuoc4O/FWDij3ko2Ag==";
	public static final String IV_KEY = "9qZzCaBvFTa1yX2RhIYcpw==";

	public static String enc(String plainText) throws Exception {
		byte[] encodedKey = Base64.getDecoder().decode(ENC_KEY);
		SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

		byte[] ivKey = Base64.getDecoder().decode(IV_KEY);
		return encryptAES(plainText, secretKey, ivKey);
	}

	public static String dect(String cipherText) throws Exception {
		byte[] encodedKey = Base64.getDecoder().decode(ENC_KEY);
		SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

		byte[] ivKey = Base64.getDecoder().decode(IV_KEY);
		return decryptAES(cipherText, secretKey, ivKey);
	}

	private static String encryptAES(String oristr, SecretKey secretKey, byte[] iv) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
			byte[] byteCipherText = cipher.doFinal(oristr.getBytes("UTF-8"));
			String res = Base64.getMimeEncoder().encodeToString(byteCipherText);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String decryptAES(String encstr, SecretKey secretKey, byte[] iv) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
			byte[] decryptedText = cipher.doFinal(Base64.getMimeDecoder().decode(encstr));
			String strDecryptedText = new String(decryptedText);
			return strDecryptedText;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
