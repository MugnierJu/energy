package model;

import java.util.Date;

public class Data {

	Date date;
	int electricCooker;
	int freezer;
	int lamp;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getElectricCooker() {
		return electricCooker;
	}
	public void setElectricCooker(int electricCooker) {
		this.electricCooker = electricCooker;
	}
	public int getFreezer() {
		return freezer;
	}
	public void setFreezer(int freezer) {
		this.freezer = freezer;
	}
	public int getLamp() {
		return lamp;
	}
	public void setLamp(int lamp) {
		this.lamp = lamp;
	}
	
	public Data(Date date, int electricCooker, int freezer, int lamp) {
		super();
		this.date = date;
		this.electricCooker = electricCooker;
		this.freezer = freezer;
		this.lamp = lamp;
	}
}
