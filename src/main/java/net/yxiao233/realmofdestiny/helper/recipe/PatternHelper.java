package net.yxiao233.realmofdestiny.helper.recipe;

import java.util.ArrayList;

public class PatternHelper {
    private ArrayList<String> patternList;
    private ArrayList<String> keys;
    private char[][][] patterns;
    public PatternHelper(ArrayList<String> patternList){
        this.keys = new ArrayList<>();
        this.patternList = patternList;
        getPatternChars();
    }

    public char[][][] getPatternChars(){
        String p0 = patternList.get(0);
        int tier = patternList.size();
        int count = (int) p0.chars().filter(ch -> ch == ',').count() + 1;
        int keyCount = p0.substring(p0.indexOf("\""),p0.indexOf("\"",p0.indexOf("\"") + 1)).length() - 1;
        char[][][] allPattern = new char[tier][count][keyCount];

        for (int i = 0; i < patternList.size(); i++) {
            String s = patternList.get(i);
            allPattern[i] = getChars(s,keyCount);
        }
        this.patterns = allPattern;
        return allPattern;
    }
    public ArrayList<String> getKeysMap(){
        return this.keys;
    }

    public char[][][] getPatternsMap() {
        return patterns;
    }

    private char[][] getChars(String s, int keyCount){
        char[][] chars = new char[getNeededLength(s) / keyCount][keyCount];
        int i = 0;
        int j = 0;
        for (int m = 0; m < s.length(); m++) {
            if(!haseNeeded(m,s)){
                if(i == keyCount){
                    j ++;
                    i = 0;
                }
                char c = s.charAt(m);
                chars[j][i] = c;
                addToKeys(String.valueOf(c));
                i ++;
            }
        }
        return chars;
    }

    private boolean haseNeeded(int index,String s){
        String toEqual = String.valueOf(s.charAt(index));
        return toEqual.equals("[") || toEqual.equals("]") || toEqual.equals(",") || toEqual.equals("\"");
    }

    private int getNeededLength(String s){
        int repeat = (int) s.chars().filter(ch -> ch == ',').count() + 1;
        return s.substring(s.indexOf("\""),s.indexOf(",") - 2).length() * repeat;
    }

    private void addToKeys(String toAdd){
        if(keys.isEmpty()){
            keys.add(toAdd);
        }
        for (int i = 0; i < keys.size(); i++) {
            if(!keys.contains(toAdd)){
                keys.add(toAdd);
            }
        }
    }
}
