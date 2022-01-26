package com.pickpl.dto;

public class ReportDto {
	private int idx;
	private String report_id;
	private String report_date;
	private String report_type;
	private String target_id;
	private int target_d_id;
	private String contents;
	private String mng_contents;
	private String stat;
	
	public ReportDto() { }
	public ReportDto(int idx, String report_id, String report_date, String report_type, String target_id,
			int target_d_id, String contents, String mng_contents, String stat) {
		this.idx = idx;
		this.report_id = report_id;
		this.report_date = report_date;
		this.report_type = report_type;
		this.target_id = target_id;
		this.target_d_id = target_d_id;
		this.contents = contents;
		this.mng_contents = mng_contents;
		this.stat = stat;
	}
	
	public int getIdx() {
		return idx;
	}
	public String getReport_id() {
		return report_id;
	}
	public String getReport_date() {
		return report_date;
	}
	public String getReport_type() {
		return report_type;
	}
	public String getTarget_id() {
		return target_id;
	}
	public int getTarget_d_id() {
		return target_d_id;
	}
	public String getContents() {
		return contents;
	}
	public String getMng_contents() {
		return mng_contents;
	}
	public String getStat() {
		return stat;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}
	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}
	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}
	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}
	public void setTarget_d_id(int target_d_id) {
		this.target_d_id = target_d_id;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public void setMng_contents(String mng_contents) {
		this.mng_contents = mng_contents;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
}
