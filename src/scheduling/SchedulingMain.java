/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;

import java.util.HashMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Ahmed
 */
public class SchedulingMain extends Application {

    
    Screen screen = Screen.getScreen();
    
    private static AnchorPane root = new AnchorPane();
    
    @Override
    public void start(Stage stage) throws Exception {
        
        screen.setScreen(screen.HOME);
        
        Scene scene = new Scene(root);
        stage.setTitle("Scheduling Algorithms");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static class Screen {
    
        private static Screen onlyScreen = null;
        private final HashMap<String, String> screenLinks = new HashMap();
        // screen names
        public final String HOME = "Home";
        public final String ROUND_ROBIN = "RoundRobin";
        public final String PRIORITY_BASED = "PriorityBased";
        public final String RMS = "RMS";
        public final String EDF = "EDF";

        // constructor for saving screen name with typical screen link.
        private Screen() {
            screenLinks.put(HOME, "views/Home.fxml");
            screenLinks.put(ROUND_ROBIN, "views/RoundRobin.fxml");
            screenLinks.put(PRIORITY_BASED, "views/PriorityBased.fxml");
            screenLinks.put(RMS, "views/RMS.fxml");
            screenLinks.put(EDF, "views/EDF.fxml");
        }

        public static Screen getScreen() {
            if (onlyScreen == null) {
                onlyScreen = new Screen();
            }
            return onlyScreen;
        }

        public void setScreen(String screenName) {
            root.getChildren().clear();
            try {
                root.getChildren().add(loadScreen(screenLinks.get(screenName)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        private AnchorPane loadScreen(String screenLink) throws Exception {
            return FXMLLoader.load(getClass().getResource(screenLink));
        }
    }

}
