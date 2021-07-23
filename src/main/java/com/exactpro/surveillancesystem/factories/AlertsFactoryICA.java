package com.exactpro.surveillancesystem.factories;

import com.exactpro.surveillancesystem.entities.AlertICA;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.exactpro.surveillancesystem.utils.DateTimeUtils.nowDate;

@Service
public class AlertsFactoryICA{

    private static  List<AlertICA> resultAlerts;

    public static List<AlertICA> createEntities(Map<Integer, String> entityNameAlerts, Map<Integer, String> instrumentNameAlerts, ArrayList<Integer> affectedTransactionsCount) {
        resultAlerts = new ArrayList<>(affectedTransactionsCount.size());
        for (Integer data : entityNameAlerts.keySet()) {
            AlertICA alertsICA = new AlertICA();
            alertsICA.setAlertID("ICA"+nowDate()+data.toString());
            alertsICA.setAlertType("ICA");
            alertsICA.setDescription(entityNameAlerts.get(data), instrumentNameAlerts.get(data));
            alertsICA.setAffectedTransactionsCount(affectedTransactionsCount.get(data));
            resultAlerts.add(alertsICA);
        }
        return resultAlerts;
    }

    @GetMapping(value = "/get/alerts")
    public List<AlertICA> readAll() {
        return new ArrayList<>(resultAlerts);
    }
}
