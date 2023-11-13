package org.learning.inventoryservice;

import org.learning.inventoryservice.model.Inventory;
import org.learning.inventoryservice.repositories.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
        return args -> {
			Inventory inventory = new Inventory();
			inventory.setQuantity(2);
			inventory.setProductCode("iphone-9e");
			inventoryRepository.save(inventory);

			Inventory inventory1 = new Inventory();
			inventory1.setQuantity(0);
			inventory1.setProductCode("iphone-9d");
			inventoryRepository.save(inventory1);
		};
    }

}
