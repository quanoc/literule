package com.yart.literule.support.text.dict;

import com.yart.literule.regex.util.PinYinCharDict;
import com.yart.literule.support.text.util.FileUtil;

public class DictLoader {
    private final static String SPLIT_SPACE = " ";
    private final static String DICT_SPLIT = "\\^";
    public static void load() {
        FileUtil.readLineFromFileWithException("/dict/convert_to_en", line -> {
            String[] s = line.split(SPLIT_SPACE);
            char[] c = s[0].toCharArray();
            // char -> int   unicode 编码
            BasicDict.addConvertEn((int) c[0], s[1].trim());
        });

        FileUtil.readLineFromFileWithException("/dict/convert_to_num", line -> {
            String[] s = line.split(SPLIT_SPACE);
            char[] c = s[0].trim().toCharArray();
            // char -> int   unicode 编码
            BasicDict.addConvertNum((int) c[0], Integer.valueOf(s[1]));
        });

        FileUtil.readLineFromFileWithException("/dict/punctuation", line -> {
            String[] item = line.split(DICT_SPLIT);
            if (item.length == 2) {
                if (item[0].length() == 1) {
                    BasicDict.addPunctuation(item[0].charAt(0), Integer.valueOf(item[1].trim()));
                }
            } else if (item.length == 1) {
                BasicDict.addPunctuation(item[0].charAt(0), 5);
            }
        });
        FileUtil.readLineFromFileWithException("/dict/special_symbol", line -> {
            String[] item = line.split(SPLIT_SPACE);
            BasicDict.addSymbol(item[0].trim(), Integer.valueOf(item[1].trim()));
        });
        FileUtil.readLineFromFileWithException("/dict/pinyin_to_num", line -> {
            String[] s = line.split(" ");
            PinYinCharDict.addPinYinToNum(s[0], Integer.valueOf(s[1]));
        });
        loadKeywords("/dict/keywords_0");
        loadKeywords("/dict/keywords_1");
        KeyWordDict.build();
    }

    private static void loadKeywords(String path){
        FileUtil.readLineFromFileWithException(path,
                line -> {
                    String[] s = line.split("\\^");
                    String word = s[0];
                    if (s.length == 2) {
                        if ("1".equals(s[1].trim())) {
                            // 微信触发词
                            KeyWordDict.addWord(word, new Word(word, Word.Type.WxTrigger.name()));
                        } else {
                            // 其他词
                            try {
                                Word.Type t = Word.Type.valueOf(s[1].trim());
                                KeyWordDict.addWord(word, new Word(word, t.name()));
                            }catch (Exception e) {
                                System.err.println("keyword load error." + e.getMessage());
                            }
                        }
                    }
                });
    }

}
