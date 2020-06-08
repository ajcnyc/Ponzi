import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.sun.javafx.tk.Toolkit;
import com.sun.prism.paint.Color;

import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author Alex Cohen
 */
public class gameUI extends Application {

	public static void main ( String[] args ) {
		launch(args);
	}

	@Override
	public void start ( Stage stage ) throws Exception {
		Ponzi.playGame();
		Rectangle2D screen = Screen.getPrimary().getVisualBounds();

		stage.setTitle("Ponzi");
		stage.setResizable(false);

		stage.setWidth(screen.getWidth() * 2 / 5);
		stage.setHeight(screen.getHeight() * 4 / 5);
		stage.setX((screen.getWidth() / 2) - stage.getWidth() / 2);
		stage.setY((screen.getHeight() / 2) - stage.getHeight() / 2);

		// GraphicsContext g = startCanvas.getGraphicsContext2D();

		BorderPane root = new BorderPane();
		root.setPadding(new Insets(screen.getHeight() / 10,20,20,20));
		root.setStyle("-fx-background-color: black");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setSpacing(10);
		Image image = new Image("file:images/logo 1.JPEG");
		ImageView iv1 = new ImageView(image);
		vbox.setPadding(new Insets(50,20,20,20));
		root.setAlignment(iv1,Pos.TOP_CENTER);
		root.setTop(iv1);
		// vbox.getChildren().add(iv1);
		root.setCenter(vbox);
		HBox hbox = new HBox();
		System.out.println((stage.getHeight()));
		Button startBtn = new Button("Play");

		startBtn.setOnAction(e -> {
			scene.setRoot(hbox);
			stage.setWidth(screen.getWidth() / 2);
			stage.setHeight(screen.getHeight() / 2);
			stage.setX((screen.getWidth() / 2) - stage.getWidth() / 2);
			stage.setY((screen.getHeight() / 2) - stage.getHeight() / 2);
		});
		vbox.getChildren().add(startBtn);

		// vbox.setSpacing(10);

		Button instructionBtn = new Button("Instructions");
		BorderPane instructionBox = new BorderPane();
		instructionBtn.setOnAction(e -> {
			scene.setRoot(instructionBox);
			instructionBox.setStyle("-fx-background-color: black");
			// instructionBox.setAlignment(Pos.TOP_CENTER);
			// instructionBox.setSpacing(10);
			instructionBox.setPadding(new Insets(50,20,20,20));
			ImageView iv2 = new ImageView(image);
			instructionBox.setAlignment(iv2,Pos.TOP_CENTER);
			instructionBox.setTop(iv2);
			TextArea instructionTextArea = new TextArea();
			instructionTextArea.setEditable(false);
			instructionTextArea.setWrapText(true);
			instructionBox.setCenter(instructionTextArea);
			instructionBox.setPadding(new Insets(10,40,10,40));
			Button instructionBack = new Button("Main Menu");
			instructionBack.setOnAction(f -> {
				scene.setRoot(root);
			});
			instructionBox.setBottom(instructionBack);

			File instructionFile = new File("README.txt");
			Scanner scanner;
			try {
				scanner = new Scanner(instructionFile);
				for ( ; scanner.hasNextLine() ; ) {
					String line = scanner.nextLine();
					if ( line.equals("") ) {
						instructionTextArea
						    .appendText(System.getProperty("line.separator"));
					}
					instructionTextArea.appendText(line);
					System.out.println(scanner.nextLine());
				}
				scanner.close();
			} catch ( FileNotFoundException e1 ) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		vbox.getChildren().add(instructionBtn);
		Label author = new Label("Created by Alex Cohen");
		author.setStyle("-fx-color: red");
		vbox.getChildren().add(author);

		Canvas canvas = new Canvas();
		VBox inputV = new VBox();

		hbox.setStyle("-fx-background-color: black");

		hbox.getChildren().add(canvas);
		hbox.getChildren().add(inputV);
		TextArea textArea = new TextArea();
		textArea.setPadding(new Insets(5,5,5,5));
		TextField inputBox = new TextField("type a command");
		canvas.setWidth((screen.getHeight() / 2));
		canvas.setHeight((screen.getHeight() / 2));

		textArea.setMinHeight(screen.getHeight() * 3 / 9);
		textArea.setMaxHeight(screen.getHeight());
		textArea.setMinWidth((screen.getWidth() / 2) - (screen.getHeight() / 2));
		textArea.setEditable(false);
		textArea.setWrapText(true);

		Button enterBtn = new Button("Enter");
		enterBtn.setOnAction(e -> {
			textArea.appendText(inputBox.getText());
			textArea.appendText("" + "\n");
			Ponzi.run(inputBox.getText(),textArea);
		});

		inputV.getChildren().add(textArea);
		inputV.getChildren().add(inputBox);
		inputV.getChildren().add(enterBtn);

		stage.show();
		// inputV.getChildren().add(textArea);
		stage.show();

	}
}
