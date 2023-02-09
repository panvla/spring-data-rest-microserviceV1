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
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
public class SpringDataRestMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataRestMicroserviceApplication.class, args);
	}
	@Bean
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
@Data
@NoArgsConstructor
@AllArgsConstructor
class Product{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double price;
	private boolean isInStock;
}
@RepositoryRestResource
interface ProductRepository extends JpaRepository<Product, Long> {
	@RestResource(path="/findByName")
	Page<Product> findByNameContains(@Param("productName") String name, Pageable pageable);
}
@Projection(name = "web", types = Product.class)
interface WebProjection{
	Long getId();
	String getName();
	double getPrice();
}
@Projection(name="mobile", types = Product.class)
interface MobileProjection {
	Long getId();
	String getName();
}
//@RestController
//@AllArgsConstructor
//class ProductController{
//
//	private final ProductRepository productRepository;
//
//	@GetMapping("/products")
//	public List<Product> list(){
//		return this.productRepository.findAll();
//	}
//	@GetMapping("/products/{id}")
//	public Product getProduct(@PathVariable("id") Long id){
//		return this.productRepository.findById(id).get();
//	}
//	@PostMapping(path = "/products")
//	public Product saveProduct(@RequestBody Product product){
//		return productRepository.save(product);
//	}
//
//}