package com.cug.baggage_calculator.service.Impl;

import com.cug.baggage_calculator.vo.BaggageVo;
import com.cug.baggage_calculator.vo.Result;
import com.cug.baggage_calculator.vo.param.BaggageParam;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

public class BaggageServiceImplTest {

    @DataProvider(name = "CalBaggagePrice")
    public Object[][] provideData() throws IOException {

        ExcelReader reader = new ExcelReader();
        List<BaggageParam> baggageParams = reader.readExcel("src/test/testNG.xlsx");

        return new Object[][] {
                {baggageParams}
        };
    }

    @Test(dataProvider = "CalBaggagePrice")
    public void testCalBaggagePrice(List<BaggageParam> baggageParams) {

        // 创建 BaggageServiceImpl 对象
        BaggageServiceImpl baggageService = new BaggageServiceImpl();

        // 调用 CalBaggagePrice 方法计算价格
        Result result = baggageService.CalBaggagePrice(baggageParams.get(0));

        // 预期结果
        Result expected = new Result(true,200, "...", (double)0);
        System.out.println("price" + result.getPrice());

        // 断言实际结果与预期结果相同
        Assert.assertEquals(result.getPrice(), expected.getPrice());

    }


    @Test(dataProvider = "CalBaggagePrice")
    public void testCal_inland_normal(List<BaggageParam> baggageParams) {
        // 创建 BaggageServiceImpl 对象
        BaggageServiceImpl baggageService = new BaggageServiceImpl();
        new BaggageParam();
        BaggageParam baggageParam;
        baggageParam = baggageParams.get(1);

        List<BaggageVo> baggageVos = baggageParam.getNormalBaggage();
        String peopleType = baggageParam.getPeopleType();
        String vipType = baggageParam.getVipType();
        String seatType = baggageParam.getSeatType();
        double ticketPrice = baggageParam.getTicketPrice();

        // 调用 CalBaggagePrice 方法计算价格
        Result result = baggageService.Cal_inland_normal(baggageVos, peopleType, vipType, seatType, ticketPrice);

        // 预期结果
        Result expected = new Result(true,200, "...", (double)337.5);

        // 断言实际结果与预期结果相同
        Assert.assertEquals(result.getPrice(), expected.getPrice());
    }

    @Test(dataProvider = "CalBaggagePrice")
    public void testCal_inland_special(List<BaggageParam> baggageParams) {
        // 创建 BaggageServiceImpl 对象
        BaggageServiceImpl baggageService = new BaggageServiceImpl();

        new BaggageParam();
        BaggageParam baggageParam;
        baggageParam = baggageParams.get(2);

        List<BaggageVo> baggageVos = baggageParam.getNormalBaggage();
        String peopleType = baggageParam.getPeopleType();
        boolean isDisability = Boolean.parseBoolean(baggageParam.getIsDisability());
        double ticketPrice = baggageParam.getTicketPrice();

        // 调用 CalBaggagePrice 方法计算价格
        double result = baggageService.Cal_inland_special(baggageVos, isDisability, peopleType, ticketPrice);

        // 预期结果
        Result expected = new Result(true,200, "...", (double)472.5);

        // 断言实际结果与预期结果相同
        Assert.assertEquals(result, expected.getPrice());
    }

    @Test(dataProvider = "CalBaggagePrice")
    public void testCal_outland_normal(List<BaggageParam> baggageParams) {
        // 创建 BaggageServiceImpl 对象
        BaggageServiceImpl baggageService = new BaggageServiceImpl();
        new BaggageParam();
        BaggageParam baggageParam;
        baggageParam = baggageParams.get(3);

        List<BaggageVo> baggageVos = baggageParam.getNormalBaggage();
        String peopleType = baggageParam.getPeopleType();
        String seatType = baggageParam.getSeatType();
        double ticketPrice = baggageParam.getTicketPrice();
        String flightArea = baggageParam.getFlightArea();

        // 调用 CalBaggagePrice 方法计算价格
        Result result = baggageService.Cal_outland_normal(baggageVos, seatType, peopleType, flightArea, ticketPrice);

        // 预期结果
        Result expected = new Result(true,200, "...", (double)520);

        // 断言实际结果与预期结果相同
        Assert.assertEquals(result.getPrice(), expected.getPrice());
    }

    @Test(dataProvider = "CalBaggagePrice")
    public void testCal_outland_special(List<BaggageParam> baggageParams) {
        // 创建 BaggageServiceImpl 对象
        BaggageServiceImpl baggageService = new BaggageServiceImpl();
        new BaggageParam();
        BaggageParam baggageParam;
        baggageParam = baggageParams.get(4);

        List<BaggageVo> baggageVos = baggageParam.getNormalBaggage();
        String peopleType = baggageParam.getPeopleType();
        double ticketPrice = baggageParam.getTicketPrice();
        boolean isDisability = Boolean.parseBoolean(baggageParam.getIsDisability());

        // 调用 CalBaggagePrice 方法计算价格
        Result result = baggageService.Cal_outland_special(baggageVos, isDisability, peopleType, ticketPrice);

        // 预期结果
        Result expected = new Result(true,200, "...", (double)3900);

        // 断言实际结果与预期结果相同
        Assert.assertEquals(result.getPrice(), expected.getPrice());
    }
}