package model;

import java.io.File;
import java.util.Vector;
import javafx.application.Platform;
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
	
	ExpandProductsTask expTask;
	
	public ProductProcessor( File f, String sup, Vector<Product> inV){
		this.f = f;
		this.supName = sup;
		bar = new ProgressBar();
		bar2 = new ProgressBar();
	}//end of constructor
	
	
	public void process(){
		
		GetProdFromFileTask getTask =  new GetProdFromFileTask(f, supName);
		
		
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
				  Thread th2 = new Thread(expTask);
				  th2.setDaemon(true);
				  th2.run();
				  th2.join();
				  outVector = expTask.get();

				 CsvWriter csvw = new CsvWriter();
				 csvw.writeProdVectToCsv(FileHelper.getExecFolder()+"/out.csv", outVector);
				  
				  return null;
			}//end of call
		};//end of task
		
		Platform.runLater(new Runnable() {
            public void run() {
            	bar.setProgress(0);
            	bar.progressProperty().bind(getTask.progressProperty());
            	Label messagel = new Label("hgj");
            	messagel.textProperty().bind(task.messageProperty());  
            	
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
	            	
	            	expTask.setOnSucceeded(event->{
	        			System.out.println("FINISHED READING");//if(pbm!=null){pbm.getStage().close();}
	        			if(pbw != null) pbw.getStage().close();
	        		});
	            	
	            	Label labelCount = new Label("gfkjf");
	                labelCount.textProperty().bind(task.messageProperty());
	                
	            	bar2.setProgress(0);
	            	bar2.progressProperty().bind(expTask.progressProperty());	
	            	pbw = new ProgressBarPopWindow(bar2, 
	            			"Processing Supplier Files...", new Stage(),labelCount);
	            	pbw.pop();
	            }//end of run
	         });//end of inner runLater
			
		});//getTask.setOnSucceeded

		
		
		Thread th  = new Thread(task);
		th.setDaemon(true);
		th.start();
		
				
	}//end of run
	

	
}//end of ProductHelperThread
