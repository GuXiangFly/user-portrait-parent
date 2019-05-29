package cn.guxiangfly.map;

import cn.guxiangfly.entity.UserGroupInfo;
import cn.guxiangfly.util.DateUtils;
import cn.guxiangfly.utils.ReadProperties;
import org.apache.flink.api.common.functions.MapFunction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserGroupMapbyreduce implements MapFunction<UserGroupInfo, UserGroupInfo> {
    @Override
    public UserGroupInfo map(UserGroupInfo userGroupInfo) throws Exception {
        //消费类目，电子（电脑，手机，电视） 生活家居（衣服、生活用户，床上用品） 生鲜（油，米等等）
        //消费时间点，上午（7-12），下午（12-7），晚上（7-12），凌晨（0-7）

        List<UserGroupInfo> list = userGroupInfo.getList();

        //排序 ---start
        // 排序主要是为了 计算  间隔多少天 消费一次
        Collections.sort(list, new Comparator<UserGroupInfo>() {
            @Override
            public int compare(UserGroupInfo o1, UserGroupInfo o2) {
                String timeo1 = o1.getCreatetime();
                String timeo2 = o2.getCreatetime();
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd hhmmss");
                Date datenow = new Date();
                Date time1 = datenow;
                Date time2 = datenow;
                try {
                    time1 = dateFormat.parse(timeo1);
                    time2 = dateFormat.parse(timeo2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return time1.compareTo(time2);
            }
        });
        //排序 ---end
        Map<String,Long> productypemap = new HashMap<String,Long>();//商品类别map

        Map<Integer,Long> timeMap = new HashMap<Integer,Long>();//时间的map



        double totalamount = 0l;//总金额  这个是总金额为 减去代金券的总金额
        double maxamout = Double.MIN_VALUE;//最大金额

        Map<Integer,Integer> frequencymap = new HashMap<Integer,Integer>();//消费频次
        UserGroupInfo userGroupInfobefore = null;

        for (UserGroupInfo usergrinfo : list) {
            double totalamoutdouble = Double.valueOf(usergrinfo.getTotalamount());
            totalamount += totalamoutdouble;
            if(totalamoutdouble > maxamout){
                maxamout = totalamoutdouble;
            }
            if(userGroupInfobefore == null){
                userGroupInfobefore = usergrinfo;
                continue;
            }
            //计算购买的频率
            String beforetime = userGroupInfobefore.getCreatetime();
            String endstime = usergrinfo.getCreatetime();
            int days = DateUtils.getDaysBetweenbyStartAndend(beforetime,endstime,"yyyyMMdd hhmmss");
            // 这个 Before 代表的是  间隔n天买一次东西的评率  n 为 days
            int brefore = frequencymap.get(days)==null?0:frequencymap.get(days);
            // 间隔 days 天买一次东西的 次数为 before+1
            frequencymap.put(days,brefore+1);

            //计算消费类目
            String productype = usergrinfo.getProducttypeid();
            String bitproductype = ReadProperties.getKey(productype,"productypedic.properties");
            Long pre = productypemap.get(productype)==null?0l:productypemap.get(bitproductype);
            productypemap.put(bitproductype,pre+1);

            //时间点，上午（7-12）1，下午（12-7）2，晚上（7-12）3，凌晨（0-7）4
            String time = usergrinfo.getCreatetime();
            String hours = DateUtils.gethoursbydate(time);
            Integer hoursInt = Integer.valueOf(hours);
            int timetype = -1;
            if(hoursInt >=7 && hoursInt < 12){
                timetype = 1;
            }else if (hoursInt >=12 && hoursInt < 19){
                timetype = 2;
            }else if (hoursInt >=19 && hoursInt < 24){
                timetype = 3;
            }else if(hoursInt >=0 && hoursInt < 7){
                timetype = 4;
            }
            Long timespre = timeMap.get(timetype)==null?0l:timeMap.get(timetype);
            timeMap.put(timetype,timespre);
        }
        int ordernums = list.size();
        double avramout = totalamount/ordernums;//平均消费金额
        Set<Map.Entry<Integer,Integer>> set = frequencymap.entrySet();
        Integer totaldays = 0;
        for(Map.Entry<Integer,Integer> map:set){
            Integer days = map.getKey();
            Integer cou = map.getValue();
            totaldays += days*cou;
        }
        int days = totaldays/ordernums;//消费频次
        Random random = new Random();
        UserGroupInfo userGroupInfofinal = new UserGroupInfo();
        userGroupInfofinal.setUserid(userGroupInfo.getUserid());
        userGroupInfofinal.setAvramout(avramout);
        userGroupInfofinal.setMaxamout(maxamout);
        userGroupInfofinal.setDays(days);
        userGroupInfofinal.setBuytype1(productypemap.get("1"));
        userGroupInfofinal.setBuytype2(productypemap.get("2"));
        userGroupInfofinal.setBuytype3(productypemap.get("3"));
        userGroupInfofinal.setBuytime1(timeMap.get(1));
        userGroupInfofinal.setBuytime2(timeMap.get(2));
        userGroupInfofinal.setBuytime3(timeMap.get(3));
        userGroupInfofinal.setBuytime4(timeMap.get(4));
        userGroupInfofinal.setGroupfield("usergrouykmean"+random.nextInt(100));
        return userGroupInfofinal;
    }
}