package com.example.q.practice_a;

import android.graphics.drawable.Drawable;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by q on 2016-07-04.
 */
public class OneContact {
    /* 리스트 정보를 담고 있을 객체 생성 */
    public Drawable mPhoto;
    public String mName;
    public String mNumberOrEmail;
    public Long mFrom; // 0:From Facebook, 1: From Phone


    /* 알파벳 정렬 */

    public static final Comparator<OneContact> ALPHA_COMPARATOR = new Comparator<OneContact>() {
        private final Collator sCollactor = Collator.getInstance();

        @Override
        public int compare(OneContact lhs, OneContact rhs) {
            return sCollactor.compare(lhs.mName,rhs.mName);
        }
    };
}
