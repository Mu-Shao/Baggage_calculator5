package com.cug.baggage_calculator.service.Impl;

import com.cug.baggage_calculator.service.BaggageService;
import com.cug.baggage_calculator.vo.BaggageDetailVo;
import com.cug.baggage_calculator.vo.BaggageVo;
import com.cug.baggage_calculator.vo.Result;
import com.cug.baggage_calculator.vo.SpecialBaggageType;
import com.cug.baggage_calculator.vo.param.BaggageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BaggageServiceImpl implements BaggageService {

    private SpecialBaggageType specialBaggageType;

    @Override
    public Result CalBaggagePrice(BaggageParam baggageParam) {

        String flightType = baggageParam.getFlightType();
        String flightArea = baggageParam.getFlightArea();
        String peopleType = baggageParam.getPeopleType();
        String seatType = baggageParam.getSeatType();
        String vipType = baggageParam.getVipType();
        Boolean isDisability = Boolean.parseBoolean(baggageParam.getIsDisability());
        Double ticketPrice = baggageParam.getTicketPrice();
        List<BaggageVo> baggageNormals = baggageParam.getNormalBaggage();
        List<BaggageVo> baggageSpecials = baggageParam.getSpecialBaggage();


        String message = "";
        Double price = 0.0;

        if (ticketPrice < 0 || ticketPrice > 50000){
            message = message.concat("票价输入错误，请重新输入\n") ;
        }

        /**
         * ------------------------- 国内航线 ---------------------------
         */
        if (flightType.equals("inland")) {

//          计算国内航线的普通行李的价格
            Result result = Cal_inland_normal(baggageNormals, peopleType, vipType, seatType, ticketPrice);

//          计算国内航线特殊行李费用
            price = Cal_inland_special(baggageSpecials, isDisability, peopleType, ticketPrice);

//           计算普通行李的价格和特殊行李的价格
            price = price + result.getPrice();
            result.setPrice(price);

            return result;

        }
        /**
         * ------------------------- 国际航线 ---------------------------
         */
        else {
            System.out.println("outland");
//          计算国际地区的普通行李的价格
            Result result = Cal_outland_normal(baggageNormals, seatType, peopleType, flightArea, ticketPrice);

//          计算国际地区的特殊行李的价格
            Result result1 = Cal_outland_special(baggageSpecials, isDisability, peopleType, ticketPrice);
//          计算普通和特殊行李的价格
            price = result.getPrice() + result1.getPrice();
            message = result.getMessage() + result1.getMessage();

            return Result.success(message, price);
        }
    }

    @Override
    public Result Cal_inland_normal(List<BaggageVo> baggageVos, String peopleType, String vipType, String seatType, double ticketPrice) {

        // 计算免费托运额度
        int weightLimit = 0;
        // 计算随身行李限额
        int carryOnLimit = 0;
        // 成人或儿童
        if (peopleType.equals("adult") || peopleType.equals("child")) {

            switch (seatType) {
                case "头等舱":
                    weightLimit = 40;
                    carryOnLimit = 2;
                    break;
                case "公务舱":
                    weightLimit = 30;
                    carryOnLimit = 2;
                    break;
                case "经济舱":
                    weightLimit = 20;
                    carryOnLimit = 1;
                    break;
            }
        }
        // 婴儿
        else if (peopleType.equals("infant")) {
            weightLimit = 10;
            switch (seatType) {
                case "头等舱":
                case "公务舱":
                    carryOnLimit = 2;
                    break;
                case "经济舱":
                    carryOnLimit = 1;
                    break;
            }
        }
        // vip类型
        switch (vipType) {
            case "凤凰知音白金卡":
                weightLimit += 30;
                break;
            case "凤凰知音金卡、银卡":
            case "星空联盟金卡":
                weightLimit += 20;
                break;
        }

        String message = "";
        Double price = 0.0;

        // 计算收费价格
        double baggageWeight = 0;


        for (int i = 0, len = baggageVos.size(); i < len; i++) {
            BaggageVo baggage = baggageVos.get(i);

            // 检查普通行李尺寸是否超标
            if (baggage.getLength() > 100 || baggage.getWidth() > 60 || baggage.getHeight() > 40) {
                message = message.concat("普通行李" + (i + 1) + "存在尺寸超标的情况，请合理划分\n") ;
            }
            if (baggage.getLength() <= 0 || baggage.getWidth() <= 0 || baggage.getHeight() <= 0) {
                message = message.concat("普通行李" + (i + 1) + "尺寸输入错误，请重新输入\n") ;
            }
            if (baggage.getLength() == null || baggage.getWidth() == null || baggage.getHeight() == null) {
                message = message.concat("普通行李" + (i + 1) + "尺寸输入错误，请重新输入\n") ;
            }
            if (baggage.getWeight() == null) {
                message = message.concat("普通行李" + (i + 1) + "重量输入错误，请重新输入\n") ;
            }

            // 判断是否可以作为随身行李
            if (baggage.getLength() <= 55 && baggage.getWidth() <= 40 && baggage.getHeight() <= 20 && carryOnLimit > 0 && baggage.getType().equals("普通行李")) {
                if (((seatType.equals("头等舱")|| seatType.equals("公务舱")) && baggage.getWeight() <= 8) || (seatType.equals("经济舱") && baggage.getWeight() <= 5)) {
                    carryOnLimit--;
                    message += "普通行李" + (i + 1) + "可以作为随身行李携带，不参与计算\n";
                    continue;
                }
            }

            // 累加重量
            baggageWeight += baggage.getWeight();
        }
        price = baggageWeight > weightLimit ? (baggageWeight - weightLimit) * ticketPrice * 0.015 : 0;

        return Result.success(message, price);
    }

    @Override
    public Double Cal_inland_special(List<BaggageVo> baggageVos, Boolean isDisability, String peopleType, double ticketPrice) {

        List<String> freeSpecialBaggage1 = Arrays.asList("手动轮椅", "电动轮椅");
        List<String> freeSpecialBaggage2 = Arrays.asList("婴儿车或摇篮");
        List<String> freeSpecialBaggage3 = Arrays.asList("导盲犬", "骨灰");

        List<String> sportsSpecialBaggage1 = Arrays.asList("自行车");
        List<String> sportsSpecialBaggage2 = Arrays.asList("皮划艇");
        List<String> sportsSpecialBaggage3 = Arrays.asList("撑杆");

        List<String> othersSpecialBaggage1 = Arrays.asList("睡袋");
        List<String> othersSpecialBaggage2 = Arrays.asList("小型电器或仪器");
        List<String> othersSpecialBaggage3 = Arrays.asList("可作为行李运输的枪支");
        List<String> othersSpecialBaggage4 = Arrays.asList("可作为行李运输的弹药");
        List<String> othersSpecialBaggage5 = Arrays.asList("小动物");

        Double price = 0.0;

        for (int i = 0, len = baggageVos.size(); i < len; i++) {
            BaggageVo baggage = baggageVos.get(i);

            // ---------------- 可免费托运的行李 -----------------
            if ((isDisability && freeSpecialBaggage1.contains(baggage.getType())) ||
                    (peopleType.equals("infant") && freeSpecialBaggage2.contains(baggage.getType())) ||
                    (freeSpecialBaggage3.contains(baggage.getType()))) {
                continue;
            }
            // 非残疾或婴儿旅客托运 轮椅或婴儿床
            else if ((!isDisability || !peopleType.equals("infant")) && (freeSpecialBaggage1.contains(baggage.getType()) || freeSpecialBaggage2.contains(baggage.getType()))) {
                // 已经按照普通行李进行计算了
            }
            // ---------------- 运动器械器具1，计入免费额度 -----------------
            else if (sportsSpecialBaggage1.contains(baggage.getType())) {
                // 计入免费额度的特殊行李已经被当作普通行李计算过了
            }
            // ---------------- 运动器械器具2-3，不计入免费额度 -----------------
            else if (sportsSpecialBaggage2.contains(baggage.getType()) || sportsSpecialBaggage3.contains(baggage.getType())) {
                // 根据实际重量收费
                price += baggage.getWeight() * ticketPrice * 0.015;
            }
            // ---------------- 其他类型的特殊行李1，计入免费额度 -----------------
            else if (othersSpecialBaggage1.contains(baggage.getType())) {
                // 计入免费额度的特殊行李已经被当作普通行李计算过了
            }
            // ---------------- 其他类型的特殊行李2-5，不计入免费额度 -----------------
            else if (othersSpecialBaggage2.contains(baggage.getType()) ||
                    othersSpecialBaggage3.contains(baggage.getType()) ||
                    othersSpecialBaggage4.contains(baggage.getType()) ||
                    othersSpecialBaggage5.contains(baggage.getType())) {
                // 根据实际重量收费
                price += baggage.getWeight() * ticketPrice * 0.015;
            }
        }
        return price;
    }

    @Override
    public Result Cal_outland_normal(List<BaggageVo> baggageVos, String seatType, String peopleType, String flightArea, double ticketPrice) {

        // 五个区域 超重且超尺寸 收费情况
        int[] weightAndSize = new int[]{0, 1400, 1100, 520, 2050, 830};
        // 五个区域 不超重但超尺寸 收费情况
        int[] noWeightButSize = new int[]{0, 980, 690, 520, 1040, 520};
        // 五个区域 超重量(28, 32]但不超尺寸 收费情况
        int[] weight28To32ButNoSize = new int[]{0, 980, 690, 520, 1040, 520};
        // 五个区域 超重量(23, 28]但不超尺寸 收费情况
        int[] weight23To28ButNoSize = new int[]{0, 380, 280, 520, 690, 210};
        // 五个区域 行李件数超出 收费情况
        int[][] exceedBaggage = new int[][]{{0, 1400, 1100, 1170, 1380, 830}, {0, 2000, 1100, 1170, 1380, 1100}, {0, 3000, 1590, 1590, 1590, 1590}};

        // 计算随身行李限额
        int carryOnLimit = 0;
        if (seatType.equals("头等、公务舱")) {
            carryOnLimit = 2;
        }
        else {
            carryOnLimit = 1;
        }

        String message = "";
        Double price = 0.0;

        // 遍历行李，检查 重量和尺寸 是否需要收费
        for (int i = 0, len = baggageVos.size(); i < len; i++) {

            BaggageVo baggage = baggageVos.get(i);
            Double baggageSize = baggage.getLength() + baggage.getWidth() + baggage.getHeight();

            // 检查普通行李尺寸或重量是否合理
            if (baggageSize < 60 || baggageSize > 203 || baggage.getWeight() < 2 || baggage.getWeight() > 32) {
                if (baggage.getType().equals("普通行李")) {
                    message += "普通行李" + (i + 1) + "存在尺寸或重量不合理的情况，请合理划分\n";
                }
            }

            if (baggage.getLength() <= 0 || baggage.getWidth() <= 0 || baggage.getHeight() <= 0) {
                message = message.concat("普通行李" + (i + 1) + "尺寸输入错误，请重新输入\n") ;
            }

            if (baggage.getLength() == null || baggage.getWidth() == null || baggage.getHeight() == null) {
                message = message.concat("普通行李" + (i + 1) + "尺寸输入错误，请重新输入\n") ;
            }
            if (baggage.getWeight() == null) {
                message = message.concat("普通行李" + (i + 1) + "重量输入错误，请重新输入\n") ;
            }

            // 判断是否可以作为随身行李
            if (baggage.getLength() <= 55 && baggage.getWidth() <= 40 && baggage.getHeight() <= 20 && carryOnLimit > 0 && baggage.getType().equals("普通行李")){
                if (seatType.equals("头等、公务舱") && baggage.getWeight() <= 8) {
                    carryOnLimit--;
                    message += "普通行李" + (i + 1) + "可以作为随身行李携带，不参与计算\n";
                    continue;
                }
                else if (baggage.getWeight() <= 5) {
                    carryOnLimit--;
                    message += "普通行李" + (i + 1) + "可以作为随身行李携带，不参与计算\n";
                    continue;
                }
            }

            // 检查 重量和尺寸 是否需要收费(只有非头等、公务舱才会超重)
            if (baggageSize > 158 && baggage.getType().equals("普通行李")) {
                // 超尺寸
                if (baggage.getWeight() > 23 && !seatType.equals("头等、公务舱")) {       // 超重+超尺寸
                    price += weightAndSize[Integer.parseInt(flightArea)];
                }
                else {  // 没有超重
                    price += noWeightButSize[Integer.parseInt(flightArea)];
                }
            }
            else {
                // 没有超尺寸
                if (baggage.getWeight() > 28 && !seatType.equals("头等、公务舱")) {       // 超重 (28, 32]
                    price += weight28To32ButNoSize[Integer.parseInt(flightArea)];
                }
                else if (baggage.getWeight() > 23 && !seatType.equals("头等、公务舱")) {  // 超重 (23, 28]
                    price += weight23To28ButNoSize[Integer.parseInt(flightArea)];
                }
            }

        } // !行李遍历for循环结束

        // 检查 件数 是否需要收费
        int baggageNumLimit = 0;
        if (seatType.equals("头等、公务舱")) {
            if (peopleType.equals("adult") || peopleType.equals("child")) {
                baggageNumLimit = 2;
            }
            else {
                baggageNumLimit = 1;
            }
        }
        else if (seatType.equals("悦享经济舱、超级经济舱")) {
            baggageNumLimit = 2;
        }
        else if (seatType.equals("经济舱（区域A）")) {
            baggageNumLimit = 1;
        }
        else if (seatType.equals("经济舱（区域B）")) {
            baggageNumLimit = 2;
        }
        else {
            baggageNumLimit = 0;
        }

        int len = baggageVos.size() - baggageNumLimit;
        for (int i = 0; i < len; i++) {
            if (i < exceedBaggage.length) {
                // 首先确定这是超出的第几件，然后确定区域
                price += exceedBaggage[i][Integer.parseInt(flightArea)];
            }
            else {
                // 超出三件以上，按照最高的标准收费
                price += exceedBaggage[exceedBaggage.length - 1][Integer.parseInt(flightArea)];
            }
        }
        return Result.success(message, price);

    }

    @Override
    public Result Cal_outland_special(List<BaggageVo> baggageVos, Boolean isDisability, String peopleType, double ticketPrice) {

        List<String> freeSpecialBaggage1 = Arrays.asList("手动轮椅", "电动轮椅");
        List<String> freeSpecialBaggage2 = Arrays.asList("婴儿车或摇篮");
        List<String> freeSpecialBaggage3 = Arrays.asList("导盲犬", "骨灰");

        List<String> sportsSpecialBaggage1 = Arrays.asList("自行车");
        List<String> sportsSpecialBaggage2 = Arrays.asList("皮划艇");
        List<String> sportsSpecialBaggage3 = Arrays.asList("撑杆");

        List<String> othersSpecialBaggage1 = Arrays.asList("睡袋");
        List<String> othersSpecialBaggage2 = Arrays.asList("小型电器或仪器");
        List<String> othersSpecialBaggage3 = Arrays.asList("可作为行李运输的枪支");
        List<String> othersSpecialBaggage4 = Arrays.asList("可作为行李运输的弹药");
        List<String> othersSpecialBaggage5 = Arrays.asList("小动物");

        String message = "";
        Double price = 0.0;

        for (int i = 0; i < baggageVos.size(); i++) {
            BaggageVo baggage = baggageVos.get(i);
//            if (baggage.getLength() <= 0 || baggage.getWidth() <= 0 || baggage.getHeight() <= 0) {
//                message = message.concat("普通行李" + (i + 1) + "尺寸输入错误，请重新输入\n") ;
//            }
            if (baggage.getLength() == null || baggage.getWidth() == null || baggage.getHeight() == null) {
                message = message.concat("普通行李" + (i + 1) + "尺寸输入错误，请重新输入\n") ;
            }
            if (baggage.getWeight() == null) {
                message = message.concat("普通行李" + (i + 1) + "重量输入错误，请重新输入\n") ;
            }
            // ---------------- 可免费托运的行李 -----------------
            if ((isDisability && freeSpecialBaggage1.contains(baggage.getType())) ||
                    (peopleType.equals("infant") && freeSpecialBaggage2.contains(baggage.getType())) ||
                    (freeSpecialBaggage3.contains(baggage.getType()))) {
                continue;
            }
            // 非残疾或婴儿旅客托运 轮椅或婴儿床
            else if ((!isDisability || peopleType.equals("infant")) && (freeSpecialBaggage1.contains(baggage.getType()) || freeSpecialBaggage2.contains(baggage.getType()))) {
                // 已经按照普通行李进行计算了
            }
            // ---------------- 运动器械器具，计入免费额度 -----------------
            else if (sportsSpecialBaggage1.contains(baggage.getType())) {
                // 计入免费额度的特殊行李已经被当作普通行李计算过了
            }
            // ---------------- 运动器械器具，不计入免费额度 -----------------
            else if (sportsSpecialBaggage2.contains(baggage.getType())) {
                if (baggage.getWeight() < 2) {
                    message += "特殊行李" + (i + 1) + "存在重量不合理的情况，请合理划分\n";
                } else if (baggage.getWeight() > 45) {
                    message += "特殊行李" + (i + 1) + "存在重量不合理的情况，请合理划分\n";

                    price += 5200;
                } else if (baggage.getWeight() > 32) {
                    price += 5200;
                } else if (baggage.getWeight() > 23) {
                    price += 3900;
                } else if (baggage.getWeight() >= 2) {
                    price += 2600;
                }
            }
            else if (sportsSpecialBaggage3.contains(baggage.getType())) {
                if (baggage.getWeight() < 2) {
                    message += "特殊行李" + (i + 1) + "存在重量不合理的情况，请合理划分\n";
                } else if (baggage.getWeight() > 45) {
                    message += "特殊行李" + (i + 1) + "存在重量不合理的情况，请合理划分\n";

                    price += 3900;
                } else if (baggage.getWeight() > 32) {
                    price += 3900;
                } else if (baggage.getWeight() > 23) {
                    price += 2600;
                } else if (baggage.getWeight() >= 2) {
                    price += 1300;
                }
            }
            // ---------------- 其他类型的特殊行李，计入免费额度 -----------------
            else if (othersSpecialBaggage1.contains(baggage.getType())) {
                // 计入免费额度的特殊行李已经被当作普通行李计算过了
            }
            // ---------------- 其他类型的特殊行李，不计入免费额度 -----------------
            else if (othersSpecialBaggage2.contains(baggage.getType())) {
                if (baggage.getWeight() < 2) {
                    message += "特殊行李" + (i + 1) + "存在重量不合理的情况，请合理划分\n";
                } else if (baggage.getWeight() > 32) {
                    message += "特殊行李" + (i + 1) + "存在重量不合理的情况，请合理划分\n";

                    price += 3900;
                } else if (baggage.getWeight() > 23) {
                    price += 3900;
                } else if (baggage.getWeight() >= 2) {
                    price += 490;
                }
            }
            else if (othersSpecialBaggage3.contains(baggage.getType())) {
                if (baggage.getWeight() < 2) {
                    message += "特殊行李" + (i + 1) + "存在重量不合理的情况，请合理划分\n";
                } else if (baggage.getWeight() > 32) {
                    message += "特殊行李" + (i + 1) + "存在重量不合理的情况，请合理划分\n";

                    price += 2600;
                } else if (baggage.getWeight() > 23) {
                    price += 2600;
                } else if (baggage.getWeight() >= 2) {
                    price += 1300;
                }
            }
            else if (othersSpecialBaggage4.contains(baggage.getType())) {
                if (baggage.getWeight() < 2) {
                    message += "特殊行李" + (i + 1) + "存在重量不合理的情况，请合理划分\n";
                } else if (baggage.getWeight() > 5) {
                    message += "特殊行李" + (i + 1) + "存在重量不合理的情况，请合理划分\n";

                    price += 1300;
                } else if (baggage.getWeight() >= 2) {
                    price += 1300;
                }
            }
            else if (othersSpecialBaggage5.contains(baggage.getType())) {
                if (baggage.getWeight() < 2) {
                    message += "特殊行李" + (i + 1) + "存在重量不合理的情况，请合理划分\n";
                } else if (baggage.getWeight() > 32) {
                    message += "特殊行李" + (i + 1) + "存在重量不合理的情况，请合理划分\n";

                    price += 7800;
                } else if (baggage.getWeight() > 23) {
                    price += 7800;
                } else if (baggage.getWeight() > 8) {
                    price += 5200;
                } else if (baggage.getWeight() >= 2) {
                    price += 3900;
                }
            }
        } return Result.success(message, price);
    }

}
