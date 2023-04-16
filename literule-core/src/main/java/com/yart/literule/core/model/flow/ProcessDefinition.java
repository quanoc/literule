package com.yart.literule.core.model.flow;

import java.util.List;

public interface ProcessDefinition {
	//List<Library> getLibraries();
	String getId();
	boolean isDebug();
	List<NodeConfig> getNodes();
}