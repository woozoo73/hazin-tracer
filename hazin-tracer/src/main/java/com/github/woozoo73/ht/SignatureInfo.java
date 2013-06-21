package com.github.woozoo73.ht;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aspectj.lang.Signature;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "typeName", "name" })
public class SignatureInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "typeName")
	private String typeName;

	@XmlElement(name = "name")
	private String name;
	
	public SignatureInfo() {
	}

	public SignatureInfo(Signature signature) {
		this.typeName = signature.getDeclaringType().getName();
		this.name = signature.getName();
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
