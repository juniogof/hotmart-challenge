/**
 * 
 */
package com.hotmart.challenge.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hotmart.challenge.domain.Product;
import com.hotmart.challenge.repository.ProductRepository;

import lombok.AllArgsConstructor;

/**
 * Ranking service
 * 
 * @author junio
 *
 */
@Service
@AllArgsConstructor
public class RankingService {

	private final ProductRepository productRepository;

	@Cacheable(cacheNames = {"rankingFindProductsCache"})
	public List<Product> findProducts(String term) {
		List<Product> products = new ArrayList<>();

		if (term != null && !term.isEmpty()) {
			products = productRepository
					.findByNameOrProductCategoryNameOrderByScoreDescNameAscProductCategoryNameAsc(term, term);
		} else {
			productRepository.findAll().forEach(products::add);
		}

		return products;
	}

}
