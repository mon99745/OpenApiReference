package com.project.weatherapi;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Data
public class WeatherVO {
    private String cityname; // 도시이름
    private Coord coord; // 좌표
    private Weather weather; // 날씨정보
    private Temp temp; // 기온정보
    @Data
    public class Coord{
        double lon; // 경도
        double lat; // 위도
    }
    @Data
    public class Weather{
        String main; //간략 기상 ( ex.태양)
        String description; // 세부 기상
    }
    @Data
    public class Temp{
        double temp_cur; // 현재 기온
        double temp_min; // 최저 기온
        double temp_max; // 최고 기온
        int humidity; // 습도
    }
    public WeatherVO() {
        this.cityname = new String();
        this.coord = new Coord();
        this.weather = new Weather();
        this.temp = new Temp();
    }
    public WeatherVO(String cityname, Coord coord, Weather weather, Temp temp ) {
        this.cityname = cityname;
        this.coord = coord;
        this.weather = weather;
        this.temp = temp;
    }
}