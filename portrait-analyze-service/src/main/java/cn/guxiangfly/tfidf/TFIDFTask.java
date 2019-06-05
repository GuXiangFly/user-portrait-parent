package cn.guxiangfly.tfidf;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

public class TFIDFTask {
    public static void main(String[] args) {
        final ParameterTool params = ParameterTool.fromArgs(args);

        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);
        // get input data
        DataSet<String> text = env.readTextFile(params.get("input"));

        DataSet<TfIdfEntity> mapresult = text.map(new IdfMap());
        DataSet<TfIdfEntity> reduceresult = mapresult.reduce(new IdfReduce());
        Long totaldoucment = 0L;
        try {
            totaldoucment = reduceresult.collect().get(0).getTotaldocumet();
            DataSet<TfIdfEntity> mapfinalresult = mapresult.map(new IdfMapfinal(totaldoucment,3));
            mapfinalresult.writeAsText("hdfs://youfan/test");//hdfs的路径
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}