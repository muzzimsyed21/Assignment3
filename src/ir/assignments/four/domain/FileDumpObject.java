package ir.assignments.four.domain;

import org.json.JSONObject;

public class FileDumpObject {
	
	private String text;
	private String url;
	private String title;
	private String html;
	private int id;

	public FileDumpObject(JSONObject json) {
		this.text = json.get("text").toString();
		this.url = json.get("_id").toString();
		this.title = json.get("title").toString();
		this.html = json.get("html").toString();
		this.id = (int) Double.parseDouble(json.get("id").toString());
	}

	/** returns true if FDO is valid **/
	public boolean isValid() {
		return getText() != null && !getText().isEmpty();
	}
	
	@Override
	public String toString() {
		return this.text + "\n" + this.url + "\n" + this.title + "\n" + this.html + "\n" + this.id
				+ "\n";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
