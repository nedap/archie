package com.nedap.archie.rules.evaluation;

import com.google.common.base.Joiner;
import com.nedap.archie.paths.PathSegment;
import com.nedap.archie.query.APathQuery;
import com.nedap.archie.rm.archetyped.Archetyped;
import com.nedap.archie.rm.archetyped.Locatable;

import javax.xml.xpath.XPathExpressionException;
import java.util.List;

/**
 * Created by pieter.bos on 14/07/2017.
 */
public class RuleEvaluationUtil {

    public static Archetyped findLastArchetypeDetails(RuleEvaluation ruleEvaluation, String path) throws XPathExpressionException {
        APathQuery query = new APathQuery(path);
        for(int i = query.getPathSegments().size();i > 0; i--) {
            String subpath = Joiner.on("").join(query.getPathSegments().subList(0, i));
            List<Object> list = ruleEvaluation.getQueryContext().findList(subpath);
            for(Object object:list) {
                if(object instanceof Locatable) {
                    Locatable object1 = (Locatable) object;
                    if(object1.getArchetypeDetails() != null) {
                        return object1.getArchetypeDetails();
                    }
                }
            }
        }
        return null;
    }

    public static String convertRMObjectPathToArchetypePath(String path) {
        APathQuery query = new APathQuery(path);
        for(PathSegment segment:query.getPathSegments()) {
            segment.setIndex(null);
        }
        return Joiner.on("").join(query.getPathSegments());
    }

}
