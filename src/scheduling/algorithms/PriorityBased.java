/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.PriorityQueue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author Ahmed
 */
public class PriorityBased {
    
    private PriorityQueue<Process> readyProcesses = new PriorityQueue();
    private Timeline timeline = new Timeline();
    private Process currentProcess;
    private int timeElipsed = 0;
    private KeyFrame frame;
    private int x = 0;
    
    public void addProcess(Process p){
        readyProcesses.add(p);
    }
    
    public Process getReadyProcess(){
        return readyProcesses.poll();
    }
    
    public boolean isEmpty(){
        return readyProcesses.isEmpty();
    }
    
    public void reset(){
        timeline.stop();
        timeline.getKeyFrames().clear();
        readyProcesses.clear();
        currentProcess.resetY();
        timeElipsed = 0;
        x = 0;
    }
    
    public void start(Pane p){
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        currentProcess = readyProcesses.poll();

        frame = new KeyFrame(Duration.seconds(1),e->{
            ++timeElipsed;
            // draw unit time if the process is still active.
            if(!currentProcess.isFinished())
                p.getChildren().add(currentProcess.getTimeUnit(x));
             
            x += 6;
            
            
            // if the current process finished , get another one when there is more.
            if(currentProcess.isFinished() && !readyProcesses.isEmpty())
                currentProcess = readyProcesses.poll();
                
            // check if there is ready process with higher priority.
            if(!currentProcess.isFinished() && !readyProcesses.isEmpty() && currentProcess.isLowerPriority(readyProcesses.peek())){
                readyProcesses.add(currentProcess);
                currentProcess = readyProcesses.poll();
            }
            // when the process finishes wait untill new one is ready.
        });
        
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }
    
    public static class Process implements Comparable<Process> {

        private int remainingTime;
        private final Color color;
        private int yAxis;
        private static int y = 150;
        private final Integer priority;
        private final int executionTime;

        public Process(int priority, int executionTime, Color color) {
            this.priority = priority;
            this.executionTime = executionTime;
            this.color = color;
            yAxis = y;
            remainingTime = executionTime;
            y -= 30;
        }

        public Integer getPriority() {
            return priority;
        }

        public boolean isLowerPriority(Process p) {
            return this.priority > p.getPriority();
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

        public void resetY() {
            y = 150;
        }

        // send the width as it changes every reset.
        public Rectangle getTimeUnit(int x) {
            // create a rectangle to indicate the passed unit time.
            Rectangle unit = new Rectangle(x, yAxis, 6, 20);
            unit.setFill(color);
            --remainingTime;
            return unit;
        }

        @Override
        public int compareTo(Process t) {
            return priority.compareTo(t.priority);
        }
    }
}
