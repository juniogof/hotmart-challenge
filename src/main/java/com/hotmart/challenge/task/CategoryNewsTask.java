/**
 * 
 */
package com.hotmart.challenge.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hotmart.challenge.domain.CategoryNews;
import com.hotmart.challenge.domain.ProductCategory;
import com.hotmart.challenge.dto.NewsApiDTO;
import com.hotmart.challenge.repository.CategoryNewsRepository;
import com.hotmart.challenge.repository.ProductCategoryRepository;
import com.hotmart.challenge.service.NewsApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Scheduled task to get 'News Api Service' and save the data
 * 
 * @author junio
 *
 */
@EnableAsync
@RequiredArgsConstructor
@Component
@Slf4j
public class CategoryNewsTask {

	private final ProductCategoryRepository productCategoryRepository;
	private final CategoryNewsRepository categoryNewsRepository;
	private final NewsApiService newsApiService;
	
	@Async
	@Scheduled(cron = "0 0 */8 * * *")
	//Runs each 8 hours
	public void scheduledTaskToCreateCategoryNews() {
		log.info("Initiated category news scheduled task");
		
		List<ProductCategory> productCategories = productCategoryRepository.findAll();

		productCategories.parallelStream().forEach(productCategory -> {
			
			NewsApiDTO response = newsApiService.consumeNewsApi(productCategory.getName());
			
			if (response != null && response.getStatus().equalsIgnoreCase(HttpStatus.OK.name())) {
				categoryNewsRepository.save(CategoryNews.builder()
						.newsDate(LocalDate.now())
						.updateAt(LocalTime.now())
						.totalResults(response.getTotalResults())
						.productCategory(productCategory).build());
			}
		});
		
		log.info("Finalized category news scheduled task");
	}
}
