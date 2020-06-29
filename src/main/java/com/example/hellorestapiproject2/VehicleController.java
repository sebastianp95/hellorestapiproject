package com.example.hellorestapiproject2;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@RestController
public class VehicleController {

    @Autowired
    private VehicleDAO vehicleDao;


//    @Timed
//    @Log
//    @CheckYear
    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) throws IOException {
        Vehicle newGreeting = new Vehicle(vehicle.getMakeModel(), vehicle.getYear(), vehicle.getRetailPrice());
        System.out.println(newGreeting.toString());
        vehicleDao.create(newGreeting);
        return newGreeting;
    }


    @Timed
    @Log
    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {
        return vehicleDao.getById(id);
    }

//    @CheckYear
//    @Timed
//    @Log
//    @CheckPrice
    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle g) throws Exception {
        Vehicle v = vehicleDao.getById(g.getId());
        if (v == null)
            return null;
        else {
            v.setMakeModel(g.getMakeModel());
            v.setYear(g.getYear());
            v.setRetailPrice(g.getRetailPrice());
            vehicleDao.update(v);
        }

        return v;
    }

//    @Timed
//    @Log
    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public void deleteVehicle(@PathVariable int id) throws Exception {
        vehicleDao.delete(id);
    }

    @Timed
    @Log
    @RequestMapping(value = "/getLatestVehicles", method = RequestMethod.GET)
    public List<Vehicle> getLatestVehicles() throws Exception {
        return vehicleDao.getLastTen();

    }
    @CheckYear
    @Timed
    @Log
    @CheckPrice
    @RequestMapping(value = "/annualDevaluationVehicle", method = RequestMethod.GET)
    public List<Vehicle> annualDevaluationVehicle() throws Exception {
            List<Vehicle> list = vehicleDao.annualDevaluation();
        int newPrice;

        for (Vehicle s: list ) {
            System.out.println( s.toString());
            newPrice = (int) (s.getRetailPrice() - s.getRetailPrice() * 0.01);
            s.setRetailPrice(newPrice);
            vehicleDao.update(s);
        }
        return list;
    }

    @Timed
    @Log
    @CheckPrice
    @RequestMapping(value = "/tenYearOrOlderDiscountVehicle", method = RequestMethod.GET)
    public List<Vehicle> tenYearOrOlderDiscountVehicle() throws Exception {
        List<Vehicle> list = vehicleDao.tenYearOrOlderDiscount();
        int newPrice;

        for (Vehicle s: list ) {
            System.out.println( s.toString());
            newPrice = (int) (s.getRetailPrice() - s.getRetailPrice() * 0.10);
            s.setRetailPrice(newPrice);
            vehicleDao.update(s);
        }
        return list;
    }


}

