package com.hotmart.challenge.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotmart.challenge.domain.Product;
import com.hotmart.challenge.domain.ProductCategory;
import com.hotmart.challenge.dto.ProductDTO;
import com.hotmart.challenge.error.handling.EntityNotFoundException;
import com.hotmart.challenge.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

/**
 * Product Service
 * 
 * @author junio
 *
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductCategoryService productCategoryService;
	
	public Product create(ProductDTO productDTO) {
		ProductCategory productCategory = productCategoryService.findByName(productDTO.getProductCategoryName());

		Product product = Product.builder()
				.name(productDTO.getName())
				.description(productDTO.getDescription())
				.creationDate(LocalDateTime.now())
				.productCategory(productCategory)
				.build();
		
		return productRepository.save(product);
	}

	@Cacheable(cacheNames = {"productFindCache"})
	public Product find(Long id) {
		Optional<Product> product = productRepository.findById(id);

		return product.orElseThrow(() -> new EntityNotFoundException(Product.class, "id", id.toString()));
	}

	@Cacheable(cacheNames = {"productCategoryFindAllCache"})
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	public Product update(Long id, ProductDTO productDTO) {
		Product product = find(id);

		if (productDTO.getProductCategoryName() != null && !productDTO.getProductCategoryName().isEmpty()
				&& !productDTO.getProductCategoryName().equals(product.getProductCategory().getName())) {
			product.setProductCategory(productCategoryService.findByName(productDTO.getProductCategoryName()));
		}

		if (productDTO.getName() != null && !productDTO.getName().isEmpty()) {
			product.setName(productDTO.getName());
		}

		if (productDTO.getDescription() != null && !productDTO.getDescription().isEmpty()) {
			product.setDescription(productDTO.getDescription());
		}

		return productRepository.save(product);
	}

	public void delete(Long id) {
		Optional<Product> product = productRepository.findById(id);

		if (product.isPresent()) {
			productRepository.delete(product.get());
		} else {
			throw new EntityNotFoundException(Product.class, "id", id.toString());
		}
	}
}
