// Questo è il package principale dell'applicazione
package com.example.demo;

// Importiamo le classi necessarie da Spring Framework
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.Drink;
import com.example.demo.model.Menu;
import com.example.demo.model.Order;
import com.example.demo.model.Pizza;
import com.example.demo.model.Table;
import com.example.demo.service.OrderService;

// Questa annotazione indica che questa è un'applicazione Spring Boot
// Abilita automaticamente molte funzionalità di Spring
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	@Value("${restaurant.cover.charge}")
	private double coverCharge;

	private final OrderService orderService;

	public DemoApplication(OrderService orderService) {
		this.orderService = orderService;
	}

	// Questo è il metodo main, punto di partenza dell'applicazione
	public static void main(String[] args) {
		// Avvia l'applicazione Spring e ottiene il contesto dell'applicazione
		// Il contesto contiene tutti i componenti (bean) dell'applicazione
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		
		// Ottiene un'istanza di OrderService dal contesto di Spring
		// OrderService gestirà la logica degli ordini
		OrderService orderService = context.getBean(OrderService.class);
		
		// Avvia il processo di creazione di un nuovo ordine
		orderService.start();
	}

	@Override
	public void run(String... args) {
		orderService.start();
	}

	@Bean
	public CommandLineRunner commandLineRunner(Menu menu, List<Table> tables) {
		return args -> {
			// Crea un nuovo ordine
			Table table = tables.get(0);  // Prende il primo tavolo
			Order order = new Order(table, 2, coverCharge);  // Ordine per 2 persone

			// Aggiunge alcuni items all'ordine
			Pizza margherita = menu.getPizzas().get(0);  // Prende la prima pizza
			Drink coca = menu.getDrinks().get(0);        // Prende la prima bevanda
			
			order.addItem(margherita);
			order.addItem(coca);

			// Logga l'ordine
			logger.info("\n" + order.toString());
		};
	}
}
