// Questo è il package principale dell'applicazione
package com.example.demo;

// Importiamo le classi necessarie da Spring Framework
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.model.Drink;
import com.example.demo.model.Menu;
import com.example.demo.model.Order;
import com.example.demo.model.Pizza;
import com.example.demo.model.Table;
import com.example.demo.model.TableStatus;
import com.example.demo.repository.DrinkRepository;
import com.example.demo.repository.PizzaRepository;
import com.example.demo.repository.ToppingRepository;
import com.example.demo.service.OrderService;

// Questa annotazione indica che questa è un'applicazione Spring Boot
// Abilita automaticamente molte funzionalità di Spring
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	@Value("${restaurant.cover.charge}")
	private double coverCharge;

	private final OrderService orderService;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private PizzaRepository pizzaRepository;
	@Autowired
	private DrinkRepository drinkRepository;
	@Autowired
	private ToppingRepository toppingRepository;
	private Scanner scanner = new Scanner(System.in);

	public DemoApplication(OrderService orderService) {
		this.orderService = orderService;
		
		// Aggiungi uno shutdown hook per gestire la terminazione gracefully
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			logger.info("Applicazione in fase di chiusura...");
			// Pulizia delle risorse se necessario
			Runtime.getRuntime().halt(0); // Forza uscita con codice 0
		}));
	}

	// Questo è il metodo main, punto di partenza dell'applicazione
	public static void main(String[] args) {
		// Avvia l'applicazione Spring e ottiene il contesto dell'applicazione
		// Il contesto contiene tutti i componenti (bean) dell'applicazione
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Inizializza silenziosamente il database
		Menu menu = context.getBean(Menu.class);
		List<Table> tables = context.getBean(List.class);
		initializeDatabase(menu);
		
		// Pulisce la console
		System.out.print("\033[H\033[2J");
		System.out.flush();
		
		// Avvia il menu interattivo
		while (true) {
			logger.info("\n=== RISTORANTE DEMO ===");
			logger.info("1. Crea nuovo ordine");
			logger.info("2. Visualizza menu");
			logger.info("3. Visualizza stato tavoli");
			logger.info("0. Esci");
			
			int scelta = getIntInput("Seleziona un'opzione: ");
			
			switch (scelta) {
				case 1:
					creaOrdine(menu, tables);
					break;
				case 2:
					mostraMenu();
					break;
				case 3:
					mostraStatoTavoli(tables);
					break;
				case 0:
					logger.info("Grazie per aver utilizzato il nostro servizio!");
					return;
				default:
					logger.info("Opzione non valida!");
			}
		}
	}

	private void creaOrdine(Menu menu, List<Table> tables) {
		logger.info("\n=== NUOVO ORDINE ===");
		logger.info("1. Ordine al tavolo");
		logger.info("2. Ordine delivery");
		
		int tipo = getIntInput("Seleziona tipo ordine: ");
		Order order;
		
		if (tipo == 1) {
			// Ordine al tavolo
			mostraStatoTavoli(tables);
			int numTavolo = getIntInput("Seleziona numero tavolo: ");
			Table table = tables.stream()
							  .filter(t -> t.getNumber() == numTavolo && t.getStatus() == TableStatus.FREE)
							  .findFirst()
							  .orElse(null);
							  
			if (table == null) {
				logger.info("Tavolo non disponibile!");
				return;
			}
			
			int coperti = getIntInput("Numero di coperti: ");
			order = new Order(table, coperti, coverCharge);
		} else if (tipo == 2) {
			// Ordine delivery
			order = new Order(null, 0, 0); // Delivery non ha coperti
		} else {
			logger.info("Tipo ordine non valido!");
			return;
		}
		
		// Aggiunta items all'ordine
		boolean continua = true;
		while (continua) {
			logger.info("\n=== AGGIUNGI ITEM ===");
			logger.info("1. Pizza");
			logger.info("2. Bevanda");
			logger.info("0. Termina ordine");
			
			int sceltaItem = getIntInput("Seleziona tipo item: ");
			
			switch (sceltaItem) {
				case 1:
					aggiungiPizza(order, menu);
					break;
				case 2:
					aggiungiBevanda(order, menu);
					break;
				case 0:
					continua = false;
					break;
				default:
					logger.info("Opzione non valida!");
			}
		}
		
		// Mostra riepilogo ordine
		logger.info("\n=== RIEPILOGO ORDINE ===");
		logger.info(order.toString());
	}

	private void aggiungiPizza(Order order, Menu menu) {
		logger.info("\n=== MENU PIZZE ===");
		List<Pizza> pizze = menu.getPizzas();
		for (int i = 0; i < pizze.size(); i++) {
			logger.info((i+1) + ". " + pizze.get(i));
		}
		
		int scelta = getIntInput("Seleziona pizza (0 per annullare): ");
		if (scelta > 0 && scelta <= pizze.size()) {
			Pizza pizza = pizze.get(scelta-1);
			
			logger.info("Vuoi la versione XL? (1: Sì, 0: No)");
			if (getIntInput("") == 1) {
				pizza.makeXL();
			}
			
			order.addItem(pizza);
			logger.info("Pizza aggiunta all'ordine!");
		}
	}

	private void aggiungiBevanda(Order order, Menu menu) {
		logger.info("\n=== MENU BEVANDE ===");
		List<Drink> bevande = menu.getDrinks();
		for (int i = 0; i < bevande.size(); i++) {
			logger.info((i+1) + ". " + bevande.get(i));
		}
		
		int scelta = getIntInput("Seleziona bevanda (0 per annullare): ");
		if (scelta > 0 && scelta <= bevande.size()) {
			order.addItem(bevande.get(scelta-1));
			logger.info("Bevanda aggiunta all'ordine!");
		}
	}

	private void mostraMenu() {
		logger.info("\n=== MENU COMPLETO ===");
		logger.info("\nPIZZE:");
		pizzaRepository.findAll().forEach(p -> logger.info(p.toString()));
		
		logger.info("\nBEVANDE:");
		drinkRepository.findAll().forEach(d -> logger.info(d.toString()));
	}

	private void mostraStatoTavoli(List<Table> tables) {
		logger.info("\n=== STATO TAVOLI ===");
		tables.forEach(t -> logger.info(t.toString()));
	}

	private void initializeDatabase(Menu menu) {
		menu.getAvailableToppings().forEach(topping -> toppingRepository.save(topping));
		menu.getPizzas().forEach(pizza -> pizzaRepository.save(pizza));
		menu.getDrinks().forEach(drink -> drinkRepository.save(drink));
	}

	private int getIntInput(String message) {
		logger.info(message);
		while (true) {
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				logger.info("Inserisci un numero valido!");
			}
		}
	}
}
