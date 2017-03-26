package cn.itcast.yycg.webservice.impl;

import javax.jws.WebService;

import cn.itcast.yycg.webservice.WsYpxxService;
@WebService(endpointInterface="cn.itcast.yycg.webservice.WsYpxxService")
public class WsYpxxServiceImpl implements WsYpxxService {

	@Override
	public String findById(String id) {
		return "服务器："+id;
	}

}
