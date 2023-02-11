package com.yart.literule.support.text.dict;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Word.
 */
@Getter
@ToString
@NoArgsConstructor
public class Word {
    private String value;
    private String type;
    private int s;
    private int e;
    private String origin;

    public Word(String value, String type) {
        this.value = value;
        this.type = type;
    }


    public Word(String value, String type, int s, int e) {
        this.value = value;
        this.type = type;
        this.s = s;
        this.e = e;
    }

    public Word(String value, String type, int s, int e, String origin) {
        this.value = value;
        this.type = type;
        this.s = s;
        this.e = e;
        this.origin = origin;
    }


    /**
     * Word Type.
     */
    public enum Type {
        RiskUrl,
        WhiteUrl,
        White,
        SPLIT,
        NOR,
        GET_CONTACT,
        RECALL /* 召回词 */,
        None,
        WxIntent,
        WxTrigger,
        WxTriggerWhite
    }
}
