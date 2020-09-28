/**
 * 
 */
package com.hotmart.challenge.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the sale table in the database
 * 
 * @author junio
 *
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, insertable = false, updatable = false)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime saleDate;

	private Integer userScore;

	@ManyToOne
	@JoinColumn(name = "seller_id")
	private Seller seller;

	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private Buyer buyer;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
}
