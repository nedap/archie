package com.nedap.archie.query;

import com.nedap.archie.util.JAXBUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.bind.Binder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

    /**
     * TODO: for now w will add /firstXPathNode, because otherwise there will be something like '/composition' missing
     * However that is rather annoying, because apath does not specify this. So find a way of fixing this.
     */
    private String firstXPathNode;


    /**
     * Construct a query object for a given root node. You can later query subnodes of this rootnode if you desire.
     * @param rootNode
     */
    public RMQuery(Object rootNode) {
        this(rootNode, JAXBUtil.getArchieJAXBContext());
    }

    /**
     * Construct a query object for a given root node with a different RM implementation than the Archie one.
     * please construct your own JAXBContext, see JAXBUtil for how to do this
     * @param rootNode
     */
    public RMQuery(Object rootNode, JAXBContext jaxbContext) {
        try {
            this.binder = jaxbContext.createBinder();
            domForQueries = createBlankDOMDocument(true);

            binder.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //Marshall Query object to a blank DOM document.
            //Binder will maintains association between two views.
            binder.marshal( rootNode/*new JAXBElement<Query>(qname, Query.class, query)*/  , domForQueries);

            firstXPathNode = domForQueries.getFirstChild().getNodeName();

            //print to stdout. Don't you love java xml api's?
            //TransformerFactory.newInstance().newTransformer().transform(new DOMSource(domForQueries), new StreamResult(System.out));

        } catch (JAXBException e) {
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

        query = m.replaceAll("[${first}@archetype_node_id='${idnode}'${last}]");
        if(query.startsWith("/")) {
            return "/" + firstXPathNode + query;
        } else {
            return query;
        }
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

    public List<RMObjectWithPath> findListWithPaths(String query) throws XPathExpressionException {
        String convertedQuery = convertQueryToXPath(query);
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext( new NamespaceResolver(domForQueries));
        NodeList foundNodes = (NodeList)xpath.evaluate(convertedQuery, domForQueries, XPathConstants.NODESET);
        List<RMObjectWithPath> result = new ArrayList<>();
        for(int i=0; i<foundNodes.getLength(); i++){
            Node node = foundNodes.item(i);
            String path = constructPath(node);
            result.add(new RMObjectWithPath(binder.getJAXBNode(node), path));
        }
        return result;
    }

    private String constructPath(Node node) {
        Node parent = node.getParentNode();
        String path = "";
        if(parent == null || parent.getParentNode() == null) {
            //TODO: this is a bit of a hack, parent.getParentNode() == null means the current node is one below the parent
            //that should not happen, but because of differences between xpath and apath...
            return path;
        } else {
            int index = findNodeIndex(node, parent);

            return constructPath(parent) + "/" + node.getNodeName() + "[" + index + "]";

        }
    }

    private int findNodeIndex(Node node, Node parent) {
        int index = 1;
        for(int i = 0; i < parent.getChildNodes().getLength(); i++) {
            //nodes with same name in XML means in the same collection in Java
            if(parent.getChildNodes().item(i).getNodeName().equals(node.getNodeName())) {
                if (parent.getChildNodes().item(i) == node) {
                    return index;
                }
                index++;
            }
        }
        return -1;
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
