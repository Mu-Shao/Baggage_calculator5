package com.cug.baggage_calculator.vo;

import java.util.List;

public class SpecialBaggageType {
    private List<String> freeSpecialBaggage1;
    private List<String> freeSpecialBaggage2;
    private List<String> freeSpecialBaggage3;

    private List<String> sportsSpecialBaggage1;
    private List<String> sportsSpecialBaggage2;
    private List<String> sportsSpecialBaggage3;

    private List<String> othersSpecialBaggage1;
    private List<String> othersSpecialBaggage2;
    private List<String> othersSpecialBaggage3;
    private List<String> othersSpecialBaggage4;
    private List<String> othersSpecialBaggage5;

    public List<String> getFreeSpecialBaggage1() {
        return freeSpecialBaggage1;
    }

    public void setFreeSpecialBaggage1(List<String> freeSpecialBaggage1) {
        freeSpecialBaggage1.add("手动轮椅");
        freeSpecialBaggage1.add("电动轮椅");
        this.freeSpecialBaggage1 = freeSpecialBaggage1;
    }

    public List<String> getFreeSpecialBaggage2() {
        return freeSpecialBaggage2;
    }

    public void setFreeSpecialBaggage2(List<String> freeSpecialBaggage2) {
        freeSpecialBaggage2.add("婴儿车");
        freeSpecialBaggage2.add("摇篮");
        this.freeSpecialBaggage2 = freeSpecialBaggage2;
    }

    public List<String> getFreeSpecialBaggage3() {
        return freeSpecialBaggage3;
    }

    public void setFreeSpecialBaggage3(List<String> freeSpecialBaggage3) {
        freeSpecialBaggage3.add("导盲犬");
        freeSpecialBaggage3.add("骨灰");
        this.freeSpecialBaggage3 = freeSpecialBaggage3;
    }

    public List<String> getSportsSpecialBaggage1() {
        return sportsSpecialBaggage1;
    }

    public void setSportsSpecialBaggage1(List<String> sportsSpecialBaggage1) {
        sportsSpecialBaggage1.add("自行车");
        this.freeSpecialBaggage2 = sportsSpecialBaggage1;
    }

    public List<String> getSportsSpecialBaggage2() {
        return sportsSpecialBaggage2;
    }

    public void setSportsSpecialBaggage2(List<String> sportsSpecialBaggage2) {
        sportsSpecialBaggage2.add("皮划艇");
        this.sportsSpecialBaggage2 = sportsSpecialBaggage2;
    }

    public List<String> getSportsSpecialBaggage3() {
        return sportsSpecialBaggage3;
    }

    public void setSportsSpecialBaggage3(List<String> sportsSpecialBaggage3) {
        sportsSpecialBaggage3.add("撑杆");
        this.sportsSpecialBaggage3 = sportsSpecialBaggage3;
    }

    public List<String> getOthersSpecialBaggage1() {
        return othersSpecialBaggage1;
    }

    public void setOthersSpecialBaggage1(List<String> othersSpecialBaggage1) {
        othersSpecialBaggage1.add("睡袋");
        this.othersSpecialBaggage1 = othersSpecialBaggage1;
    }

    public List<String> getOthersSpecialBaggage2() {
        return othersSpecialBaggage2;
    }

    public void setOthersSpecialBaggage2(List<String> othersSpecialBaggage2) {
        othersSpecialBaggage2.add("小型电器或仪器");
        this.othersSpecialBaggage2 = othersSpecialBaggage2;
    }

    public List<String> getOthersSpecialBaggage3() {
        return othersSpecialBaggage3;
    }

    public void setOthersSpecialBaggage3(List<String> othersSpecialBaggage3) {
        othersSpecialBaggage3.add("可作为运输的枪支");
        this.othersSpecialBaggage3 = othersSpecialBaggage3;
    }

    public List<String> getOthersSpecialBaggage4() {
        return othersSpecialBaggage4;
    }

    public void setOthersSpecialBaggage4(List<String> othersSpecialBaggage4) {
        othersSpecialBaggage4.add("可作为行李运输的弹药");
        this.othersSpecialBaggage4 = othersSpecialBaggage4;
    }

    public List<String> getOthersSpecialBaggage5() {
        return othersSpecialBaggage5;
    }

    public void setOthersSpecialBaggage5(List<String> othersSpecialBaggage5) {
        othersSpecialBaggage5.add("小动物");
        this.othersSpecialBaggage5 = othersSpecialBaggage5;
    }
}
