package com.expert.xybs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expert.xybs.mapper.BaseDaoMapper;
import com.expert.xybs.mapper.PaperTitleSubjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PaperTitleSubjectService {
    @Autowired
    private BaseDaoMapper baseDaoMapper;
    @Autowired
    private PaperTitleSubjectMapper paperTitleSubjectMapper;

    public Integer add(JSONObject json) {
        System.out.println("jinlai");
        String questions = json.getString("questions");
        Long paper_title_id = json.getLong("paper_title_id");
        Integer lx = json.getInteger("lx");
        String a = json.getString("a");
        String b = json.getString("b");
        String c = json.getString("c");
        String d = json.getString("d");
        String e = json.getString("e");
        String f = json.getString("f");
        String da = json.getString("da").toUpperCase();
        String analysis = json.getString("analysis");

        String SQL="INSERT INTO paper_title_subject (questions,paper_title_id,lx,a,b,c,d,e,f,da,analysis)  VALUES " +
                " ('"+questions+"',"+paper_title_id+","+lx+",'"+a+"','"+b+"','"+c+"','"+d+"','"+e+"','"+f+"','"+da+"','"+analysis+"')";
        baseDaoMapper.insertSql(SQL);
        return 0;
    }
    public Integer del(JSONObject json) {
        Long id = json.getLong("id");
        paperTitleSubjectMapper.deleteByPrimaryKey(id);
        return 0;
    }

    public JSONArray list(JSONObject json) {
        Long paper_title_id = json.getLong("paper_title_id");
        String SQL="SELECT *,f as uda from  paper_title_subject  where paper_title_id="+paper_title_id+"   ORDER BY  lx asc,id asc ";
        List<LinkedHashMap<String, Object>> linkedHashMaps = baseDaoMapper.selectSql(SQL);
        JSONArray listArray = JSON.parseArray(JSON.toJSONString(linkedHashMaps));
        return listArray;
    }


    public  String excelToShopIdList(InputStream inputStream,Long id) {
        Workbook workbook = null;
        try {
            // 根据传递过来的文件输入流创建一个workbook对象（对应Excel中的工作簿）
            workbook = WorkbookFactory.create(inputStream);
            // 创建完成，关闭输入流
            inputStream.close();
            //获取工作表对象，即第一个工作表 （工作簿里面有很多张工作表，这里取第一张工作表）
            Sheet sheet = workbook.getSheetAt(0);
            //获取工作表的总行数
//            System.out.println(sheet.getLastRowNum());
            int rowLength = sheet.getLastRowNum() + 1;
//            System.out.println("总行数有多少行" + rowLength);
            Cell cell = null;

            for (int i = 1; i < rowLength; i++) {
                JSONObject jo =new JSONObject();
                for (int k = 1; k < 8; k++) {
                    cell = sheet.getRow(i).getCell(k);
                    // 设置单元格的类型是String类型
                    cell.setCellType(CellType.STRING);
                    // 获取单元格的数据
                    String stringCellValue = cell.getStringCellValue();
                    switch (k){
                        case 1:
                            jo.put("questions",stringCellValue);
                            break;
                        case 2:
                            jo.put("lx",stringCellValue);
                            break;
                        case 3:
                            jo.put("a",stringCellValue);
                            break;
                        case 4:
                            jo.put("b",stringCellValue);
                            break;
                        case 5:
                            jo.put("c",stringCellValue);
                            break;
                        case 6:
                            jo.put("d",stringCellValue);
                            break;
                        case 7:
                            jo.put("da",stringCellValue);
                            break;
                    }
                }

                String questions = jo.getString("questions");
                Integer lx = jo.getInteger("lx");
                String a = jo.getString("a");
                String b = jo.getString("b");
                String c = jo.getString("c");
                String d = jo.getString("d");
                String da = jo.getString("da").toUpperCase();

                String SQL="INSERT INTO paper_title_subject (questions,paper_title_id,lx,a,b,c,d,da)  VALUES " +
                        " ('"+questions+"',"+id+","+lx+",'"+a+"','"+b+"','"+c+"','"+d+"','"+da+"')";
                System.out.println(SQL);
                baseDaoMapper.insertSql(SQL);

            }

        } catch (Exception e) {

        }
        return "";
    }



}
