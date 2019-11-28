import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
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
    private final Group welcome = new Group();

    // settings
    private String username = "主人";
    private String[] language = {"中文", "English"};

    // initial welcome scene
    private void makeWelcome(){
        welcome.getChildren().clear();
        Text title = new Text("欢迎回来，主人");
        title.setStyle("-fx-font: 100 arial;");
        title.setLayoutX(VIEWER_WIDTH*0.15);
        title.setLayoutY(VIEWER_HEIGHT*0.25);

        welcome.getChildren().addAll(title);
        root.toBack();
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

        root.getChildren().addAll(welcome);
        makeWelcome();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}