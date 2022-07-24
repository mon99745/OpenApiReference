package com.project.weatherapi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// Weather들의 List를 관리하는 데이터 접근 객체
@Component
public class WeatherDAO {
    private List<WeatherVO> datas = new ArrayList<>();

    public List<WeatherVO> getDatas(){
        return datas;
    }
    public void addData(WeatherVO w) {
        datas.add(w);
        return;
    }
}


