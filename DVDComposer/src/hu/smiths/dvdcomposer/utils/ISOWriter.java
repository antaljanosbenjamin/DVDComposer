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
import hu.smiths.dvdcomposer.model.exceptions.CannotAddFolderToISOImageException;
import hu.smiths.dvdcomposer.model.exceptions.CannotCreateISOFile;

public class ISOWriter {

	private CreateISO iso;

	private File output;

	private ISO9660Config iso9660Config;

	private JolietConfig jolietConfig;

	private ISO9660RootDirectory root;

	public void dumpContentToFile(File output) throws CannotCreateISOFile {
		try {
			this.output = output;
			initializeAllConfig();
			writeData();
			setNewEmptyRootDirectory();
		} catch (FileNotFoundException | ConfigException | HandlerException e) {
			throw new CannotCreateISOFile(e);
		}
	}

	public ISOWriter() {
		setNewEmptyRootDirectory();
	}

	public void setNewEmptyRootDirectory() {
		ISO9660RootDirectory.MOVED_DIRECTORIES_STORE_NAME = "rr_moved";
		root = new ISO9660RootDirectory();
	}

	public void addFolderToISOImage(File folder) throws CannotAddFolderToISOImageException {
		try {
			root.addRecursively(folder);
		} catch (HandlerException e) {
			throw new CannotAddFolderToISOImageException(e);
		}
	}

	private void writeData() throws HandlerException {
		iso.process(iso9660Config, null, jolietConfig, null);
	}

	private void initializeAllConfig() throws ConfigException, FileNotFoundException {
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

	private void initializeCreateISO() throws FileNotFoundException {
		ISOImageFileHandler streamHandler = new ISOImageFileHandler(output);
		iso = new CreateISO(streamHandler, root);
	}
}
