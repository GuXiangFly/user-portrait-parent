package cn.guxiangfly.reduce;

import cn.guxiangfly.entity.YearBase;
import org.apache.flink.api.common.functions.ReduceFunction;

public class YearBaseReduce implements ReduceFunction<YearBase> {
    @Override
    public YearBase reduce(YearBase yearBase1, YearBase yearBase2) throws Exception {
        long count1 = yearBase1.getCount();
        long count2 = yearBase2.getCount();

        YearBase yearBase = new YearBase();
        yearBase.setCount(count1+count2);
        yearBase.setYeartype(yearBase1.getYeartype());
        return yearBase;
    }
}
