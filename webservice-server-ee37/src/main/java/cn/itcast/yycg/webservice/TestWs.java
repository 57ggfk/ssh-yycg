package cn.itcast.yycg.webservice;

import javax.xml.ws.Endpoint;

import cn.itcast.yycg.webservice.impl.WsYpxxServiceImpl;

public class TestWs {
	public static void main(String[] args) {
		WsYpxxService wsYpxxService = new WsYpxxServiceImpl();
		Endpoint.publish("http://localhost:12345/ws", new WsYpxxServiceImpl());
	}
}
