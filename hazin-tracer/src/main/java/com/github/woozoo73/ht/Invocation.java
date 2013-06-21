package com.github.woozoo73.ht;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aspectj.lang.JoinPoint;

/**
 * 메소드 호출.
 * 
 * @author woozoo73
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "depth", "durationNanoTime", "durationPercentage", "joinPointInfo", "childInvocationList",
		"throwable", "result" })
public class Invocation implements Serializable {

	private static final long serialVersionUID = 1L;

	private static NumberFormat timeFormat = new DecimalFormat("###,##0.00");

	@XmlAttribute
	private Integer depth = 0;

	@XmlElementWrapper(name = "childInvocationList")
	@XmlElement(name = "invocation")
	private List<Invocation> childInvocationList = new ArrayList<Invocation>();

	private JoinPoint joinPoint;

	@XmlElement(name = "joinPoint")
	private JoinPointInfo joinPointInfo;

	@XmlAttribute
	private Long durationNanoTime;

	@XmlAttribute
	private Double durationPercentage = 100D;

	private Long startNanoTime;

	private Long endNanoTime;

	private Object returnValue;

	@XmlElement(name = "returnValue")
	private String result;

	private Throwable t;

	@XmlElement
	private String throwable;

	public boolean equalsJoinPoint(Invocation another) {
		if (another == null) {
			return false;
		}

		return equalsJoinPoint(another.joinPoint);
	}

	public boolean equalsJoinPoint(JoinPoint anotherJoinPoint) {
		if (joinPoint == null) {
			return false;
		}

		return joinPoint.equals(anotherJoinPoint);
	}

	public Invocation getInvocationByJoinPoint(JoinPoint search) {
		if (search == null) {
			return null;
		}

		if (equalsJoinPoint(search)) {
			return this;
		}

		for (Invocation childInvocation : childInvocationList) {
			Invocation match = childInvocation.getInvocationByJoinPoint(search);
			if (match != null) {
				return match;
			}
		}

		return null;
	}

	public void start() {
		startNanoTime = System.nanoTime();
	}

	public void stop() {
		endNanoTime = System.nanoTime();
		durationNanoTime = endNanoTime - startNanoTime;
	}

	public void add(Invocation childInvocation) {
		childInvocation.setDepth(depth + 1);
		childInvocationList.add(childInvocation);
	}

	public void calculateChildDurationPercentage() {
		Long totalSibling = 0L;
		for (Invocation invocation : childInvocationList) {
			totalSibling += invocation.durationNanoTime;
		}

		for (Invocation invocation : childInvocationList) {
			Double percentage = 0D;
			if (totalSibling > 0L) {
				percentage = (100D * invocation.durationNanoTime) / totalSibling;

			}
			invocation.setDurationPercentage(percentage);

			invocation.calculateChildDurationPercentage();
		}
	}

	public Double getDurationMiliTime() {
		if (durationNanoTime == null) {
			return null;
		}

		return durationNanoTime.doubleValue() / (1000 * 1000);
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public List<Invocation> getChildInvocationList() {
		return childInvocationList;
	}

	public JoinPoint getJoinPoint() {
		return joinPoint;
	}

	public void setJoinPoint(JoinPoint joinPoint) {
		this.joinPoint = joinPoint;
		this.joinPointInfo = new JoinPointInfo(joinPoint);
	}

	public JoinPointInfo getJoinPointInfo() {
		return joinPointInfo;
	}

	public void setJoinPointInfo(JoinPointInfo joinPointInfo) {
		this.joinPointInfo = joinPointInfo;
	}

	public Long getDurationNanoTime() {
		return durationNanoTime;
	}

	public void setDurationNanoTime(Long durationNanoTime) {
		this.durationNanoTime = durationNanoTime;
	}

	public Double getDurationPercentage() {
		return durationPercentage;
	}

	public void setDurationPercentage(Double durationPercentage) {
		this.durationPercentage = durationPercentage;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
		if (returnValue != null) {
			this.result = returnValue.toString();
		}
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Throwable getT() {
		return t;
	}

	public void setT(Throwable t) {
		this.t = t;
		this.throwable = t.toString();
	}

	public String getThrowable() {
		return throwable;
	}

	public void setThrowable(String throwable) {
		this.throwable = throwable;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("signature=");
		builder.append(joinPoint.getSignature()).append(", ");
		builder.append("duration=");
		builder.append("(");
		builder.append(timeFormat.format(getDurationMiliTime()));
		builder.append("ms");
		builder.append(":");
		builder.append(timeFormat.format(getDurationPercentage()));
		builder.append("%");
		builder.append(")");
		return builder.toString();
	}

}
