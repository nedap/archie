package com.nedap.archie.query;

import com.google.common.collect.Lists;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.datavalues.DvText;
import org.apache.commons.jxpath.JXPathContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.management.Query;
import javax.xml.XMLConstants;
import javax.xml.bind.Binder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pieter.bos on 03/05/16.
 */
public class RMQuery {

    private Binder<Node> binder;
    private Document domForQueries;
    private RMObject rootNode;

    /**
     * Construct a query object for a given root node. You can later query subnodes of this rootnode if you desire.
     * @param rootNode
     */
    public RMQuery(RMObject rootNode) {
        try {
            this.rootNode = rootNode;
            JAXBContext jaxbContext = JAXBContext.newInstance(rootNode.getClass());
            this.binder = jaxbContext.createBinder();
            domForQueries = createBlankDOMDocument(true);

            //Marshall Query object to a blank DOM document.
            //Binder will maintains association between two views.
            binder.marshal( rootNode/*new JAXBElement<Query>(qname, Query.class, query)*/  , domForQueries);

            //print to stdout. Don't you love java xml api's?
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(domForQueries), new StreamResult(System.out));
            //Search for all occurrences of Company using XPath.



        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }


    }

    public Document createBlankDOMDocument(boolean namespaceAware) {
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        fact.setNamespaceAware(namespaceAware);
        DocumentBuilder builder;
        try {
            builder = fact.newDocumentBuilder();

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        return builder.newDocument();
    }

    private String convertQueryToXPath(String query) {
        Pattern pattern = Pattern.compile("\\[(?<first>[^\\]]*)(?<idnode>id\\d+)(?<last>[^\\]]*)\\]");
        Matcher m = pattern.matcher(query);

        return m.replaceAll("[${first}@archetype_node_id='${idnode}'${last}]");
    }

    public <T> List<T> findList(String query) throws XPathExpressionException {
        String convertedQuery = convertQueryToXPath(query);
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext( new NamespaceResolver(domForQueries));
        NodeList foundNodes = (NodeList)xpath.evaluate(convertedQuery, domForQueries, XPathConstants.NODESET);
        List<T> result = new ArrayList<T>();
        //Perform decoration
        for(int i=0; i<foundNodes.getLength(); i++){
            Node node = foundNodes.item(i);

            result.add((T) binder.getJAXBNode(node));
        }

        //Synchronize the changes back to Query object.
        return result;
    }

    public <T> T find(String query) throws XPathExpressionException {
        List result = findList(query);
        if(result.isEmpty()) {
            return null;
        } else if (result.size() == 1) {
            return (T) result.get(0);
        } else {
            throw new RuntimeException("query returned more than one element: " + result.size());
        }


    }
//    public <T> T  find(RMObject object) {
//        return (T) JXPathContext.newContext(object).getValue(query);
//    }
}

class NamespaceResolver implements NamespaceContext {

    private final Document document;

    public NamespaceResolver(Document document) {
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
