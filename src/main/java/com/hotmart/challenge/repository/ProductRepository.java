/**
 * 
 */
package com.hotmart.challenge.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hotmart.challenge.domain.Product;

/**
 * product data access object
 * 
 * @author junio
 *
 */
@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	public List<Product> findByNameOrProductCategoryNameOrderByScoreDescNameAscProductCategoryNameAsc(String name,
			String productCategoryName);
}
