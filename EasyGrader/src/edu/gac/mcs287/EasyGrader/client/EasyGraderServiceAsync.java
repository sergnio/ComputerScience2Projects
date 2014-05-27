package edu.gac.mcs287.EasyGrader.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.gac.mcs287.EasyGrader.shared.Form;

// Async interface
public interface EasyGraderServiceAsync {

	void submitFormToServer(Form form, AsyncCallback<String> callback);

	void getFormsFromServer(AsyncCallback<List<Form>> callback);

	void getLogOutUrl(AsyncCallback<String> callback);

	void isUserLoggedIn(AsyncCallback<Boolean> callback);

	void setAppBaseURL(String homeURL, AsyncCallback<String> callback);

	void deleteFormFromDB(Form form, AsyncCallback<String> callback);

}
