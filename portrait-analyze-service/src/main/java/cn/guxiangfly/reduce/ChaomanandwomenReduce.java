package cn.guxiangfly.reduce;

import cn.guxiangfly.entity.ChaomanAndWomenInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

public class ChaomanandwomenReduce implements ReduceFunction<ChaomanAndWomenInfo> {
    @Override
    public ChaomanAndWomenInfo reduce(ChaomanAndWomenInfo chaomanAndWomenInfo, ChaomanAndWomenInfo t1) throws Exception {

        return null;
    }
}
