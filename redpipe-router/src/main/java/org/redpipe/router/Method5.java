package org.redpipe.router;

@FunctionalInterface
public interface Method5<Target, P1, P2, P3, P4, P5> extends MethodFinder {
	Object method(Target target, P1 param1, P2 param2, P3 param3, P4 param4, P5 param5);
}
