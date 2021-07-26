//package com.exactpro.surveillancesystem.factories;
//
//import com.exactpro.surveillancesystem.entities.Alert;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import static com.exactpro.surveillancesystem.utils.DateTimeUtils.nowDate;
//
//@Service
//public class AlertsFactoryICA{
//
//    private static List<Alert> resultAlerts;
//
//    public static List<Alert> createEntities(Map<Integer, String> entityNameAlerts, Map<Integer, String> instrumentNameAlerts, ArrayList<Integer> affectedTransactionsCount) {
//        resultAlerts = new ArrayList<>(affectedTransactionsCount.size());
//        for (Integer data : entityNameAlerts.keySet()) {
//            Alert alertsICA = new Alert();
//            alertsICA.setAlertId("ICA"+nowDate()+data.toString());
//            alertsICA.setAlertType("ICA");
//            alertsICA.setDescriptionExecutionEntityName(entityNameAlerts.get(data));
//            alertsICA.setDescriptionInstrumentName(instrumentNameAlerts.get(data));
//            alertsICA.setAffectedTransactionsCount(affectedTransactionsCount.get(data));
//            resultAlerts.add(alertsICA);
//        }
//        return resultAlerts;
//    }
//
//    public List<Alert> getAlertsICA() {
//        return new ArrayList<>(resultAlerts);
//    }
//}
