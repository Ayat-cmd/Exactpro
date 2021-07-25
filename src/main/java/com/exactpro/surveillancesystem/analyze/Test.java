package com.exactpro.surveillancesystem.analyze;

import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.shaded.guava.common.collect.HashMultimap;
import com.datastax.oss.driver.shaded.guava.common.collect.Lists;
import com.datastax.oss.driver.shaded.guava.common.collect.Multimap;
import com.exactpro.surveillancesystem.db.CassandraConnector;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.exactpro.surveillancesystem.entities.Transaction;
import jnr.ffi.annotations.In;

import java.util.*;

public class Test {

    private CassandraConnector connector;
    private String executionEntityName;
    private String instrumentName;
    private String currency;
    private double price;
    private double avgPrice;
    private int idTransactions;
    private ArrayList<Double> priceTransactions;
    private HashMultimap<Integer, String> multimapTransactions;
    private List<Integer> countTransactionsPrice;
    private int countPrice = 1;
    private boolean flagAddOrNotAdd;
    private List<Double> average;

    Map<Integer, String> mapInstrumentName;
    Map<Integer, String> mapCurrency;
    List<Double> listAvgPrice;
    int id = 0;

    public Test(CassandraConnector connector) {
        this.connector = connector;
        this.idTransactions = 0;
        this.priceTransactions = new ArrayList<>();
        this.multimapTransactions = HashMultimap.create();
        this.countTransactionsPrice = new ArrayList<>();
        this.countPrice = 1;


        this.average = new ArrayList<>();
        this.mapInstrumentName = new HashMap<>();
        this.mapCurrency = new HashMap<>();
        this.listAvgPrice = new ArrayList<>();
    }

    public void PPA() {
        String queryTransactions = "SELECT execution_entity_name, instrument_name, currency, price FROM ayat.transactions;";
        String queryPrices = "SELECT instrument_name, currency, avg_price FROM ayat.prices";
        ResultSet resultSetTransactions = connector.execute(queryTransactions);
        ResultSet resultSetPrices = connector.execute(queryPrices);

        for(Row alert : resultSetTransactions) {
            flagAddOrNotAdd = true;
            executionEntityName = alert.getString("execution_entity_name");
            instrumentName = alert.getString("instrument_name");
            currency = alert.getString("currency");
            price = alert.getDouble("price");

            if(currency.length() != 3) {
                continue;
            }

            if(!multimapTransactions.containsValue(executionEntityName) || !multimapTransactions.containsValue(instrumentName)  || !multimapTransactions.containsValue(currency)){
                addPPA();
            } else {
                findTransaction();
                if(flagAddOrNotAdd) { addPPA(); }
            }
        }

        for(Integer i : multimapTransactions.keySet()) {
            System.out.println(multimapTransactions.get(i));
            System.out.println(priceTransactions.get(i));
            System.out.println(countTransactionsPrice.get(i));
        }

//        for(Integer i : multimapTransactions.keySet()) {
//            prices.set(i, prices.get(i)/countTransactionsPrice.get(i));
//        }


        for(Row alertPrice : resultSetPrices) {
            flagAddOrNotAdd = true;
            instrumentName = alertPrice.getString("instrument_name");
            currency = alertPrice.getString("currency");
            avgPrice = alertPrice.getDouble("avg_price");
            if(!mapInstrumentName.containsValue(instrumentName) || !mapCurrency.containsValue(currency)) {
                addPricesToCollection();
            } else {
                for(Integer findValue : mapCurrency.keySet()) {
                    if(currency.equals(mapCurrency.get(findValue)) && instrumentName.equals(mapInstrumentName.get(findValue))) {
                        listAvgPrice.set(findValue, listAvgPrice.get(findValue)+avgPrice);
                        flagAddOrNotAdd = false;
                    }
                }
                if(flagAddOrNotAdd) { addPricesToCollection(); }
            }
        }

        id=0;
//        List<>
        for(Integer findPrice : mapCurrency.keySet()) {
            for(Map.Entry<Integer, Collection<String>> entry : multimapTransactions.asMap().entrySet()) {
                if(entry.getValue().contains(mapCurrency.get(findPrice)) && entry.getValue().contains(mapInstrumentName.get(findPrice))) {
                    average.add(((priceTransactions.get(entry.getKey()) - listAvgPrice.get(findPrice))/listAvgPrice.get(findPrice)) * 100);
                }
            }
        }

//        for(Integer i : mapCurrency.keySet()) {
//            System.out.print(mapInstrumentName.get(i)+" ");
//            System.out.print(mapCurrency.get(i)+" ");
//            System.out.println(listAvgPrice.get(i)+" ");
//        }

    }

    private void addPricesToCollection() {
        mapInstrumentName.put(id, instrumentName);
        mapCurrency.put(id, currency);
        listAvgPrice.add(avgPrice);
        id++;
    }

    private void addPPA() {
        multimapTransactions.putAll(idTransactions, Lists.newArrayList(executionEntityName, instrumentName, currency));
        priceTransactions.add(price);
        countTransactionsPrice.add(countPrice);
        idTransactions++;
    }

    private void findTransaction() {
        for(Map.Entry<Integer, Collection<String>> entry : multimapTransactions.asMap().entrySet()){
            if(entry.getValue().contains(executionEntityName) && entry.getValue().contains(instrumentName) && entry.getValue().contains(currency)){
                countTransactionsPrice.set(entry.getKey(), countTransactionsPrice.get(entry.getKey())+1);
                priceTransactions.set(entry.getKey(), priceTransactions.get(entry.getKey()) + price);
                flagAddOrNotAdd = false;
            }
        }
    }
}
