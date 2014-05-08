package fieldbox;

import field.graphics.*;
import fieldbox.io.IO;

/**
 * Created by marc on 3/21/14.
 */
public class FieldBox {

	static public final FieldBox fieldBox = new FieldBox();

	public final IO io = new IO("/home/marc/Documents/FirstNewFieldWorkspace/");

	{
		io.addFilespec("code", io.EXECUTION, io.EXECUTION);

		io.addFilespec("fragment", ".glslf", "glsl");
		io.addFilespec("vertex", ".glslf", "glsl");
		io.addFilespec("geometry", ".glslg", "glsl");
	}

	public void go() {
		RunLoop.main.enterMainLoop();
	}


	static public void main(String[] s) {

		Open open = new Open("testFile.field2");

		fieldBox.go();
	}



}