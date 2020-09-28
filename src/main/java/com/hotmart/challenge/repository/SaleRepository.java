/**
 * 
 */
package com.hotmart.challenge.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotmart.challenge.domain.Sale;

/**
 * sale data access object
 * 
 * @author junio
 *
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("select sum(userScore)/count(1) from Sale s where s.product.id = ?1 and saleDate >= ?2 and userScore is not null")
	public Float averageUserScoreByProductIdAndSaleDateGreaterThanAndUserScoreIsNotNull(Long productId,
			LocalDateTime saleDate);

	@Query("select count(1) from Sale s where s.product.id = ?1")
	public Long countByProductId(Long productId);
}
