package cn.guxiangfly.map;

import cn.guxiangfly.entity.YearBase;
import cn.guxiangfly.util.DateUtils;
import cn.guxiangfly.util.HbaseUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.time.Year;

public class YearBaseMap implements MapFunction<String, YearBase> {

    @Override
    public YearBase map(String s) throws Exception {
        if (StringUtils.isEmpty(s)){
            return null;
        }
        String[] userinfos = s.split(",");
        String userid = userinfos[0];
        String username = userinfos[1];
        String sex = userinfos[2];
        String telphone = userinfos[3];
        String email = userinfos[4];
        String age = userinfos[5];
        String registerTime = userinfos[6];
        /*'终端类型：0、pc端；1、移动端；2、小程序端'*/
        String usetype = userinfos[7];

        String yearbasetype = DateUtils.getYearbasebyAge(age);
        String tablename = "userflaginfo";
        String rowkey = userid;
        String famliyname = "baseinfo";
        //年代
        String colum = "yearbase";
        HbaseUtils.putdata(tablename,rowkey,famliyname,colum,yearbasetype);
        HbaseUtils.putdata(tablename,rowkey,famliyname,"age",age);
        YearBase yearBase = new YearBase();
        String groupfield = "yearbase=="+yearbasetype;
        yearBase.setYeartype(yearbasetype);
        yearBase.setCount(1l);
        yearBase.setGroupfield(groupfield);
        return yearBase;
    }
}
