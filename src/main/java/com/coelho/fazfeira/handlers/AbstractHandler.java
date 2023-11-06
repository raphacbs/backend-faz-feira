package com.coelho.fazfeira.handlers;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public abstract class AbstractHandler implements Handler {
    protected Handler next;
    @Autowired
    private ApplicationContext applicationContext;
    @Override
    public void setNext(Handler next) {
        this.next = next;
    }

    @Override
    public Handler getNextHandler() {
        return this.next;
    }

    @Override
    public void handle(Context context) {
        doHandle(context);
        if (next != null) {
            next.handle(context);
        }
    }

    protected void callHandler(Class<? extends Handler> handlerClass,
                               Context context) {
        Handler action = null;
        try {
            action = applicationContext.getBean(handlerClass);
            action = (Handler) action.clone();
        } catch (BeansException e) {
            try {
                action = handlerClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        action.handle(context);
    }

    protected abstract void doHandle(Context context);

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}