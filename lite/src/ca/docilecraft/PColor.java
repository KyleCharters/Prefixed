package ca.docilecraft;

public class PColor{
	public static final String BLACK = "§0";
	public static final String DARKBLUE = "§1";
	public static final String DARKGREEN = "§2";
	public static final String DARKAQUA = "§3";
	public static final String DARKRED = "§4";
	public static final String PURPLE = "§5";
	public static final String GOLD = "§6";
	public static final String GRAY = "§7";
	public static final String DARKGRAY = "§8";
	public static final String BLUE = "§9";
	public static final String GREEN = "§a";
	public static final String AQUA = "§b";
	public static final String RED = "§c";
	public static final String PINK = "§d";
	public static final String YELLOW = "§e";
	public static final String WHITE = "§f";
	
	public static final String OBFUSCATED = "§k";
	public static final String BOLD = "§l";
	public static final String STRIKE = "§m";
	public static final String UNDERLINE = "§n";
	public static final String ITALIC = "§o";
	public static final String RESET = "§r";
	
	public static String toString(String color){
		if(color.equalsIgnoreCase(BLACK)) return "Black";
		if(color.equalsIgnoreCase(DARKBLUE)) return "Dark Blue";
		if(color.equalsIgnoreCase(DARKGREEN)) return "Dark Green";
		if(color.equalsIgnoreCase(DARKAQUA)) return "Dark Aqua";
		if(color.equalsIgnoreCase(DARKRED)) return "Dark Red";
		if(color.equalsIgnoreCase(PURPLE)) return "Purple";
		if(color.equalsIgnoreCase(GOLD)) return "Gold";
		if(color.equalsIgnoreCase(GRAY)) return "Gray";
		if(color.equalsIgnoreCase(DARKGRAY)) return "Dark Gray";
		if(color.equalsIgnoreCase(BLUE)) return "Blue";
		if(color.equalsIgnoreCase(GREEN)) return "Green";
		if(color.equalsIgnoreCase(AQUA)) return "Aqua";
		if(color.equalsIgnoreCase(RED)) return "Red";
		if(color.equalsIgnoreCase(PINK)) return "Pink";
		if(color.equalsIgnoreCase(YELLOW)) return "Yellow";
		if(color.equalsIgnoreCase(WHITE)) return "White";
		
		if(color.equalsIgnoreCase(OBFUSCATED)) return "Obfuscated";
		if(color.equalsIgnoreCase(BOLD)) return "Bold";
		if(color.equalsIgnoreCase(STRIKE)) return "Strike";
		if(color.equalsIgnoreCase(UNDERLINE)) return "Underline";
		if(color.equalsIgnoreCase(ITALIC)) return "Italic";
		if(color.equalsIgnoreCase(RESET)) return "Reset";
		return "";
	}
	
	public static String translateColorCodes(String toTranslate){
		char[] text = toTranslate.toCharArray();
		StringBuilder n = new StringBuilder();
		
		for(int i = 0 ; i < text.length ; i++){
			if((text[i] == '&')){
				if((i != 0) && (text[i-1] == '\\')){
					n.deleteCharAt(n.length()-1);
					n.append('&');
					continue;
				}
				
				if(insideAndEquals(toTranslate, i, "BLACK")){
					n.append(BLACK);
					i += 5;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "DARKBLUE")){
					n.append(DARKBLUE);
					i += 8;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "DARKGREEN")){
					n.append(DARKGREEN);
					i += 9;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "DARKAQUA")){
					n.append(DARKAQUA);
					i += 8;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "DARKRED")){
					n.append(DARKRED);
					i += 7;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "PURPLE")){
					n.append(PURPLE);
					i += 6;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "GOLD")){
					n.append(GOLD);
					i += 4;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "GRAY")){
					n.append(GRAY);
					i += 4;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "DARKGRAY")){
					n.append(DARKGRAY);
					i += 8;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "BLUE")){
					n.append(BLUE);
					i += 4;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "GREEN")){
					n.append(GREEN);
					i += 5;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "AQUA")){
					n.append(AQUA);
					i += 4;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "RED")){
					n.append(RED);
					i += 3;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "PINK")){
					n.append(PINK);
					i += 4;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "YELLOW")){
					n.append(YELLOW);
					i += 6;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "WHITE")){
					n.append(WHITE);
					i += 5;
					continue;
				}

				if(insideAndEquals(toTranslate, i, "OBFUSCATED")){
					n.append(OBFUSCATED);
					i += 10;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "BOLD")){
					n.append(BOLD);
					i += 4;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "STRIKE")){
					n.append(STRIKE);
					i += 6;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "UNDERLINE")){
					n.append(UNDERLINE);
					i += 9;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "ITALIC")){
					n.append(ITALIC);
					i += 6;
					continue;
				}
				if(insideAndEquals(toTranslate, i, "RESET")){
					n.append(RESET);
					i += 5;
					continue;
				}
					
				if((i+1 != text.length) && ("0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(text[i + 1]) > -1)){
					n.append('§');
					n.append(Character.toLowerCase(text[i + 1]));
					i += 1;
					continue;
				}
			}
			n.append(text[i]);
		}
		
		return n.toString();
	}
	
	private static boolean insideAndEquals(String text, int location, String value){
		location += 1;
		if(location+value.length() <= text.length()){
			if(text.substring(location, location+value.length()).equalsIgnoreCase(value)){
				return true;
			}
		}
		
		return false;
	}
}