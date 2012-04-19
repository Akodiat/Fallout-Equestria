package content;

import java.io.InputStream;

public interface IContentLoader<T> {
	public Class<T> getClassAbleToLoad();
	public T loadContent(InputStream in) throws Exception;
	public String getFolder();
}
