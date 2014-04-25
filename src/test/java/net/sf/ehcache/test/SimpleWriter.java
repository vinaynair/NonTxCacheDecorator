package net.sf.ehcache.test;

import net.sf.ehcache.CacheEntry;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.writer.CacheWriter;
import net.sf.ehcache.writer.writebehind.operations.SingleOperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Hashtable;

/**
 * SimpleWriter that stores the entries in an hashtable (for unit testing purposes ONLY)
 * Created by vch on 1/5/14.
 */
public class SimpleWriter implements CacheWriter {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleWriter.class);
    public static Hashtable<Object, Object> ENTRIES = new Hashtable<Object, Object>();

    @Override

    public CacheWriter clone(Ehcache ehcache) throws CloneNotSupportedException {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public void dispose() throws CacheException {

    }

    @Override
    public void write(Element element) throws CacheException {
        LOG.info("Writing element[" + element + "]");
        ENTRIES.put(element.getObjectKey(), element.getObjectValue());

    }

    @Override
    public void writeAll(Collection<Element> elements) throws CacheException {
        LOG.info("Writing element[" + elements + "]");
        for (Element element : elements)
            ENTRIES.put(element.getObjectKey(), element.getObjectValue());

    }

    @Override
    public void delete(CacheEntry cacheEntry) throws CacheException {

    }

    @Override
    public void deleteAll(Collection<CacheEntry> cacheEntries) throws CacheException {

    }

    @Override
    public void throwAway(Element element, SingleOperationType singleOperationType, RuntimeException e) {

    }
}
