package com.heitor.duatcassinos2_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Duatcassinos2BackApplication {

	public static void main(String[] args) {
		SpringApplication.run(Duatcassinos2BackApplication.class, args);
		System.out.println("""
            
            ╔═══════════════════════════════════════╗
            ║   🌌 DUAT CASSINOS II INICIADO 🌌    ║
            ║                                       ║
            ║   Backend rodando em:                 ║
            ║   http://localhost:8080               ║
            ║                                       ║
            ║   Console H2:                         ║
            ║   http://localhost:8080/h2-console    ║
            ╚═══════════════════════════════════════╝
            
            """);
	
	}

}
