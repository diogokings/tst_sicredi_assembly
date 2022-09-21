package com.sicredi.assembly.vote;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Component
public class AssociateInfo {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	public String consumer(String url) {
		String response = restTemplate(new RestTemplateBuilder()).getForObject(url, String.class);
		return response;
	}
	
	public String getAssociate(String cpf) {
		try {
			String url = "https://user-info.herokuapp.com/users/".concat(cpf);
			JsonObject jsonObject = new Gson().fromJson(consumer(url), JsonObject.class);
			return jsonObject.get("status").getAsString();			
		} catch (Exception e) {
			return "";
		}
	}
}
