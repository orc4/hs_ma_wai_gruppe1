package data_model;

import java.net.URI;

public class Cam {

	private long id;
	private String name;
	private URI uri;
	private long interval;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public long getId() {
		return id;
	}

	public Cam(long id, String name, URI uri, long interval) {
		super();
		this.id = id;
		this.name = name;
		this.uri = uri;
		this.interval = interval;
	}

}
