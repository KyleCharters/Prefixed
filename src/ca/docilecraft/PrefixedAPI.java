package ca.docilecraft;

import org.bukkit.entity.Player;

public class PrefixedAPI{
	public static String getPrefix(String player){
		return CustomsHandler.getPrefix(player);
	}
	
	public static String getSuffix(String player){
		return CustomsHandler.getSuffix(player);
	}
	
	public static void setPrefix(String player, String prefix){
		CustomsHandler.setPrefixe(player, prefix);
	}
	
	public static void setSuffix(String player, String suffix){
		CustomsHandler.setSuffix(player, suffix);
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
