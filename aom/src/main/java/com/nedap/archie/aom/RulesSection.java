package com.nedap.archie.aom;

import com.nedap.archie.rules.RuleStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 27/10/15.
 */
public class RulesSection extends ArchetypeModelObject {

    private String content;
    private List<RuleStatement> rules = new ArrayList<>();

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<RuleStatement> getRules() {
        return rules;
    }

    public void setRules(List<RuleStatement> rules) {
        this.rules = rules;
    }

    public void addRule(RuleStatement rule) {
        rules.add(rule);
    }
}
