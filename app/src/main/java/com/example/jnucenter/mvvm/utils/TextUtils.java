package com.example.jnucenter.mvvm.utils;

public class TextUtils {

    // 텍스트 첫번째 글자의 초성을 얻어온다
    public char getFirstConsonant(String text){
        char chr = text.charAt(0);
        char first = '0';

        // 0xAC00이 한글 시작
        // 한글 생성 규칙 => (초성 * 21 + 중성) * 28 + 종성 + 0xAC00
        // 초성 = ((문자유니코드 - 0xAC00)/28)/21
        if(chr >= 0xAC00){
            first = (char)((chr - 0xAC00)/28/21);
        }

        return first;
    }
}
