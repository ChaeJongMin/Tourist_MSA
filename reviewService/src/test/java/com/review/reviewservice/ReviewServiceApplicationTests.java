package com.review.reviewservice;

import com.review.reviewservice.Respository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReviewServiceApplicationTests {
	@Autowired
	private ReviewRepository touristRepository;

	@Test
	void contextLoads() {
//		TestRestTemplate testRestTemplate=new TestRestTemplate();
//		String baseUrl = "http://localhost:" + 9004 + "/api/map";
//		ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl, String.class);
//
//		String responseBody = response.getBody();
//
//		System.out.println(responseBody);
//		JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
//		long id=1;
//		// JsonArray에서 tourDestNm 필드 값을 추출
//		for (JsonElement element : jsonArray) {
//			JsonObject jsonObject = element.getAsJsonObject();
//			String tourDestNm = jsonObject.get("tourDestNm").getAsString();
//			System.out.println("tourDestNm: " + tourDestNm);
//			if(id==43 || id==32)
//				id++;
//			TouristEntity touristEntity=new TouristEntity(id,tourDestNm);
//			touristRepository.save(touristEntity);
//			id++;
//		}
		//43; 32
	}

}
