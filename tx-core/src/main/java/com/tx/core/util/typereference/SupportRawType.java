package com.tx.core.util.typereference;

import java.lang.reflect.Type;

public interface SupportRawType<T> {
    
    /**
     * @return 返回 rawTypeInit
     */
    public abstract Type getRawType();
    
    /**
     * @return 返回 rawTypeInit
     */
    public abstract Class<?> getType();
    
}