package cn.guxiangfly.reduce;

import cn.guxiangfly.entity.BaiJiaInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

import java.util.ArrayList;
import java.util.List;

public class BaijiaReduce implements ReduceFunction<BaiJiaInfo> {
    @Override
    public BaiJiaInfo reduce(BaiJiaInfo baiJiaInfo, BaiJiaInfo t1) throws Exception {
        String userid = baiJiaInfo.getUserid();
        List<BaiJiaInfo> list1 = baiJiaInfo.getList();
        List<BaiJiaInfo> list2 = t1.getList();
        List<BaiJiaInfo>  finalList = new ArrayList<>();
        finalList.addAll(list2);
        finalList.addAll(list1);

        BaiJiaInfo baiJiaInfofinal = new BaiJiaInfo();
        baiJiaInfofinal.setUserid(userid);
        baiJiaInfofinal.setList(finalList);
        return baiJiaInfofinal;
    }
}
