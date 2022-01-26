package com.pickpl.admin.dto;

public class ReportDto {
	private int reportIdx;
	private String reportId;
	private String reportDate;
	private String reportType;
	private String targetId;
	private String targetDId;
	private String contents;
	private String mngContents;
	private String stat;
	
	public ReportDto() {}

	public ReportDto(int reportIdx, String reportId, String reportDate, String reportType, String targetId,
			String targetDId, String contents, String mngCongtents, String stat) {
		this.reportIdx = reportIdx;
		this.reportId = reportId;
		this.reportDate = reportDate;
		this.reportType = reportType;
		this.targetId = targetId;
		this.targetDId = targetDId;
		this.contents = contents;
		this.mngContents = mngCongtents;
		this.stat = stat;
	}

	public int getReportIdx() {
		return reportIdx;
	}
	public String getReportId() {
		return reportId;
	}
	public String getReportDate() {
		return reportDate;
	}
	public String getReportType() {
		return reportType;
	}
	public String getTargetId() {
		return targetId;
	}
	public String getTargetDId() {
		return targetDId;
	}
	public String getContents() {
		return contents;
	}
	public String getMngContents() {
		return mngContents;
	}
	public String getStat() {
		return stat;
	}
	public void setReportIdx(int reportIdx) {
		this.reportIdx = reportIdx;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public void setTargetDId(String targetDId) {
		this.targetDId = targetDId;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public void setMngContents(String mngContents) {
		this.mngContents = mngContents;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}

	@Override
	public String toString() {
		return "[reportIdx=" + reportIdx + ", reportId=" + reportId + ", reportDate=" + reportDate
				+ ", reportType=" + reportType + ", targetId=" + targetId + ", targetDId=" + targetDId + ", contents="
				+ contents + ", mngContents=" + mngContents + ", stat=" + stat + "]";
	}
}
