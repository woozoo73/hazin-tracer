package com.github.woozoo73.ht;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.SourceLocation;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "fileName", "line", "signatureInfo", "args" })
public class JoinPointInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "signature")
	private SignatureInfo signatureInfo;

	private Object[] arguments;

	@XmlElementWrapper(name = "args")
	@XmlElement(name = "arg")
	private String[] args;

	@XmlAttribute
	private String fileName;

	@XmlAttribute
	private int line;

	public JoinPointInfo() {
	}

	public JoinPointInfo(JoinPoint joinPoint) {
		this.signatureInfo = new SignatureInfo(joinPoint.getSignature());
		this.arguments = joinPoint.getArgs();

		if (this.arguments != null) {
			this.args = new String[arguments.length];
			for (int i = 0; i < arguments.length; i++) {
				this.args[i] = argInfo(arguments[i]);
			}
		}

		SourceLocation sourceLocation = joinPoint.getSourceLocation();
		if (sourceLocation != null) {
			this.fileName = sourceLocation.getFileName();
			this.line = sourceLocation.getLine();
		}
	}

	private String argInfo(Object argument) {
		StringBuilder builder = new StringBuilder();

		if (argument != null && argument instanceof Object[]) {
			Object[] arguments = (Object[]) argument;
			builder.append("[");
			if (arguments != null) {
				boolean firstArg = true;
				for (Object arg : arguments) {
					if (!firstArg) {
						builder.append(", ");
					}
					builder.append(arg);
					firstArg = false;
				}
			}
			builder.append("]");
		} else {
			builder.append(argument);
		}

		return builder.toString();
	}

	public SignatureInfo getSignatureInfo() {
		return signatureInfo;
	}

	public void setSignatureInfo(SignatureInfo signatureInfo) {
		this.signatureInfo = signatureInfo;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

}
