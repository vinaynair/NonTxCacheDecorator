package net.sf.ehcache.test;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.writer.CacheWriter;
import net.sf.ehcache.writer.CacheWriterFactory;

import java.util.Properties;

/**
 * Created by vch on 1/5/14.
 */
public class SimpleWriterFactory extends CacheWriterFactory {
    @Override
    public CacheWriter createCacheWriter(Ehcache ehcache, Properties properties) {
        return new SimpleWriter();
    }
}
