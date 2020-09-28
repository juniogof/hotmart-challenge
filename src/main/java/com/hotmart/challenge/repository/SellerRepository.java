/**
 * 
 */
package com.hotmart.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotmart.challenge.domain.Seller;

/**
 * seller data access object
 * 
 * @author junio
 *
 */
@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

}
