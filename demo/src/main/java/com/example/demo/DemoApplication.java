// Questo è il package principale dell'applicazione
package com.example.demo;

// Importiamo le classi necessarie da Spring Framework
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.service.OrderService;

// Questa annotazione indica che questa è un'applicazione Spring Boot
// Abilita automaticamente molte funzionalità di Spring
@SpringBootApplication
public class DemoApplication {

	// Questo è il metodo main, punto di partenza dell'applicazione
	public static void main(String[] args) {
		// Avvia l'applicazione Spring e ottiene il contesto dell'applicazione
		// Il contesto contiene tutti i componenti (bean) dell'applicazione
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		
		// Ottiene un'istanza di OrderService dal contesto di Spring
		// OrderService gestirà la logica degli ordini
		OrderService orderService = context.getBean(OrderService.class);
		
		// Avvia il processo di creazione di un nuovo ordine
		orderService.startOrder();
	}

}
