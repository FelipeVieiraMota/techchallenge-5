package br.com.agendafacilsus.autorizacaoeusuarios;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordGenerator {

	private void passwordGenerator() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "test123";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);
	}

	public static void main(String[] args) {
		new PasswordGenerator().passwordGenerator();
	}
}
