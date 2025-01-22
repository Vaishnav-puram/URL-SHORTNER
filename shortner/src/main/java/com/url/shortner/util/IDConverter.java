package com.url.shortner.util;

import java.util.*;

public class IDConverter {
    private static final IDConverter instance=new IDConverter();

    private  IDConverter(){
        initialize();
    }

    private static HashMap<Character,Integer> charToIndexMap;

    private static List<Character> indexToCharTable;


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

    public static String createUniqueId(Long id){
        List<Integer> ids=convertBase10ToBase62(id);
        StringBuilder sb=new StringBuilder();
        for(int ele:ids){
            sb.append(indexToCharTable.get(ele));
        }
        return sb.toString();
    }

    private static List<Integer> convertBase10ToBase62(Long id) {
        List<Integer> base62Ids=new LinkedList<>();
        while (id>0){
            int remainder=(int)(id%62);
            ((LinkedList<Integer>) base62Ids).addFirst(remainder);
            id/=62;

        }
        return base62Ids;
    }

    private static Long getDictionaryKeyFromUniqueId(String uniqueId){
        List<Character> ids=new ArrayList<>();
        for(int i=0;i<uniqueId.length();i++){
            ids.add(uniqueId.charAt(i));
        }
        return convertBase62ToBase10(ids);
    }

    private static Long convertBase62ToBase10(List<Character> ids) {
        long id=0;
        for(int i=0,exp=ids.size()-1;i<ids.size();i++,exp--){
            int base10=charToIndexMap.get(ids.get(i));
            id+= (long) (base10* Math.pow(62.0,exp));
        }
        return id;
    }
}
