package com.kan.lib.pic;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LruCache中Lru算法的实现就是通过LinkedHashMap来实现的。LinkedHashMap继承于HashMap，它使用了一个双向
 * 链表来存储Map中的Entry顺序关系，这种顺序有两种，一种是LRU顺序，一种是插入顺序，这可以由其构造函数public
 * LinkedHashMap(int initialCapacity,float loadFactor, boolean accessOrder)指定。所以，对于get、put、
 * remove等操作，LinkedHashMap除了要做HashMap做的事情，还做些调整Entry顺序链表的工作。LruCache中将LinkedHashMap的
 * 顺序设置为LRU顺序来实现LRU缓存，每次调用get(也就是从内存缓存中取图片)，则将该对象移到链表的尾端。调用put插入新的对象
 * 也是存储在链表尾端，这样当内存缓存达到设定的最大值时，将链表头部的对象（近期最少用到的）移除。
 * Created by kan212 on 16/11/8.
 */
public class LruCache<K, V> {

    private final LinkedHashMap<K, V> map;

    private int size;
    private int maxSize;

    private int hitCount;
    private int missCount;
    private int createCount;
    private int evictionCount;
    private int putCount;

    public LruCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize;
        //将LinkedHashMap的accessOrder设置为true来实现LRU
        this.map = new LinkedHashMap<>(0, 0.75f, true);
    }

    public final V get(K key) {
        if (null == key) {
            throw new NullPointerException("key is null");
        }
        V mapValue;
        synchronized (this) {
            mapValue = map.get(key);
            if (null != mapValue) {
                hitCount++;
                return mapValue;
            }
            missCount++;
        }
        /**
         * 如果未命中，则试图创建一个对象，这里create方法返回null,并没有实现创建对象的方法
         * 如果需要事项创建对象的方法可以重写create方法。因为图片缓存时内存缓存没有命中会去
         * 文件缓存中去取或者从网络下载，所以并不需要创建。
         */

        V createdValue = create(key);
        if (null == createdValue) {
            return null;
        }
        synchronized (this) {
            createCount++;
            mapValue = map.put(key, createdValue);
            if (null != mapValue) {
                map.put(key, mapValue);
            } else {
                size += safeSizeOf(key, createdValue);
            }
        }
        if (null != mapValue) {
            entryRemoved(false, key, createdValue, mapValue);
            return mapValue;
        } else {
            //每次新加入对象都需要调用trimToSize方法看是否需要回收
            trimToSize(maxSize);
            return createdValue;
        }
    }

    public final V put(K key, V value) {
        if (null == key || null == value) {
            throw new NullPointerException("key == null || value == null");
        }
        V previous;
        synchronized (this) {
            putCount++;
            size += safeSizeOf(key, value);
            previous = map.put(key, value);
            if (null != previous) {
                size -= safeSizeOf(key, value);
            }
        }
        if (null != previous) {
            entryRemoved(false, key, previous, value);
        }
        //每次新加入对象都需要调用trimToSize方法看是否需要回收
        trimToSize(maxSize);
        return previous;
    }

    /**
     * 此方法根据maxSize来调整内存cache的大小，如果maxSize传入-1，则清空缓存中的所有对象
     *
     * @param maxSize
     */
    private void trimToSize(int maxSize) {
        while (true) {
            K key;
            V value;
            synchronized (this) {
                if (size < 0 || (map.isEmpty() && size != 0)) {
                    throw new IllegalStateException(getClass().getName()
                            + ".sizeOf() is reporting inconsistent results!");
                }
                if (size <= maxSize || map.isEmpty()) {
                    break;
                }
                Map.Entry<K, V> toEvict = map.entrySet().iterator().next();
                key = toEvict.getKey();
                value = toEvict.getValue();
                map.remove(key);
                size -= safeSizeOf(key, value);
                evictionCount++;
            }
            entryRemoved(true, key, value, null);
        }
    }

    public final V remove(K key) {
        if (null == key) {
            throw new NullPointerException("key == null");
        }
        V previous;
        synchronized (this) {
            previous = map.remove(key);
            if (null != previous) {
                size -= safeSizeOf(key, previous);
            }
        }
        if (null != previous) {
            entryRemoved(false, key, previous, null);
        }
        return previous;
    }

    /**
     * Called for entries that have been evicted or removed. This method is
     * invoked when a value is evicted to make space, removed by a call to
     * {@link #remove}, or replaced by a call to {@link #put}. The default
     * implementation does nothing.
     * <p/>
     * <p>The method is called without synchronization: other threads may
     * access the cache while this method is executing.
     *
     * @param evicted  true if the entry is being removed to make space, false
     *                 if the removal was caused by a {@link #put} or {@link #remove}.
     * @param newValue the new value for {@code key}, if it exists. If non-null,
     *                 this removal was caused by a {@link #put}. Otherwise it was caused by
     *                 an eviction or a {@link #remove}.
     */
    protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
    }


    private int safeSizeOf(K key, V value) {
        int result = sizeOf(key, value);
        if (result < 0) {
            throw new IllegalStateException("Negative size: " + key + "=" + value);
        }
        return result;
    }

    /**
     * Returns the size of the entry for {@code key} and {@code value} in
     * user-defined units.  The default implementation returns 1 so that size
     * is the number of entries and max size is the maximum number of entries.
     * <p/>
     * <p>An entry's size must not change while it is in the cache.
     */
    protected int sizeOf(K key, V value) {
        return 1;
    }

    /**
     * Clear the cache, calling {@link #entryRemoved} on each removed entry.
     */
    public final void evictAll() {
        trimToSize(-1); // -1 will evict 0-sized elements
    }

    /**
     * Called after a cache miss to compute a value for the corresponding key.
     * Returns the computed value or null if no value can be computed. The
     * default implementation returns null.
     * <p/>
     * <p>The method is called without synchronization: other threads may
     * access the cache while this method is executing.
     * <p/>
     * <p>If a value for {@code key} exists in the cache when this method
     * returns, the created value will be released with {@link #entryRemoved}
     * and discarded. This can occur when multiple threads request the same key
     * at the same time (causing multiple values to be created), or when one
     * thread calls {@link #put} while another is creating a value for the same
     * key.
     */
    protected V create(K key) {
        return null;
    }

    /**
     * Returns a copy of the current contents of the cache, ordered from least
     * recently accessed to most recently accessed.
     */
    public synchronized final Map<K, V> snapshot() {
        return new LinkedHashMap<K, V>(map);
    }

    @Override public synchronized final String toString() {
        int accesses = hitCount + missCount;
        int hitPercent = accesses != 0 ? (100 * hitCount / accesses) : 0;
        return String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]",
                maxSize, hitCount, missCount, hitPercent);
    }
}
