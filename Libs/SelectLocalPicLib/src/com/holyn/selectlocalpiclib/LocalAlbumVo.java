package com.holyn.selectlocalpiclib;

import java.io.Serializable;
import java.util.List;

public class LocalAlbumVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String name;
	String coverPath;
	List<LocalImageVo> localImageVos;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCoverPath() {
		return coverPath;
	}
	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}
	public List<LocalImageVo> getLocalImageVos() {
		return localImageVos;
	}
	public void setLocalImageVos(List<LocalImageVo> localImageVos) {
		this.localImageVos = localImageVos;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
