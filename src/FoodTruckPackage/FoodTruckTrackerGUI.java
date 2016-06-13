package FoodTruckPackage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FoodTruckTrackerGUI extends Application{
	Stage window;
	Scene openingScene, userScene, ownerScene;

	@Override
	public void start(Stage primaryStage){
		window = primaryStage;
		
		/*
		 * Opening Scene to distinguish User from Owner
		 */
		GridPane originalGrid = new GridPane();
		originalGrid.setAlignment(Pos.CENTER);
		originalGrid.setHgap(10);
		originalGrid.setVgap(10);
		originalGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Button isUser = new Button(" Food Truck User  ");
		isUser.setId("large-button");
		Button isOwner = new Button ("Food Truck Owner");
		isOwner.setId("large-button");
		
		VBox vBox = new VBox(20);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(isUser, isOwner);
		originalGrid.add(vBox, 0, 0);
		
		isUser.setOnAction(e -> window.setScene(userScene));
		isOwner.setOnAction(e -> window.setScene(ownerScene));
		
		openingScene = new Scene(originalGrid, 600, 550);
		openingScene.getStylesheets().add(FoodTruckTrackerGUI.class.getResource("Login.css").toExternalForm());
		
		/*
		 * Start of userScene
		 */
		GridPane userGrid = new GridPane();
		userGrid.setAlignment(Pos.CENTER);
		userGrid.setHgap(10);
		userGrid.setVgap(10);
		userGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Text userSceneTitle = new Text("Welcome, User!");
		userSceneTitle.setId("welcome-text");
		userGrid.add(userSceneTitle, 0, 0, 2, 1);
		
		Label userName = new Label("Username:");
		userGrid.add(userName, 0, 1);
		
		TextField userTextField = new TextField();
		userGrid.add(userTextField, 1, 1);
		
		Label userPW = new Label("Password:");
		userGrid.add(userPW, 0, 2);
		
		PasswordField userPWBox = new PasswordField();
		userGrid.add(userPWBox, 1, 2);
		
		Button userSignIn = new Button("Sign in");
		HBox userHBox = new HBox(10);
		userHBox.setAlignment(Pos.BOTTOM_RIGHT);
		userHBox.getChildren().add(userSignIn);
		userGrid.add(userHBox, 1, 4);
		
		final Text actionTarget = new Text();
		userGrid.add(actionTarget, 1, 6);
		actionTarget.setId("actiontarget");
		userSignIn.setOnAction(e -> window.setScene(ownerScene)); //awesome... change to check database!!!
		
		userScene = new Scene(userGrid, 600, 550);
		userScene.getStylesheets().add(FoodTruckTrackerGUI.class.getResource("Login.css").toExternalForm());
		
		/*
		 * Start of Owner Scene
		 */
		GridPane ownerGrid = new GridPane();
		ownerGrid.setAlignment(Pos.CENTER);
		ownerGrid.setHgap(10);
		ownerGrid.setVgap(10);
		ownerGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Text ownerSceneTitle2 = new Text("Welcome, Owner!");
		ownerSceneTitle2.setId("welcome-text");
		ownerGrid.add(ownerSceneTitle2, 0, 0, 2, 1);
		
		Label ownerName = new Label("Username:");
		ownerGrid.add(ownerName, 0, 1);
		
		TextField ownerTextField = new TextField();
		ownerGrid.add(ownerTextField, 1, 1);
		
		Label ownerPW = new Label("Password:");
		ownerGrid.add(ownerPW, 0, 2);
		
		PasswordField ownerPWBox = new PasswordField();
		ownerGrid.add(ownerPWBox, 1, 2);
		
		Button ownerSignIn = new Button("Sign in");
		HBox ownerHBox = new HBox(10);
		ownerHBox.setAlignment(Pos.BOTTOM_RIGHT);
		ownerHBox.getChildren().add(ownerSignIn);
		ownerGrid.add(ownerHBox, 1, 4);
		
		final Text actionTarget2 = new Text();
		ownerGrid.add(actionTarget2, 1, 6);
		actionTarget2.setId("actiontarget");
		ownerSignIn.setOnAction(e2 -> actionTarget2.setText("Sign in button pressed"));
		
		ownerScene = new Scene(ownerGrid, 600, 550);
		ownerScene.getStylesheets().add(FoodTruckTrackerGUI.class.getResource("Login.css").toExternalForm());
		
		window.setScene(openingScene);
		window.setTitle("Welcome to FoodTruckTracker!");
		window.show();
		
	}
	
	public static void main (String[] args){
		launch(args);
	}

}
