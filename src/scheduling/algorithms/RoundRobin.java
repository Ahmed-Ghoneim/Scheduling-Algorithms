/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author Ahmed
 */
public class RoundRobin {
    
    private Queue<Process> readyProcesses = new LinkedList();
    private int timeElipsed = 0;
    private final int quantum;
    private Timeline timeline = new Timeline();
    private KeyFrame keyFrame;    
    private Process currentProcess;
    private int remainingQuntum;
    private int x = 0;
    private float timeUnitWidth;
    
    public RoundRobin(int quantum){
        this.quantum = quantum;
    }

    public RoundRobin(int quantum, Process[] processes){
        this.quantum = quantum;
        Collections.addAll(readyProcesses, processes);
    }
    
    // add new process to the ready process
    public void addProcess(Process newProcess){
        readyProcesses.add(newProcess);
    }
    
    // pause the current executing process by storing it at the end of queue.
    public void pauseProcess(Process curruntProcess){
        readyProcesses.add(curruntProcess);
    }
    
    // get the first process in the queue as it will be executing.
    public Process getReadyProcess(){
        return readyProcesses.remove();
    }
    
    public int getSize(){
        return readyProcesses.size();
    }
    
    public void reset(){
        readyProcesses.clear();
    }
    
    
    // start the algorithm. 
    public void start(Pane p){
        timeUnitWidth = 30/quantum;
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        // get the first ready process.
        currentProcess = getReadyProcess();
        // set the quantum.
        remainingQuntum = quantum;
        // set the key frame for timeline.
        keyFrame = new KeyFrame(Duration.seconds(1), (ActionEvent e) -> {
            // update total system time
            ++timeElipsed;
            // decrement the quantum every one second
            --remainingQuntum;
            // draw process unit time.            
            p.getChildren().add(currentProcess.getTimeUnit(x, timeUnitWidth));
            // move the start position of the rectangel to the next position
            x += 6;
  
            System.out.println("Quantum: " + remainingQuntum + " Elipsed: " + timeElipsed);
            
            // check if the curreunt process finished, and if there
            // more processes to be executed, then get the next process.
            if(currentProcess.isFinished() && !readyProcesses.isEmpty()){
                remainingQuntum = quantum;
                currentProcess = getReadyProcess();
            }
            // if the process finished and there are no more processes then,
            // set the quantum to -1 to exit the loop and finish the execution.
            if(currentProcess.isFinished() && readyProcesses.isEmpty()){
                remainingQuntum = -1;
                timeline.stop();
            }
            // if the quantum has finished, then pause the current process and get another one.
            if(remainingQuntum == 0){
                remainingQuntum = quantum;
                pauseProcess(currentProcess);
                currentProcess = getReadyProcess();
            }
        });
        
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

    }
    
    public static class Process {

        private int y = 100;
        private int remainingTime;
        private final Color color;
        private final int executionTime;

        public Process(int executionTime, Color color) {
            this.executionTime = executionTime;
            remainingTime = executionTime;
            this.color = color;
        }

        public int getExecutionTime() {
            return executionTime;
        }

        public int getRemainingTime() {
            return remainingTime;
        }

        public void timeUnitElipsed() {
            --remainingTime;
        }

        public boolean isFinished() {
            return remainingTime == 0;
        }

        public Color getColor() {
            return color;
        }

        // send the width as it changes every reset.
        public Rectangle getTimeUnit(int x, float width) {
            // create arectangle to indicate the passed unit time.
            Rectangle unit = new Rectangle(x, y, width, 20);
            unit.setFill(color);
            --remainingTime;
            return unit;
        }

    }
}
