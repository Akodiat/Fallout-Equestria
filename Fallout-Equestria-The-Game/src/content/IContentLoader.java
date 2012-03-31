package content;

import java.io.IOException;
import java.io.InputStream;

public interface IContentLoader<T> {
	public Class<T> getClassAbleToLoad();
	public T loadContent(InputStream in) throws Exception;
}
