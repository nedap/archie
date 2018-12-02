package org.openehr.odin.jackson;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ODINParserContext {

    private ParserRuleContext odinContext;
    private Queue<ParserRuleContext> queue = new LinkedBlockingQueue<>();

    public ODINParserContext(ParserRuleContext odinContext) {
        this.odinContext = odinContext;
    }

    public ParserRuleContext getOdinContext() {
        return odinContext;
    }

    public void setOdinContext(ParserRuleContext odinContext) {
        this.odinContext = odinContext;
    }

    public Queue<ParserRuleContext> getQueue() {
        return queue;
    }

    public void setQueue(Queue<ParserRuleContext> queue) {
        this.queue = queue;
    }

    public void add(ParserRuleContext ctx) {
        queue.add(ctx);
    }

    public ParserRuleContext nextFromQueue() {
        return queue.poll();
    }



}
