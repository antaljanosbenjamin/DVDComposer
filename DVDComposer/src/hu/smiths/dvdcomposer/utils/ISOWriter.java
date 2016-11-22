package hu.smiths.dvdcomposer.utils;

import java.io.File;
import java.io.FileNotFoundException;

import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.ConfigException;
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.ISO9660RootDirectory;
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.impl.CreateISO;
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.impl.ISO9660Config;
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.impl.ISOImageFileHandler;
import de.tu_darmstadt.informatik.rbg.hatlak.joliet.impl.JolietConfig;
import de.tu_darmstadt.informatik.rbg.mhartle.sabre.HandlerException;
import hu.smiths.dvdcomposer.model.exceptions.CannotCreateISOFile;

public class ISOWriter {

	private CreateISO iso;

	private File output;

	private ISO9660Config iso9660Config;

	private JolietConfig jolietConfig;

	private ISO9660RootDirectory root;

	public void writeContentToFile(File output) throws CannotCreateISOFile, HandlerException {
		try {
			this.output = output;
			initializeAll();
			writeData();
		} catch (FileNotFoundException | ConfigException e) {
			throw new CannotCreateISOFile(e);
		}
	}

	public ISOWriter() {
		initializeRoot();
	}

	public void addFolderToISOImage(File folder) throws HandlerException {
		root.addRecursively(folder);
	}

	private void writeData() throws HandlerException {
		iso.process(iso9660Config, null, jolietConfig, null);

	}

	private void initializeAll() throws ConfigException, FileNotFoundException {
		initializeISOConfig();
		initializeJolietConfig();
		initializeCreateISO();
	}

	private void initializeISOConfig() throws ConfigException {
		iso9660Config = new ISO9660Config();
		iso9660Config.allowASCII(false);
		iso9660Config.setInterchangeLevel(1);
		iso9660Config.restrictDirDepthTo8(true);
		iso9660Config.setPublisher(System.getProperty("user.name"));
		iso9660Config.forceDotDelimiter(true);
	}

	private void initializeJolietConfig() throws ConfigException {
		jolietConfig = new JolietConfig();
		jolietConfig.setPublisher(System.getProperty("user.name"));
		jolietConfig.forceDotDelimiter(true);
	}

	private void initializeRoot() {
		ISO9660RootDirectory.MOVED_DIRECTORIES_STORE_NAME = "rr_moved";
		root = new ISO9660RootDirectory();
	}

	private void initializeCreateISO() throws FileNotFoundException {
		ISOImageFileHandler streamHandler = new ISOImageFileHandler(output);
		iso = new CreateISO(streamHandler, root);
	}
}
