package com.yart.literule.support.text.util;

import com.yart.literule.support.text.dict.Word;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 字典树匹配.
 *
 * @author zhangquanquan 2022.05.20.
 */
@Slf4j
public class WordMatcherUtil {
    /**
     * 多模式匹配. 最长串匹配模式.
     * 例：位信 岗位信息 -> 岗位信息； 色魔 橙色魔方 -> 橙色魔方
     *
     * @param text pending string
     * @return 命中的串
     */
    public static List<Word> longestMultiMode(
            String text, ACDoubleArrayTrie<Word> wordTrieTmp
    ) {
        final List<Word> hits = new ArrayList<>(8);
        if (wordTrieTmp.size() == 0) {
            return hits;
        }
        wordTrieTmp.parseText(text, (begin, end, value) -> {
            if (Objects.isNull(value.getValue())) {
                log.error("word.value is null. word:{}", value);
                return;
            }
            if (hits.isEmpty()) {
                hits.add(new Word(value.getValue(), value.getType(), begin, end));
            } else {
                Word last = hits.get(hits.size() - 1);
                // 位置相同的跳过.
                if (value.getValue().contains(last.getValue())) {
                    hits.set(hits.size() - 1,
                            new Word(value.getValue(), value.getType(), begin, end));
                } else if (last.getValue().contains(value.getValue())) {
                    // do nothing.
                } else {
                    hits.add(new Word(value.getValue(), value.getType(), begin, end));
                }
            }
        });
        return hits;
    }
}
