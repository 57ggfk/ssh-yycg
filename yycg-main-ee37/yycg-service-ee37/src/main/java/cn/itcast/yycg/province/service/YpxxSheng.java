
package cn.itcast.yycg.province.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ypxxSheng complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ypxxSheng"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="bzcz" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="bzdw" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cpsm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dw" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="gg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jky" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jyzt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lsjg" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="lsjgcc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="mc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="py" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pzwh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pzwhyxq" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="scqymc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="spmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="vchar1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="vchar2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="vchar3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ypjybg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ypjybgbm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ypjybgyxq" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="zbjg" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="zhxs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="zlcc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="zlccsm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="zpdz" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ypxxSheng", propOrder = {
    "bm",
    "bzcz",
    "bzdw",
    "cpsm",
    "dw",
    "gg",
    "id",
    "jky",
    "jx",
    "jyzt",
    "lb",
    "lsjg",
    "lsjgcc",
    "mc",
    "py",
    "pzwh",
    "pzwhyxq",
    "scqymc",
    "spmc",
    "vchar1",
    "vchar2",
    "vchar3",
    "ypjybg",
    "ypjybgbm",
    "ypjybgyxq",
    "zbjg",
    "zhxs",
    "zlcc",
    "zlccsm",
    "zpdz"
})
public class YpxxSheng {

    protected String bm;
    protected String bzcz;
    protected String bzdw;
    protected String cpsm;
    protected String dw;
    protected String gg;
    protected String id;
    protected String jky;
    protected String jx;
    protected String jyzt;
    protected String lb;
    protected Float lsjg;
    protected String lsjgcc;
    protected String mc;
    protected String py;
    protected String pzwh;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar pzwhyxq;
    protected String scqymc;
    protected String spmc;
    protected String vchar1;
    protected String vchar2;
    protected String vchar3;
    protected String ypjybg;
    protected String ypjybgbm;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ypjybgyxq;
    protected Float zbjg;
    protected String zhxs;
    protected String zlcc;
    protected String zlccsm;
    protected String zpdz;

    /**
     * Gets the value of the bm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBm() {
        return bm;
    }

    /**
     * Sets the value of the bm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBm(String value) {
        this.bm = value;
    }

    /**
     * Gets the value of the bzcz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBzcz() {
        return bzcz;
    }

    /**
     * Sets the value of the bzcz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBzcz(String value) {
        this.bzcz = value;
    }

    /**
     * Gets the value of the bzdw property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBzdw() {
        return bzdw;
    }

    /**
     * Sets the value of the bzdw property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBzdw(String value) {
        this.bzdw = value;
    }

    /**
     * Gets the value of the cpsm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpsm() {
        return cpsm;
    }

    /**
     * Sets the value of the cpsm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpsm(String value) {
        this.cpsm = value;
    }

    /**
     * Gets the value of the dw property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDw() {
        return dw;
    }

    /**
     * Sets the value of the dw property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDw(String value) {
        this.dw = value;
    }

    /**
     * Gets the value of the gg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGg() {
        return gg;
    }

    /**
     * Sets the value of the gg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGg(String value) {
        this.gg = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the jky property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJky() {
        return jky;
    }

    /**
     * Sets the value of the jky property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJky(String value) {
        this.jky = value;
    }

    /**
     * Gets the value of the jx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJx() {
        return jx;
    }

    /**
     * Sets the value of the jx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJx(String value) {
        this.jx = value;
    }

    /**
     * Gets the value of the jyzt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJyzt() {
        return jyzt;
    }

    /**
     * Sets the value of the jyzt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJyzt(String value) {
        this.jyzt = value;
    }

    /**
     * Gets the value of the lb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLb() {
        return lb;
    }

    /**
     * Sets the value of the lb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLb(String value) {
        this.lb = value;
    }

    /**
     * Gets the value of the lsjg property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getLsjg() {
        return lsjg;
    }

    /**
     * Sets the value of the lsjg property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setLsjg(Float value) {
        this.lsjg = value;
    }

    /**
     * Gets the value of the lsjgcc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLsjgcc() {
        return lsjgcc;
    }

    /**
     * Sets the value of the lsjgcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLsjgcc(String value) {
        this.lsjgcc = value;
    }

    /**
     * Gets the value of the mc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMc() {
        return mc;
    }

    /**
     * Sets the value of the mc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMc(String value) {
        this.mc = value;
    }

    /**
     * Gets the value of the py property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPy() {
        return py;
    }

    /**
     * Sets the value of the py property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPy(String value) {
        this.py = value;
    }

    /**
     * Gets the value of the pzwh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPzwh() {
        return pzwh;
    }

    /**
     * Sets the value of the pzwh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPzwh(String value) {
        this.pzwh = value;
    }

    /**
     * Gets the value of the pzwhyxq property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPzwhyxq() {
        return pzwhyxq;
    }

    /**
     * Sets the value of the pzwhyxq property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPzwhyxq(XMLGregorianCalendar value) {
        this.pzwhyxq = value;
    }

    /**
     * Gets the value of the scqymc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScqymc() {
        return scqymc;
    }

    /**
     * Sets the value of the scqymc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScqymc(String value) {
        this.scqymc = value;
    }

    /**
     * Gets the value of the spmc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpmc() {
        return spmc;
    }

    /**
     * Sets the value of the spmc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpmc(String value) {
        this.spmc = value;
    }

    /**
     * Gets the value of the vchar1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVchar1() {
        return vchar1;
    }

    /**
     * Sets the value of the vchar1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVchar1(String value) {
        this.vchar1 = value;
    }

    /**
     * Gets the value of the vchar2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVchar2() {
        return vchar2;
    }

    /**
     * Sets the value of the vchar2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVchar2(String value) {
        this.vchar2 = value;
    }

    /**
     * Gets the value of the vchar3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVchar3() {
        return vchar3;
    }

    /**
     * Sets the value of the vchar3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVchar3(String value) {
        this.vchar3 = value;
    }

    /**
     * Gets the value of the ypjybg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYpjybg() {
        return ypjybg;
    }

    /**
     * Sets the value of the ypjybg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYpjybg(String value) {
        this.ypjybg = value;
    }

    /**
     * Gets the value of the ypjybgbm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYpjybgbm() {
        return ypjybgbm;
    }

    /**
     * Sets the value of the ypjybgbm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYpjybgbm(String value) {
        this.ypjybgbm = value;
    }

    /**
     * Gets the value of the ypjybgyxq property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getYpjybgyxq() {
        return ypjybgyxq;
    }

    /**
     * Sets the value of the ypjybgyxq property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setYpjybgyxq(XMLGregorianCalendar value) {
        this.ypjybgyxq = value;
    }

    /**
     * Gets the value of the zbjg property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getZbjg() {
        return zbjg;
    }

    /**
     * Sets the value of the zbjg property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setZbjg(Float value) {
        this.zbjg = value;
    }

    /**
     * Gets the value of the zhxs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZhxs() {
        return zhxs;
    }

    /**
     * Sets the value of the zhxs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZhxs(String value) {
        this.zhxs = value;
    }

    /**
     * Gets the value of the zlcc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZlcc() {
        return zlcc;
    }

    /**
     * Sets the value of the zlcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZlcc(String value) {
        this.zlcc = value;
    }

    /**
     * Gets the value of the zlccsm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZlccsm() {
        return zlccsm;
    }

    /**
     * Sets the value of the zlccsm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZlccsm(String value) {
        this.zlccsm = value;
    }

    /**
     * Gets the value of the zpdz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZpdz() {
        return zpdz;
    }

    /**
     * Sets the value of the zpdz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZpdz(String value) {
        this.zpdz = value;
    }

}
