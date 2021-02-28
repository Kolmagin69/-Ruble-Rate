package com.check.rate.ruble;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class RubleApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetFirstHomePage() throws Exception {
		mockMvc.perform(get("/rub/home"))
				.andExpect(status().isOk())
				.andExpect(view().name("first_page"));
	}

	@Test
	void testCheckRateChanges() throws Exception {
		mockMvc.perform(get("/rub/check")
				.param("exc_rate","USD")
				.param("date","2021-01-31"))
					.andExpect(status().isOk())
					.andExpect(view().name("home_page"));
	}


	@Test
	void testCheckRateChangesWithOtherLiteralCaseAndOtherRate() throws Exception {
		final String url = "/rub/check";
		final String excRateParam1 = "Eur";
		final String excRateParam2= "uah";
		final String dateParam = "2019-05-15";


		mockMvc.perform(get(url)
				.param("exc_rate",excRateParam1)
				.param("date",dateParam))
					.andExpect(status().isOk())
					.andExpect(view().name("home_page"));

		mockMvc.perform(get(url)
				.param("exc_rate",excRateParam2)
				.param("date",dateParam))
					.andExpect(status().isOk())
					.andExpect(view().name("home_page"));
	}

	@Test
	void failTestCheckRateChangesWithoutParam() throws Exception {
		mockMvc.perform(get("/rub/check"))
				.andExpect(status().isOk())
				.andExpect(view().name("error_date_page"));
	}

	@Test
	void failTestCheckRateChangesWithInvalidExcRateParam() throws Exception {
		final String url = "/rub/check";
		final String invalidExcRateParam1 = "";
		final String invalidExcRateParam2 = null;
		final String invalidExcRateParam3 = "USDD";
		final String dateParam = "2021-01-31";

		mockMvc.perform(get(url)
				.param("exc_rate",invalidExcRateParam1)
				.param("date",dateParam))
					.andExpect(status().isOk())
					.andExpect(view().name("error_rate_page"));

		mockMvc.perform(get(url)
				.param("exc_rate",invalidExcRateParam2)
				.param("date",dateParam))
					.andExpect(status().isOk())
					.andExpect(view().name("error_rate_page"));

		mockMvc.perform(get(url)
				.param("exc_rate",invalidExcRateParam3)
				.param("date",dateParam))
					.andExpect(status().isOk())
					.andExpect(view().name("error_rate_page"));
	}

	@Test
	void failTestCheckRateChangesWithInvalidDateParam() throws Exception {
		final String url = "/rub/check";
		final String excRateParam = "USD";
		final String invalidDateParam1 = "2021-01-3100";
		final String invalidDateParam2 = "";
		final String invalidDateParam3 = null;

		mockMvc.perform(get(url)
				.param("exc_rate",excRateParam)
				.param("date",invalidDateParam1))
					.andExpect(status().isOk())
					.andExpect(view().name("error_date_page"));

		mockMvc.perform(get(url)
				.param("exc_rate",excRateParam)
				.param("date",invalidDateParam2))
					.andExpect(status().isOk())
					.andExpect(view().name("error_date_page"));

		mockMvc.perform(get(url)
				.param("exc_rate",excRateParam)
				.param("date",invalidDateParam3))
					.andExpect(status().isOk())
					.andExpect(view().name("error_date_page"));
	}


}
