package com.netim;

public class NetimAPIException extends Exception
{
    public NetimAPIException(String message){
        super(message);
    }

    public NetimAPIException(Exception e){
        super(e);
    }
}