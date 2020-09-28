/**
 * 
 */
package com.hotmart.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotmart.challenge.domain.ProductCategory;

/**
 * product_category data access object
 * 
 * @author junio
 *
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

	public Optional<ProductCategory> findByName(String name);

}
