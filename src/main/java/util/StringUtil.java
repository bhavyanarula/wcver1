package util;

import java.util.HashSet;

public class StringUtil {
	public static HashSet<String> hs = new HashSet<String>();
	static {
		hs.add("aboard");
		hs.add("about");
		hs.add("above");
		hs.add("across");
		hs.add("after");
		hs.add("against");
		hs.add("along");
		hs.add("amid");
		hs.add("among");
		hs.add("anti");
		hs.add("around");
		hs.add("as");
		hs.add("at");
		hs.add("before");
		hs.add("behind");
		hs.add("below");
		hs.add("beneath");
		hs.add("beside");
		hs.add("besides");
		hs.add("between");
		hs.add("beyond");
		hs.add("but");
		hs.add("by");
		hs.add("concerning");
		hs.add("considering");
		hs.add("despite");
		hs.add("down");
		hs.add("during");
		hs.add("except");
		hs.add("excepting");
		hs.add("excluding");
		hs.add("following");
		hs.add("for");
		hs.add("from");
		hs.add("in");
		hs.add("inside");
		hs.add("into");
		hs.add("like");
		hs.add("minus");
		hs.add("near");
		hs.add("of");
		hs.add("off");
		hs.add("on");
		hs.add("onto");
		hs.add("opposite");
		hs.add("outside");
		hs.add("over");
		hs.add("past");
		hs.add("per");
		hs.add("plus");
		hs.add("regarding");
		hs.add("round");
		hs.add("save");
		hs.add("since");
		hs.add("than");
		hs.add("through");
		hs.add("to");
		hs.add("toward");
		hs.add("towards");
		hs.add("under");
		hs.add("underneath");
		hs.add("unlike");
		hs.add("until");
		hs.add("up");
		hs.add("upon");
		hs.add("versus");
		hs.add("via");
		hs.add("with");
		hs.add("within");
		hs.add("without");
		hs.add("without");
		hs.add("have");
		hs.add("has");
		hs.add("had");
		hs.add("do");
		hs.add("does");
		hs.add("did");
		hs.add("shall");
		hs.add("will");
		hs.add("should");
		hs.add("would");
		hs.add("may");
		hs.add("might");
		hs.add("must");
		hs.add("can");
		hs.add("could");
		hs.add("am");
		hs.add("is");
		hs.add("are");
		hs.add("was");
		hs.add("were");
		hs.add("be");
		hs.add("being");
		hs.add("been");
	}
	public static String toSentanceCase(String inp){
		String output = "";
		String arr [] = inp.split(" ");
		for(int i =0;i<arr.length;i++){
			String a = arr[i];
			if(!hs.contains(a)){
				a =  (char)(((int)a.charAt(0))-32)+a.substring(1);
			}
				
			arr[i] = a;
		}
		for(String a : arr){
			output+=a+" ";
		}
		return output.trim();
	}

}
