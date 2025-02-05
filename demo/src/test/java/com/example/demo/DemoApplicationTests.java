package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.Drink;
import com.example.demo.model.Menu;
import com.example.demo.model.Order;
import com.example.demo.model.Pizza;
import com.example.demo.model.Table;
import com.example.demo.model.TableStatus;
import com.example.demo.model.Topping;
import com.example.demo.service.OrderService;

@SpringBootTest
@TestConfiguration
class DemoApplicationTests {

	@Autowired
	private Menu menu;

	@Autowired
	private List<Table> tables;

	@Autowired
	private OrderService orderService;

	@Value("${restaurant.cover.charge}")
	private double coverCharge;

	@Bean
	public Menu testMenu() {
		Menu menu = new Menu();
		menu.addPizza(new Pizza("Margherita", 8.5, 800));
		menu.addDrink(new Drink("Coca Cola", 3.0, 140, 330));
		menu.addTopping(new Topping("Prosciutto", 2.0, 35));
		return menu;
	}

	@Bean
	public List<Table> testTables() {
		List<Table> tables = new ArrayList<>();
		tables.add(new Table(1, 2));
		tables.add(new Table(2, 4));
		tables.add(new Table(3, 6));
		tables.add(new Table(4, 8));
		return tables;
	}

	// Test 1: Verifica della creazione di un Drink
	@Test
	void testDrinkCreation() {
		Drink coca = new Drink("Coca Cola", 3.0, 140, 330);
		assertEquals("Coca Cola", coca.getName());
		assertEquals(3.0, coca.getPrice());
		assertEquals(140, coca.getCalories());
		assertEquals(330, coca.getSize());
	}

	// Test 2: Verifica della pizza con toppings
	@Test
	void testPizzaWithToppings() {
		Pizza margherita = new Pizza("Margherita", 8.5, 800);
		Topping prosciutto = new Topping("Prosciutto", 2.0, 35);
		Topping funghi = new Topping("Funghi", 1.8, 20);
		
		margherita.addTopping(prosciutto);
		margherita.addTopping(funghi);

		assertEquals(12.3, margherita.getPrice(), 0.01);
		assertEquals(855, margherita.getCalories());
		assertEquals(2, margherita.getToppings().size());
	}

	// Test 3: Verifica della trasformazione in XL
	@Test
	void testPizzaXLTransformation() {
		Pizza pizza = new Pizza("Margherita", 10.0, 1000);
		pizza.makeXL();

		assertEquals(15.0, pizza.getPrice(), 0.01);
		assertEquals(1800, pizza.getCalories());
		assertTrue(pizza.getName().contains("XL"));
	}

	// Test 4: Verifica capacità tavoli
	@ParameterizedTest
	@CsvSource({
		"1, 2",
		"2, 4",
		"3, 6",
		"4, 8"
	})
	void testTableCapacity(int tableNumber, int expectedCapacity) {
		Table table = tables.stream()
						  .filter(t -> t.getNumber() == tableNumber)
						  .findFirst()
						  .orElseThrow();
		
		assertEquals(expectedCapacity, table.getMaxSeats());
		assertEquals(TableStatus.FREE, table.getStatus());
	}

	// Test 5: Verifica del calcolo totale ordine
	@Test
	void testComplexOrderTotal() {
		Table table = tables.get(0); // Usa un tavolo esistente invece di crearne uno nuovo
		Order order = new Order(table, 3, coverCharge);

		Pizza margherita = menu.getPizzas().stream()
							 .filter(p -> p.getName().equals("Margherita"))
							 .findFirst()
							 .orElseThrow();
		
		Topping prosciutto = menu.getAvailableToppings().stream()
							   .filter(t -> t.getName().equals("Prosciutto"))
							   .findFirst()
							   .orElseThrow();

		Pizza orderedPizza = new Pizza(margherita.getName(), margherita.getPrice(), margherita.getCalories());
		orderedPizza.addTopping(prosciutto);
		order.addItem(orderedPizza);

		Drink coca = menu.getDrinks().stream()
						   .filter(d -> d.getName().equals("Coca Cola"))
						   .findFirst()
						   .orElseThrow();
		order.addItem(coca);

		// Totale atteso:
		// - Pizza Margherita (8.5) + Prosciutto (2.0) = 10.5
		// - Coca Cola = 3.0
		// - Coperto (2.50 × 3 persone) = 7.50
		// Totale: 21.00
		assertEquals(21.00, order.getTotal(), 0.01);
	}
}
