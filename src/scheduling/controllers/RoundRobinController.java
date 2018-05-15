package scheduling.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import scheduling.algorithms.RoundRobin;
import scheduling.assets.IntegerField;

public class RoundRobinController {
    
    private RoundRobin round;
    
    @FXML
    private AnchorPane pane;

    @FXML
    private Canvas canvas;
    
    @FXML
    private Label process;

    @FXML
    private Label time;
    
    @FXML
    private Button setButton;
    
    @FXML
    private Button addButton;
    
    @FXML
    private ColorPicker processColor;
    
    @FXML
    private IntegerField quantum;

    @FXML
    private IntegerField exexutionTime;
            
    
    @FXML
    void setQuantum(ActionEvent event) {
        if(!quantum.getText().isEmpty()){
            round = new RoundRobin(quantum.getValue());
            setButton.setDisable(true);
            quantum.setDisable(true);
            setCanvas(canvas, quantum.getValue());
        }
    }

    @FXML
    void addProcess(ActionEvent event) {
        if(!exexutionTime.getText().isEmpty()){
            round.addProcess(new RoundRobin.Process(exexutionTime.getValue(), processColor.getValue()));
            exexutionTime.clear();
            processColor.setValue(Color.color(Math.random(), Math.random(), Math.random()));
        }
    }

    @FXML
    void start(ActionEvent event) {
        if(quantum.isDisabled() && round.getSize()>0){
            addButton.setDisable(true);
            round.start(pane);
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
        round.reset();
        setButton.setDisable(false);
        quantum.setDisable(false);
        addButton.setDisable(false);
        process.setVisible(false);
        time.setVisible(false);
        canvas.getGraphicsContext2D().clearRect(0, 0, 360, 200);
        pane.getChildren().clear();
    }
  
    void setCanvas(Canvas c, final int q) {
        
        process.setVisible(true);
        time.setVisible(true);
        
        int scale = 0;
        int width = (int) c.getWidth();
        int height = (int) c.getHeight();
        
        GraphicsContext gc = c.getGraphicsContext2D() ;
        gc.setLineWidth(.7);
        
        for (int x = 0; x < width; x+=30, scale+=q) {
            
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
}

  

