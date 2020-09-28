/**
 * 
 */
package com.hotmart.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotmart.challenge.domain.Buyer;

/**
 * buyer data access object
 * 
 * @author junio
 *
 */
@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {

}
