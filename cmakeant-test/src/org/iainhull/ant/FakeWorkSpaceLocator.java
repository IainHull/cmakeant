package org.iainhull.ant;

import java.io.File;

public class FakeWorkSpaceLocator extends WorkSpaceLocator {
	private String workspace;

	public FakeWorkSpaceLocator(String workspace) {
		this.workspace = workspace;
	}
	
	@Override
	public String findByExtension(File dir, String extension) {
		if (workspace == null)
			return super.findByExtension(dir, extension);
		else
			return new File(dir, workspace).toString();
	}
}
