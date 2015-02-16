package edu.gac.mcs287.EasyGrader.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Form implements Serializable {
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;
	@NotPersistent
	private java.util.List<Category> categories = new ArrayList<Category>();
	@Persistent
	private java.util.List<Category> flattened = new ArrayList<Category>();
	@Persistent
	private boolean isFilledOut = false;
	@Persistent
	private String person = "nobody";
	@Persistent
	private String title = "no title";
	@Persistent
	private long millis = 0;

	public Form() {
	}

	public Form(String title, List<Category> categories) {
		setCategories(categories);
		setTitle(title);
		setFilledOut(false);
		Date d = new Date();
		millis = d.getTime();
	}

	// Testing for a visual of what a newly created form looks like
	public void print() {
		StringBuffer printString = new StringBuffer(title + "\n");
		for (Category category : this.categories) {
			printString.append(category.toString() + "\n");
		}
		System.out.println(printString.toString());
	}

	// Getters and setters

	public List<Category> getFlattened() {
		return flattened;
	}

	public void setFlattened(List<Category> flattened) {
		this.flattened = flattened;
	}

	public String getId() {
		return id;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> newCategories) {
		this.categories = newCategories;
	}

	public String getPerson() {
		return person;
	}

	public void setFilledOut(boolean isFilledOut) {
		this.isFilledOut = isFilledOut;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public boolean isFilledOut() {
		return isFilledOut;
	}

	public Date getDate() {
		return new Date(millis);
	}

	// testing
	public static void main(String[] args) {
		List<Category> categories = new ArrayList();
		categories.add(new Category("How awesome is Sergio", 10));
		categories.add(new Category("Sergio's grade in life", 10));

		categories.get(0).addCategory(new Category("So awesome", 10));
		categories.get(0).addCategory(new Category("Super awesome", 10));
		categories.get(0).addCategory(new Category("the best", 10));

		final Form f = new Form("Sergio's form", categories);

		f.setCategories(categories);

		f.print();
		f.flatten();
		f.unflatten();

	}

	// used to put all categories and subcategories in one list in order to
	// properly store and retrieve from the datastore
	public void flatten() {
		for (Category c : categories) {
			flattened.add(c);
			for (Category s : c.getSubcategories()) {
				flattened.add(s);
			}
		}
		System.out.println("after flattened \n" + flattened);
	}

	// used in order to take all the categories and subcategories out of a list
	// to properly get them from the datastore.
	public void unflatten() {
		categories = new ArrayList<Category>();
		Category currentCategory = null;
		for (Category c : flattened) {
			if (!c.isSubcategory()) {
				c.setSubcategories(new ArrayList<Category>());
				categories.add(c);
				currentCategory = c;
			} else {
				if (currentCategory != null) {
					currentCategory.addCategory(c);
				}
			}
		}
		System.out.println("after unflattened \n" + categories);
	}

}
