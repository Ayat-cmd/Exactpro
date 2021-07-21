package com.exactpro.surveillancesystem.API;

import com.exactpro.surveillancesystem.dao.AlertsDAO;
import com.exactpro.surveillancesystem.entities.AlertICA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ControllerAPI {

    @Autowired
    private final AlertsDAO alertsDAO;

    @Autowired
    public ControllerAPI(List<AlertICA> alertICA, AlertsDAO alertsDAO) {
        this.alertsDAO = alertsDAO;
    }

    @GetMapping()
    public List<AlertICA> index(Model model) {
        return this.alertsDAO.getDAO();
    }
}
