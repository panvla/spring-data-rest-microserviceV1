package com.vladimirpandurov.springdatarestmicroservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SpringBootApplication
public class SpringDataRestMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataRestMicroserviceApplication.class, args);
	}

	CommandLineRunner run (ProductRepository productRepository){
		return args -> {
			productRepository.save(new Product(null, "Lenovo", 10.00, true));
			productRepository.save(new Product(null, "Dell Latitude", 92.00, true));
			productRepository.save(new Product(null, "HP Pavilion", 150.00, true));
			productRepository.save(new Product(null, "Dell Precision", 500.90, false));
			productRepository.save(new Product(null, "Dell Inspiron", 110.40, true));
			productRepository.save(new Product(null, "MacBook Pro", 1000.99, true));
			productRepository.save(new Product(null, "iMac", 600.00, true));
			productRepository.findAll().forEach(product -> {
				System.out.println(product.getName());
			});


		};
	}

}
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
class Product{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double price;
	private boolean isInStock;
}
@Repository
interface ProductRepository extends JpaRepository<Product, Long>{

}

