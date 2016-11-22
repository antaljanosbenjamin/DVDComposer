package hu.smiths.dvdcomposer.model.algorithm;

import java.io.File;
import java.util.Collections;
import java.util.Set;

import hu.smiths.dvdcomposer.model.DiscGroup;

public class Input {
	
	private Set<DiscGroup> discGroups;
	
	private Set<File> folders;
	
	public Input(Set<DiscGroup> discGroups, Set<File> folders){
		this.discGroups = discGroups;
		this.folders = folders;
	}
	
	public Set<DiscGroup> getDiscGroups(){
		return Collections.unmodifiableSet(discGroups);
	}
	
	public Set<File> getFolders(){
		return Collections.unmodifiableSet(folders);
	}
	
	
}
