package cn.guxiangfly.task;

import cn.guxiangfly.entity.KeyWordEntity;
import cn.guxiangfly.map.KeyWordMapfinal;
import cn.guxiangfly.map.KeywordMap;
import cn.guxiangfly.map.KeywordMap2;
import cn.guxiangfly.reduce.KeyWordReduce2;
import cn.guxiangfly.reduce.KeywordReduce;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

public class QuarterKeyWordTask {
    public static void main(String[] args) {
        final ParameterTool params = ParameterTool.fromArgs(args);
        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);

        // get input data
        DataSet<String> text = env.readTextFile(params.get("input"));

        DataSet<KeyWordEntity> mapresult = text.map(new KeywordMap());
        //这一步 是将 单个用户所有的 操作行为 reduce 到一个 document中
        DataSet<KeyWordEntity> reduceResult  = mapresult.groupBy("userid").reduce(new KeywordReduce());
        // 这一步是将所有的  没个document 的 tfmapfinal 值算出来 并且将 word 存储到 Hbase中
        DataSet<KeyWordEntity> mapresult2 = reduceResult.map(new KeywordMap2());
        // 计算 totaldocument的个数
        DataSet<KeyWordEntity> reduceresult2 = mapresult2.reduce(new KeyWordReduce2());
        Long totaldoucment = 0L;
        try {
            totaldoucment = reduceresult2.collect().get(0).getTotaldocumet();
            DataSet<KeyWordEntity> mapfinalresult = mapresult.map(new KeyWordMapfinal(totaldoucment,3,"quarter"));
            //hdfs的路径
            mapfinalresult.writeAsText("hdfs://userportait/quarter");
            env.execute("QuarterKeyWordTask analy");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}