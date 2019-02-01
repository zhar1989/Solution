package net.zaycev.Solution.model;

import jdk.nashorn.internal.ir.annotations.Immutable;
import java.util.Arrays;

/**
 * Created by у on 01.02.19.
 * Неизменяемый контейнер с комбинацией
 */

@Immutable
public class WrapElem {
    private final int[] elems;

    //собственная реализация хэшкода позволяет ускорить выполнение примерно на 5%
    //в данном случае это уместно, т.к. неизменяемый объект WrapElem создается только в одном методе и при этом
    //проходятся все элементы массива
    private final int customHashCode;


    public WrapElem(int[] elems, int hash) {
        this.elems = elems;
        this.customHashCode = hash;
    }

    public int[] getElems() {
        return elems;
    }

    public int getCustomHashCode() {
        return customHashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WrapElem wrapElem = (WrapElem) o;

        if (customHashCode != wrapElem.customHashCode) return false;
        return Arrays.equals(elems, wrapElem.elems);
    }

    @Override
    public int hashCode() {
        return customHashCode;
    }

    @Override
    public String toString() {
        return "WrapElem{" +
                "elems=" + Arrays.toString(elems) +
                ", customHashCode=" + customHashCode +
                '}';
    }
}