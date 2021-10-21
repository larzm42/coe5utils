package coe5utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Monster {
	private int id;
	private String name;
	private Integer hp;
	private Integer str;
	private Integer mr;
	private Integer mor; 
	private Integer armor;
	private Integer rank;
	private Set<Attr> attributes = new TreeSet<Attr>();
	
	private static List<String> baseAttrs;
	
	static {
		baseAttrs = new ArrayList<String>();
		baseAttrs.add("name");
		baseAttrs.add("id");
		baseAttrs.add("ap");      
		baseAttrs.add("mapmove"); 
		baseAttrs.add("size");    
		baseAttrs.add("ressize"); 
		baseAttrs.add("hp");     
		baseAttrs.add("prot");    
		baseAttrs.add("str");    
		baseAttrs.add("enc");
		baseAttrs.add("prec");
		baseAttrs.add("att");
		baseAttrs.add("def");     
		baseAttrs.add("mr");      
		baseAttrs.add("mor");     
		baseAttrs.add("wpn1");    
		baseAttrs.add("wpn2");    
		baseAttrs.add("wpn3");    
		baseAttrs.add("wpn4");    
		baseAttrs.add("wpn5");    
		baseAttrs.add("wpn6");    
		baseAttrs.add("wpn7");    
		baseAttrs.add("armor1");  
		baseAttrs.add("armor2");  
		baseAttrs.add("armor3");  
		baseAttrs.add("basecost");
		baseAttrs.add("rcost");   
		baseAttrs.add("rpcost");  
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public Integer getHp() {
		return hp;
	}
	public void setHp(Integer hp) {
		this.hp = hp;
	}
	public Integer getStr() {
		return str;
	}
	public void setStr(Integer str) {
		this.str = str;
	}
	public Integer getMr() {
		return mr;
	}
	public void setMr(Integer mr) {
		this.mr = mr;
	}
	public Integer getMor() {
		return mor;
	}
	public void setMor(Integer mor) {
		this.mor = mor;
	}
	public Integer getArmor() {
		return armor;
	}
	public void setArmor(Integer armor) {
		this.armor = armor;
	}
	
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Set<Attr> getAttributes() {
		return attributes;
	}
	public Set<Attr> getAllAttributes() {
		Set<Attr> attr = new TreeSet<Attr>();
		attr.addAll(attributes);
		for (String key : baseAttrs) {
			attr.add(new Attr(key, getAttribute(key)));
		}
		return attr;
	}
	public void setAttributes(Set<Attr> attributes) {
		this.attributes = attributes;
	}
	public void addAttribute(Attr attribute) {
		if (baseAttrs.contains(attribute.getKey())) {
			try {
				new PropertyDescriptor(attribute.getKey(), Monster.class).getWriteMethod().invoke(this, Integer.parseInt(attribute.getValue()));
				return;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| IntrospectionException e) {
				e.printStackTrace();
			}
		}
		if (this.attributes == null) {
			this.attributes = new HashSet<Attr>();
		}
		this.attributes.remove(attribute);
		this.attributes.add(attribute);
	}
	
	public String getAttribute(String key) {
		if (baseAttrs.contains(key)) {
			try {
				Object value = new PropertyDescriptor(key, Monster.class).getReadMethod().invoke(this);
				if (value != null) {
					return value.toString();
				}
				return null;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| IntrospectionException e) {
				e.printStackTrace();
			}
		}
		for (Attr attr : attributes) {
			if (attr.getKey().equals(key)) {
				return attr.getValue();
			}
		}
		return null;
	}

}
