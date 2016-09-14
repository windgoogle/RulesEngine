package com.simplerule.rulesengine.core;

import java.lang.reflect.Method;


class ActionOrder implements Comparable<ActionOrder> {

    private Method method;

    private int order;

    ActionOrder(final Method method, final int order) {
        this.method = method;
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public int compareTo(final ActionOrder actionOrder) {
        if (order < actionOrder.getOrder()) {
            return -1;
        } else if (order > actionOrder.getOrder()) {
            return 1;
        } else {
            return method.equals(actionOrder.getMethod()) ? 0 : 1;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionOrder)) return false;

        ActionOrder that = (ActionOrder) o;

        if (order != that.order) return false;
        if (!method.equals(that.method)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + order;
        return result;
    }

}
