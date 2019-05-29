package cn.guxiangfly.reduce;

import cn.guxiangfly.entity.ConsumptionLevel;
import cn.guxiangfly.util.HbaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.util.Collector;

import java.util.Iterator;

public class ConsumptionLevelReduce implements GroupReduceFunction<ConsumptionLevel,ConsumptionLevel> {

    @Override
    public void reduce(Iterable<ConsumptionLevel> iterable, Collector<ConsumptionLevel> collector) throws Exception {
        Iterator<ConsumptionLevel> iterator = iterable.iterator();
        int sum=0;
        double totalamount = 0d;
        String userid = "-1";
        while (iterator.hasNext()){
            ConsumptionLevel comsumptionLevel = iterator.next();
            userid = comsumptionLevel.getUserid();
            String amounttotaol = comsumptionLevel.getAmounttotaol();
            double amoutndouble = Double.valueOf(amounttotaol);
            totalamount += amoutndouble;
            sum++;
        }
        double avramout = totalamount/sum;//高消费5000 中等消费 1000 低消费 小于1000
        String flag = "low";
        if(avramout >=1000 && avramout <5000){
            flag = "middle";
        }else if(avramout >= 5000){
            flag = "high";
        }
        String tablename = "userflaginfo";
        String rowkey = userid+"";
        String famliyname = "consumerinfo";
        String colum = "consumptionlevel";
        String data = HbaseUtils.getdata(tablename,rowkey,famliyname,colum);
        if(StringUtils.isBlank(data)){
            ConsumptionLevel consumptionLevel = new ConsumptionLevel();
            consumptionLevel.setConsumptiontype(flag);
            consumptionLevel.setCount(1l);
            consumptionLevel.setGroupfield("==consumptionLevelfinal=="+flag);
            collector.collect(consumptionLevel);
        }else if(!data.equals(flag)){
            //如果之前的标志和 现在的标志不符合的话  那么hbase里面会被现在的覆盖
            ConsumptionLevel consumptionLevel = new ConsumptionLevel();
            consumptionLevel.setConsumptiontype(data);
            consumptionLevel.setCount(-1l);
            consumptionLevel.setGroupfield("==consumptionLevelfinal=="+data);

            ConsumptionLevel consumptionLevel2 = new ConsumptionLevel();
            consumptionLevel2.setConsumptiontype(flag);
            consumptionLevel2.setCount(1l);
            consumptionLevel.setGroupfield("==consumptionLevelfinal=="+flag);
            collector.collect(consumptionLevel);
            collector.collect(consumptionLevel2);
        }

        HbaseUtils.putdata(tablename,rowkey,famliyname,colum,flag);
    }
}