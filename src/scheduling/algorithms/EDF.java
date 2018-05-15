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
public class EDF {
    
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
    
    private Process getReadyProcess(){
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
    
    private void pauseProcess(Process p){
        readyProcesses.add(p);
    }
    
    private void checkFinishedProcesses(){
        
        readyProcesses.forEach((p)->{
            p.decrementRemainingDeadline();
        });
        
        finishedProcesses.forEach((p)->{
            if(timeElapsed % p.getPeriod() == 0){
                finishedProcesses.remove(p);
                readyProcesses.add(p.reset());
            }
        });
    }
    
    private void addFinished(Process p){
        p.setAdded(true);
       finishedProcesses.add(p);
    }
    
    private boolean isHigherPriorityArrived(){
        return !isReadyEmpty() && currentProcess.isLowerPriority(readyProcesses.peek());
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
            
            // check if there is 
            checkFinishedProcesses();
            
            // if the current process finished, get another one when there is more.
            if(currentProcess.isFinished() && !currentProcess.isAdded()){
                addFinished(currentProcess);
                if(!isReadyEmpty())
                    currentProcess = getReadyProcess();
            }
            
            if(isHigherPriorityArrived()){
                pauseProcess(currentProcess);
                currentProcess = getReadyProcess();
            }
            
            if(timeElapsed >= 24)   timeline.stop();

        });
        
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }
    
    
    public static class Process implements Comparable<Process>{
    
    private boolean added = false;
    private static int y = 150;
    private int remainingTime;
    private final Color color;
    private int yAxis;
    private final Integer period;
    private final Integer deadline;
    private Integer remainingDeadline;
    private final Integer executionTime;
        
    
    public Process(int period, int executionTime, int deadline, Color color){
        
        this.executionTime = executionTime;
        this.deadline = deadline;
        this.period = period;
        this.color = color;
        yAxis = y;
        remainingTime = executionTime;
        remainingDeadline = deadline;
        y -= 30;
    }

    Integer getPeriod() {
        return period;
    }
    
    boolean isHigherPeriod(Process p){
        return this.period > p.getPeriod();
    }

    int getRemainingDeadline() {
        return remainingDeadline;
    }

    boolean isAdded() {
        return added;
    }

    void setAdded(boolean added) {
        this.added = added;
    }
    
    boolean isPeriodPassed(int timeElapsed){
        return (timeElapsed % period == 0);
    }
    
    boolean isFinished(){
        return remainingTime <= 0;
    }

    void decrementRemainingDeadline(){
        --remainingDeadline;
    }
    
    static void resetY(){
        y = 150;
    }
    
    boolean isLowerPriority(Process p){
        return this.remainingDeadline > p.remainingDeadline;
    }
    
    Process reset(){
        this.remainingTime = executionTime;
        this.remainingDeadline = deadline;
        this.added = false;
        return this;
    }
    
    // send the width as it changes every reset.
    Rectangle getTimeUnit(int x){
        // create a rectangle to indicate the passed unit time.
        Rectangle unit = new Rectangle(x, yAxis, 15, 20);
        unit.setFill(color);
        --remainingDeadline;
        --remainingTime;
        return unit;
    }

    @Override
    public int compareTo(Process t) {
        return remainingDeadline.compareTo(t.remainingDeadline);
    }
    
}
    
}
