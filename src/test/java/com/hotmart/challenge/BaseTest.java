/**
 * 
 */
package com.hotmart.challenge;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.hotmart.challenge.domain.Buyer;
import com.hotmart.challenge.domain.CategoryNews;
import com.hotmart.challenge.domain.Product;
import com.hotmart.challenge.domain.ProductCategory;
import com.hotmart.challenge.domain.Sale;
import com.hotmart.challenge.domain.Seller;
import com.hotmart.challenge.dto.NewsApiDTO;
import com.hotmart.challenge.dto.ProductDTO;

/**
 * Base class for unit tests
 * 
 * @author junio
 *
 */

public class BaseTest {

	//Product builders
	protected ProductDTO buildProductDTO() {
		return ProductDTO.builder()
				.name("Unit test")
				.description("Description of unit test")
				.productCategoryName("CDs")
				.build();
	}
	
	protected Product buildProduct() {
		return Product.builder()
				.name("Unit test")
				.description("Description of unit test")
				.creationDate(LocalDateTime.now())
				.productCategory(buildProductCategory())
				.build();
	}
	
	protected Product buildProductCreated() {
		return Product.builder()
				.id(0L)
				.name("Unit test")
				.description("Description of unit test")
				.creationDate(LocalDateTime.now())
				.productCategory(buildProductCategory())
				.build();
	}
	
	protected Product buildProductScored() {
		return Product.builder()
				.id(0L)
				.name("Unit test")
				.description("Description of unit test")
				.creationDate(LocalDateTime.now())
				.score(1000F)
				.productCategory(buildProductCategory())
				.build();
	}
	
	protected Product buildProductUpdated() {
		return Product.builder()
				.id(0L)
				.name("Unit test Updated")
				.description("Description of unit test Updated")
				.creationDate(LocalDateTime.now())
				.productCategory(buildProductCategoryUpdate())
				.build();
	}
	
	protected ProductDTO buildProductDTOUpdate() {
		return ProductDTO.builder()
				.name("Unit test Updated")
				.description("Description of unit test Updated")
				.productCategoryName("Books")
				.build();
	}
	
	protected List<Product> buildProducts() {
		Product product1 = Product.builder()
				.id(0L)
				.name("Unit test")
				.description("Description of unit test")
				.creationDate(LocalDateTime.now())
				.score(1000f)
				.productCategory(buildProductCategory())
				.build();
		
		Product product2 = Product.builder()
				.id(1L)
				.name("Unit test 2")
				.description("Description of unit test 2")
				.creationDate(LocalDateTime.now())
				.score(900.5f)
				.productCategory(buildProductCategoryUpdate())
				.build();
		
		Product product3 = Product.builder()
				.id(2L)
				.name("Unit test 3")
				.description("Description of unit test 3")
				.creationDate(LocalDateTime.now())
				.score(900f)
				.productCategory(ProductCategory.builder().id(5L).name("Electronics").build())
				.build();
		
		List<Product> products = new ArrayList<>();
		products.add(product1);
		products.add(product2);
		products.add(product3);
		
		return products;
	}
	
	protected List<Product> buildBooksProducts() {
		Product product1 = Product.builder()
				.id(0L)
				.name("Unit test")
				.description("Description of unit test")
				.creationDate(LocalDateTime.now())
				.score(1200f)
				.productCategory(buildProductCategory())
				.build();
		
		Product product2 = Product.builder()
				.id(1L)
				.name("Unit test 2")
				.description("Description of unit test 2")
				.creationDate(LocalDateTime.now())
				.score(560.5f)
				.productCategory(buildProductCategory())
				.build();
		
		Product product3 = Product.builder()
				.id(2L)
				.name("Unit test 3")
				.description("Description of unit test 3")
				.creationDate(LocalDateTime.now())
				.score(100f)
				.productCategory(buildProductCategory())
				.build();
		
		List<Product> products = new ArrayList<>();
		products.add(product1);
		products.add(product2);
		products.add(product3);
		
		return products;
	}
	
	//ProductCategory builders
	protected ProductCategory buildProductCategory() {
		return ProductCategory.builder()
				.id(1L)
				.name("CDs")
				.build();
	}
	
	protected ProductCategory buildProductCategoryUpdate() {
		return ProductCategory.builder()
				.id(2L)
				.name("Books")
				.build();
	}
	
	protected List<ProductCategory> buildProductCategories() {
		List<ProductCategory> productCategories = new ArrayList<>();
		productCategories.add(buildProductCategory());
		productCategories.add(buildProductCategoryUpdate());
		
		return productCategories;
	}

	//CategoryNews builder
	protected CategoryNews buildCategoryNews() {
		return CategoryNews.builder()
				.newsDate(LocalDate.now())
				.updateAt(LocalTime.now())
				.totalResults(1000L)
				.productCategory(ProductCategory.builder().id(1L).name("CDs").build()).build();
	}
	
	//Sale builder
	protected Sale buildSale() {
		return Sale.builder()
				.buyer(Buyer.builder().id(1L).build())
				.seller(Seller.builder().id(1L).build())
				.saleDate(LocalDateTime.now())
				.userScore(3)
				.product(buildProduct())
				.build();
	}
	
	//News Api
	protected NewsApiDTO builderNewsApiDTO() {
		return NewsApiDTO.builder()
				.status(HttpStatus.OK.name())
				.totalResults(1000L)
				.build();
	}
}
