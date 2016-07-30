package ca.docilecraft;

import static ca.docilecraft.CustomsHandler.PlayerOption.COLOR;
import static ca.docilecraft.CustomsHandler.PlayerOption.PREFIX;
import static ca.docilecraft.CustomsHandler.PlayerOption.SUFFIX;

import java.util.UUID;

import org.bukkit.entity.Player;

import ca.docilecraft.color.PColor;

public class PrefixedAPI{
	/**
	 * Gets a player's prefix
	 * 
	 * @param player The player
	 * @return The prefix of supplied player
	 */
	public static String get(Player player){return CustomsHandler.get(player.getUniqueId(), PREFIX);}
	
	/**
	 * Gets a player's prefix
	 * 
	 * @param uuid The player's uuid
	 * @return The prefix of supplied player
	 */
	public static String get(UUID uuid){return CustomsHandler.get(uuid, PREFIX);}
	
	/**
	 * Gets a player's prefix
	 * 
	 * @param name The player's name
	 * @return The prefix of supplied player
	 */
	public static String get(String name){return CustomsHandler.get(name, PREFIX);}
	
	/**
	 * Gets a player's suffix
	 * 
	 * @param player The player
	 * @return The suffix of supplied player
	 */
	public static String getSuffix(Player player){return CustomsHandler.get(player.getUniqueId(), SUFFIX);}
	
	/**
	 * Gets a player's suffix
	 * 
	 * @param uuid The player's uuid
	 * @return The suffix of supplied player
	 */
	public static String getSuffix(UUID uuid){return CustomsHandler.get(uuid, SUFFIX);}
	
	/**
	 * Gets a player's suffix
	 * 
	 * @param name The player's name
	 * @return The suffix of supplied player
	 */
	public static String getSuffix(String name){return CustomsHandler.get(name, SUFFIX);}
	
	/**
	 * Gets a player's color
	 * 
	 * @param player The player
	 * @return The color of supplied player
	 */
	public static String getColor(Player player){return CustomsHandler.get(player.getUniqueId(), COLOR);}
	
	/**
	 * Gets a player's color
	 * 
	 * @param uuid The player's uuid
	 * @return The color of supplied player
	 */
	public static String getColor(UUID uuid){return CustomsHandler.get(uuid, COLOR);}
	
	/**
	 * Gets a player's color
	 * 
	 * @param name The player's name
	 * @return The color of supplied player
	 */
	public static String getColor(String name){return CustomsHandler.get(name, COLOR);}
	
	/**
	 * Sets a player's prefix
	 * 
	 * @param player The player
	 * @param prefix The player's new prefix
	 */
	public static void setPrefix(Player player, String prefix){CustomsHandler.set(player.getUniqueId(), PREFIX, prefix);}
	
	/**
	 * Sets a player's prefix
	 * 
	 * @param uuid The player's uuid
	 * @param prefix The player's new prefix
	 */
	public static void setPrefix(UUID uuid, String prefix){
		CustomsHandler.set(uuid, PREFIX, prefix);
	}
	
	/**
	 * Sets a player's prefix
	 * 
	 * @param name The player's name
	 * @param prefix The player's new prefix
	 */
	public static void setPrefix(String name, String prefix){CustomsHandler.set(name, PREFIX, prefix);}
	
	/**
	 * Sets a player's suffix
	 * 
	 * @param player The player
	 * @param suffix The player's new suffix
	 */
	public static void setSuffix(Player player, String suffix){CustomsHandler.set(player.getUniqueId(), SUFFIX, suffix);}
	
	/**
	 * Sets a player's suffix
	 * 
	 * @param uuid The player's uuid
	 * @param suffix The player's new suffix
	 */
	public static void setSuffix(UUID uuid, String suffix){CustomsHandler.set(uuid, SUFFIX, suffix);}
	
	/**
	 * Sets a player's suffix
	 * 
	 * @param name The player's name
	 * @param suffix The player's new suffix
	 */
	public static void setSuffix(String name, String suffix){CustomsHandler.set(name, SUFFIX, suffix);}
	
	/**
	 * Sets a player's color
	 * 
	 * @param player The player
	 * @param color The player's new color
	 */
	public static void setColor(Player player, String color){if(PColor.isValidColor(color))CustomsHandler.set(player.getUniqueId(), COLOR, color);}
	
	/**
	 * Sets a player's color
	 * 
	 * @param uuid The player's uuid
	 * @param color The player's new color
	 */
	public static void setColor(UUID uuid, String color){if(PColor.isValidColor(color))CustomsHandler.set(uuid, COLOR, color);}
	
	/**
	 * Sets a player's color
	 * 
	 * @param name The player's name
	 * @param color The player's new color
	 */
	public static void setColor(String name, String color){if(PColor.isValidColor(color))CustomsHandler.set(name, COLOR, color);}
	
	/**
	 * Adds a parameter to the Prefixed MessageHandler
	 * 
	 * @param parameter The parameter to add
	 * @return true if the parameter was successfully added
	 */
	public static boolean addParameter(PrefixedParameter parameter){return MessageHandler.loadParameter(parameter);}
	
	/**
	 * Removes a parameter from the Prefixed MessageHandler
	 * 
	 * @param parameter the parameter to remove
	 * @return true if the parameter was successfully removed
	 */
	public static boolean removeParameter(PrefixedParameter parameter){return MessageHandler.parameters.remove(parameter);}
	
	public static abstract class PrefixedParameter{
		/**
		 * @param name The parameter that activates this use
		 */
		public PrefixedParameter(String name){
			this.name = name;
		}
		public String name;
		
		/**
		 * Activated when user sends message
		 * 
		 * @param player The player
		 * @param message The player's message
		 * @return The text to be placed in the chat
		 */
		public abstract String use(Player player, String message);
	}
}
