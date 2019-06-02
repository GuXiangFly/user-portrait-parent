package cn.guxiangfly.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guxiang
 */
@RestController
@RequestMapping("hbaseData")
public class HbaseDataController {

    @RequestMapping(value = "baiJiaZhiShuInfo",method = RequestMethod.POST)
    public String baiJiaZhiShuInfo(String userid){
        String result = "";
//        String tablename = "userflaginfo";
//        String rowkey = userid;
//        String famliyname = "baseinfo";
//        String colum = "baijiasoce";
//        try {
//            result = HbaseServiceImpl.getdata(tablename,rowkey,famliyname,colum);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        result = "属于中等败家(56)";
        return result;
    }
}
