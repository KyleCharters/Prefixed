package ca.docilecraft.color;

import java.util.ArrayList;

public class PColorBuilder{
	private ArrayList<PColor> colors = new ArrayList<PColor>();
	
	public PColor[] getColors(){
		return (PColor[]) colors.toArray();
	}
	
	public boolean isEmpty(){
		return colors.isEmpty();
	}
	
	public void set(PColor color){
		colors.clear();
		colors.add(color);
	}
	
	public void append(PColor color){
		colors.add(color);
	}
	
	public void remove(PColor color){
		colors.remove(color);
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		
		for(PColor color : colors){
			builder.append(color.toString());
		}
		
		return builder.toString();
	}
	
	public String getNames(){
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < colors.size(); i++){
			builder.append(colors.get(i).toName());
			
			if(i < colors.size()-1){
				builder.append(",");
			}
		}
		
		return builder.toString();
	}
}
