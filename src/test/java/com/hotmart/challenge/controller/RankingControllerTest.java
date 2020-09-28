/**
 * 
 */
package com.hotmart.challenge.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.hotmart.challenge.BaseTest;
import com.hotmart.challenge.service.RankingService;

/**
 * Class for RankingController.java unit tests 
 * @author junio
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RankingController.class)
public class RankingControllerTest extends BaseTest {
	
	@MockBean
	private RankingService rankingService;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void givenNoArgs_whenFind_thenOk() throws Exception {
		when(rankingService.findProducts(null)).thenReturn(buildProducts());
		
		mockMvc.perform(get("/ranking"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void givenAllArgs_whenFind_thenOk() throws Exception {
		String term = "Books";
		
		when(rankingService.findProducts(term)).thenReturn(buildBooksProducts());
		
		mockMvc.perform(get("/ranking").param("term", term))
				.andExpect(status().isOk());
	}	
}
