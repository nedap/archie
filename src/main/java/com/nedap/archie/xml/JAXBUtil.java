package com.nedap.archie.xml;

import com.nedap.archie.rminfo.ArchieRMInfoLookup;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Created by pieter.bos on 06/05/16.
 */
public class JAXBUtil {

    private static JAXBContext archieJaxbContext;

    /**
     * Get the JAXBContext to work with archie reference model objects.
     *
     * Can marshal and unmarshal reference model object trees.
     *
     * @return
     */
    public static JAXBContext getArchieJAXBContext() {
        if(archieJaxbContext == null) {
            initArchieJaxbContext();
        }
        return archieJaxbContext;
    }

    private static synchronized void initArchieJaxbContext() {
        if(archieJaxbContext == null) {
            try {
                archieJaxbContext = JAXBContext.newInstance(ArchieRMInfoLookup.getInstance().getRmTypeNameToClassMap().values().toArray(new Class[0]));
            } catch (JAXBException e) {
                throw new RuntimeException(e);//programmer error, tests will fail
            }
        }
    }
}
