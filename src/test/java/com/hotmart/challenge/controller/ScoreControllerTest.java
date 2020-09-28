/**
 * 
 */
package com.hotmart.challenge.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.hotmart.challenge.BaseTest;
import com.hotmart.challenge.service.SaleService;

/**
 * Class for ScoreController.java unit tests 
 * @author junio
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ScoreController.class)
public class ScoreControllerTest extends BaseTest {
	
	@MockBean
	private SaleService saleService;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void givenAllArgs_whenUpdateScore_thenNoContent() throws Exception {
		Long id = 1L;
		Integer userScore = 3;
		
		when(saleService.updateScore(id, userScore)).thenReturn(buildSale());
		
		mockMvc.perform(put("/sales/{id}/score", id)
				.content("{\"userScore\" : " + userScore + " }")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}	
	
	@Test
	public void givenScoreGreatherThan_whenUpdateScore_thenBadRequest() throws Exception {
		Long id = 1L;
		Integer userScore = 6;
		
		when(saleService.updateScore(id, userScore)).thenReturn(buildSale());
		
		mockMvc.perform(put("/sales/{id}/score", id)
				.content("{\"userScore\" : " + userScore + " }")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(result -> assertThat(result.getResolvedException().getMessage()).containsIgnoringCase("UserScore can't be more than 5"));
	}
	
	@Test
	public void givenScoreLesserThan_whenUpdateScore_thenBadRequest() throws Exception {
		Long id = 1L;
		Integer userScore = 0;
		
		when(saleService.updateScore(id, userScore)).thenReturn(buildSale());
		
		mockMvc.perform(put("/sales/{id}/score", id)
				.content("{\"userScore\" : " + userScore + " }")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(result -> assertThat(result.getResolvedException().getMessage()).containsIgnoringCase("UserScore can't be less than 1"));
	}
}
