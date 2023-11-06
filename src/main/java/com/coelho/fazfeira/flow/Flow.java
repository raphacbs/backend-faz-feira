package com.coelho.fazfeira.flow;

import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.handlers.Handler;

public class Flow {
    private Handler action;
    private Context context;

    protected Flow(){

    }

    public void setAction(Handler action) {
        this.action = action;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void run() {
        if (action != null) {
            action.handle(context);
        }
    }
}





