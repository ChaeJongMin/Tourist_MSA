package com.mapinfo.mapservice;
import com.google.gson.*;
import com.mapinfo.mapservice.Domain.TouristEntity;
import com.mapinfo.mapservice.Repository.TouristRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.InputStreamReader;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@SpringBootTest

class MapServiceApplicationTests {
    private static final String API_URL = "http://apis.data.go.kr/6290000/tourdestbaseinfo/gettourdestbaseinfo";
    private static final String SERVICE_KEY = "UZXY3v%2FziYQ%2BPUvFGDXkN9Idj0nYnjMj7I%2FCQKaEJUyqhpOdtCMF%2B6LTVFwdbd6Rbd%2BFHf5UVm47hRULi5%2FQZg%3D%3D";
    @Autowired
    private TouristRepository touristRepository;
    @Test
    //call api
    void contextLoads() throws IOException, URISyntaxException {
        /*URL*/

        StringBuilder urlBuilder = new StringBuilder(API_URL);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + SERVICE_KEY);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", StandardCharsets.UTF_8.toString()));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8.toString()));
        urlBuilder.append("&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("json", StandardCharsets.UTF_8.toString()));
        urlBuilder.append("&" + URLEncoder.encode("tourDestNm", "UTF-8") + "=" + URLEncoder.encode("", StandardCharsets.UTF_8.toString()));
        URL url=new URI(urlBuilder.toString()).toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type",  "application/json;charset=UTF-8");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader bf;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        } else {
            bf = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        String result;
        // bf.readLine();

        while ((line = bf.readLine()) != null){
            sb.append(line);
        }
        Gson gson = new Gson();
        JsonObject jsonObject=gson.fromJson(sb.toString(),JsonObject.class);
        JsonArray tourDestArray = jsonObject.getAsJsonArray("TourDestBaseInfo");

        for (JsonElement item : tourDestArray) {
            JsonObject tourDestBaseInfo = item.getAsJsonObject();
            System.out.println("tourDestNm : "+tourDestBaseInfo.get("tourDestNm"));
//            System.out.println("lat : "+tourDestBaseInfo.get("lat"));
//            System.out.println("lng : "+tourDestBaseInfo.get("lng"));
//            System.out.println("availParkingCnt : "+tourDestBaseInfo.get("availParkingCnt"));
//            System.out.println("capacity : "+tourDestBaseInfo.get("capacity"));
//            System.out.println("tourDestIntro : "+tourDestBaseInfo.get("tourDestIntro"));
//            System.out.println("addrRoad : "+tourDestBaseInfo.get("addrRoad"));
            System.out.println("mngAgcTel : "+tourDestBaseInfo.get("mngAgcTel"));
            String handNumber=tourDestBaseInfo.get("mngAgcTel").toString();
            String name=tourDestBaseInfo.get("tourDestNm").toString();
            Optional<TouristEntity> touristEntity=touristRepository.findByTourDestNm(name);
            if(touristEntity.isPresent()){
                TouristEntity entity=touristEntity.get();
                entity.setMngAgcTel(handNumber);
                System.out.println("받아온 연락처 : "+tourDestBaseInfo.get("mngAgcTel"));
                touristRepository.save(entity);
            } else {
                System.out.println("존재하지 않아요......");
            }
            System.out.println("------------------------------------------------------");
        }


        conn.disconnect();
    }
//    @Test
//    void setRandomVisitCnt() {
//        List<TouristEntity> touristEntityList = touristRepository.findAll();
//        for(TouristEntity t : touristEntityList) {
//            int value=getRandomNumberWithCondition();
//            t.setVisitCnt((long)value);
//            touristRepository.save(t);
//        }
//
//    }
    public static int getRandomNumberWithCondition() {
        Random random = new Random();

        // 천의자리, 만의자리, 십만자리의 확률
        double[] probabilities = {0.6, 0.3, 0.1};

        int thousand = random.nextInt(10);
        int tenThousand = random.nextInt(10);
        int hundredThousand = random.nextInt(10);

        // 각 자리수에 해당하는 수를 할당
        for (int i = 0; i < probabilities.length; i++) {
            int index = random.nextInt(probabilities.length);
            if (probabilities[index] > 0) {
                switch (i) {
                    case 0:
                        thousand = index;
                        break;
                    case 1:
                        tenThousand = index;
                        break;
                    case 2:
                        hundredThousand = index;
                        break;
                }
                probabilities[index] = 0;
                break;
            }
        }

        return thousand * 10000 + tenThousand * 1000 + hundredThousand;
    }

}
