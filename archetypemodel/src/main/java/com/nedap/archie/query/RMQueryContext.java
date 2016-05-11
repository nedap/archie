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
 * EXPERIMENTAL: full xpath support for the reference model.
 *
 * It's done by converting the RM objects into an XML-DOM using JAXB's Binder. XPATH is then evaluated against the DOM.
 * The binder enables us to return the original objects.
 *
 * The APATH-shorthand notation is converted to its equivalent XPATH-notation before evaluation
 *
 * KNOWN ISSUES: the shorthand notation with comma's instead of AND does not yet work
 *
 * Created by pieter.bos on 03/05/16.
 */
public class RMQueryContext {

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
    public RMQueryContext(Object rootNode) {
        this(rootNode, JAXBUtil.getArchieJAXBContext());
    }

    /**
     * Construct a query object for a given root node with a different RM implementation than the Archie one.
     * please construct your own JAXBContext, see JAXBUtil for how to do this
     * @param rootNode
     */
    public RMQueryContext(Object rootNode, JAXBContext jaxbContext) {
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

    public <T> List<T> findList(String query) throws XPathExpressionException {
        String convertedQuery = APathToXPathConverter.convertQueryToXPath(query, firstXPathNode);
        XPath xpath = XPathFactory.newInstance().newXPath();

        xpath.setNamespaceContext( new ArchieNamespaceResolver(domForQueries));
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
        String convertedQuery = APathToXPathConverter.convertQueryToXPath(query, firstXPathNode);
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext( new ArchieNamespaceResolver(domForQueries));
        NodeList foundNodes = (NodeList)xpath.evaluate(convertedQuery, domForQueries, XPathConstants.NODESET);
        List<RMObjectWithPath> result = new ArrayList<>();
        for(int i=0; i<foundNodes.getLength(); i++){
            Node node = foundNodes.item(i);
            String path = UniqueNodePathBuilder.constructPath(node);
            result.add(new RMObjectWithPath(binder.getJAXBNode(node), path));
        }
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
