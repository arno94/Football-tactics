package items;


import java.util.ArrayList;

/**
 * 
 * @author Arno
 * Store items to draw
 *
 */
public class Item {

	private String path;
	private String showName;
	
	private ArrayList<String> iconPaths;
	
	public Item(String showName, ArrayList<String> list, int width, int height) {
		this(null, showName, list, width, height);
	}
	
	public Item(String path, String showName, ArrayList<String> list, int width, int height) {
		this.path = path;
		this.showName = showName;
		iconPaths = new ArrayList<>();
		
		for(int i = 0;i < list.size(); i++) {
			iconPaths.add(list.get(i));
		}
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getShowName() {
		return showName;
	}
	
	public void setShowName(String name) {
		this.showName = name;
	}
	
	public ArrayList<String> getIcons(){
		return iconPaths;
	}
	
	public void setIcons(ArrayList<String> iconPaths) {
		this.iconPaths = iconPaths;
	}
	
}
