package com.util;

import java.util.HashSet;

public class ReturnType{
    private final HashSet<String> result;
    private final boolean isAccepted;

    public ReturnType(HashSet<String> result){
        this.result = result;
        this.isAccepted = result.size() > 0;
    }

    public HashSet<String> getResult(){
        return result;
    }

    public boolean isAccepted(){
        return isAccepted;
    }
}
