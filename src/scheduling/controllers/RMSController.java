package scheduling.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import scheduling.algorithms.RMS;
import scheduling.assets.IntegerField;

public class RMSController implements Initializable{
    
    private RMS pBased = new RMS();

    @FXML
    private IntegerField priority;

    @FXML
    private IntegerField exexutionTime;

    @FXML
    private ColorPicker processColor;

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane pane;
    
    @FXML
    private Button startButton;

    @FXML
    private Label process;

    @FXML
    private Label time;

    @FXML
    void addProcess(ActionEvent event) {
        if(!priority.isEmpty() && !exexutionTime.isEmpty()){
            pBased.addProcess(new RMS.Process(priority.getValue(), exexutionTime.getValue(), processColor.getValue()));
            processColor.setValue(Color.color(Math.random(), Math.random(), Math.random()));
        }
        
    }

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    void backToHome(ActionEvent event) {
        ScreenController.setHome();
    }

    @FXML
    void reset(ActionEvent event) {
        pBased.reset();
        startButton.setDisable(false);
        pane.getChildren().clear();
    }

    @FXML
    void start(ActionEvent event) {
        if(!pBased.isReadyEmpty()){
            startButton.setDisable(true);
            pBased.start(pane);
            
        }
    }
    
    void setCanvas() {
        
        int scale = 0;
        time.setVisible(true);
        process.setVisible(true);
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();
        
        GraphicsContext gc = canvas.getGraphicsContext2D() ;
        gc.setLineWidth(.7);
        
        for (int x = 0; x < width; x+=30, scale += 2) {
            
            double x1 = x + 0.5 ;
            
            gc.moveTo(x1, 20);
            gc.lineTo(x1, height-15);
            gc.stroke();
            
            gc.fillText(String.valueOf(scale), x1-2, height);
        }
        
        gc.moveTo(0, height-15 + .5);
        gc.lineTo(width, height-15 + .5);
        gc.stroke();
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCanvas();
    }

}
