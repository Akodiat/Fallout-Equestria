package archetypeEditor;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**This code is taken from the URL. http://snippets.dzone.com/posts/show/4831
 *
 */
public class ReflectionHelper {
	
    @SuppressWarnings("rawtypes")
	public static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }
    
    @SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Class[] getClassesThatContainAnnotation(Class annotationClass, String packageName) throws ClassNotFoundException, IOException {
    	Class[] classes = getClasses(packageName);
    	List<Class> correctClasses = new ArrayList<>();
    	for (Class clazz : classes) {
			Annotation annotation = clazz.getAnnotation(annotationClass);
			if(annotation != null) {
				correctClasses.add(clazz);
			}
		}
    	
    	return correctClasses.toArray(new Class[correctClasses.size()]);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T createNewInstance(Class clazz) {
    	try {
    		Object obj = clazz.newInstance();
    		return (T)obj;
    	} catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    }
     
}
