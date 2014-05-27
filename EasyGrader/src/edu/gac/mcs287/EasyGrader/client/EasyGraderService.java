package edu.gac.mcs287.EasyGrader.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.gac.mcs287.EasyGrader.shared.Form;

// Services for various things.
@RemoteServiceRelativePath("easygraderservice")
public interface EasyGraderService extends RemoteService {
	public String submitFormToServer(Form form);

	public List<Form> getFormsFromServer();

	public Boolean isUserLoggedIn();

	public String getLogOutUrl();

	public String setAppBaseURL(String homeURL);

	public String deleteFormFromDB(Form form);
}
