package hu.smiths.dvdcomposer.model;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import hu.smiths.dvdcomposer.model.algorithm.Algorithm;

public interface IModel extends Serializable {

	public List<DiskType> getDiskTypes();

	public List<File> getFolderEntries();

	public GeneratedResult getResult();

	public List<Algorithm> getAlgorithms();

}
