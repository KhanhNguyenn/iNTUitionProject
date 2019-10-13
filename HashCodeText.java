package iNTuition.ternary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HashCodeText {
	public static int getName(String script_text) {
		return script_text.trim().hashCode();
	}

	public static int getName(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		String script_text="";
		while ((line = br.readLine()) != null) {
			script_text+= line;
			script_text+="\n";
		}
		script_text.trim();
		return getName(script_text);
	}
}
