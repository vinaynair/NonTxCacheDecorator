package net.sf.ehcache.transaction;

import net.sf.ehcache.store.Store;

/**
 * Created by lorban on 24/04/14.
 */
public class UnderlyingStoreHelper {

  public static Store getUnderlyingStore(AbstractTransactionStore abstractTransactionStore) {
    Store s = abstractTransactionStore.underlyingStore;
      System.out.println("s="+s.getClass());
    while (s instanceof AbstractTransactionStore) {
      s = ((AbstractTransactionStore)s).underlyingStore;
        System.out.println("s again="+s.getClass());
    }
    return s;
  }

}
