package net.zaycev.Solution.service;

import net.zaycev.Solution.model.SolutionResult;
import net.zaycev.Solution.model.ValidationException;

/**
 * Created by evgeny on 01.02.19.
 * Интерфейс решения
 */
public interface Service {
    SolutionResult solution(int[] params) throws ValidationException;
}
