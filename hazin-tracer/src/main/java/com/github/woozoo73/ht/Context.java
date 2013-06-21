package com.github.woozoo73.ht;

import java.util.Stack;

/**
 * 메소드 호출 컨텍스트.<br/>
 * 최상위 메소드 호출 및 현재 호출 메소드 스택 정보를 저장한다.
 * 
 * @author woozoo73
 */
public class Context {

	private static ThreadLocal<Invocation> ENDPOINT_INVOCATION_CONTEXT = new ThreadLocal<Invocation>();

	private static ThreadLocal<Stack<Invocation>> INVOCATION_STACK_CONTEXT = new ThreadLocal<Stack<Invocation>>() {
		@Override
		protected Stack<Invocation> initialValue() {
			return new Stack<Invocation>();
		}
	};

	protected static Invocation getEndpointInvocation() {
		return ENDPOINT_INVOCATION_CONTEXT.get();
	}

	protected static void setEndpointInvocation(Invocation invocation) {
		ENDPOINT_INVOCATION_CONTEXT.set(invocation);
	}

	protected static void addToInvocationStack(Invocation invocation) {
		Stack<Invocation> stack = INVOCATION_STACK_CONTEXT.get();
		stack.add(invocation);
	}

	protected static Invocation popFromInvocationStack() {
		Stack<Invocation> stack = INVOCATION_STACK_CONTEXT.get();
		return stack.pop();
	}

	protected static Invocation peekFromInvocationStack() {
		Stack<Invocation> stack = INVOCATION_STACK_CONTEXT.get();
		if (stack.isEmpty()) {
			return null;
		}

		return stack.peek();
	}

	protected static Invocation dump() {
		Invocation invocation = ENDPOINT_INVOCATION_CONTEXT.get();
		invocation.calculateChildDurationPercentage();

		ENDPOINT_INVOCATION_CONTEXT.set(null);
		INVOCATION_STACK_CONTEXT.set(new Stack<Invocation>());

		return invocation;
	}

}
