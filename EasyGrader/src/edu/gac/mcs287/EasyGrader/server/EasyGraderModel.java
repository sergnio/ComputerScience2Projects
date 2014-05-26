package edu.gac.mcs287.EasyGrader.server;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import edu.gac.mcs287.EasyGrader.shared.Category;
import edu.gac.mcs287.EasyGrader.shared.Form;


public class EasyGraderModel {
	
	static final PersistenceManagerFactory pmf = PMF.get();
	
	
	static String appBaseURL="";

	public static void submitFormToServer(Form form){
		PersistenceManager pm = pmf.getPersistenceManager();
		form.flatten();
		try {
			pm.currentTransaction().begin();
			pm.makePersistent(form);
			pm.currentTransaction().commit();
		}
		finally {
		    if (pm.currentTransaction().isActive())
		      pm.currentTransaction().rollback();
		    if (!pm.isClosed())
		      pm.close();
		   }
	}
	
	
	public static List<Form> getFormsFromServer(){
		PersistenceManager pm = pmf.getPersistenceManager();
		Query query = pm.newQuery(Form.class);
		Query queryc = pm.newQuery(Category.class);
		
		List<Form> forms = (List<Form>)query.execute();
		List<Category> categories = (List<Category>)queryc.execute();

		for(Form form: forms){
			List<Category> cats = form.getFlattened();
		}
		
		return (java.util.List<Form>) pm.detachCopyAll(forms);
	}
	
	public static void deleteForm(Form form) {
		PersistenceManager pm = pmf.getPersistenceManager();
		// Keep alterations in a Transaction, so records are locked until done
		try {
			pm.currentTransaction().begin();
			// Find the object in the datastore that matches the
			// class and ID of the Form. Then, delete it.
			pm.deletePersistent(pm.getObjectById(Form.class, form.getId()));
			pm.currentTransaction().commit();
		} finally {
			if (pm.currentTransaction().isActive())
				pm.currentTransaction().rollback();
			if (!pm.isClosed())
				pm.close();
		}
	}

	
	// Getters and setters of globally needed values
		public static void setAppBaseURL(String url){
			EasyGraderModel.appBaseURL = url;
		}

		public static String getAppBaseURL(){
			return EasyGraderModel.appBaseURL;
		}
	
		
	
	
}


