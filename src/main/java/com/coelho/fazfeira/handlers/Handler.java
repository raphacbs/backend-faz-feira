package com.coelho.fazfeira.handlers;

public interface Handler extends  Cloneable {
    void setNext(Handler next);
    Handler getNextHandler();
    void handle(Context context);
    Object clone() throws CloneNotSupportedException;
}
