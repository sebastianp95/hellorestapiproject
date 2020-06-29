package com.example.hellorestapiproject2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Source;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


@Component
public class MyTask {

    private RestTemplate restTemplate = new RestTemplate();

    //   @Scheduled(fixedRate = 1000)
    public void addVehicle() {
        String url = "http://localhost:8080/addVehicle";

        String makeModel = RandomStringUtils.randomAlphabetic(5, 10);
        int year = RandomUtils.nextInt(1986, 2017);
        int retailPrice = RandomUtils.nextInt(15000, 45001);
        Vehicle v = new Vehicle(makeModel, year, retailPrice);
        restTemplate.postForLocation(url, v);
        System.out.println(v.toString());
    }

    // @Scheduled(fixedRate = 2000)
    public void deleteVehicle() throws Exception {
        int randomId = RandomUtils.nextInt(1, 23);
        String preUrl = "http://localhost:8080/getVehicle/" + randomId;
        Vehicle v = restTemplate.getForObject(preUrl, Vehicle.class);
        if (v == null) {
            System.out.println("Didn't find it");
        } else {
            System.out.println(v.toString());
            String url = "http://localhost:8080/deleteVehicle/" + randomId;
            System.out.println(url);
            restTemplate.delete(url);
        }

    }

//    @Scheduled(fixedRate = 1000)
    public void updateVehicle() throws Exception {
        int randomId = RandomUtils.nextInt(1, 23);
        String preUrl = "http://localhost:8080/getVehicle/" + randomId;
        Vehicle v = restTemplate.getForObject(preUrl, Vehicle.class);
        if (v == null) {
            System.out.println("Didn't find it");
        } else {

            String postUrl = "http://localhost:8080/updateVehicle";
            String makeModel = "UpdateExample";
            System.out.println(randomId);
//            Vehicle vnew = new Vehicle(randomId, makeModel, 1111, 111111);
            v.setMakeModel(makeModel);
            v.setYear(1111);
            v.setRetailPrice(11111);
            restTemplate.put(postUrl, v);
        }
    }

    //  @Scheduled(fixedRate = 1000)
    public void reportVehicle() throws Exception {

        List<Vehicle> list = new ArrayList<>();

        String Url = "http://localhost:8080/getLatestVehicles";

        list = restTemplate.getForObject(Url, List.class);


        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

    }
//      @Scheduled(fixedRate = 2000)
    public void annual() throws Exception {

        List<Vehicle> list = new ArrayList<>();

        String Url = "http://localhost:8080/annualDevaluationVehicle";

        list = restTemplate.getForObject(Url, List.class);


        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

    }

//     @Scheduled(fixedRate = 10000)
    public void tenOrOlder() throws Exception {

        List<Vehicle> list = new ArrayList<>();

        String Url = "http://localhost:8080/tenYearOrOlderDiscountVehicle";

        list = restTemplate.getForObject(Url, List.class);


        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

    }


}
