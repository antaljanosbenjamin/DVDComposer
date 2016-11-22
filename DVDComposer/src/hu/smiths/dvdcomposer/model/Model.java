package hu.smiths.dvdcomposer.model;

import java.io.File;
import java.io.Serializable;
import java.util.Set;

import hu.smiths.dvdcomposer.model.algorithm.Algorithm;
import hu.smiths.dvdcomposer.model.exceptions.InvalidResultException;

public interface Model extends Serializable {

	public Set<DiscGroup> getDiscGroups();
	
	public void setDiscGroups(Set<DiscGroup> discGroups);
	
	public boolean addDiscGroup(DiscGroup group);
	
	public boolean removeDiscGroup(DiscGroup group);

	public Set<File> getFolders();
	
	public void setFolders(Set<File> folders);
	
	public boolean addFolder(File folder);
	
	public boolean removeFolder(File folder);

	public Result generateResult() throws InvalidResultException;
	
	public void setAlgorithm(Algorithm algorithm);

}
