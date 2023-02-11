package com.yart.literule.support.text.model;


import lombok.ToString;

import java.io.Serializable;

@ToString
public class Tuple3<A, B, C> implements Serializable {
    public final A _1;
    public final B _2;
    public final C _3;

    public Tuple3(A a, B b, C c) {
        this._1 = a;
        this._2 = b;
        this._3 = c;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tuple3)
            return _1.equals(((Tuple3) obj)._1) && _2.equals(((Tuple3) obj)._2);
        return false;
    }

    @Override
    public int hashCode() {
        return _1.hashCode() + _2.hashCode() + _3.hashCode();
    }
}
