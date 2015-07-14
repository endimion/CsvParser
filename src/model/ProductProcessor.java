package model;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.Vector;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import view.ProgressBarPopWindow;

public class ProductProcessor{

	private File f;
	private String supName;
	private Vector<Product> outVector;
	private ProgressBarPopWindow pbw ;
	ProgressBar bar ;
	ProgressBar bar2 ;
	private final static String fileSep = FileSystems.getDefault().getSeparator();
	
	ExpandProductsTask expTask;
	Label messagel = new Label("Starting to read products..."+"'\n");
	
	public ProductProcessor( File f, String sup, Vector<Product> inV){
		this.f = f;
		this.supName = sup;
		bar = new ProgressBar();
		bar2 = new ProgressBar();
	}//end of constructor
	
	
	public void process(){
		
		GetProdFromFileTask getTask =  new GetProdFromFileTask(f, supName);
		getTask.messageProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //System.out.println("ProductProcess.process::" +newValue);
                messagel.setText(newValue);
            }
        });
		
	
		
		
		final Task<Void> task = new Task<Void>(){
			@Override
			protected Void call() throws Exception {
				 
				  //GetProdFromFileTask task =  new GetProdFromFileTask(f, supName);
				  Thread th = new Thread(getTask);
				  th.setDaemon(true);
				  th.run();
				  th.join();
				  outVector = getTask.get();
				 
				  expTask =  new  ExpandProductsTask(outVector,supName);
				  expTask.messageProperty().addListener(new ChangeListener<String>() {
			            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			                //System.out.println("ProductProcess.process::" +newValue);
			                messagel.setText(newValue);
			            }
			        });
				  expTask.setOnSucceeded(event->{
	        			System.out.println("FINISHED READING");//if(pbm!=null){pbm.getStage().close();}
	        			if(pbw != null) pbw.getStage().close();
				  });
				  
				  Thread th2 = new Thread(expTask);
				  th2.setDaemon(true);
				  th2.run();
				  th2.join();
				  outVector = expTask.get();

				 CsvWriter csvw = new CsvWriter();
				 csvw.writeProdVectToCsv(FileHelper.getExecFolder()+fileSep+"out.csv", outVector);
				  
				  return null;
			}//end of call
		};//end of task
		
		Platform.runLater(new Runnable() {
            public void run() {
            	bar.setProgress(0);
            	bar.progressProperty().bind(getTask.progressProperty());
            	//messagel.textProperty().bind(task.messageProperty());  
            	
            	pbw = new ProgressBarPopWindow(bar, 
            			"Reading Supplier Files...", new Stage(),messagel);
            	pbw.pop();
            }//end of run
         });
		
		getTask.setOnSucceeded(event2->{
			System.out.println("FINISHED READING");//if(pbm!=null){pbm.getStage().close();}
			if(pbw != null) pbw.getStage().close();
			
		Platform.runLater(new Runnable() {
	            public void run() {
	            	
	            	//Label labelCount = new Label("gfkjf");
	                //messagel.textProperty().bind(task.messageProperty());
	                
	            	bar2.setProgress(0);
	            	bar2.progressProperty().bind(expTask.progressProperty());	
	            	pbw = new ProgressBarPopWindow(bar2, 
	            			"Processing Supplier Files...", new Stage(), messagel);
	            	pbw.pop();
	            }//end of run
	         });//end of inner runLater
			
		});//getTask.setOnSucceeded

		
		
		Thread th  = new Thread(task);
		th.setDaemon(true);
		th.start();
		
				
	}//end of run
	

	
}//end of ProductHelperThread
