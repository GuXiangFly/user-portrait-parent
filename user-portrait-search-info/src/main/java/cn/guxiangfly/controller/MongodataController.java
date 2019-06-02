package cn.guxiangfly.controller;

import cn.guxiangfly.entity.AnalyResult;
import cn.guxiangfly.service.MongoDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guxiang
 * /**
 *  * 年代：yearbasestatics
 *  终端偏好：usetypestatics
 *  邮件运营商：emailstatics
 *  消费水平：consumptionlevelstatics
 *  潮男潮女：chaoManAndWomenstatics
 *  手机运营商：carrierstatics
 *  品牌偏好：brandlikestatics
 *
 */
@RestController
@RequestMapping("yearBase")
public class MongodataController {

    @Autowired
    private MongoDataServiceImpl mongoDataServiceImpl;


    @RequestMapping(value = "searchYearBase",method = RequestMethod.POST)
    public List<AnalyResult> searchYearBase() {
        List<AnalyResult> list = new ArrayList<AnalyResult>();
        AnalyResult analyResult = new AnalyResult();
        //40年代，50年代，60年代，70年代，80年代，90年代，00年代 10后
        analyResult.setCount(50L);
        analyResult.setInfo("40年代");
        list.add(analyResult);
        analyResult = new AnalyResult();
        analyResult.setCount(60L);
        analyResult.setInfo("50年代");
        list.add(analyResult);
        analyResult = new AnalyResult();
        analyResult.setCount(100L);
        analyResult.setInfo("60年代");
        list.add(analyResult);
        analyResult = new AnalyResult();
        analyResult.setCount(90L);
        analyResult.setInfo("70年代");
        list.add(analyResult);
        analyResult = new AnalyResult();
        analyResult.setCount(500L);
        analyResult.setInfo("80年代");
        list.add(analyResult);
        analyResult = new AnalyResult();
        analyResult.setCount(600L);
        analyResult.setInfo("90年代");
        list.add(analyResult);
        analyResult = new AnalyResult();
        analyResult.setCount(300L);
        analyResult.setInfo("00年代");
        list.add(analyResult);
        analyResult = new AnalyResult();
        analyResult.setCount(70L);
        analyResult.setInfo("10后");
        list.add(analyResult);

        return list;
    }

    @RequestMapping(value = "searchUseType",method = RequestMethod.POST)
    public List<AnalyResult> searchUseType(){
        List<AnalyResult> list = new ArrayList<AnalyResult>();
        AnalyResult analyResult = new AnalyResult();
        //pc端，小程序端，移动端
        analyResult.setCount(50l);
        analyResult.setInfo("pc端");
        list.add(analyResult);

        analyResult = new AnalyResult();
        analyResult.setCount(60l);
        analyResult.setInfo("小程序端");
        list.add(analyResult);

        analyResult = new AnalyResult();
        analyResult.setCount(40l);
        analyResult.setInfo("移动端");
        list.add(analyResult);

        return list;
    }
}
