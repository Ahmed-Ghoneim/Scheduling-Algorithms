/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.controllers;

import scheduling.SchedulingMain;

/**
 *
 * @author Ahmed
 */
public class ScreenController {
    
    private static SchedulingMain.Screen screen = SchedulingMain.Screen.getScreen();
    
    public static void setHome(){
        screen.setScreen(screen.HOME);
    }
    
    public static void setEDF(){
        screen.setScreen(screen.EDF);
    }
    
    public static void setRMS(){
        screen.setScreen(screen.RMS);
    }
    
    public static void setPriorityBased(){
        screen.setScreen(screen.PRIORITY_BASED);
    }
    
    public static void setRoundRobin(){
        screen.setScreen(screen.ROUND_ROBIN);
    }
}
