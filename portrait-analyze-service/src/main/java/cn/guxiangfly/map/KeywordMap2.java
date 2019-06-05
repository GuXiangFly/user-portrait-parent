package cn.guxiangfly.map;

import cn.guxiangfly.entity.KeyWordEntity;
import cn.guxiangfly.util.HbaseUtils;
import cn.guxiangfly.util.IkUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.*;

/**
 * 这里 将一个一个用户 看做 一个一个 document
 *
 */
public class KeywordMap2 implements MapFunction<KeyWordEntity, KeyWordEntity> {

    @Override
    public KeyWordEntity map(KeyWordEntity keyWordEntity) throws Exception {
        List<String> wordLists = keyWordEntity.getOriginalwords();

        /** 单个document 的词频率*/
        Map<String,Long> tfmap = new HashMap<String,Long>();

        /** 单个document 的word set*/
        Set<String> wordset = new HashSet<String>();
        for (String wordList : wordLists) {
            List<String> ikWord = IkUtils.getIkWord(wordList);
            for (String word : ikWord) {
                Long pre = tfmap.get(word)==null?0l:tfmap.get(word);
                tfmap.put(word,pre+1);
                wordset.add(word);
            }
        }

        KeyWordEntity keyWordEntityfinal = new KeyWordEntity();
        String userid = keyWordEntity.getUserid();
        keyWordEntityfinal.setUserid(userid);
        keyWordEntityfinal.setDatamap(tfmap);


        //计算总数  单个文档里面 词的总数
        long sum = 0L;
        for (Long value : tfmap.values()) {
            sum+=value;
        }

        //tfmapfinal 这个保存的是 每个词 计算后的 tf 值
        Map<String,Double> tfmapfinal = new HashMap<String,Double>();
        for (Map.Entry<String, Long> entry : tfmap.entrySet()) {
            String word = entry.getKey();
            Long count = entry.getValue();
            double tf = Double.valueOf(count) / Double.valueOf(sum);
            tfmapfinal.put(word,tf);
        }
        keyWordEntityfinal.setTfmap(tfmapfinal);

        //我们将这篇文章的 wordset 存储到 Hbase中 每个word为一个 rowkey 这么 就可以知道  这个词在 几个 document中出现过了
        for (String word : wordset) {
            String tablename = "keyworddata";
            String rowkey=word;
            String famliyname="baseinfo";
            String colum="idfcount";
            String countString = HbaseUtils.getdata(tablename,rowkey,famliyname,colum);
            Long count = countString==null?0L:Long.valueOf(countString);
            // count + 1
            HbaseUtils.putdata(tablename,rowkey,famliyname,colum,String.valueOf(count+1));
        }
        return keyWordEntityfinal;
    }
}