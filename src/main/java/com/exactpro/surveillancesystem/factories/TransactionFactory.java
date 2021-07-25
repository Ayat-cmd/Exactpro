package com.exactpro.surveillancesystem.factories;

import com.exactpro.surveillancesystem.entities.Transaction;
import org.springframework.stereotype.Service;

import static com.exactpro.surveillancesystem.utils.DateTimeUtils.convertDateFormat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionFactory implements EntityFactory<Transaction>{
    private static List<Transaction> resultTransaction;

    @Override
    public List<Transaction> createEntities(List<String[]> rawData) throws ParseException {
        resultTransaction = new ArrayList<>(rawData.size());
        for (String[] data : rawData) {
            Transaction transaction = new Transaction();
            transaction.setTransactionID(Long.parseLong(data[0]));
            transaction.setExecutionEntityName(data[1]);
            transaction.setInstrumentName(data[2]);
            transaction.setInstrumentClassification(data[3]);
            transaction.setQuantity(Integer.parseInt(data[4]));
            transaction.setPrice(Double.parseDouble(data[5]));
            transaction.setCurrency(data[6]);
            transaction.setDatestamp(convertDateFormat(data[7]));
            transaction.setNetAmount(Double.parseDouble(data[8]));
            resultTransaction.add(transaction);
        }
        return resultTransaction;
    }

    public List<Transaction> getAlertsTransactions() {
        return new ArrayList<>(resultTransaction);
    }
}
