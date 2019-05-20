package cn.guxiangfly.task;

import cn.guxiangfly.entity.YearBase;
import cn.guxiangfly.map.YearBaseMap;
import cn.guxiangfly.reduce.YearBaseReduce;
import cn.guxiangfly.util.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;


import java.util.List;

public class YearBaseTask {
    public static void main(String[] args) {
        final ParameterTool params = ParameterTool.fromArgs(args);
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setGlobalJobParameters(params);
        // get input data
        DataSet<String> text = env.readTextFile(params.get("input"));
        DataSet<YearBase> mapResult = text.map(new YearBaseMap());
        DataSet<YearBase> reduceResult = mapResult.reduce(new YearBaseReduce());
        try {
            List<YearBase> reusltlist = reduceResult.collect();
            for(YearBase yearBase:reusltlist){
                String yeartype = yearBase.getYeartype();
                Long count = yearBase.getCount();

                Document doc = MongoUtils.findoneby("yearbasestatics","youfanPortrait",yeartype);
                if(doc == null){
                    doc = new Document();
                    doc.put("info",yeartype);
                    doc.put("count",count);
                }else{
                    Long countpre = doc.getLong("count");
                    Long total = countpre+count;
                    doc.put("count",total);
                }
                MongoUtils.saveorupdatemongo("yearbasestatics","youfanPortrait",doc);
            }
            env.execute("year base analy");
        }catch (Exception e){

        }
    }
}
