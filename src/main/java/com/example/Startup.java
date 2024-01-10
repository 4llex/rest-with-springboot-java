package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Startup {

	//TODO: Quando tenta get em persons sem estar autenticado, retorna 403 mas o body esta vazio!
	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);


		//Enconder
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		Pbkdf2PasswordEncoder pbkdf2Encoder =
			new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

		encoders.put("pbkdf2", pbkdf2Encoder);
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
		// End-encoder

		String leandroPass = passwordEncoder.encode("admin123");
		String flavioPass2 = passwordEncoder.encode("admin234");
		String alexpass = passwordEncoder.encode("admin123");

		System.out.println(leandroPass);
		System.out.println(flavioPass2);
		System.out.println(alexpass);
	}

}
