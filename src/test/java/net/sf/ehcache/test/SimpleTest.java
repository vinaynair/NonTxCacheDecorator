package net.sf.ehcache.test;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.rawtx.RawTxCache;
import net.sf.ehcache.transaction.TransactionException;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by lorban on 24/04/14.
 * Updated by vinay on 25/04/14.
 */
public class SimpleTest {

    @org.junit.Test
    public void testSimpleRawTxCache() throws Exception {
        TransactionManagerServices.getConfiguration().setServerId("testSimpleRawTxCache").setJournal("null");
        BitronixTransactionManager transactionManager = TransactionManagerServices.getTransactionManager();


        CacheManager cacheManager = CacheManager.create();

        Cache txCache = cacheManager.getCache("xaCacheOne");
        Ehcache simpleRawTxCache = new RawTxCache(txCache);

        try {
            txCache.put(new Element(1, "ooo"));
            fail("expected TransactionException");
        } catch (TransactionException te) {
            // expected
        }

        //put() test
        simpleRawTxCache.put(new Element(1, "one"));
        assertThat((String) simpleRawTxCache.get(1).getObjectValue(), is("one"));

        transactionManager.begin();
        assertThat((String) txCache.get(1).getObjectValue(), is("one"));
        transactionManager.commit();

        //putAll() test
        //put using decorator directly into store
        Collection<Element> elements = new ArrayList<Element>();
        for (int i = 0; i < 10; i++) {
            elements.add(new Element("key-" + i, "value-" + i));
        }
        simpleRawTxCache.putAll(elements);

        //get from XA cache
        transactionManager.begin();
        for (int i = 0; i < 10; i++) {
            assertThat((String) txCache.get("key-" + i).getObjectValue(), is("value-" + i));
        }
        transactionManager.commit();


        //direct put & get on XA (sanity test)
        transactionManager.begin();
        for (int i = 0; i < 10; i++) {
            txCache.put(new Element("key-" + i, "value2-" + i));
        }
        for (int i = 0; i < 10; i++) {
            assertThat((String) txCache.get("key-" + i).getObjectValue(), is("value2-" + i));
        }
        transactionManager.commit();


        //direct put & get on XA (sanity test)
        transactionManager.begin();
        for (int i = 0; i < 20; i++) {
            txCache.putWithWriter(new Element("key-" + i, "value3-" + i));
        }
        transactionManager.commit();
        Thread.sleep(5000);
        assertThat(SimpleWriter.ENTRIES.size(), is(20));


        cacheManager.shutdown();
        transactionManager.shutdown();
    }
}
