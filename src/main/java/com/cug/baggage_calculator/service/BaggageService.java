package com.cug.baggage_calculator.service;

import com.cug.baggage_calculator.vo.BaggageVo;
import com.cug.baggage_calculator.vo.Result;
import com.cug.baggage_calculator.vo.param.BaggageParam;

import java.util.List;


public interface BaggageService {
    /**
     * 计算国航的行李费用
     */
    Result CalBaggagePrice(BaggageParam baggageParam);

    Result Cal_inland_normal(List<BaggageVo> baggageVos, String peopleType, String vipType, String seatType, double ticketPrice);

    Double Cal_inland_special(List<BaggageVo> baggageVos, Boolean isDisability, String peopleType, double ticketPrice);

    Result Cal_outland_normal(List<BaggageVo> baggageVos, String seatType, String peopelType, String fightArea, double ticketPrice);

    Result Cal_outland_special(List<BaggageVo> baggageVos, Boolean isDisability, String peopleType, double ticketPrice);
}
