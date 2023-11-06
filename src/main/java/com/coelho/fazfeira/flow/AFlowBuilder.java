package com.coelho.fazfeira.flow;

import com.coelho.fazfeira.flow.item.CreateProductAndItemFlowBuilder;
import com.coelho.fazfeira.handlers.Context;
import org.springframework.stereotype.Component;


public abstract class AFlowBuilder<T> {
    protected Flow flow;

     protected void run() {
        if (this.flow != null) {
            this.flow.run();
        } else {
            throw new IllegalStateException("FlowFactory has not been created. Call create() method first.");
        }
    }

    public Flow build() {
        if (this.flow != null) {
            return this.flow;
        } else {
            throw new IllegalStateException("FlowFactory has not been created. Call create() method first.");
        }
    }
    public abstract T create(Context context);
}
