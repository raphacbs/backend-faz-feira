package com.coelho.fazfeira.flow;

import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.handlers.Handler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FlowFactory {
    private Handler action;
    private Context context;

    private Flow flow;

    private final ApplicationContext applicationContext;

    private boolean isStarted;

    public FlowFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        isStarted = false;
    }

    public FlowFactory start(){
        this.flow = new Flow();
        isStarted = true;
        this.action = null;
        return this;
    }

    public FlowFactory addAction(Class<? extends Handler> handlerClass) {
        if (!isStarted) {
            throw new IllegalStateException("You must call start() before adding actions.");
        }
        Handler handler = null;
        try {
            handler = applicationContext.getBean(handlerClass);
            handler = (Handler) handler.clone();
        } catch (BeansException e) {
            try {
                handler = handlerClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        if (action == null) {
            action = handler;
        } else {
            Handler lastHandler = action;
            Handler nextHandler = lastHandler.getNextHandler();
            while (nextHandler != null) {
                lastHandler = nextHandler;
                nextHandler = nextHandler.getNextHandler();
            }
            lastHandler.setNext(handler);
        }
        return this;
    }

    public FlowFactory context(Context context) {
        this.context = context;
        return this;
    }

    public Flow build() {
        if (!isStarted) {
            throw new IllegalStateException("You must call start() before build.");
        }
        flow.setAction(action);
        flow.setContext(context);
        return flow;
    }
}
