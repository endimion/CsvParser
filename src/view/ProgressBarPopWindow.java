package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProgressBarPopWindow {

	Stage st;
	
	public ProgressBarPopWindow(ProgressBar bar, String message, 
																										Stage st, Label messageTxt){
		VBox hbox = new VBox(20);
		hbox.setPadding(new Insets(30, 30, 30, 30));
		bar.setPrefSize(250, 24);
		hbox.getChildren().addAll(new Text(message), bar, messageTxt);
		
		this.st = st;
		Scene scene = new Scene(hbox);
		st.setTitle("Please Wait...");
		st.setScene(scene);
		
		st.show();
	}
	
	public Stage getStage(){
		return st;
	}
	
	
	public void pop(){
		st.show();
	}
}//end of class
