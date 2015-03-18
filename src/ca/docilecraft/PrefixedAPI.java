package ca.docilecraft;

import org.bukkit.entity.Player;

public class PrefixedAPI{
	public static String getPrefix(){
		return "";
	}
	
	public static String getSuffix(){
		return "";
	}
	
	public static void setPrefix(String suffix, String player){
		
	}
	
	public static void setSuffix(String suffix, String player){
		
	}
	
	public static void addParameter(PrefixedParameter parameter){
		MessageHandler.parameters.add(parameter);
	}
	
	public static boolean removeParameter(PrefixedParameter parameter){
		return MessageHandler.parameters.remove(parameter);
	}
	
	public static abstract class PrefixedParameter{
		public PrefixedParameter(String name){
			this.name = name;
		}
		public String name;
		
		public abstract String use(Player player, String message);
	}
}