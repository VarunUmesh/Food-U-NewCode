package model;

public class MenuItem {
	private int eatery;
	private String name;
	private double price;
	private byte[] img;
	
	public MenuItem(int e, String i, double p, byte[] im){
		this.eatery = e;
		this.name = i;
		this.price = p;
		this.img = im;
	}
	
	public int getEatery(){
		return eatery;
	}
	
	public double getPrice(){
		return price;
	}
	
	public String getName(){
		return name;
	}
	
	public byte[] getImage(){
		return img;
	}
}
