package com.org.apiservices.util;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncryptionUtils {
	private static final String characterEncoding = "UTF-8";
	private static final String cipherTransformation = "AES/CBC/PKCS5Padding";
	private static final String aesEncryptionAlgorithm = "AES";
	private static final String key256 = "TFRGUzAxMjM0NTY3ODkwMTIzNDU2Nzg5MDEyMzQ1Njc=";
	static byte[] initVector = EncryptionUtils.random(128 / 8);

	public static String decrypt(String encryptedText) throws Exception {

		byte[] cipherText = Base64.decodeBase64(encryptedText.getBytes());
		byte[] keyBytes = Base64.decodeBase64(key256.getBytes());
		return decryptWithManagedIV(cipherText, keyBytes);
	}

	public static String decryptWithManagedIV(byte[] cipherText, byte[] key) throws Exception {
		byte[] initialVector = Arrays.copyOfRange(cipherText, 0, 16);
		
		//System.out.println("initialVector == "+new String(initialVector));
		byte[] trimmedCipherText = Arrays.copyOfRange(cipherText, 16, cipherText.length);
		// byte[] trimmedCipherText =new byte[cipherText.length-16];
		// byte[] trimmedCipherText =cipherText;
		// byte[] initialVector =cipherText;
		//System.out.println(new String(key));
		return decrypt(trimmedCipherText, key, initialVector);
	}

	public static String decrypt(byte[] cipherText, byte[] key, byte[] initialVector) throws Exception {
		Cipher cipher = Cipher.getInstance(cipherTransformation);
		SecretKeySpec secretKeySpecy = new SecretKeySpec(key, aesEncryptionAlgorithm);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
		cipherText = cipher.doFinal(cipherText);
		return new String(cipherText, characterEncoding);
	}

	public String encrypt(String plainText) throws Exception {
		byte[] plainTextbytes = plainText.getBytes(characterEncoding);
		byte[] keyBytes = Base64.decodeBase64(key256.getBytes());//key256.getBytes();
		byte[] encrypted = byteencrypt(plainTextbytes, keyBytes, initVector);
		byte[] finalEncryption = new byte[initVector.length + encrypted.length];
		System.arraycopy(initVector, 0, finalEncryption, 0, initVector.length);
		System.arraycopy(encrypted, 0, finalEncryption, initVector.length, encrypted.length);
		return Base64.encodeBase64String(finalEncryption);
	}

	public byte[] bytedecrypt(byte[] cipherText, byte[] key, byte[] initialVector) throws Exception {
		Cipher cipher = Cipher.getInstance(cipherTransformation);
		SecretKeySpec secretKeySpecy = new SecretKeySpec(key, aesEncryptionAlgorithm);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
		cipherText = cipher.doFinal(cipherText);
		return cipherText;
	}

	public byte[] byteencrypt(byte[] plainText, byte[] key, byte[] initialVector) throws Exception {
		Cipher cipher = Cipher.getInstance(cipherTransformation);
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, aesEncryptionAlgorithm);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		plainText = cipher.doFinal(plainText);
		return plainText;
	}
	
	public static byte[] random(int length) {
		byte[] salt = new byte[length];
		new SecureRandom().nextBytes(salt);
		return salt;// hex(salt);
	}


	public static void main(String args[]) throws Exception {
		// System.out.println(Base64.encodeBase64String("CL1916010201094".getBytes()));

		EncryptionUtils aES256 = new EncryptionUtils();
//		String cipherText = aES256.encrypt("{\r\n"
//				+ "    \"authMode\": \"Test\",\r\n"
//				+ "    \"channel\": \"D2CWEB\",\r\n"
//				+ "    \"level\": \"1\",\r\n"
//				+ "    \"mobileNo\": \"9656324462\",\r\n"
//				+ "    \"serviceName\": \"creditscoredetails\",\r\n"
//				//+ "    \"lob\": \"Farm\",\r\n"
//				//+ "    \"lanId\": \"0KG797148R1500942650\",\r\n"
//				+ "    \"srId\": \"1122211434534343434\",\r\n"
//				+ "    \"userGroup\": \"D2C\",\r\n"
//				+ "    \"userId\": \"D2CWEB\",\r\n"
//				+ "    \"userIp\": \"165.225.106.90\",\r\n"
//				+ "    \"userRole\": \"D2CWEB\"\r\n"
//				+ "}");//9135564473
		
		// Question & Answer details
		
//			 String cipherText = aES256.encrypt("{\r\n" +
//			  "\"fosUserId\":\"50027430\",\r\n" +	// 50027430 20064317 50016037
//			  "\"userRole\":\"CollectionApp\",\r\n" +
//			  "\"userIp\":\"10.2.185.112\",\r\n" +
//			  "\"userId\":\"CollectionApp\",\r\n" +
//			  "\"userGroup\":\"CollectionApp\",\r\n" +
//			  "\"srId\":\"8910231532523535\",\r\n" +
//			  "\"level\":\"1\",\r\n" +
//			  "\"channel\":\"CollectionApp\",\r\n" +
//			  "\"authMode\":\"1\",\r\n" +
//			  "\"serviceName\":\"viewFosDetails\"\r\n" + "}");
		
		// Device Update API
		
		 String cipherText = aES256.encrypt("{\r\n" +
		  "\"fosUserId\":\"03845\",\r\n" +	// 50027430 20064317 50016037
		  "\"requestFlag\":\"I\",\r\n" +
		  "\"deviceId\":\"00002437-7e40-4015-ab0e-98beb8888f\",\r\n" +
		  "\"mobileNo\":\"9730419977\",\r\n" +
		  "\"userRole\":\"CollectionApp\",\r\n" +
		  "\"userIp\":\"10.2.185.112\",\r\n" +
		  "\"userId\":\"CollectionApp\",\r\n" +
		  "\"userGroup\":\"CollectionApp\",\r\n" +
		  "\"srId\":\"8910231532523535\",\r\n" +
		  "\"level\":\"1\",\r\n" +
		  "\"channel\":\"CollectionApp\",\r\n" +
		  "\"authMode\":\"1\",\r\n" +
		  "\"serviceName\":\"changeDeviceMobileNo\"\r\n" + "}");
		
		//String upload=aES256.encrypt("{\"srId\":\"1013223456\",\"channel\":\"SFDC\",\"lob\":\"TW\",\"serviceName\":\"Addition\",\"lans\":[\"T14967300619012809\",\"T14928030519011025\",\"4460759\",\"T02881260622110256\",\"T15832250622035726\"]}");
		
		 System.out.println(cipherText);
			
		 String clearText = null;
		try {
			clearText = decrypt(cipherText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //String clearText = decryptBase64EncodedWithManagedIV("7W+XAOtJA7XFX5fI3GTQqheG2L4Fvc/TMbvrqzsYDLGWKWWYLWlJT4qJNjfJp3U/jn0uWCj1zix23cp2qq7wd1zhf9JNpxEorho6UDEAUO3WW7fHUk433S4XkbmiHiiyy3gOimKp3fMY+Skg8rH4uqHocZr32mKc7Vdo5wVkNHgfn/hZHTnCmQnuYLOMMVV/4PHkq7SnJdOc3dV9Nf6lUGbsbHJwCTA3+kGoBnuMkW2Xnth6c70UwSM3uTtuWKrpvbT59PtmRzKfg9KElgI7LdtIIwfAtjHCogy8EAHfCckhNgIjX4jqpPRT6BOctR143lkPkv5ELXVrRpus8ibriOQavH8DNr4S7x6cgKN/MnOg5Vuki90SPfbsejN3ukNSw0fCN8EzIxAQlBZ4w8Uxpq2xkjjVVrgQPhWASooYoa4SmkMr3xuIPH1+9pz+WbUM0qW2LXGjLsZ7XJsfJB4T++sOBTxy0xXAt5IyHtoeNi5+UkfjvsTn62Nh1KifmD2bxKl/4MErwG6SPrSbD+n//dFumG/AgVZGofpFkd7C/fXYUJ83RxHNkd0oY6VfN+iBc1Ukf8CKPmkMG8eoSGpFO+TxZs8vCmUgNugiMylitQFk+tZGLumHGVAf1MOhgBIEBCHU8i5znCnQL/ciDiajmcgUyRIeayO0boO0R/WgX+s=");

		 System.out.println("ClearText: " + clearText);
	}

}
