package com.project.weatherapi.api;


import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.weatherapi.WeatherDAO;
import com.project.weatherapi.WeatherVO;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/*
    @RestController : 기본으로 하위에 있는 메소드들은 모두 @ResponseBody를 가지게 된다.
    @RequestBody : 클라이언트가 요청한 XML/JSON을 자바 객체로 변환해서 전달 받을 수 있다.
    @ResponseBody : 자바 객체를 XML/JSON으로 변환해서 응답 객체의 Body에 실어 전송할 수 있다.
            클라이언트에게 JSON 객체를 받아야 할 경우는 @RequestBody, 자바 객체를 클라이언트에게 JSON으로 전달해야할 경우에는 @ResponseBody 어노테이션을 붙여주면 된다.
    @ResponseBody를 사용한 경우 View가 아닌 자바 객체를 리턴해주면 된다.
*/

@RestController
@RequestMapping("/weather")
@ConfigurationProperties(prefix="greeting")
public class WeatherApiController {
    @Autowired
    WeatherDAO wDAO;

    @Getter
    @Setter
    String hello;

    String returnString;

    @GetMapping("/getall")
    public List<WeatherVO> getAll() {
        return wDAO.getDatas();
    }

    @GetMapping("/get")
    public String getWeather(@RequestParam(value = "name", defaultValue = "Seoul") String cityname,
                             @RequestParam(value = "APIkey", defaultValue = "") String apikey) throws JsonProcessingException {

        HashMap<String, Object> result = new HashMap<String, Object>();
        WeatherVO w = new WeatherVO();

        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000); //타임아웃 설정 5초
            factory.setReadTimeout(5000);//타임아웃 설정 5초
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);

            String url = "http://api.openweathermap.org/data/2.5/weather";

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+"?q="+cityname+"&appid="+apikey).build();

            //이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

            //데이터를 제대로 전달 받았는지 확인 string형태로 파싱해줌
            ObjectMapper mapper = new ObjectMapper();

            //데이터를 WeatherVO 형태로 저장해 weatherDAO에 넣어줌
            w.setCityname(cityname);
            w.getCoord().setLon(
                    Double.valueOf(((LinkedHashMap)resultMap.getBody().get("coord")).get("lon").toString())
            );
            w.getCoord().setLat(
                    Double.valueOf(((LinkedHashMap)resultMap.getBody().get("coord")).get("lat").toString())
            );
            w.getTemp().setTemp_cur(
                    Double.valueOf(((LinkedHashMap)resultMap.getBody().get("main")).get("temp").toString()) - 273.15
            );
            w.getTemp().setTemp_min(
                    Double.valueOf(((LinkedHashMap)resultMap.getBody().get("main")).get("temp_min").toString()) - 273.15
            );
            w.getTemp().setTemp_max(
                    Double.valueOf(((LinkedHashMap)resultMap.getBody().get("main")).get("temp_max").toString()) - 273.15
            );
            w.getTemp().setHumidity(
                    Integer.valueOf(((LinkedHashMap)resultMap.getBody().get("main")).get("humidity").toString())
            );
            w.getWeather().setMain(
                    ((LinkedHashMap)((ArrayList)resultMap.getBody().get("weather")).get(0)).get("main").toString()
            );
            w.getWeather().setDescription(
                    ((LinkedHashMap)((ArrayList)resultMap.getBody().get("weather")).get(0)).get("description").toString()
            );
            wDAO.addData(w);
            returnString = w.getCityname() + "의 현재 날씨는 " + w.getWeather().getDescription() + " 이고, 기온은 "
                    + Double.toString(w.getTemp().getTemp_cur()).substring(0,4) +"'C 이며, 습도는 " + w.getTemp().getHumidity() + "% 입니다. 좋은 하루 보내세요!";
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body"  , e.getStatusText());
            System.out.println("dfdfdfdf");
            System.out.println(e.toString());
        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body"  , "excpetion오류");
            System.out.println(e.toString());
        }
        return returnString;
    }
}
