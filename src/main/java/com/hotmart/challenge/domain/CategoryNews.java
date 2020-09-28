/**
 * 
 */
package com.hotmart.challenge.domain;

import java.time.LocalDate;
import java.time.LocalTime;

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
 * Represents the category_news table in the database
 * 
 * @author junio
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryNews {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, insertable = false, updatable = false)
	private Long id;

	private Long totalResults;

	private LocalDate newsDate;

	private LocalTime updateAt;

	@ManyToOne
	@JoinColumn(name = "product_category_id")
	private ProductCategory productCategory;
}
