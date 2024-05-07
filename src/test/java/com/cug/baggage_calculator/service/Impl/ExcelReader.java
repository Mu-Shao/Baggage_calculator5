package com.cug.baggage_calculator.service.Impl;

import com.cug.baggage_calculator.vo.BaggageVo;
import com.cug.baggage_calculator.vo.param.BaggageParam;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    public List<BaggageParam> readExcel(String filePath) throws IOException {
        List<BaggageParam> baggageParams = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0); // 假设数据在第一个表单中

        Iterator<Row> rowIterator = sheet.iterator();
        // 跳过表头
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            BaggageParam baggageParam = createBaggageParamFromRow(row);
            if (baggageParam != null) {
                baggageParams.add(baggageParam);
            }
        }

        workbook.close();
        fileInputStream.close();

        return baggageParams;
    }

    private BaggageParam createBaggageParamFromRow(Row row) {
        BaggageParam baggageParam = new BaggageParam();

        // 从单元格中读取数据并设置到 baggageParam 中
        baggageParam.setFlightType(getStringCellValue(row.getCell(1)));
        baggageParam.setFlightArea(getStringCellValue(row.getCell(2)));
        baggageParam.setSeatType(getStringCellValue(row.getCell(3)));
        baggageParam.setPeopleType(getStringCellValue(row.getCell(4)));
        baggageParam.setVipType(getStringCellValue(row.getCell(5)));
        baggageParam.setTicketPrice(getNumericCellValue(row.getCell(6)));
        baggageParam.setIsDisability(getStringCellValue(row.getCell(7)));

        // 创建普通行李列表
        List<BaggageVo> normalBaggage = new ArrayList<>();
        BaggageVo baggage1 = new BaggageVo();
        baggage1.setType(getStringCellValue(row.getCell(8)));
        baggage1.setLength(getNumericCellValue(row.getCell(9)));
        baggage1.setWidth(getNumericCellValue(row.getCell(10)));
        baggage1.setHeight(getNumericCellValue(row.getCell(11)));
        baggage1.setWeight(getNumericCellValue(row.getCell(12)));
        normalBaggage.add(baggage1);
        baggageParam.setNormalBaggage(normalBaggage);

        // 创建特殊行李列表
        List<BaggageVo> specialBaggage = new ArrayList<>();
        BaggageVo baggage2 = new BaggageVo(); // 这里假设只有一种特殊行李
        // 设置特殊行李的属性
        // ...
        specialBaggage.add(baggage2);
        baggageParam.setSpecialBaggage(specialBaggage);

        return baggageParam;
    }

    private String getStringCellValue(Cell cell) {
        if (cell != null) {
            cell.setCellType(CellType.STRING);
            return cell.getStringCellValue();
        }
        return null;
    }

    private Double getNumericCellValue(Cell cell) {
        if (cell != null) {
            cell.setCellType(CellType.NUMERIC);
            return cell.getNumericCellValue();
        }
        return null;
    }

    // BaggageParam 类和 BaggageVo 类的定义和设置方法需要根据你的实际情况进行修改

}
