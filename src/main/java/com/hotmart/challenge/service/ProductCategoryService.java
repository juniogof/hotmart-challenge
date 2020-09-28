package com.hotmart.challenge.service;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotmart.challenge.domain.ProductCategory;
import com.hotmart.challenge.error.handling.EntityNotFoundException;
import com.hotmart.challenge.repository.ProductCategoryRepository;

import lombok.RequiredArgsConstructor;

/**
 * Product category service
 * 
 * @author junio
 *
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductCategoryService {

	private final ProductCategoryRepository productCategoryRepository;

	@Cacheable(cacheNames = {"productCategoryFindByNameCache"})
	public ProductCategory findByName(String name) {
		Optional<ProductCategory> productCategory = productCategoryRepository.findByName(name);

		return productCategory.orElseThrow(() -> new EntityNotFoundException(ProductCategory.class, "name", name));
	}

}
