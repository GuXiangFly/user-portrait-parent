package cn.guxiangfly.logic;

import cn.guxiangfly.entity.CarrierInfo;
import cn.guxiangfly.map.CarrierMap;
import cn.guxiangfly.reduce.CarrierReduce;
import cn.guxiangfly.util.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.summarize.aggregation.DoubleSummaryAggregator;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

public class LogicTask {
    public static void main(String[] args) {
        final ParameterTool params = ParameterTool.fromArgs(args);
        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);
        // get input data
        DataSet<String> text = env.readTextFile(params.get("input"));

        DataSet<LogicInfo> mapresult = text.map(new LogicMap());
    }
}
