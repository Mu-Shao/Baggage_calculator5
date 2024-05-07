package com.cug.baggage_calculator.vo;

import lombok.Data;

import java.util.List;

@Data
public class BaggageDetailVo {
    private List<BaggageVo> normal;

    private List<BaggageVo> special;

    public List<BaggageVo> getNormal() {
        return normal;
    }

    public void setNormal(List<BaggageVo> normal) {
        this.normal = normal;
    }

    public List<BaggageVo> getSpecial() {
        return special;
    }

    public void setSpecial(List<BaggageVo> special) {
        this.special = special;
    }
}
