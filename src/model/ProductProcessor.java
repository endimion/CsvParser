package model;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.Vector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import view.ProgressBarPopWindow;

public class ProductProcessor{

	private File f;
	private String supName;
	private Vector<Product> outVector;
	//private ProgressBarPopWindow pbw ;
	ProgressBar bar ;
	ProgressBar bar2 ;
	private final static String fileSep = FileSystems.getDefault().getSeparator();
	
	ExpandProductsTask expTask;
	Label messagel = new Label("Starting to read products..."+"'\n");
	ProgressBarPopWindow downloadWindow ;
	ProgressBarPopWindow  processingWindow ;
	ProgressBarPopWindow readWindow ;
	GetProdFromFileTask getTask ;
	
	Task<File> downloadTask;
	
	public ProductProcessor( File f, String sup){
		this.f = f;
		this.supName = sup;
		bar = new ProgressBar();
		bar2 = new ProgressBar();
	}//end of constructor
	
	
	public void process(SupplierXmlDownloader sxd){
		Thread th  = new Thread(getMainTask(sxd));
		th.setDaemon(true);
		th.start();
	}//end of run
	

	
	
	
	public Task<Void> getMainTask(SupplierXmlDownloader sxd){

		ProgressBar bar3 = new ProgressBar();
		downloadWindow = new ProgressBarPopWindow(bar3, 
    			"Downloading Supplier "+supName+" XML feed...",new Label());
		bar3.progressProperty().unbind();
		bar3.setProgress(0);
		
		downloadWindow.pop();
	
		downloadTask = new Task<File>() {
			@Override
			protected File call() throws Exception {
				if(sxd != null){
					f = sxd.getUrl();
				}//end if sxd != null
				return f;
			}
		};
		
		bar3.progressProperty().bind(downloadTask.progressProperty());
		
		downloadTask.setOnSucceeded(event ->{
			downloadWindow.getStage().close();
		
			readWindow = 
					new ProgressBarPopWindow(bar, 
	    			"Reading Supplier "+supName+"Files...", messagel);
			bar.progressProperty().unbind();
			bar.setProgress(0);
			bar.progressProperty().bind(getTask.progressProperty());
			readWindow.pop();
		});
		
		
	
		final Task<Void> task = new Task<Void>(){
			@Override
			protected Void call() throws Exception {

				Thread dTh = new Thread(downloadTask);
				dTh.setDaemon(true);
				dTh.run();
				dTh.join();
				f = downloadTask.get();
				System.out.println("finished donwloading file");
				
				getTask =  new GetProdFromFileTask(f, supName);
				getTask.messageProperty().addListener(new ChangeListener<String>() {
		            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		                //System.out.println("ProductProcess.process::" +newValue);
		                messagel.setText(newValue);
		            }
		        });
				
				getTask.setOnSucceeded(event2->{
					System.out.println("ProductProcessor.process.call.getTask:: FINISHED READING");//if(pbm!=null){pbm.getStage().close();}
					readWindow.getStage().close();
					   processingWindow 
		 				= new ProgressBarPopWindow(bar2, 
		 						"Processing Supplier "+supName+" Files...", messagel);
		             processingWindow.pop();
				});//getTask.setOnSucceeded
				
				
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
				 
				  bar2.setProgress(0);
	              bar2.progressProperty().bind(expTask.progressProperty());	
	              
					 expTask.setOnSucceeded(event->{
		        			System.out.println("ProductProcessor.process.call.expTask :: FINISHED Processing");//if(pbm!=null){pbm.getStage().close();}
		        			if(processingWindow != null) processingWindow.getStage().close();
					  });	
	            
					 System.out.println("ProductProcess.process:: second thread shoudl start");
				  Thread th2 = new Thread(expTask);
				  th2.setDaemon(true);
				  th2.run();
				  th2.join();
				  outVector = expTask.get();

				 CsvWriter csvw = new CsvWriter();
				 System.out.println("ProductProcessor.prpocess.call ::  will write to the output" + outVector.size());
				 csvw.writeProdVectToCsv(FileHelper.getExecFolder()+fileSep+"out.csv", outVector);

				 return null;
			}//end of call
		};//end of task
		
		
		
		
		return task;
	}//end of getMainTask

	
	

	
}//end of ProductHelperThread