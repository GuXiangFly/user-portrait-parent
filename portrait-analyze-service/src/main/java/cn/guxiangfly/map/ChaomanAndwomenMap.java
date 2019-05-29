package cn.guxiangfly.map;

import cn.guxiangfly.entity.ChaomanAndWomenInfo;
import cn.guxiangfly.kafka.KafkaEvent;
import cn.guxiangfly.log.ScanProductLog;
import cn.guxiangfly.utils.ReadProperties;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

public class ChaomanAndwomenMap implements FlatMapFunction<KafkaEvent, ChaomanAndWomenInfo> {

    @Override
    public void flatMap(KafkaEvent kafkaEvent, Collector<ChaomanAndWomenInfo> collector) throws Exception {
        String data = kafkaEvent.getWord();
        ScanProductLog scanProductLog = JSONObject.parseObject(data,ScanProductLog.class);
        int userid = scanProductLog.getUserid();
        int productid = scanProductLog.getProductid();
        ChaomanAndWomenInfo chaomanAndWomenInfo = new ChaomanAndWomenInfo();
        chaomanAndWomenInfo.setUserid(userid+"");
        String chaotype = ReadProperties.getKey(productid+"","productChaoLiudic.properties");
        if(StringUtils.isNotBlank(chaotype)){
            chaomanAndWomenInfo.setChaotype(chaotype);
            chaomanAndWomenInfo.setCount(1l);
            chaomanAndWomenInfo.setGroupbyfield("chaomanAndWomen=="+userid);
            List<ChaomanAndWomenInfo> list = new ArrayList<ChaomanAndWomenInfo>();
            list.add(chaomanAndWomenInfo);
            collector.collect(chaomanAndWomenInfo);
        }
    }
}