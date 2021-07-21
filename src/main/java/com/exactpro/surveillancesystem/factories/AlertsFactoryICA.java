package com.exactpro.surveillancesystem.factories;

import com.exactpro.surveillancesystem.entities.AlertICA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.exactpro.surveillancesystem.utils.DateTimeUtils.nowDate;

public class AlertsFactoryICA{
    public static List<AlertICA> createEntities(Map<Integer, String> entityNameAlerts, Map<Integer, String> instrumentNameAlerts, ArrayList<Integer> affectedTransactionsCount) {
        List<AlertICA> resultPrices = new ArrayList<>(affectedTransactionsCount.size());
        for (Integer data : entityNameAlerts.keySet()) {
            AlertICA alertsICA = new AlertICA();
            alertsICA.setAlertID("ICA"+nowDate()+data);
            alertsICA.setAlertType("ICA");
            alertsICA.setDescription(entityNameAlerts.get(data), instrumentNameAlerts.get(data));
            alertsICA.setAffectedTransactionsCount(affectedTransactionsCount.get(data));
            resultPrices.add(alertsICA);
        }
        return resultPrices;
    }
}
