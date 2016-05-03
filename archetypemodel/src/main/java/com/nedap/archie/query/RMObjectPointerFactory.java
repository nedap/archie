package com.nedap.archie.query;

import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.RMTypeInfo;
import org.apache.commons.jxpath.ri.JXPathContextReferenceImpl;
import org.apache.commons.jxpath.ri.model.beans.*;

import java.util.Locale;

import org.apache.commons.jxpath.JXPathBeanInfo;
import org.apache.commons.jxpath.JXPathIntrospector;
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.NodePointerFactory;

/**
 * Implements NodePointerFactory for Archie RM Objects.
 * @version $Revision$ $Date$
 */
public class RMObjectPointerFactory implements NodePointerFactory {

    /** factory order constant */
    public static final int RM_OBJECT_POINTER_FACTORY_ORDER = 850;
    private final ModelInfoLookup infoLookup;

    public static void register() {
        JXPathContextReferenceImpl.addNodePointerFactory(new RMObjectPointerFactory(ArchieRMInfoLookup.getInstance()));
    }

    public RMObjectPointerFactory(ModelInfoLookup infoLookup) {
        this.infoLookup = infoLookup;
    }

    public int getOrder() {
        return RM_OBJECT_POINTER_FACTORY_ORDER;
    }

    public NodePointer createNodePointer(QName name, Object bean, Locale locale) {
        if(bean instanceof RMObject) {
            getBeanInfo(bean.getClass());
            JXPathBeanInfo bi = getBeanInfo(bean.getClass());
            return bi == null ? null : new BeanPointer(name, bean, bi, locale);
        }
        return null;

    }

    private JXPathBeanInfo getBeanInfo(Class clazz) {
        //TODO when this actually works: cache this :)
        RMTypeInfo typeInfo = infoLookup.getTypeInfo(clazz);
        if(typeInfo == null) {
            return null;
        }
        return new RMJXPathBeanInfo(typeInfo);
    }

    public NodePointer createNodePointer(NodePointer parent, QName name, Object bean) {
        if (bean == null) {
            return new NullPointer(parent, name);
        }
        if(bean instanceof RMObject) {
            getBeanInfo(bean.getClass());
            JXPathBeanInfo bi = getBeanInfo(bean.getClass());
            return bi == null ? null : new BeanPointer(parent, name, bean, bi);
        }
        return null;
    }
}
