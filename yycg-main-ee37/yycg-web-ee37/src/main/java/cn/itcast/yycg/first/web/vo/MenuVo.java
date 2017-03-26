package cn.itcast.yycg.first.web.vo;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import org.ietf.jgss.Oid;

import com.alibaba.fastjson.annotation.JSONField;
import com.mchange.v2.c3p0.impl.IdentityTokenized;

public class MenuVo implements Comparable<MenuVo> {
	
	//加注解此属性不生成json
	@JSONField(serialize=false)
	private String id;
	
	private String icon;
	private String menuid;
	private String menuname;
	private String url;
	private Set<MenuVo> menus = new TreeSet<MenuVo>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Set<MenuVo> getMenus() {
		return menus;
	}
	public void setMenus(Set<MenuVo> menus) {
		this.menus = menus;
	}
	
	@Override
	public int compareTo(MenuVo o) {
		try {
			//存在null返回1
			if(o ==null || this ==null) {
				return 1;
			}
			String oId = o.getId();
			String thisId = this.getId();
			//相等返回1
			if (oId.equals(thisId)) {
				return 1;
			}
			//去掉结尾的点
			if (oId.endsWith(".")) {
				oId = oId.substring(0, oId.length()-1);
			}
			if (thisId.endsWith(".")) {
				thisId = thisId.substring(0, thisId.length()-1);
			}
			
			String[] oIds = oId.split(".");
			String[] thisIds = thisId.split(".");
			
			//长度不等返回1
			if (oIds.length != thisIds.length) {
				return 1;
			}
			
			for (int i =0;i<oIds.length;i++) {
				
					int oIdInt = Integer.parseInt(oIds[i]);
					int thisIdInt = Integer.parseInt(thisIds[i]);
					if (oIdInt == thisIdInt) {
						continue;
					} else if (oIdInt > thisIdInt) {
						return 1;
					} else {
						return -1;
					}
	 		}
		} catch (Exception e) {
			return 1;
		}
		return 1;
	}
	
}
