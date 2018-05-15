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
import scheduling.algorithms.EDF;
import scheduling.assets.IntegerField;

public class EDFController implements Initializable{
    
    private EDF edf = new EDF();

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
    private IntegerField deadline;

    @FXML
    private Label process;

    @FXML
    private Label time;

    @FXML
    void addProcess(ActionEvent event) {
        if(!priority.isEmpty() && !exexutionTime.isEmpty() && !deadline.isEmpty()){
            edf.addProcess(new EDF.Process(priority.getValue(), exexutionTime.getValue(), deadline.getValue(), processColor.getValue()));
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
        edf.reset();
        startButton.setDisable(false);
        pane.getChildren().clear();
    }

    @FXML
    void start(ActionEvent event) {
        if(!edf.isReadyEmpty()){
            startButton.setDisable(true);
            edf.start(pane);
            
        }
    }
    
    void clearCanavs(){
        time.setVisible(false);
        process.setVisible(false);
        pane.getChildren().clear();
        canvas.getGraphicsContext2D().clearRect(0, 0, 360, 200);
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
        processColor.setValue(Color.color(Math.random(), Math.random(), Math.random()));
        setCanvas();
    }

}
