package ca.docilecraft.color;



public enum PColor{
	BLACK("0", "BLACK"),
	DARKBLUE("1", "DARKBLUE"),
	DARKGREEN("2", "DARKGREEN"),
	DARKAQUA("3", "DARKAQUA"),
	DARKRED("4", "DARKRED"),
	PURPLE("5", "PURPLE"),
	GOLD("6", "GOLD"),
	GRAY("7", "GRAY"),
	DARKGRAY("8", "DARKGRAY"),
	BLUE("9", "BLUE"),
	GREEN("a", "GREEN"),
	AQUA("b", "AQUA"),
	RED("c", "RED"),
	PINK("d", "PINK"),
	YELLOW("e", "YELLOW"),
	WHITE("f", "WHITE"),
	
	OBFUSCATED("k", "OBFUSCATED"),
	BOLD("l", "BOLD"),
	STRIKE("m", "STRIKE"),
	UNDERLINE("n", "UNDERLINE"),
	ITALIC("o", "ITALIC"),
	RESET("r", "RESET");
	
	private PColor(String code, String name){
		this.code = code;
		this.name = name;
	}

	private String code;
	private String name;
	
	public String toString(){
		return "§"+code;
	}
	
	public String toCode(){
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
	
	public static boolean isValidColor(String color){
		for(PColor pcolor : PColor.values())
			if(color.equalsIgnoreCase(pcolor.toName()))
				return true;
		return false;
	}
	
	public static PColorBuilder getCodesFromString(String string){
		PColorBuilder pcolorbuilder = new PColorBuilder();
		
		COLORS: for(String split : string.split(",")){
			for(PColor pcolor : PColor.values()){
				if(split.equalsIgnoreCase(pcolor.toName())){
					pcolorbuilder.append(pcolor);
					continue COLORS;
				}
			}
		}
		
		return pcolorbuilder;
	}
	
	public static String getStringFromCodes(String string){
		PColorBuilder pcolorbuilder = new PColorBuilder();
		
		COLORS: for(String split : string.split("§")){
			for(PColor pcolor : PColor.values()){
				if(split.equalsIgnoreCase(pcolor.toCode())){
					pcolorbuilder.append(pcolor);
					continue COLORS;
				}
			}
		}
		
		return pcolorbuilder.getNames();
	}
	
	public static String translateTextColors(String toTranslate){
		char[] text = toTranslate.toCharArray();
		StringBuilder builder = new StringBuilder();
		
		CHARACTER: for(int i = 0 ; i < text.length ; i++){
			if((text[i] == '&')){
				if((i != 0) && (text[i-1] == '\\')){
					builder.deleteCharAt(builder.length()-1);
					builder.append('&');
					continue;
				}
				
				for(PColor pcolor : PColor.values()){
					if(insideAndEquals(toTranslate, i, pcolor.toCode())){
						i = appendColor(i, builder, pcolor);
						continue CHARACTER;
					}
				}
			}
			builder.append(text[i]);
		}
		
		return builder.toString();
	}
	
	public static String translateColorCodes(String toTranslate){
		char[] text = toTranslate.toCharArray();
		StringBuilder builder = new StringBuilder();
		
		CHARACTER: for(int i = 0 ; i < text.length ; i++){
			if((text[i] == '&')){
				if((i != 0) && (text[i-1] == '\\')){
					builder.deleteCharAt(builder.length()-1);
					builder.append('&');
					continue;
				}
				
				for(PColor pcolor : PColor.values()){
					if(insideAndEquals(toTranslate, i, pcolor.toName())){
						i = appendColor(i, builder, pcolor);
						continue CHARACTER;
					}
					
					if(insideAndEquals(toTranslate, i, pcolor.toCode())){
						i = appendColorCode(i, builder, pcolor);
						continue CHARACTER;
					}
				}
			}
			builder.append(text[i]);
		}
		
		return builder.toString();
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
	
	private static int appendColor(int index, StringBuilder builder, PColor color){
		builder.append(color.toString());
		return index + color.getLength();
	}
	
	private static int appendColorCode(int index, StringBuilder builder, PColor color){
		builder.append(color.toString());
		return index + 1;
	}
}