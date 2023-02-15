package com.yart.literule.core.debug;


public class MessageItem {
	private final String msg;
	private final MsgType type;
	public MessageItem(String msg,MsgType type) {
		this.msg=msg;			
		this.type=type;
	}
	public String toHtml(){
		String color="#000";
		switch(type){
		case Condition:
			color="#6495ED";
			break;
		case ConsoleOutput:
			color="#000";
			break;
		case ExecuteBeanMethod:
			color="#8A2BE2";
			break;
		case ExecuteFunction:
			color="#008B8B";
			break;
		case RuleFlow:
			color="#9932CC";
			break;
		case VarAssign:
			color="#FF7F50";
			break;
		case ScoreCard:
			color="#40E0D0";
			break;
		case RuleMatch:
			color="#666600";
			break;
		}
		return "<div style=\"color:"+color+";margin:2px\">"+msg+"</div>";
	}
	public String getMsg() {
		return msg;
	}
	public MsgType getType() {
		return type;
	}
}
