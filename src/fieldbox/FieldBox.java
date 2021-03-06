package fieldbox;

import field.app.RunLoop;
import field.utility.Options;
import fieldagent.Main;
import fieldbox.io.IO;
import fieldcef.browser.CefSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.OpenFilesEvent;
import java.awt.desktop.OpenFilesHandler;
import java.awt.desktop.SystemEventListener;

/**
 * The main entry-point for Field2.
 */
public class FieldBox {

	static public final FieldBox fieldBox = new FieldBox();
	static public String[] args;
	public static String workspace;

	public IO io;

	public void go() {
		RunLoop.main.enterMainLoop();
	}


	static public void main(String[] s) {
//		SwingUtilities.invokeLater(() -> {
			args = s;
			Options.parseCommandLine(s);

			// needs to be initialized after 'args'
			workspace = Options.getDirectory("workspace", () -> System.getProperty("user.home") + "/Documents/FieldWorkspace/");


			System.err.println(" lauching toolkit");
			if (Main.os == Main.OS.mac) {
				Toolkit.getDefaultToolkit();

				// this possibly works on windows?

//			Desktop.getDesktop().setOpenFileHandler(new OpenFilesHandler() {
//				@Override
//				public void openFiles(OpenFilesEvent e) {
//					System.out.println("\n\n!! open files !! \n\n" + e);
//				}
//			});
			}


			// experimenting with moving this initialization first. Seems to remove the occasional crash on startup?
			new Thread(() -> {
				System.err.println(" building the CefSystem");
				CefSystem sys = CefSystem.cefSystem;
				System.err.println(" finished building the CefSystem");
			}).start();

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.err.println(" finished hanging out");


			LoggingDefaults.initialize();

			fieldBox.io = new IO(workspace);
			fieldBox.io.addFilespec("code", IO.EXECUTION, IO.EXECUTION);

			new Open(Options.getString("file", () -> "testIB.field2"));

			SplashScreen splash = SplashScreen.getSplashScreen();
			if (splash != null)
				splash.close();

			fieldBox.go();
//		});
	}


}
