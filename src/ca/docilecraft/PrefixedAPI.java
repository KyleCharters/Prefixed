package ca.docilecraft;

import org.bukkit.entity.Player;

public class PrefixedAPI{
	/**
	 * Gets a player's prefix
	 * 
	 * @param player The player's name
	 * @return The prefix of supplied player
	 */
	public static String getPrefix(String player){
		return CustomsHandler.getPrefix(player);
	}
	
	/**
	 * Gets a player's suffix
	 * 
	 * @param player The player's name
	 * @return The suffix of supplied player
	 */
	public static String getSuffix(String player){
		return CustomsHandler.getSuffix(player);
	}
	
	/**
	 * Sets a player's prefix
	 * 
	 * @param player The player's name
	 * @param prefix The player's new prefix
	 */
	public static void setPrefix(String player, String prefix){
		CustomsHandler.setPrefix(player, prefix);
	}
	
	/**
	 * Sets a player's suffix
	 * 
	 * @param player The player's name
	 * @param suffix The player's new suffix
	 */
	public static void setSuffix(String player, String suffix){
		CustomsHandler.setSuffix(player, suffix);
	}
	
	/**
	 * Adds a parameter to the Prefixed MessageHandler
	 * 
	 * @param parameter The parameter to add
	 */
	public static void addParameter(PrefixedParameter parameter){
		MessageHandler.parameters.add(parameter);
	}
	
	/**
	 * Removes a parameter from the Prefixed MessageHandler
	 * 
	 * @param parameter the parameter to remove
	 * @return true if the parameter was successfully removed
	 */
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
