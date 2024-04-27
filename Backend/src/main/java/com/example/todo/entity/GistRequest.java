package com.example.todo.entity;

import java.util.Map;

public class GistRequest {
	private String description;
	private boolean publics;
	private Map<String, GistFile> files;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublics() {
		return publics;
	}

	public void setPublics(boolean publics) {
		this.publics = publics;
	}

	public Map<String, GistFile> getFiles() {
		return files;
	}

	public void setFiles(Map<String, GistFile> files) {
		this.files = files;
	}

	// Getters and setters
	// Constructor
}
