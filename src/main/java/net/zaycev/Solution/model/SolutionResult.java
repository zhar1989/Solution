package net.zaycev.Solution.model;

/**
 * Created by evgeny on 01.02.19.
 * Резельтат работы программы
 */

public class SolutionResult {

    /**
     * Количество шагов
     */
    private int countStep;

    /**
     * Длина цикла
     */
    private int lengthCycle;

    public SolutionResult() {

    }

    public SolutionResult(int countStep, int lengthCycle) {
        this.countStep = countStep;
        this.lengthCycle = lengthCycle;
    }

    public int getCountStep() {
        return countStep;
    }

    public void setCountStep(int countStep) {
        this.countStep = countStep;
    }

    public int getLengthCycle() {
        return lengthCycle;
    }

    public void setLengthCycle(int lengthCycle) {
        this.lengthCycle = lengthCycle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SolutionResult that = (SolutionResult) o;

        if (countStep != that.countStep) return false;
        return lengthCycle == that.lengthCycle;
    }

    @Override
    public int hashCode() {
        int result = countStep;
        result = 31 * result + lengthCycle;
        return result;
    }

    @Override
    public String toString() {
        return "SolutionResult: countStep="+countStep+"; lengthCycle="+lengthCycle;
    }

}
