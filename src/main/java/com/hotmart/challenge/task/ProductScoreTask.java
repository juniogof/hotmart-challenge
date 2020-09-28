/**
 * 
 */
package com.hotmart.challenge.task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hotmart.challenge.domain.CategoryNews;
import com.hotmart.challenge.domain.Product;
import com.hotmart.challenge.repository.CategoryNewsRepository;
import com.hotmart.challenge.repository.ProductRepository;
import com.hotmart.challenge.repository.SaleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Scheduled task to calc product score and save the data
 * 
 * @author junio
 *
 */
@EnableAsync
@RequiredArgsConstructor
@Component
@Slf4j
public class ProductScoreTask {

	private final CategoryNewsRepository categoryNewsRepository;
	private final ProductRepository productRepository;
	private final SaleRepository saleRepository;

	@Async
	@Scheduled(cron = "0 0 */2 * * *")
	// Runs each 2 hours
	public void scheduledTaskToCalcProductScore() {
		log.info("Initiated calc product score scheduled task");
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(products::add);

		products.parallelStream().forEach(product -> {
			Float x = doAverageUserScore(product.getId());
			Float y = doSellByDay(product.getId(), product.getCreationDate());
			Long z = countCategoryProductNews(product.getProductCategory().getId());

			product.setScore(x + y + z);

			productRepository.save(product);
		});
		
		log.info("Finalized calc product score scheduled task");
	}

	private Float doAverageUserScore(Long productId) {
		LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1L);

		Float average = saleRepository.averageUserScoreByProductIdAndSaleDateGreaterThanAndUserScoreIsNotNull(productId,
				oneYearAgo);

		return average != null ? average : 0f;
	}

	private Float doSellByDay(Long productId, LocalDateTime productDate) {
		Long sales = saleRepository.countByProductId(productId);
		Duration duration = Duration.between(productDate, LocalDateTime.now());
		Long days = duration != null ? duration.toDays() : 0L;
		Float average = 0f;

		if (sales != null && !sales.equals(0L) && !days.equals(0L)) {
			average = Float.valueOf(sales.toString()) / days;
		}

		return average;
	}

	private Long countCategoryProductNews(Long productCategoryId) {
		CategoryNews categoryNews = categoryNewsRepository
				.findFirstByProductCategoryIdAndNewsDateOrderByUpdateAtDesc(productCategoryId, LocalDate.now());

		return categoryNews != null ? categoryNews.getTotalResults() : 0L;
	}
}
