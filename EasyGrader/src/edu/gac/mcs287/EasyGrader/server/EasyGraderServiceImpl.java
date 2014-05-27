package edu.gac.mcs287.EasyGrader.server;

import java.util.List;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.gac.mcs287.EasyGrader.client.EasyGraderService;
import edu.gac.mcs287.EasyGrader.shared.Form;

public class EasyGraderServiceImpl extends RemoteServiceServlet implements
		EasyGraderService {
	public String submitFormToServer(Form Form) {
		EasyGraderModel.submitFormToServer(Form);
		return null;
	}

	@Override
	public List<Form> getFormsFromServer() {

		return EasyGraderModel.getFormsFromServer();

	}

	public String getLogOutUrl() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.createLogoutURL("../Welcome.html");
	}

	public String setAppBaseURL(String homeURL) {
		EasyGraderModel.setAppBaseURL(homeURL);
		return null;
	}

	public Boolean isUserLoggedIn() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		return new Boolean(user != null);
	}

	// Implementation for deleting form from database
	@Override
	public String deleteFormFromDB(Form form) {
		EasyGraderModel.deleteForm(form);
		return null;
	}

}
