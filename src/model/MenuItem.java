package model;

public class MenuItem {
	private int eatery;
	private String name;
	
	public MenuItem(int e, String i){
		this.eatery = e;
		this.name = i;
	}
	
	public int getEatery(){
		return eatery;
	}
	
	public String getName(){
		return name;
	}
}
