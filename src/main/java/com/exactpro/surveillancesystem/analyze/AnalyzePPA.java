package com.exactpro.surveillancesystem.analyze;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.exactpro.surveillancesystem.db.CassandraConnector;
import com.exactpro.surveillancesystem.entities.AlertPPA;
import com.exactpro.surveillancesystem.entities.Price;
import com.exactpro.surveillancesystem.entities.Transaction;

import java.util.*;

import static com.exactpro.surveillancesystem.utils.DateTimeUtils.nowDate;

public class AnalyzePPA {
    private CassandraConnector connector;
    private List<Integer> countTransactionsPrice;
    private int countPrice = 1;
    private boolean flagAddOrNotAdd;
    private List<Double> average;
    int id = 0;

    public AnalyzePPA(CassandraConnector connector) {
        this.connector = connector;
        this.countTransactionsPrice = new ArrayList<>();
        this.countPrice = 1;
        this.average = new ArrayList<>();
    }

    public List<AlertPPA> PPA() {
        String queryTransactions = "SELECT execution_entity_name, instrument_name, currency, price FROM ayat.transactions;";
        String queryPrices = "SELECT instrument_name, currency, avg_price FROM ayat.prices";
        ResultSet resultSetTransactions = connector.execute(queryTransactions);
        ResultSet resultSetPrices = connector.execute(queryPrices);
        List<Transaction> transactions = new ArrayList<>();
        for (Row alert : resultSetTransactions) {
            Transaction transaction = new Transaction();
            flagAddOrNotAdd = true;
            transaction.setExecutionEntityName(alert.getString("execution_entity_name"));
            transaction.setInstrumentName(alert.getString("instrument_name"));
            transaction.setCurrency(alert.getString("currency"));
            transaction.setPrice(alert.getDouble("price"));

            if (transaction.getCurrency().length() != 3) {
                continue;
            }
            transactions = findTransaction(transactions, transaction);
            if (flagAddOrNotAdd) {
                addPPA(transactions, transaction);
            }
        }

        id = 0;
        for (Transaction transaction : transactions) {
            transaction.setPrice(transaction.getPrice() / countTransactionsPrice.get(id));
            id++;
        }

        List<Price> prices = new ArrayList<>();
        for (Row alertPrice : resultSetPrices) {
            flagAddOrNotAdd = true;
            Price price = new Price();
            price.setInstrumentName(alertPrice.getString("instrument_name"));
            price.setCurrency(alertPrice.getString("currency"));
            price.setAvgPrice(alertPrice.getDouble("avg_price"));
            findPrice(prices, price);
            if (flagAddOrNotAdd) {
                addPricesToCollection(prices, price);
            }
        }

        id = 1;
        int i = 0;
        List<AlertPPA> alertPPAS = new ArrayList<>();
        for (Price findPrice : prices) {
            AlertPPA alertPPA = new AlertPPA();
            for (Transaction transaction : transactions) {
                if (transaction.getCurrency().equals(findPrice.getCurrency()) && transaction.getInstrumentName().equals(findPrice.getInstrumentName())) {
                    alertPPA.setDescriptionCurrentPercent(((transaction.getPrice() - findPrice.getAvgPrice()) / findPrice.getAvgPrice()) * 100);
                    if (alertPPA.getDescriptionCurrentPercent() > 50) {
                        alertPPA.setAlertId("PPA" + nowDate() + id);
                        alertPPA.setAlertType("PPA");
                        alertPPA.setDescriptionExecutionEntityName(transaction.getExecutionEntityName());
                        alertPPA.setDescriptionInstrumentName(transaction.getInstrumentName());
                        alertPPA.setDescriptionCurrency(transaction.getCurrency());
                        alertPPA.setAffectedTransactionsCount(countTransactionsPrice.get(i));
                        alertPPAS.add(alertPPA);
                        i++;
                    }
                }
            }
        }
        return alertPPAS;
    }

    private List<Price> addPricesToCollection(List<Price> prices, Price price) {
        prices.add(price);
        return prices;
    }

    private List<Transaction> addPPA(List<Transaction> transactions, Transaction transaction) {
        transactions.add(transaction);
        countTransactionsPrice.add(countPrice);
        return transactions;
    }

    private List<Transaction> findTransaction(List<Transaction> transactions, Transaction transaction) {

        int i = 0;
        for(Transaction listTransaction : transactions) {
            String listTransactionExecutionEntityName = listTransaction.getExecutionEntityName();
            String listTransactionInstrumentName = listTransaction.getInstrumentName();
            String listTransactionCurrency = listTransaction.getCurrency();
            String transactionExecutionEntityName = transaction.getExecutionEntityName();
            String transactionInstrumentName = transaction.getInstrumentName();
            String transactionCurrency = transaction.getCurrency();
            if (listTransactionExecutionEntityName.equals(transactionExecutionEntityName) && listTransactionInstrumentName.equals(transactionInstrumentName) && listTransactionCurrency.equals(transactionCurrency)) {
                countTransactionsPrice.set(i, countTransactionsPrice.get(i) + 1);
                listTransaction.setPrice(listTransaction.getPrice() + transaction.getPrice());
                flagAddOrNotAdd = false;
            }
            i++;
        }
        return transactions;
    }
    private List<Price> findPrice(List<Price> prices, Price price) {
        for(Price findPrice : prices) {
            if(price.getCurrency().equals(findPrice.getCurrency()) && price.getInstrumentName().equals(findPrice.getInstrumentName())) {
                findPrice.setAvgPrice(findPrice.getAvgPrice() + price.getAvgPrice());
                flagAddOrNotAdd = false;
            }
        }
        return prices;
    }
}
