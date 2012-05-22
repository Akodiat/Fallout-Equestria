package content;

public abstract class ContentLoader<T> implements IContentLoader<T>{

	private final String folder;
	
	public ContentLoader(String folder) {
		this.folder = folder;
	}
	
	@Override
	public final String getFolder() {
		return this.folder;
	}

}
