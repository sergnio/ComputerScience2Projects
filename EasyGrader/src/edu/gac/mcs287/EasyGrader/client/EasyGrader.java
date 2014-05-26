package edu.gac.mcs287.EasyGrader.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.gac.mcs287.EasyGrader.shared.Form;





/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EasyGrader implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	
	private final EasyGraderView view = new EasyGraderView();
	private final EasyGraderServiceAsync EasyGraderService = GWT
			.create(EasyGraderService.class);
	
	// Needed to keep track of the URL that is loaded when the app first loads
		private String homeURL ="";
	
	public void onModuleLoad() {
		
		System.out.println("hkjhjk");
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			
			
			@Override
			public void onUncaughtException(Throwable e) {
				System.out.println("// TODO Auto-generated method stub");
				
			}
		});
		
		homeURL = Window.Location.getHref();
		
		
		view.setControl(this);

		view.viewWelcomePage();
		
		
		
		EasyGraderService.setAppBaseURL(homeURL,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						return;
					}
					public void onSuccess(String result) {
					}
				});

		//  Ask Model is a user is logged in 
		EasyGraderService.isUserLoggedIn(
				new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				return;
			}

			public void onSuccess(Boolean result) {
				if(result){ // If user is logged in 
					// Show welcome page
					view.viewWelcomePage();
				}// Otherwise, set page to login page
				else Window.Location.replace("../Welcome.html");
			}
		});
	}
	
	public void submitFormToServer(Form form){
		EasyGraderService.submitFormToServer(form, new AsyncCallback<String>(){

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("FAIL!");
				
			}


			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				System.out.println("success");
			}

			
		});
		
		
	}
	public void getFormsFromServer() {
		EasyGraderService.getFormsFromServer(new AsyncCallback<List<Form>>(){

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Didnt get Forms");
								
			}

			@Override
			public void onSuccess(List<Form> result) {
				System.out.print(result.toString());
				//view.viewForms(result);
				for(Form f: result){
					f.unflatten();
				}
				view.viewFormsPage(result);
			}
		});
		
	}
	
	public void handleDeleteRequest(Form form) {
		EasyGraderService.deleteFormFromDB(form, 
				new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("Failed to delete form from Server");  
			}
			public void onSuccess(String data) {
				System.out.println("successfully deleted form");
			}
		});
	}
	

	
}
