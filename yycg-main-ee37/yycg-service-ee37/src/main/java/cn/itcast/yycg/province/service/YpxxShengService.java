package cn.itcast.yycg.province.service;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.0.9
 * 2016-12-13T22:05:21.979+08:00
 * Generated source version: 3.0.9
 * 
 */
@WebService(targetNamespace = "http://service.province.yycg.itcast.cn/", name = "YpxxShengService")
@XmlSeeAlso({ObjectFactory.class})
public interface YpxxShengService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://service.province.yycg.itcast.cn/", className = "cn.itcast.yycg.province.service.FindAll")
    @WebMethod
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://service.province.yycg.itcast.cn/", className = "cn.itcast.yycg.province.service.FindAllResponse")
    public java.util.List<cn.itcast.yycg.province.service.YpxxSheng> findAll();
}
