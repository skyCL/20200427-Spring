package com.chen.junxiong.model;

import lombok.Data;

import java.util.List;

/**
 * @author junxiong.chen
 * @date 4/27
 */
@Data
public class CheckinRequest {

    private List<Passenger> passengers;
    private FlightInfo flightInfo;
}
