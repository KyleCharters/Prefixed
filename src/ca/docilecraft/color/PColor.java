package ca.docilecraft.color;

public enum PColor{
	BLACK('0', "BLACK"),
	DARKBLUE('1', "DARKBLUE"),
	DARKGREEN('2', "DARKGREEN"),
	DARKAQUA('3', "DARKAQUA"),
	DARKRED('4', "DARKRED"),
	PURPLE('5', "PURPLE"),
	GOLD('6', "GOLD"),
	GRAY('7', "GRAY"),
	DARKGRAY('8', "DARKGRAY"),
	BLUE('9', "BLUE"),
	GREEN('a', "GREEN"),
	AQUA('b', "AQUA"),
	RED('c', "RED"),
	PINK('d', "PINK"),
	YELLOW('e', "YELLOW"),
	WHITE('f', "WHITE"),
	
	OBFUSCATED('k', "OBFUSCATED"),
	BOLD('l', "BOLD"),
	STRIKE('m', "STRIKE"),
	UNDERLINE('n', "UNDERLINE"),
	ITALIC('o', "ITALIC"),
	RESET('r', "RESET");
	
	private PColor(Character code, String name){
		this.code = code;
		this.name = name;
	}

	private Character code;
	private String name;
	
	@Override
	public String toString(){
		return "§"+code;
	}
	
	public char toCode(){
		return code;
	}
	
	public String toName(){
		return name;
	}
	
	public int getLength(){
		return name.length();
	}
	
	/*
	 * STATIC METHODS
	 */
	
	public static boolean isValidColor(String string){
		for(String color : string.split(","))
			for(PColor pcolor : PColor.values())
				if(color.equalsIgnoreCase(pcolor.toName()))
					return true;
		return false;
	}
	
	public static String translateColorsToNames(String string){
		char[] text = string.toCharArray();
		StringBuilder builder = new StringBuilder();
		
		CHARACTER: for(int i = 0 ; i < text.length ; i++){
			if(text[i] == '&'){
				if(isCanceled(string, i)){
					builder.deleteCharAt(builder.length()-1);
					builder.append('&');
					continue;
				}
				
				for(PColor pcolor : PColor.values()){
					if(nextEquals(string, i, pcolor.toCode())){
						i = appendColor(i, builder, pcolor);
						continue CHARACTER;
					}
				}
			}
			builder.append(text[i]);
		}
		
		return builder.toString();
	}
	
	public static String translateColors(String string){
		char[] text = string.toCharArray();
		StringBuilder builder = new StringBuilder();
		
		CHARACTER: for(int i = 0 ; i < text.length ; i++){
			if(text[i] == '&'){
				if(isCanceled(string, i)){
					builder.deleteCharAt(builder.length()-1);
					builder.append('&');
					continue;
				}
				
				for(PColor pcolor : PColor.values()){
					if(nextEquals(string, i, pcolor.toName())){
						i = appendColor(i, builder, pcolor);
						continue CHARACTER;
					}
				}
				
				for(PColor pcolor : PColor.values()){
					if(nextEquals(string, i, pcolor.toCode())){
						i = appendColorCode(i, builder, pcolor);
						continue CHARACTER;
					}
				}
			}
			builder.append(text[i]);
		}
		
		return builder.toString();
	}
	
	/*
	 * UTILITY
	 */
	
	private static boolean isCanceled(String text, int location){
		return (location != 0) && (text.charAt(location-1) == '\\');
	}
	
	private static boolean nextEquals(String text, int location, String value){
		location += 1;
		if(location+value.length() <= text.length()){
			if(text.substring(location, location+value.length()).equalsIgnoreCase(value)){
				return true;
			}
		}
		return false;
	}
	
	private static boolean nextEquals(String text, int location, Character value){
		location += 1;
		return (location != text.length() && characterEqualsIgnoreCase(text.charAt(location), value));
	}
	
	private static int appendColor(int index, StringBuilder builder, PColor color){
		builder.append(color.toString());
		return index + color.getLength();
	}
	
	private static int appendColorCode(int index, StringBuilder builder, PColor color){
		builder.append(color.toString());
		return index + 1;
	}
	
	private static boolean characterEqualsIgnoreCase(Character a, Character b){
		return Character.toLowerCase(a) == Character.toLowerCase(b);
	}
}