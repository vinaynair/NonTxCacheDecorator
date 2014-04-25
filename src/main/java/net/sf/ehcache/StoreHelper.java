package net.sf.ehcache;

import net.sf.ehcache.store.Store;

/**
 * Created by lorban on 24/04/14.
 */
public class StoreHelper {

  public static Store getStore(Cache cache) {
    return cache.getStore();
  }

}
