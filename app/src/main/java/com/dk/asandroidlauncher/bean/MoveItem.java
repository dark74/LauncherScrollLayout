package com.dk.asandroidlauncher.bean;import java.io.Serializable;import com.lidroid.xutils.db.annotation.Id;public class MoveItem implements Serializable {	private static final long serialVersionUID = 3388701081007512693L;	@Id	private int _id;	//id	private int mid;	//正常模式下的item的Drawable Id	private int img_normal;	//按下模式下的item的Drawable Id	private int img_pressed;	//item的排序字段	private int orderId;	public int get_id() {		return _id;	}	public void set_id(int _id) {		this._id = _id;	}	public int getMid() {		return mid;	}	public void setMid(int mid) {		this.mid = mid;	}	public int getImgurl() {		return img_normal;	}	public void setImgurl(int imgurl) {		this.img_normal = imgurl;	}	public int getImgdown() {		return img_pressed;	}	public void setImgdown(int imgdown) {		this.img_pressed = imgdown;	}	public int getOrderId() {		return orderId;	}	public void setOrderId(int orderId) {		this.orderId = orderId;	}}