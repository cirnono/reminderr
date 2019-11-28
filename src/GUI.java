import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI extends Application {
    //size layout of the window
    private static final double VIEWER_WIDTH = 1000;
    private static final double VIEWER_HEIGHT = 700;
    private static final double TASK_WIDTH = 650;
    private static final double TASK_HEIGHT = 500;
    private static final double WELCOME_WORDS_X = 100;
    private static final double WELCOME_WORDS_Y = 150;
    private static final double WELCOME_TEXT_FIELD_X = 200;
    private static final double WELCOME_TEXT_FIELD_Y = 400;
    private static final double WELCOME_TEXT_FIELD_HEIGHT = 200;
    private static final double WELCOME_TEXT_FIELD_WIDTH = 600;
    private static final double TASK_X = 150;
    private static final double TASK_Y = 50;
    private static final double item_X = TASK_X+10;
    private static final double score_X = TASK_X+TASK_WIDTH-50;
    private static final double both_Y = TASK_Y+5;

    private static int score=0;

    Map<String,Task> tM = new HashMap<>();

    //more code here to make an background image

    private final Group root = new Group();
    private final Group board = new Group(); //to display the list of jobs
    private final Group controls = new Group(); //not sure will be used or not, use for add or delete task

    //private Counter counter = new Counter();
    private boolean isEnd;

    //create an welcome page
    private void makeBoard(){
        board.getChildren().clear();
        Text welcome =new Text("来好好做人吧 垃圾");
        Text format = new Text("格式：xxxxx,xx;xxxxxx,xx;...;");
        welcome.setStyle("-fx-font: 100 arial;");
        welcome.setLayoutX(WELCOME_WORDS_X);
        welcome.setLayoutY(WELCOME_WORDS_Y);
        format.setStyle("-fx-font: 20 arial;");
        format.setLayoutX(400);
        format.setLayoutY(350);

        board.getChildren().addAll(welcome,format);
        board.toBack();
    }

    //start by entering the jobs
    private void makeControl(){
        TextField jobs = new TextField();
        Button addJobs = new Button("让        我        们        开        始        吧");

        jobs.setLayoutX(WELCOME_TEXT_FIELD_X);
        jobs.setLayoutY(WELCOME_TEXT_FIELD_Y);
        jobs.setPrefHeight(WELCOME_TEXT_FIELD_HEIGHT);
        jobs.setPrefWidth(WELCOME_TEXT_FIELD_WIDTH);
        addJobs.setLayoutX(WELCOME_TEXT_FIELD_X+100);
        addJobs.setLayoutY(WELCOME_TEXT_FIELD_Y+WELCOME_TEXT_FIELD_HEIGHT);
        addJobs.setPrefWidth(400);

        board.getChildren().addAll(jobs,addJobs);



        addJobs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String s = jobs.getText();
                if(!s.isEmpty()) {
                    sorting(s);
                    makeListView();
                }
            }
        });
    }

    //creates an List of Tasks against scores
    private void sorting(String s){
        int last = 0;
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i)==';'||s.charAt(i)=='；'){
                String[] forPut=transform(s.substring(last,i));
                Task t = new Task(forPut[0], Integer.parseInt(forPut[1]));
                tM.put(forPut[0],t);
                last = i+1;
            }
        }
    }

    //transform a string into an array of the job and its score
    private String[] transform(String s){
        String[] sA = new String[2];
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i)==','||s.charAt(i)=='，'){
                sA[0] = s.substring(0,i);
                sA[1] = s.substring(i+1,s.length());
            }
        }
        return sA;
    }

    //entering the second view, which shows the job list and total score at the moment
    private void makeListView(){
        board.getChildren().clear();
        Text scoreCount = new Text("总分： "+score);
        scoreCount.setStyle("-fx-font: 30 arial;");
        scoreCount.setLayoutX(800);
        scoreCount.setLayoutY(650);

        Rectangle taskBG = new Rectangle(TASK_WIDTH,TASK_HEIGHT);
        taskBG.setLayoutX(TASK_X);
        taskBG.setLayoutX(TASK_Y);
        taskBG.setFill(Color.WHITE);
        taskBG.setOpacity(0.5);


        Text itemText = new Text("任务");
        Text scoreText = new Text("分数");
        itemText.setStyle("-fx-font: 30 arial;");
        itemText.setLayoutX(item_X);
        itemText.setLayoutY(both_Y);
        scoreText.setStyle("-fx-font: 30 arial;");
        scoreText.setLayoutX(score_X);
        scoreText.setLayoutY(both_Y);

        board.getChildren().addAll(scoreCount,taskBG,itemText,scoreText);

        displayTask();
        listControl();
    }

    private void displayTask(){
        double scoreValueX = score_X+10;
        int count = 1;
        double nowY = 10;
        double lineHeight = 20;
        nowY += both_Y+lineHeight;
        for (Task t:tM.values()) {
            Text jobNum = new Text(count+"");
            count++;
            Text newJob = new Text(t.getName());
            Text newScore = new Text(Integer.toString(t.getScore()));
            jobNum.setLayoutX(item_X-40);
            jobNum.setLayoutY(nowY);
            newJob.setLayoutX(item_X);
            newJob.setLayoutY(nowY);
            newScore.setLayoutX(scoreValueX);
            newScore.setLayoutY(nowY);
            nowY += lineHeight;
            board.getChildren().addAll(jobNum,newJob,newScore);
        }
    }

    private void listControl(){
        Button done = new Button("我好了");
        TextField whatIsDone = new TextField();
        done.setLayoutX(WELCOME_TEXT_FIELD_X+520);
        done.setLayoutY(630);
        whatIsDone.setLayoutX(WELCOME_TEXT_FIELD_X+320);
        whatIsDone.setLayoutY(630);

        Button more = new Button("还有啊……");
        TextField whatIsMore = new TextField();
        more.setLayoutX(WELCOME_TEXT_FIELD_X+100);
        more.setLayoutY(630);
        whatIsMore.setLayoutX(WELCOME_TEXT_FIELD_X-100);
        whatIsMore.setLayoutY(630);

        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //can be improved to implicit search
                String key = whatIsDone.getText();
                Task value = tM.get(key);
                score+=value.getScore();
                tM.remove(key);
                makeListView();
            }
        });

        more.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String[] anotherTask = transform(whatIsMore.getText());
                Task another = new Task(anotherTask[0],Integer.parseInt(anotherTask[1]));
                tM.put(anotherTask[0],another);
                makeListView();
            }
        });

        board.getChildren().addAll(done,whatIsDone,more,whatIsMore);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("计分器");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().addAll(board,controls);

        makeBoard();
        makeControl();


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
