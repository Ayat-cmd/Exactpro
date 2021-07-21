package com.exactpro.surveillancesystem.service;

import java.util.Collection;

public interface Service<T>{
    void saveAll(Collection<T> entities);
}
