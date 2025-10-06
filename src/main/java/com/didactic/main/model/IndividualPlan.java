package com.devrezaur.main.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IndividualPlan {
	@JsonProperty("Themes")
	Map<String, Integer> themes = new HashMap<>();

	public Map<String, Integer> getThemes() {
		return themes;
	}

	public void setThemes(Map<String, Integer> themes) {
		this.themes = themes;
	}

	public IndividualPlan(Map<String, Integer> themes) {
		super();
		this.themes = themes;
	}
	
}
