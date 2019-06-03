package cn.guxiangfly.utils;

import java.util.*;

public class MapUtils {

    public static String getmaxbyMap(Map<String,Long> datamap){
        if(datamap.isEmpty()){
                return  null;
        }
        TreeMap<Long,String> map = new TreeMap<Long, String>(new Comparator<Long>() {
            public int compare(Long o1, Long o2) {
                return o2.compareTo(o1);
            }
        });
        Set<Map.Entry<String,Long>> set = datamap.entrySet();
        for(Map.Entry<String,Long> entry :set){
            String key = entry.getKey();
            Long value = entry.getValue();
            map.put(value,key);
        }
        return map.get(map.firstKey());
    }

    /**
     * 使用 Map按value进行排序
     * @return
     */
    public static LinkedHashMap<String, Double> sortMapByValue(Map<String,Double> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        List<Map.Entry<String, Double>> entryList = new ArrayList<Map.Entry<String, Double>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<String, Double>> iter = entryList.iterator();
        Map.Entry<String, Double> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    static class MapValueComparator implements Comparator<Map.Entry<String, Double>> {
        public int compare(Map.Entry<String, Double> me1, Map.Entry<String, Double> me2) {
            return me1.getValue().compareTo(me2.getValue());
        }
    }
}
