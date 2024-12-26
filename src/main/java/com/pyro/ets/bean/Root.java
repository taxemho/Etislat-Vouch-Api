package com.pyro.ets.bean;

import org.springframework.stereotype.Component;

@Component
public class Root {

    private Reply reply;

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public Reply getReply() {
        return this.reply;
    }

    @Override
    public String toString() {
        return "Root [Reply=" + reply + "]";
    }
}
