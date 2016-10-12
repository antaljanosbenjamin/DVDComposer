package hu.smiths.dvdcomposer.utils;

import java.io.File;
import java.io.FileNotFoundException;

import de.tu_darmstadt.informatik.rbg.hatlak.eltorito.impl.ElToritoConfig;
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.ConfigException;
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.ISO9660File;
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.ISO9660RootDirectory;
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.impl.CreateISO;
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.impl.ISO9660Config;
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.impl.ISOImageFileHandler;
import de.tu_darmstadt.informatik.rbg.hatlak.joliet.impl.JolietConfig;
import de.tu_darmstadt.informatik.rbg.hatlak.rockridge.impl.RockRidgeConfig;
import de.tu_darmstadt.informatik.rbg.mhartle.sabre.HandlerException;

public class ISOWriter {

	public void writeDummyData() {

		try {

			// Output file
			File outfile = new File("ISOTest6.iso");

			// Directory hierarchy, starting from the root
			ISO9660RootDirectory.MOVED_DIRECTORIES_STORE_NAME = "rr_moved";
			ISO9660RootDirectory root = new ISO9660RootDirectory();

			// Files with different versions
			// (to appear in descending order, pointing to same LSN)
			ISO9660File file1;
			file1 = new ISO9660File("a.txt");

			root.addFile(file1);
			ISO9660File file2 = new ISO9660File("b.txt");
			root.addFile(file2);
			ISO9660File file3 = new ISO9660File("c.txt");
			root.addFile(file3);

			// ISO9660 support
			ISO9660Config iso9660Config = new ISO9660Config();
			iso9660Config.allowASCII(false);
			iso9660Config.setInterchangeLevel(1);
			iso9660Config.restrictDirDepthTo8(true);
			iso9660Config.setPublisher("Name Nickname");
			iso9660Config.setVolumeID("ISO Test Jiic");
			iso9660Config.setDataPreparer("Name Nickname");

			// iso9660Config.setCopyrightFile(new File("Copyright.txt"));
			iso9660Config.forceDotDelimiter(true);

			RockRidgeConfig rrConfig = null;
			ElToritoConfig elToritoConfig = null;

			JolietConfig jolietConfig = null;

			// Joliet support
			jolietConfig = new JolietConfig();
			jolietConfig.setPublisher("Test 2");
			jolietConfig.setVolumeID("Joliet Test");
			jolietConfig.setDataPreparer("Jens Hatlak");
			// jolietConfig.setCopyrightFile(new File("Copyright.txt"));
			jolietConfig.forceDotDelimiter(true);

			ISOImageFileHandler streamHandler = new ISOImageFileHandler(outfile);
			CreateISO iso = new CreateISO(streamHandler, root);
			iso.process(iso9660Config, rrConfig, jolietConfig, elToritoConfig);
		} catch (HandlerException | ConfigException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
