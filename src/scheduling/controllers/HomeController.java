/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author Ahmed
 */
public class HomeController implements Initializable {
    

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void loadEDF(ActionEvent event) {
        ScreenController.setEDF();
    }

    @FXML
    void loadPriorityBased(ActionEvent event) {
        ScreenController.setPriorityBased();
    }

    @FXML
    void loadRMS(ActionEvent event) {
        ScreenController.setRMS();
    }

    @FXML
    void loadRoundRobin(ActionEvent event) {
        ScreenController.setRoundRobin();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
