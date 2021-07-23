package com.exactpro.surveillancesystem.factories;

import java.text.ParseException;
import java.util.List;

public interface EntityFactory<T> {

    List<T> createEntities(List<String[]> rawData) throws ParseException;
}
