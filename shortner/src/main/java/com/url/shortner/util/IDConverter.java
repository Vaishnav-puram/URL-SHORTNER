package com.url.shortner.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IDConverter {
    private static final IDConverter instance=new IDConverter();

    private  IDConverter(){
        initialize();
    }

    private static HashMap<Character,Integer> charToIndexMap;

    private List<Character> indexToCharTable;


    private void initialize() {
        charToIndexMap=new HashMap<>();
        indexToCharTable=new ArrayList<>();
        for (int i=0;i<26;i++){
            char c='a';
            c+=i;
            charToIndexMap.put(c,i);
            indexToCharTable.add(c);
        }

        for (int i=26;i<52;i++){
            char c='A';
            c+= (char) (i-26);
            charToIndexMap.put(c,i);
            indexToCharTable.add(c);
        }

        for (int i=52;i<62;i++){
            char c='0';
            c+= (char) (i-52);
            charToIndexMap.put(c,i);
            indexToCharTable.add(c);
        }

    }
}
