/**
 * 
 */
package com.hotmart.challenge.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hotmart.challenge.domain.CategoryNews;
import com.hotmart.challenge.domain.ProductCategory;
import com.hotmart.challenge.dto.NewsApiDTO;
import com.hotmart.challenge.repository.CategoryNewsRepository;
import com.hotmart.challenge.repository.ProductCategoryRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service to consume News Api
 * 
 * @author junio
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NewsApiService {

	@Value("${news.api.base.url}")
	private String newsApiBaseUrl;

	@Value("${news.api.key}")
	private String newsApiKey;

	@Autowired
	private RestTemplate restTemplate;
	
	private final CategoryNewsRepository categoryNewsRepository;
	private final ProductCategoryRepository productCategoryRepository;

	@HystrixCommand(fallbackMethod = "fallBack")
	public NewsApiDTO consumeNewsApi(String query) {
		log.info("Initiated consume News Api q="+query);
		
		String url = newsApiBaseUrl + "?q=" + query + "&apiKey=" + newsApiKey;
		NewsApiDTO response = restTemplate.getForObject(url, NewsApiDTO.class);

		log.info("Finalized consume News Api");
		return response;
	}

	/**
	 * 
	 * @param query (product category)
	 * @return the last today's result of that product category or zero
	 */
	public NewsApiDTO fallBack(String query) {
		log.error("Error on call news api. Handling on fallback method");
		NewsApiDTO newsApiDTO = NewsApiDTO.builder()
				.status(HttpStatus.OK.name())
				.totalResults(0L).build();
		
		Optional<ProductCategory> productCategory = productCategoryRepository.findByName(query);

		if (productCategory.isPresent()) {
			CategoryNews categoryNews = categoryNewsRepository.findFirstByProductCategoryIdAndNewsDateOrderByUpdateAtDesc(productCategory.get().getId(), LocalDate.now());
			newsApiDTO = NewsApiDTO.builder()
					.status(HttpStatus.OK.name())
					.totalResults(categoryNews.getTotalResults()).build();
		}
		
		return newsApiDTO;
	}
}
