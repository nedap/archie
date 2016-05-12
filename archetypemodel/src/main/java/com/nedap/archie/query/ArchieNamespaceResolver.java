package com.nedap.archie.query;

import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;

/**
 * Created by pieter.bos on 11/05/16.
 */
class ArchieNamespaceResolver implements NamespaceContext {

    private final Document document;

    public ArchieNamespaceResolver(Document document) {
        this.document = document;
    }

    public String getNamespaceURI(String prefix) {
        if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
            return document.lookupNamespaceURI(null);
        } else {
            return document.lookupNamespaceURI(prefix);
        }
    }

    public String getPrefix(String namespaceURI) {
        return document.lookupPrefix(namespaceURI);
    }

    @SuppressWarnings("rawtypes")
    public Iterator getPrefixes(String namespaceURI) {
        // not implemented
        return null;
    }

}