package com.exactpro.surveillancesystem.factories;

import com.exactpro.surveillancesystem.entities.AlertsICA;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.exactpro.surveillancesystem.utils.ConvertDateTimeUtils.convertDateFormat;
import static com.exactpro.surveillancesystem.utils.ConvertDateTimeUtils.nowDate;

public class AlertsFactoryICA {
    public List<AlertsICA> createEntities(Map<Integer, String> entityNameAlerts, Map<Integer, String> instrumentNameAlerts, ArrayList<Integer> affectedTransactionsCount) {
        List<AlertsICA> resultPrices = new ArrayList<>(affectedTransactionsCount.size());
        for (Integer data : entityNameAlerts.keySet()) {
            AlertsICA alertsICA = new AlertsICA();
            alertsICA.setAlertID("ICA"+nowDate()+data);
            alertsICA.setAlertType("ICA");
            alertsICA.setDescription(entityNameAlerts.get(data), instrumentNameAlerts.get(data));
            alertsICA.setAffectedTransactionsCount(affectedTransactionsCount.get(data));
            resultPrices.add(alertsICA);
        }
        return resultPrices;
    }
}
