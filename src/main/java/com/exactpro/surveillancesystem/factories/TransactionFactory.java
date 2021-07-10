package com.exactpro.surveillancesystem.factories;

import com.exactpro.surveillancesystem.entities.Transaction;
import static com.exactpro.surveillancesystem.utils.ConvertDateTimeUtils.convertDateFormat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TransactionFactory implements EntityFactory<Transaction>{
    @Override
    public List<Transaction> createEntities(List<String[]> rawData) throws ParseException {
        List<Transaction> resultTransaction = new ArrayList<>();
        for (String[] data : rawData) {
            Transaction transaction = new Transaction();
            transaction.setTransaction_ID(Long.parseLong(data[0]));
            transaction.setExecution_entity_name(data[1]);
            transaction.setInstrument_name(data[2]);
            transaction.setInstrument_classification(data[3]);
            transaction.setQuantity(Integer.parseInt(data[4]));
            transaction.setPrice(Double.parseDouble(data[5]));
            transaction.setCurrency(data[6]);
            transaction.setDatestamp(convertDateFormat(data[7]));
            transaction.setNet_amount(Double.parseDouble(data[8]));
            resultTransaction.add(transaction);
        }
        return resultTransaction;
    }
}
