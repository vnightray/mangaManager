package com.github.vnightray.acgnmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest(classes = {AcgnManagerApplication.class})
@AutoConfigureMockMvc
class AcgnManagerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void libraryTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/library/findLibrary")
		).andDo(MockMvcResultHandlers.print());
	}

	@Test
	void seriesTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/series/findSeries")
		).andDo(MockMvcResultHandlers.print());
	}

	@Test
	void bookTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/book/findBook")
		).andDo(MockMvcResultHandlers.print());
	}

}
