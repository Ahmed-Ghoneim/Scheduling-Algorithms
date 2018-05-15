/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.concurrent.PriorityBlockingQueue;
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
public class RMS {
    
    private PriorityBlockingQueue<Process> readyProcesses = new PriorityBlockingQueue();
    private PriorityBlockingQueue<Process> finishedProcesses = new PriorityBlockingQueue();
    private Timeline timeline = new Timeline();
    private Process currentProcess;
    private int timeElapsed = 0;
    private KeyFrame frame;
    private int x = 0;
    
    public void addProcess(Process p){
        readyProcesses.add(p);
    }
    
    public Process getReadyProcess(){
        return readyProcesses.poll();
    }
    
    public boolean isReadyEmpty(){
        return readyProcesses.isEmpty();
    }
    
    public boolean isFinishedEmpty(){
        return finishedProcesses.isEmpty();
    }
    
    public void reset(){
        timeline.stop();
        timeline.getKeyFrames().clear();
        finishedProcesses.clear();
        readyProcesses.clear();
        Process.resetY();
        timeElapsed = 0;
        x = 0;
    }
    
    public void pauseProcess(Process p){
        p.setAdded(true);
        readyProcesses.add(p);
    }
    
    public void checkPeriodPassed(){
        finishedProcesses.forEach((p)->{
            if(timeElapsed % p.getPeriod() == 0){
                finishedProcesses.remove(p);
                readyProcesses.add(p.reset());
            }
        });
    }
    
    public void addFinished(Process p){
        p.setAdded(true);
       finishedProcesses.add(p);
    }
    
    public boolean isHigherPriorityArrived(){
        return !isReadyEmpty() && currentProcess.isLowerPriority(readyProcesses.peek()) && readyProcesses.peek().isPeriodPassed(timeElapsed);
    }
    
    public void start(Pane p){
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        currentProcess = readyProcesses.poll();

        frame = new KeyFrame(Duration.seconds(1),e->{
            ++timeElapsed;
            // draw unit time if the process is still active.
            if(!currentProcess.isFinished())
                p.getChildren().add(currentProcess.getTimeUnit(x));
             
            x += 15;
            
            checkPeriodPassed();
            // if the current process finished, get another one when there is more.
            if(currentProcess.isFinished() && !currentProcess.isAdded()){
                addFinished(currentProcess);
                if(!isReadyEmpty())
                    currentProcess = getReadyProcess();
            }
            
            // check if there is ready process with higher priority.
            if(isHigherPriorityArrived()){
                pauseProcess(currentProcess);
                currentProcess = getReadyProcess();
            }
            
            if(timeElapsed >= 24)   timeline.stop();

        });
        
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }
    
    
    public static class Process implements Comparable<Process> {

        private int remainingTime;
        private final Color color;
        private boolean added = false;
        private int yAxis;
        private static int y = 150;
        private final Integer period;
        private final int executionTime;

        public Process(int period, int executionTime, Color color) {
            this.period = period;
            this.executionTime = executionTime;
            this.color = color;
            yAxis = y;
            remainingTime = executionTime;
            y -= 30;
        }

        public Integer getPeriod() {
            return period;
        }

        public boolean isLowerPriority(Process p) {
            return this.period > p.getPeriod();
        }

        public int getExecutionTime() {
            return executionTime;
        }

        public int getRemainingTime() {
            return remainingTime;
        }

        public boolean isAdded() {
            return added;
        }

        public void setAdded(boolean added) {
            this.added = added;
        }

        public boolean isPeriodPassed(int timeElapsed) {
            return (timeElapsed % period == 0);
        }

        public boolean isFinished() {
            return remainingTime == 0;
        }

        public Color getColor() {
            return color;
        }

        public static void resetY() {
            y = 150;
        }

        public Process reset() {
            this.remainingTime = executionTime;
            this.added = false;
            return this;
        }

        // send the width as it changes every reset.
        public Rectangle getTimeUnit(int x) {
            // create a rectangle to indicate the passed unit time.
            Rectangle unit = new Rectangle(x, yAxis, 15, 20);
            unit.setFill(color);
            --remainingTime;
            return unit;
        }

        @Override
        public int compareTo(Process t) {
            return period.compareTo(t.period);
        }

    }
}
