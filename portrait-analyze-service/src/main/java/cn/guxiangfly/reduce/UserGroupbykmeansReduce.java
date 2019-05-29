package cn.guxiangfly.reduce;

import cn.guxiangfly.entity.UserGroupInfo;
import cn.guxiangfly.kmeans.Point;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;

public class UserGroupbykmeansReduce implements GroupReduceFunction<UserGroupInfo, ArrayList<Point>> {

    @Override
    public void reduce(Iterable<UserGroupInfo> iterable, Collector<ArrayList<Point>> collector) throws Exception {

    }
}