//package com.exactpro.surveillancesystem.factories;
//
//import com.exactpro.surveillancesystem.entities.Alert;
//import com.exactpro.surveillancesystem.entities.Price;
//import com.exactpro.surveillancesystem.entities.Transaction;
//import org.springframework.stereotype.Service;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.exactpro.surveillancesystem.utils.DateTimeUtils.nowDate;
//
//@Service
//public class AlertsFactoryPPA {
//
//    private static List<Alert> alertPPAS;
//
//    public List<Alert> createEntities(List<Price> prices, List<Transaction> transactions, List<Integer> countTransactionsPrice) throws ParseException {
//        int id = 1;
//        int i = 0;
//        alertPPAS = new ArrayList<>();
//        for (Price findPrice : prices) {
//            Alert alertPPA = new Alert();
//            for (Transaction transaction : transactions) {
//                if (transaction.getCurrency().equals(findPrice.getCurrency()) && transaction.getInstrumentName().equals(findPrice.getInstrumentName())) {
//                    alertPPA.setDescriptionCurrentPercent(((transaction.getPrice() - findPrice.getAvgPrice()) / findPrice.getAvgPrice()) * 100);
//                    if (alertPPA.getDescriptionCurrentPercent() > 50) {
//                        alertPPA.setAlertId("PPA" + nowDate() + id);
//                        alertPPA.setAlertType("PPA");
//                        alertPPA.setDescriptionExecutionEntityName(transaction.getExecutionEntityName());
//                        alertPPA.setDescriptionInstrumentName(transaction.getInstrumentName());
//                        alertPPA.setDescriptionCurrency(transaction.getCurrency());
//                        alertPPA.setAffectedTransactionsCount(countTransactionsPrice.get(i));
//                        alertPPAS.add(alertPPA);
//                        i++;
//                        id++;
//                    }
//                }
//            }
//        }
//        return alertPPAS;
//    }
//
//    public List<Alert> getAlertPPAS() {
//        return new ArrayList<>(alertPPAS);
//    }
//}
