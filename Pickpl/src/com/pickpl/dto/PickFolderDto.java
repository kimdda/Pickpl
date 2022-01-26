package com.pickpl.dto;

public class PickFolderDto {
	private String id;
	private String folder;
	private String pick_d_id;
	private int folder_d_count;
	
	public PickFolderDto() { }
	public PickFolderDto(String id, String folder, String pick_d_id, int folder_d_count) {
		this.id = id;
		this.folder = folder;
		this.pick_d_id = pick_d_id;
		this.folder_d_count = folder_d_count;
	}
	
	public String getId() {
		return id;
	}
	public String getFolder() {
		return folder;
	}
	public String getPick_d_id() {
		return pick_d_id;
	}
	public int getFolder_d_count() {
		return folder_d_count;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public void setPick_d_id(String pick_d_id) {
		this.pick_d_id = pick_d_id;
	}
	public void setFolder_d_count(int folder_d_count) {
		this.folder_d_count = folder_d_count;
	}
	@Override
	public String toString() {
		return "[id=" + id + ", folder=" + folder + ", pick_d_id=" + pick_d_id + ", folder_d_count="
				+ folder_d_count + "]";
	}
}
