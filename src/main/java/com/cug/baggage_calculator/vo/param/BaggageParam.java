package com.cug.baggage_calculator.vo.param;

import com.cug.baggage_calculator.vo.BaggageVo;
import lombok.Data;

import java.util.List;

@Data
public class BaggageParam {
    private String flightType;

    private String flightArea;

    private String seatType;

    private String peopleType;

    private String vipType;

    private Double ticketPrice;

    private String isDisability;

    private List<BaggageVo> normalBaggage;

    private List<BaggageVo> specialBaggage;

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getFlightArea() {
        return flightArea;
    }

    public void setFlightArea(String flightArea) {
        this.flightArea = flightArea;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(String peopleType) {
        this.peopleType = peopleType;
    }

    public String getVipType() {
        return vipType;
    }

    public void setVipType(String vipType) {
        this.vipType = vipType;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getIsDisability() {
        return isDisability;
    }

    public void setIsDisability(String isDisability) {
        this.isDisability = isDisability;
    }

    public List<BaggageVo> getNormalBaggage() {
        return normalBaggage;
    }

    public void setNormalBaggage(List<BaggageVo> normalBaggage) {
        this.normalBaggage = normalBaggage;
    }

    public List<BaggageVo> getSpecialBaggage() {
        return specialBaggage;
    }

    public void setSpecialBaggage(List<BaggageVo> specialBaggage) {
        this.specialBaggage = specialBaggage;
    }
}
