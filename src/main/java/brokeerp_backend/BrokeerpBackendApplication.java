package brokeerp_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BrokeerpBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrokeerpBackendApplication.class, args);
		System.out.println("========================================");
		System.out.println("   BrokerERP iniciado correctamente");
		System.out.println("   http://localhost:8080");
		System.out.println("========================================");
	}

}
