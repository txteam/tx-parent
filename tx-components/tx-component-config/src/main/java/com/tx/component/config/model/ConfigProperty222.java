package com.tx.component.config.model;


public class ConfigProperty222{

	
    public static String STATUS_VALID = "0";
    
    public static String STATUS_INVALID = "1";
    
	private String resourceId;
	private String key;
	private String value;
	private String name;
	private String description;
	private String status;
	private boolean DevValue = false;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public boolean getDevValue() {
		return DevValue;
	}
	public void setDevValue(boolean devValue) {
		DevValue = devValue;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
}
