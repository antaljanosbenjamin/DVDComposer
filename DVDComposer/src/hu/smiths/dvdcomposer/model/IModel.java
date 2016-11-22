package hu.smiths.dvdcomposer.model;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import hu.smiths.dvdcomposer.model.algorithm.Algorithm;

public interface IModel extends Serializable {

	public Set<DiscGroup> getDiscGroups();
	
	public void setDiscGroups(Set<DiscGroup> discGroups);
	
	public void addDiscGroup(DiscGroup group);
	
	public void removeDiscGroup(DiscGroup group);

	public Set<File> getFolders();
	
	public void setFolders(Set<File> folders);
	
	public void addFolder(File folder);
	
	public void removeFolder(File folder);

	public GeneratedResult generate();

	public List<Algorithm> getAlgorithms();

}
