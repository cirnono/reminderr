import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application{
    //window size
    private static double VIEWER_WIDTH = 1000.0;
    private static double VIEWER_HEIGHT = 700.0;

    /*root -- welcome
           --
     */
    private final Group root = new Group();

    // settings
    private String username = "主人";
    private String[] language = {"中文", "English"};

    // initial welcome scene
    private void makeWelcome(){
        root.getChildren().clear();
        Text title = new Text("欢迎回来，主人");
        title.setStyle("-fx-font: 100 arial;");
        title.setLayoutX(VIEWER_WIDTH*0.15);
        title.setLayoutY(VIEWER_HEIGHT*0.32);

        Button start = new Button("我  准  备  好  了");
        start.setLayoutX(VIEWER_WIDTH*0.3);
        start.setLayoutY(VIEWER_HEIGHT*0.66);
        start.setPrefWidth(VIEWER_WIDTH*0.4);
        start.setPrefHeight(VIEWER_HEIGHT*0.1);

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeAdd();
            }
        });

        root.getChildren().addAll(title,start);
        root.toBack();
    }

    private void makeAdd(){
        root.getChildren().clear();
        TextField input = new TextField();

        root.getChildren().addAll(input);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Reminder");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        // for resize, but not done yet
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                VIEWER_WIDTH = (double) newValue;
            }
        });

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                VIEWER_HEIGHT = (double) newValue;
            }
        });

        makeWelcome();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}