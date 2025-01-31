package net.yxiao233.realmofdestiny.item.custom;

public enum KeyType {
    SHIFT("shift"),
    ALT("alt"),
    CONTROL("control");

    private final String value;
    private KeyType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
