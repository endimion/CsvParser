package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressBarPopWindow {

	Stage st;
	
	public ProgressBarPopWindow(ProgressBar bar, String message,
																										Label messageTxt){
		VBox hbox = new VBox(20);
		hbox.setPadding(new Insets(30, 30, 30, 30));
		bar.setPrefSize(250, 24);
		hbox.getChildren().addAll(new Text(message), bar, messageTxt);
		
		this.st = new Stage();
        this.st.initStyle(StageStyle.UTILITY);
        //st.setResizable(false);
       this.st.initModality(Modality.APPLICATION_MODAL);
		
		Scene scene = new Scene(hbox);
		this.st.setTitle("Please Wait...");
		this.st.setScene(scene);
		this.st.sizeToScene();
		
		//st.show();
	}
	
	public Stage getStage(){
		return st;
	}
	
	
	public void pop(){
		st.show();
	}
}//end of class
