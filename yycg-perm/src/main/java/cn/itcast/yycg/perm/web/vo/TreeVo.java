package cn.itcast.yycg.perm.web.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeVo {
	/*
	id：节点ID，对加载远程数据很重要。
	text：显示节点文本。
	state：节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
	iconCls：显示的节点图标CSS类ID
	checked：表示该节点是否被选中。
	attributes: 被添加到节点的自定义属性。
	children: 一个节点数组声明了若干节点。
	*/
	private String id;
	private String text;
	private String state = "open";
	private String iconCls = "";
	private String checked;		// checked 、'unchecked'、'indeterminate'不确定
	private Map<String, Object> attributes;
	private List<TreeVo> children = new ArrayList<>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public List<TreeVo> getChildren() {
		return children;
	}
	public void setChildren(List<TreeVo> children) {
		this.children = children;
	}

	

}
