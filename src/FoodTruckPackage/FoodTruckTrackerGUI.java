package FoodTruckPackage;

import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FoodTruckTrackerGUI extends Application{
	private Stage window;
	private Scene openingScene, userScene, ownerScene, userServiceScene, ownerServiceScene;
	private User user = new User();
	private FoodTruck truckOwner = new FoodTruck();
	private UserDAO userDAO = new UserDAO();
	private FoodTruckDAO foodDAO = new FoodTruckDAO();
	private FavoritesDAO favesDAO = new FavoritesDAO();
	private Verify verify = new Verify();
	private Alert alert = new Alert(AlertType.ERROR);

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
		
		Label userPWlabel = new Label("Password:");
		userGrid.add(userPWlabel, 0, 2);
		
		PasswordField userPWField = new PasswordField();
		userGrid.add(userPWField, 1, 2);
		
		Button userSignIn = new Button("Sign in");
		userSignIn.setDefaultButton(true);
		Button userRegister = new Button("Register");
		HBox userHBox = new HBox(10);
		userHBox.setAlignment(Pos.BOTTOM_RIGHT);
		userHBox.getChildren().addAll(userSignIn, userRegister);
		userGrid.add(userHBox, 1, 4);
		
		final Text actionTarget = new Text();
		userGrid.add(actionTarget, 1, 6);
		actionTarget.setId("actiontarget");
		
		userSignIn.setOnAction(new EventHandler<ActionEvent>() {
            
			@Override public void handle(ActionEvent e) {
                String username = userTextField.getText();
                String password = userPWField.getText();
				
                if (username.length() > 15 || username.length() < 3){
        			actionTarget.setText("Sorry. Your username is incorrect\nMust be between 3 and 15 characters!"); 
        		}
                else{
                
	                boolean found = userDAO.selectUsername(username);
	        		
	        		if (found){
	        			String db = "FoodTruckTracker.User";
		    			boolean loggedIn = verify.confirmPW(username, "username", db, password);
		    			
		    			if (loggedIn){
		    				actionTarget.setText("You are logged in");
		    				user = userDAO.selectAll(username);
		    				window.setScene(userServiceScene);
		    			}
		    			else{
		    				actionTarget.setText("Password does not match Username!\nPlease try again:");
		    				// still need something to stop action
		    			}
	        		}
	        		else{
	        			actionTarget.setText("" + username + " not found!\nRegister or try again");
	        		}
                }
            }
        });
		
		userRegister.setOnAction(e -> window.setScene(openingScene));
		
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
		
		Label ownerName = new Label("Truckname:");
		ownerGrid.add(ownerName, 0, 1);
		
		TextField ownerTextField = new TextField();
		ownerGrid.add(ownerTextField, 1, 1);
		
		Label ownerPWlabel = new Label("Password:");
		ownerGrid.add(ownerPWlabel, 0, 2);
		
		PasswordField ownerPWField = new PasswordField();
		ownerGrid.add(ownerPWField, 1, 2);
		
		Button ownerSignIn = new Button("Sign in");
		ownerSignIn.setDefaultButton(true);
		Button ownerRegister = new Button("Register");
		HBox ownerHBox = new HBox(10);
		ownerHBox.setAlignment(Pos.BOTTOM_RIGHT);
		ownerHBox.getChildren().addAll(ownerSignIn, ownerRegister);
		ownerGrid.add(ownerHBox, 1, 4);
		
		final Text actionTarget2 = new Text();
		ownerGrid.add(actionTarget2, 1, 6);
		actionTarget2.setId("actiontarget");
		ownerSignIn.setOnAction(new EventHandler<ActionEvent>() {
            
			@Override public void handle(ActionEvent e) {
                String truckName = ownerTextField.getText();
                String password = ownerPWField.getText();
				
                if (truckName.length() > 0 && truckName.trim().length() > 0){
	                truckOwner = foodDAO.select(truckName);
	                
	                if (truckOwner.getName() == null){
	        			actionTarget2.setText("" + truckName + " not found!\nPlease Register");
	                }
	                else{
	                	String db = "FoodTruckTracker.FoodTruck";
	        			boolean loggedIn = verify.confirmPW(truckName, "truckName", db, password);
	        			if (loggedIn){
	        				actionTarget2.setText("You are logged in!");
	        				window.setScene(ownerServiceScene);
	        			}
	        			else{
	        				actionTarget2.setText("Password does not match Truckname!\nPlease try again:");
	        			}
	                }
                }
                else{
                	actionTarget2.setText("Invalid Truckname\nPlease try again");
                }
            }
        });
		
		ownerRegister.setOnAction(e -> window.setScene(openingScene));
		
		ownerScene = new Scene(ownerGrid, 600, 550);
		ownerScene.getStylesheets().add(FoodTruckTrackerGUI.class.getResource("Login.css").toExternalForm());
		
		/*
		 * Start of UserServiceScene
		 */
		GridPane userServiceGrid = new GridPane();
		userServiceGrid.setAlignment(Pos.CENTER);
		userServiceGrid.setHgap(10);
		userServiceGrid.setVgap(25);
		userServiceGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Button profileBtn = new Button("Profile Menu");
		Button favesBtn = new Button("Favorites Menu");
		Button trucksBtn = new Button("Food Truck Menu");
		Button eventsBtn = new Button("Events Menu");
		HBox userBtnHBox = new HBox(10);
		userBtnHBox.setAlignment(Pos.CENTER);
		userBtnHBox.getChildren().addAll(profileBtn, favesBtn, trucksBtn, eventsBtn);
		userServiceGrid.add(userBtnHBox, 0, 0);
		
		Button executeBtn = new Button("Execute");
		HBox executeHBox = new HBox(10);
		executeHBox.setId("executeBtn");
		executeHBox.setAlignment(Pos.CENTER);
		executeHBox.getChildren().add(executeBtn);
		userServiceGrid.add(executeHBox, 0, 4);
		
		Label changeProfile = new Label("Choose the option you would like to change:");
		HBox userLabelHBox = new HBox(10);
		userLabelHBox.setAlignment(Pos.CENTER);
		userLabelHBox.getChildren().add(changeProfile);
		userLabelHBox.setVisible(false);
		userServiceGrid.add(userLabelHBox, 0, 1);
		
		ToggleGroup profileGroup = new ToggleGroup();
		
		RadioButton usernameRB = new RadioButton("Username");
		usernameRB.setToggleGroup(profileGroup);
		
		RadioButton passwordRB = new RadioButton ("Password");
		passwordRB.setToggleGroup(profileGroup);
		
		RadioButton nameRB = new RadioButton ("Name");
		nameRB.setToggleGroup(profileGroup);
		
		RadioButton addressRB = new RadioButton ("Address");
		addressRB.setToggleGroup(profileGroup);
		
		RadioButton emailRB = new RadioButton ("Email");
		emailRB.setToggleGroup(profileGroup);
		
		HBox userProfileHBox = new HBox(10);
		userProfileHBox.setAlignment(Pos.CENTER);
		userProfileHBox.getChildren().addAll(usernameRB, passwordRB, nameRB, addressRB, emailRB);
		userProfileHBox.setVisible(false);
		userServiceGrid.add(userProfileHBox, 0, 2);
		
		ToggleGroup favesGroup = new ToggleGroup();
		
		RadioButton removeFaveRB = new RadioButton ("Remove Favorite");
		removeFaveRB.setToggleGroup(favesGroup);
		
		RadioButton faveMenuRB = new RadioButton ("Get Menu");
		faveMenuRB.setToggleGroup(favesGroup);
		
		RadioButton faveEventsRB = new RadioButton ("Get Events");
		faveEventsRB.setToggleGroup(favesGroup);
		
		HBox userFavesHBox = new HBox(10);
		userFavesHBox.setAlignment(Pos.CENTER);
		userFavesHBox.getChildren().addAll(removeFaveRB, faveMenuRB, faveEventsRB);
		userFavesHBox.setVisible(false);
		userServiceGrid.add(userFavesHBox, 0, 2);

		ToggleGroup truckGroup = new ToggleGroup();
		
		RadioButton favoriteRB = new RadioButton("Add to Favorites");
		favoriteRB.setToggleGroup(truckGroup);
		
		RadioButton menuTruckRB = new RadioButton ("Get Menu");
		menuTruckRB.setToggleGroup(truckGroup);
		
		RadioButton eventsTruckRB = new RadioButton ("Get Event");
		eventsTruckRB.setToggleGroup(truckGroup);
		
		HBox userTrucksHBox = new HBox(10);
		userTrucksHBox.setAlignment(Pos.CENTER);
		userTrucksHBox.getChildren().addAll(favoriteRB, menuTruckRB, eventsTruckRB);
		userTrucksHBox.setVisible(false);
		userServiceGrid.add(userTrucksHBox, 0, 2);
		
		ToggleGroup eventsGroup = new ToggleGroup();
		
		RadioButton eventsRB = new RadioButton("View Specific Truck Events");
		eventsRB.setToggleGroup(eventsGroup);
		
		RadioButton allEventsRB = new RadioButton ("View All Events");
		allEventsRB.setToggleGroup(eventsGroup);
		
		HBox userEventsHBox = new HBox(10);
		userEventsHBox.setAlignment(Pos.CENTER);
		userEventsHBox.getChildren().addAll(eventsRB, allEventsRB);
		userEventsHBox.setVisible(false);
		userServiceGrid.add(userEventsHBox, 0, 2);
		
		ListView<User> userList = new ListView<User>();
		userList.setPrefWidth(500);
		userList.setPrefHeight(250);
		userServiceGrid.add(userList, 0, 3);
		
		ListView<Favorites> userList2 = new ListView<Favorites>();
		userList2.setVisible(false);
		userList2.setPrefWidth(500);
		userList2.setPrefHeight(250);
		userServiceGrid.add(userList2, 0, 3);
		
		profileBtn.setOnAction(new EventHandler<ActionEvent>() {
            
			@Override public void handle(ActionEvent e) {
				userList.setVisible(true);
				userList2.setVisible(false);
				userList.getItems().clear();
				ObservableList<User> userObserve = FXCollections.observableArrayList(user);
				userList.setItems(userObserve);
				changeProfile.setText("Choose the option you would like to change:");
				userLabelHBox.setVisible(true);
				userProfileHBox.setVisible(true);
				userFavesHBox.setVisible(false);
				userTrucksHBox.setVisible(false);
				userEventsHBox.setVisible(false);
			}
		});
		
		favesBtn.setOnAction(new EventHandler<ActionEvent>() {
            
			@Override public void handle(ActionEvent e) {
				userList.setVisible(false);
				userList2.setVisible(true);
				ArrayList<Favorites> faveArray = new ArrayList<Favorites>();
				faveArray = favesDAO.displayFaves(user.getId());
				ObservableList<Favorites> faveObserve = FXCollections.observableArrayList(faveArray);
				changeProfile.setText("Choose an option, please:");
				userList2.setItems(faveObserve);
				userLabelHBox.setVisible(true);
				userProfileHBox.setVisible(false);
				userFavesHBox.setVisible(true);
				userTrucksHBox.setVisible(false);
				userEventsHBox.setVisible(false);
			}
		});

		trucksBtn.setOnAction(new EventHandler<ActionEvent>() {
    
			@Override public void handle(ActionEvent e) {
				changeProfile.setText("Choose an option, please:");
				userLabelHBox.setVisible(true);
				userProfileHBox.setVisible(false);
				userFavesHBox.setVisible(false);
				userTrucksHBox.setVisible(true);
				userEventsHBox.setVisible(false);
			}
		});

		eventsBtn.setOnAction(new EventHandler<ActionEvent>() {
    
			@Override public void handle(ActionEvent e) {
				changeProfile.setText("Choose an option, please:");
				userLabelHBox.setVisible(true);
				userProfileHBox.setVisible(false);
				userFavesHBox.setVisible(false);
				userTrucksHBox.setVisible(false);
				userEventsHBox.setVisible(true);
			}
		});
		
		executeBtn.setOnAction(new EventHandler<ActionEvent>() {
            
			@Override public void handle(ActionEvent e) {
				if (usernameRB.isSelected()){
					TextInputDialog dialog = new TextInputDialog("");
					dialog.setTitle("Change Username");
					dialog.setHeaderText("Old username: " + user.getUsername());
					dialog.setContentText("Please enter new Username\nMust be between 3 and 15 characters:");

					// Traditional way to get the response value.
					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()){
					   String newUsername = result.get();
					   
					   if (newUsername.length() > 15 || newUsername.length() < 3 || newUsername.trim().length() < 1){
						   
						   alert.setTitle("Error Dialog");
						   alert.setHeaderText(null);
						   alert.setContentText("Invalid Username");
						   alert.showAndWait();
					   }
					   else{
						   boolean found = userDAO.selectUsername(newUsername);
						   if (found){
							   alert.setContentText("Username taken!");
							   alert.showAndWait();
						   }
						   else{
							   boolean success = userDAO.update(1, newUsername, user.getUsername());
								if (success){
									user.setUsername(newUsername);
									alert = new Alert(AlertType.CONFIRMATION);
									alert.setContentText("Username updated!");
									alert.showAndWait();
									userList.getItems().clear();
									ObservableList<User> userObserve = FXCollections.observableArrayList(user);
									userList.setItems(userObserve);
								}
								else{
									alert.setContentText("Error with Username change");
									alert.showAndWait();
								}
						   }
					   }
					}
				}
				
				
//				usernameRB, passwordRB, nameRB, addressRB, emailRB
				
				
			}
		});
		
		userServiceScene = new Scene(userServiceGrid, 600, 550);
		userServiceScene.getStylesheets().add(FoodTruckTrackerGUI.class.getResource("Login.css").toExternalForm());
		
		window.setScene(openingScene);
		window.setTitle("Welcome to FoodTruckTracker!");
		window.show();
		
	}
	
	public static void main (String[] args){
		launch(args);
	}

}
