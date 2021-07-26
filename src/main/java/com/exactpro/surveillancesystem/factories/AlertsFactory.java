package com.exactpro.surveillancesystem.factories;

import com.exactpro.surveillancesystem.entities.Alert;
import com.exactpro.surveillancesystem.entities.Price;
import com.exactpro.surveillancesystem.entities.Transaction;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.exactpro.surveillancesystem.utils.DateTimeUtils.nowDate;

@Service
public class AlertsFactory {
    private static List<Alert> resultAlerts = new ArrayList<>();;

    public static List<Alert> createEntitiesICA(Map<Integer, String> entityNameAlerts, Map<Integer, String> instrumentNameAlerts, ArrayList<Integer> affectedTransactionsCount) {
        for (Integer data : entityNameAlerts.keySet()) {
            Alert alertsICA = new Alert();
            alertsICA.setAlertId("ICA"+nowDate()+data.toString());
            alertsICA.setAlertType("ICA");
            alertsICA.setDescription("Currency field is incorrect for the combination of " + entityNameAlerts.get(data) + " and " + instrumentNameAlerts.get(data));
            alertsICA.setAffectedTransactionsCount(affectedTransactionsCount.get(data));
            resultAlerts.add(alertsICA);
        }
        return resultAlerts;
    }

    public static List<Alert> createEntitiesPPA(List<Price> prices, List<Transaction> transactions, List<Integer> countTransactionsPrice) throws ParseException {
        int id = 1;
        int i = 0;
        double percent;
        for (Price findPrice : prices) {
            Alert alertPPA = new Alert();
            for (Transaction transaction : transactions) {
                if (transaction.getCurrency().equals(findPrice.getCurrency()) && transaction.getInstrumentName().equals(findPrice.getInstrumentName())) {
                    percent =((transaction.getPrice() - findPrice.getAvgPrice()) / findPrice.getAvgPrice()) * 100;
                    if (percent> 50) {
                        alertPPA.setAlertId("PPA" + nowDate() + id);
                        alertPPA.setAlertType("PPA");
                        alertPPA.setDescription("Potential pumping price activity has been noticed for the following combination of "+transaction.getExecutionEntityName() +
                                ", "+transaction.getInstrumentName()+" and "+transaction.getCurrency()+" where an average price is greater " +
                                "than previous more than 50% and is "+percent);
                        alertPPA.setAffectedTransactionsCount(countTransactionsPrice.get(i));
                        resultAlerts.add(alertPPA);
                        i++;
                        id++;
                    }
                }
            }
        }
        return resultAlerts;
    }

    public List<Alert> getAlerts() {
        return new ArrayList<>(resultAlerts);
    }
}
