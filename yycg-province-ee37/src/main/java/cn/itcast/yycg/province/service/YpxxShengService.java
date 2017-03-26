package cn.itcast.yycg.province.service;

import java.util.List;

import javax.jws.WebService;

import cn.itcast.yycg.province.po.YpxxSheng;

@WebService		//表示支持WS服务
public interface YpxxShengService {
	
	public List<YpxxSheng> findAll();

}
