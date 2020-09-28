/**
 * 
 */
package com.hotmart.challenge.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotmart.challenge.domain.CategoryNews;

/**
 * category_news data access object
 * 
 * @author junio
 *
 */
@Repository
public interface CategoryNewsRepository extends JpaRepository<CategoryNews, Long> {

	public CategoryNews findFirstByProductCategoryIdAndNewsDateOrderByUpdateAtDesc(Long productCategoryId,
			LocalDate newsDate);

}
