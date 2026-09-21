package com.example.subscription;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
@ActiveProfiles("test")
/** Verifies application startup and the JWT-protected API request flow. */
class SubscriptionApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void protectedApiRejectsRequestsWithoutToken() throws Exception {
		mockMvc.perform(get("/api/plans"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void generatedTokenCanAccessProtectedApi() throws Exception {
		String response = mockMvc.perform(post("/auth/login")
						.contentType(APPLICATION_JSON)
						.content("""
								{"username":"user","password":"user123"}
								"""))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		JsonNode token = objectMapper.readTree(response).get("token");

		mockMvc.perform(get("/api/plans")
						.header("Authorization", "Bearer " + token.asText()))
				.andExpect(status().isOk());
	}

}
