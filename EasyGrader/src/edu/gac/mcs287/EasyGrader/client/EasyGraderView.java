package edu.gac.mcs287.EasyGrader.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.gac.mcs287.EasyGrader.shared.Category;
import edu.gac.mcs287.EasyGrader.shared.Form;

public class EasyGraderView {
	private EasyGrader control;

	// 0 = welcome
	// 1 = addFormPage
	// 2 = fillOutFormPage

	public void setControl(EasyGrader control) {
		this.control = control;
	}

	public EasyGrader getControl() {
		return control;
	}

	public void setPage(Element div) {
		Element[] pages = { DOM.getElementById("welcomeDiv"),
				DOM.getElementById("addFormDiv"),
				DOM.getElementById("fillOutFormDiv"),
				DOM.getElementById("viewFormsDiv")};
		for (Element e : pages) {
			System.out.println(div.getId() + " " + e.getId());
			if (e.getId().equals(div.getId())) {
				div.removeClassName("hidden");
			} else {
				e.addClassName("hidden");
			}
		}
	}

	public void viewWelcomePage() {
		// setWindow("welcome.html");
		setPage(DOM.getElementById("welcomeDiv"));
		Button newFormButton = Button.wrap(DOM.getElementById("newFormButton"));
		System.out.println(newFormButton);
		newFormButton.addClickHandler(new ClickHandler() {
	
			@Override
			public void onClick(ClickEvent event) {
	
				
				viewAddFormPage();
	
			}
	
		});
		Button viewFormsButton = Button.wrap(DOM
				.getElementById("viewFormButton"));
		viewFormsButton.addClickHandler(new ClickHandler() {
	
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("click");
				control.getFormsFromServer();
	
			}
	
		});
		control.getFormsFromServer();
	}

	public void viewFormsPage(List<Form> forms){
		GWT.log("yep");
		setPage(DOM.getElementById("viewFormsDiv"));
		RootPanel viewFormsDivPanel = RootPanel.get("viewFormsDiv");
		viewFormsDivPanel.clear();
		
		VerticalPanel v = new VerticalPanel();
		for (final Form form : forms) {
			HorizontalPanel h = new HorizontalPanel();
			h.setSpacing(5);
			Label title = new Label(form.getTitle());
			Date d = form.getDate();
			Label date = new Label(d.toString());
			Button deleteButton = new Button();
			deleteButton.setText("Delete");
			deleteButton.setTitle("Delete");
			Button fillOutFormButton = new Button();
			fillOutFormButton.setText("Fill-out");
			fillOutFormButton.setTitle("Fill-out");
			
			deleteButton.addClickHandler(new ClickHandler(){
				
				@Override
				public void onClick(ClickEvent event) {
					control.handleDeleteRequest(form);
					control.getFormsFromServer();
				}
				});
			
			fillOutFormButton.addClickHandler(new ClickHandler(){
		
			@Override
			public void onClick(ClickEvent event) {
				viewFillOutFormPage(form);
			}
			});
			
			
			
			h.add(title);
			h.add(date);
			h.add(deleteButton);
			h.add(fillOutFormButton);
			v.add(h);
			
		}
		viewFormsDivPanel.add(v);
	}

	public void viewFillOutFormPage(Form f) {
		setPage(DOM.getElementById("fillOutFormDiv"));
		RootPanel fillOutFormDivPanel = RootPanel.get("fillOutFormDiv");
		fillOutFormDivPanel.clear();
		final DialogBox d = new DialogBox();
		d.hide();
	
		final VerticalPanel v = new VerticalPanel();
		HTML title = new HTML("<h1>" + f.getTitle() + "</h1>");
		v.add(title);
		for (int i = 0; i < f.getCategories().size(); i++) {
			Category c = f.getCategories().get(i);
			HorizontalPanel hp = convertCategory(c);
			v.add(hp);
			HTML hr = new HTML("<hr>");
			v.add(hr);
			for (int j = 0; j < c.getSubcategories().size(); j++) {
				Category s = c.getSubcategories().get(j);
				HorizontalPanel shp = convertCategory(s);
				shp.addStyleName("hpsubcategory");
				v.add(shp);
	
			}
		
		}
		TextArea comments = new TextArea();
		comments.setWidth("400px");
		comments.setHeight("100px");
		v.add(new HTML("<br>"));
		v.add(new Label("Comments"));
		v.add(comments);
		fillOutFormDivPanel.add(v);
		final Button printButton = new Button("Print");
		final Button dialogDoneButton = new Button("Done");
		printButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				d.setWidget(null);
				HorizontalPanel buttonPanel = (HorizontalPanel)v.getWidget(v.getWidgetCount()-1);
				buttonPanel.add(dialogDoneButton);
				d.setWidget(v);
				printButton.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						VerticalPanel p = (VerticalPanel)d.getWidget();
						p.remove(p.getWidgetCount()-1);
						printWidget(d.getWidget());
						d.hide();
						control.getFormsFromServer();
						
					}
					
				});
				
				
				d.show();
				
				
			}
			
		});
		dialogDoneButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				d.setWidget(null);
				d.hide();
				control.getFormsFromServer();
				
				
			}
			
		});
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(printButton);
		v.add(buttonPanel);
	
	}


	public void printWidget(Widget widget)
    {
            HeadElement headElement = (HeadElement) HeadElement.as(RootPanel.getBodyElement().getParentElement()
                            .getFirstChild());
            String printHTML = "<html>" + headElement.getInnerHTML() + "<body>" + widget.getElement().getInnerHTML()
                            + "</body></html>";
            Print.it(printHTML);
    }
	public void viewAddFormPage() {

		System.out.println("hello");
		// set page to the right one, hides all the other divs and unhides this
		// one.
		setPage(DOM.getElementById("addFormDiv"));

		// setup our button and title textbox
		Button submitButton;
		submitButton = Button.wrap(Document.get().getElementById("sub"));
		final TextBox title = TextBox.wrap(DOM.getElementById("title"));

		// create our click handler for our submit button

		submitButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				// gets categorydiv (container for all categories)
				Element div = DOM.getElementById("categoryDiv");

				// gets how many main categories there are
				int categoryCount = div.getChildCount() - 4;
				for (int i = 0; i < div.getChildCount(); i++) {
					System.out.println("child = " + div.getChild(i));
				}

				// index 0 = list of main categories, index 1+ = their
				// respective subcategories.
				ArrayList<ArrayList<Element>> categories = new ArrayList<ArrayList<Element>>();
				categories.add(new ArrayList<Element>());

				int i = 0;
				// getting categories
				while (true) {
					// gets c(i) div.
					Element element = DOM.getElementById("c" + i);
					// break if no more
					if (element == null) {
						System.out.println("element is null");
						break;
					}
					if (element.hasClassName("hidden")) {
						i++;
						continue;
					}
					categories.get(0).add(element);
					ArrayList<Element> subcategories = new ArrayList<Element>();
					int j = 0;
					while (true) {
						Element subcat = DOM.getElementById("sub" + i + j);
						if (subcat == null) {
							System.out.println("break");
							break;
						} else if (subcat.hasClassName("hidden")) {
							j++;
							continue;
						} else {
							subcategories.add(subcat);
						}
						j++;
					}
					System.out.println(i + " " + j);
					i++;
					categories.add(subcategories);
				}
				System.out.println(categories);

				ArrayList<Category> c = listOfElementsToCategories(categories);

				Form form = new Form(title.getText(), c);
				subClicked();
				control.submitFormToServer(form);
				viewWelcomePage();
				
			}

		});
	}

	public static native void subClicked() /*-{
		$doc.subClicked();
	}-*/;

	private Category[] createCategoriesFromTextBoxes(TextBox[] textboxes) {
		Category[] categories = new Category[textboxes.length];
		for (int i = 0; i < textboxes.length; i++) {
			TextBox text = textboxes[i];

			Category category = new Category(text.getText(), 100);

			categories[i] = category;
			System.out.println(category);
		}
		return categories;
	}

	// first index is main categories. [[c1, c2, c3], [c10, c11, c12], [c20,
	// c21, c22] ...]
	public ArrayList<Category> listOfElementsToCategories(
			ArrayList<ArrayList<Element>> c) {
		ArrayList<Category> categories = new ArrayList<Category>();
		for (int i = 0; i < c.get(0).size(); i++) {
			Element e = c.get(0).get(i);

			// System.out.println(e.getChild(3)); // 1 is score box, 3 is
			// textbox
			ListBox l = ListBox.wrap((Element) e.getChild(1));
			TextBox t = TextBox.wrap((Element) e.getChild(3));
			Category cat = new Category(t.getText(), Integer.parseInt(l
					.getItemText(l.getSelectedIndex())));

			ArrayList<Category> subcategories = new ArrayList<Category>();
			for (int j = 0; j < c.get(i + 1).size(); j++) {
				Element s = c.get(i + 1).get(j);
				TextBox st = TextBox.wrap((Element) s.getChild(1));
				ListBox sl = ListBox.wrap((Element) s.getChild(2));
				Category subcat = new Category(st.getText(),
						Integer.parseInt(sl.getValue(sl.getSelectedIndex())));
				// System.out.println(subcat);
				subcategories.add(subcat);

			}
			for(Category s: subcategories){
				cat.addCategory(s);
			}
			
			categories.add(cat);

		}
		return categories;
	}

	public HorizontalPanel convertCategory(Category c) {
		HorizontalPanel hp = new HorizontalPanel();
		HTML name = new HTML(c.getTitle());
		ListBox value = new ListBox();
		for (int val = 0; val < c.getValue() + 1; val++) {
			value.addItem(String.valueOf(val));
		}
		hp.add(name);
		hp.add(value);
		hp.add(new Label("/"+c.getValue()));
		
		return hp;
	}

}
