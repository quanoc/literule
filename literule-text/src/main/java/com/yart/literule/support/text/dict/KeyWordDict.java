package com.yart.literule.support.text.dict;

import com.yart.literule.core.internal.util.CollectionUtil;
import com.yart.literule.support.text.util.ACDoubleArrayTrie;
import com.yart.literule.support.text.util.WordMatcherUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 字典树关键词匹配.
 *
 * @author zhangquanquan.
 */
@Slf4j
public class KeyWordDict {

    private static ACDoubleArrayTrie<Word> wordTrieTmp = new ACDoubleArrayTrie<>();
    private static final Map<String, Word> buildMap = new ConcurrentHashMap<>();

    public static boolean addWord(Word word) {
        if (Objects.isNull(word) || word.getValue() == null) {
            return false;
        }
        if (word.getValue().length() > 1) {
            buildMap.put(word.getValue(), word);
            return true;
        }
        return false;
    }

    public static void addWord(String value, Word word) {
        if (value.length() > 1) {
            buildMap.put(value, word);
        }
    }

    public static void build() {
        if (CollectionUtil.isEmpty(buildMap)) {
            return;
        }
        long start = System.currentTimeMillis();
        int size = buildMap.size();
        ACDoubleArrayTrie<Word> trie = new ACDoubleArrayTrie<>();
        trie.build(buildMap);
        trie.exactMatchSearch("初始化");
        wordTrieTmp = trie;
        buildMap.clear();
        log.info("build contact dict success. size:{}, cost:{}", size,
                System.currentTimeMillis() - start);
    }

    public static void rebuild() {
        if (CollectionUtil.isEmpty(buildMap)) {
            return;
        }
        long start = System.currentTimeMillis();
        Map<String, Word> words = Objects.isNull(wordTrieTmp.getV()) ? new HashMap<>()
                : Arrays.stream(wordTrieTmp.getV())
                .collect(Collectors.toMap(Word::getValue, w ->w, (w1, w2) -> w1));
        words.putAll(buildMap);
        int size = words.size();
        ACDoubleArrayTrie<Word> trie = new ACDoubleArrayTrie<>();
        trie.build(words);
        trie.exactMatchSearch("初始化");
        wordTrieTmp = trie;
        buildMap.clear();
        log.info("build contact dict success. size:{}, cost:{}", size,
                System.currentTimeMillis() - start);
    }


    /**
     * 词库匹配.(最长匹配.)
     * 匹配方式：全部, 最长前缀匹配, 最长匹配(色魔 橙色魔方).
     *
     * @param text pending string
     * @return 命中的串
     */
    public static List<Word> findWords(String text) {
        if (Objects.isNull(text) || text.isEmpty()) {
            return new ArrayList<>();
        }
        return WordMatcherUtil.longestMultiMode(text, wordTrieTmp);
    }

    static Set<String> wxIntentTypes = new HashSet<>(new ArrayList<>(
            Collections.singletonList(Word.Type.WxIntent.name()))
    );

    static Set<String> wxTriggerTypes = new HashSet<>(new ArrayList<>(
            Collections.singletonList(Word.Type.WxTrigger.name()))
    );


    public static List<Word> findWxIntentWords(String text) {
        return WordMatcherUtil.longestMultiMode(text, wordTrieTmp).stream()
                .filter(w -> wxIntentTypes.contains(w.getType()))
                .collect(Collectors.toList());
    }

    public static List<Word> findWxTriggerWords(String text) {
        return WordMatcherUtil.longestMultiMode(text, wordTrieTmp).stream()
                .filter(w -> wxTriggerTypes.contains(w.getType()))
                .collect(Collectors.toList());
    }
}
