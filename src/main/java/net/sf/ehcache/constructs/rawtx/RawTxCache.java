package net.sf.ehcache.constructs.rawtx;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
import net.sf.ehcache.StoreHelper;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.constructs.EhcacheDecoratorAdapter;
import net.sf.ehcache.store.ElementValueComparator;
import net.sf.ehcache.store.Store;
import net.sf.ehcache.store.compound.ReadWriteCopyStrategy;
import net.sf.ehcache.store.compound.ReadWriteSerializationCopyStrategy;
import net.sf.ehcache.transaction.AbstractTransactionStore;
import net.sf.ehcache.transaction.UnderlyingStoreHelper;

import java.io.Serializable;
import java.util.*;

/**
 * Created by lorban on 24/04/14.
 * Updated by vinay on 25/04/14.
 * only put() and putAll() is supported by this decorator ( still experimental )
 */
public class RawTxCache extends EhcacheDecoratorAdapter {

    private final Store underlyingStore;
    private final ElementValueComparator elementValueComparator;
    private final ReadWriteCopyStrategy<Element> copyStrategy = new ReadWriteSerializationCopyStrategy();

    /**
     * Constructor accepting the cache to be decorated
     *
     * @param underlyingCache
     */
    public RawTxCache(Cache underlyingCache) {
        super(underlyingCache);
        Store store = StoreHelper.getStore(underlyingCache);
        if (!(store instanceof AbstractTransactionStore)) {
            throw new CacheException("Only transactional caches can be decorated with " + getClass().getSimpleName());
        }
        this.underlyingStore = UnderlyingStoreHelper.getUnderlyingStore((AbstractTransactionStore) store);
        CacheConfiguration underlyingCacheConfiguration = underlyingCache.getCacheConfiguration();
        this.elementValueComparator = underlyingCache.getCacheConfiguration().getElementValueComparatorConfiguration().createElementComparatorInstance(underlyingCacheConfiguration);
    }

    @Override
    public void put(Element element) throws IllegalArgumentException, IllegalStateException, CacheException {
        underlyingStore.put(copyStrategy.copyForWrite(element));
    }

    @Override
    public void putAll(Collection<Element> elements) throws IllegalArgumentException, IllegalStateException, CacheException {
        Collection<Element> elements2 = new ArrayList<Element>(elements.size());
        for (Element element : elements) elements2.add(copyStrategy.copyForWrite(element));
        underlyingStore.putAll(elements2);
    }

    @Override
    public void put(Element element, boolean doNotNotifyCacheReplicators) throws IllegalArgumentException, IllegalStateException, CacheException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putQuiet(Element element) throws IllegalArgumentException, IllegalStateException, CacheException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putWithWriter(Element element) throws IllegalArgumentException, IllegalStateException, CacheException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Element putIfAbsent(Element element) throws NullPointerException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Element putIfAbsent(Element element, boolean doNotNotifyCacheReplicators) throws NullPointerException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeElement(Element element) throws NullPointerException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean replace(Element old, Element element) throws NullPointerException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Element replace(Element element) throws NullPointerException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Element get(Serializable key) throws IllegalStateException, CacheException {
        return copyStrategy.copyForRead(underlyingStore.get(key));
    }

    @Override
    public Element get(Object key) throws IllegalStateException, CacheException {
        return copyStrategy.copyForRead(underlyingStore.get(key));
    }

    @Override
    public Map<Object, Element> getAll(Collection<?> keys) throws IllegalStateException, CacheException, NullPointerException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Element getQuiet(Serializable key) throws IllegalStateException, CacheException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Element getQuiet(Object key) throws IllegalStateException, CacheException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List getKeys() throws IllegalStateException, CacheException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List getKeysWithExpiryCheck() throws IllegalStateException, CacheException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List getKeysNoDuplicateCheck() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Serializable key) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object key) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAll(Collection<?> keys) throws IllegalStateException, NullPointerException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAll(Collection<?> keys, boolean doNotNotifyCacheReplicators) throws IllegalStateException, NullPointerException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Serializable key, boolean doNotNotifyCacheReplicators) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object key, boolean doNotNotifyCacheReplicators) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeQuiet(Serializable key) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeQuiet(Object key) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeWithWriter(Object key) throws IllegalStateException, CacheException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAll() throws IllegalStateException, CacheException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAll(boolean doNotNotifyCacheReplicators) throws IllegalStateException, CacheException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getSize() throws IllegalStateException, CacheException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getSizeBasedOnAccuracy(int statisticsAccuracy) throws IllegalArgumentException, IllegalStateException, CacheException {
        throw new UnsupportedOperationException();
    }

}
