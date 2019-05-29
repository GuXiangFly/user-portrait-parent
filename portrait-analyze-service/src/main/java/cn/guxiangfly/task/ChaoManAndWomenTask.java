package cn.guxiangfly.task;

import org.apache.flink.api.java.utils.ParameterTool;

public class ChaoManAndWomenTask {
    public static void main(String[] args) {
        args = new String[]{"--input-topic","scanProductLog","--bootstrap.servers","hadoop101:9092","--zookeeper.connect","hadoop101:2181","--group.id","youfan"};
        final ParameterTool parameterTool = ParameterTool.fromArgs(args);
    }
}