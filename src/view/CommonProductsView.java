package view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Product;

public class CommonProductsView {

	private Stage stage;
	HashMap<String,Vector<Product>> hashMap ;
	Vector<Vector<CheckBox> > eanCheckBoxes;
	
	
	
	public CommonProductsView(Stage st,  HashMap<String,Vector<Product>> map,
			Vector<Vector<CheckBox> > eanCheckBoxes ){
		this.stage = st;
		this.hashMap = map;
		this.eanCheckBoxes = eanCheckBoxes;
		
		//Vector<HBox> vericalBoxes = new Vector<HBox>(); 
		//Vector<HBox> horizontalBoxes = new Vector<HBox>();
		VBox verticalBoxes = new VBox();
		verticalBoxes.getChildren().add(new Label("AAA"));
		
		Iterator<Entry<String, Vector<Product>>> it = hashMap.entrySet().iterator();
		int checkBoxVecIndex = 0;
		
		while(it.hasNext()){
			
			Map.Entry<String, Vector<Product>> pair = (Map.Entry<String, Vector<Product>>)it.next();
			
			//HBox hNamebox = new HBox();
			//hNamebox.getChildren().addAll(new Text("Ean " + pair.getKey()  ));
			System.out.println("CommonProductsView:: EAN IS " + pair.getKey() );
			
			verticalBoxes.getChildren().add(new Text("Ean " + pair.getKey()));
			
			Vector<CheckBox> checkBoxForThisEan = eanCheckBoxes.get(checkBoxVecIndex);	
			
			VBox hBoxesForThisEan = new VBox();
			for(int i = 0; i < pair.getValue().size();i++){
				HBox  insideBox = new HBox();
				//TODO
				Text supName = new Text(pair.getValue().get(i).getSupplierName());
				insideBox.getChildren().addAll(supName,checkBoxForThisEan.get(i) );
				hBoxesForThisEan.getChildren().addAll(insideBox);
			}//end of looping through the Vector<Product> for the specific EAN of the pair
			checkBoxVecIndex++;
			
			verticalBoxes.getChildren().add(hBoxesForThisEan);
			//verticalBoxes.getChildren().add(insideBox);
			
			it.remove(); // avoids a ConcurrentModificationException
		}//end of looping through the iterator of the hashMap
		
		
		
		//finalizing the view
		//VBox center = new VBox();
		//center.getChildren().addAll(verticalBoxes);
		BorderPane MaineBorder = new BorderPane();
		MaineBorder.setId("mainPane");
		MaineBorder.setCenter(verticalBoxes);
		Scene scene = new Scene(MaineBorder, stage.getWidth(),stage.getHeight());
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
		this.stage.setScene(scene);
		
	}//end of constructor
	
	
	
	
	

	/**
	 * displays the view
	 */
	public void display(){
		stage.show();
	}//end of display
	
	
	
	
	
}//end of class
