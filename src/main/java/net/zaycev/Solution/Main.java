package net.zaycev.Solution;

import net.zaycev.Solution.model.SolutionResult;
import net.zaycev.Solution.service.Service;
import net.zaycev.Solution.service.ServiceImpl;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * Created by evgeny on 01.02.19.
 * Главный класс
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Service service = new ServiceImpl();

        int[] arr = new int[]{0,5,10,0,11,14,13,4,11,8,8,7,1,4,12,11};
        SolutionResult res = service.solution(arr);

        logger.info(
                String.format(
                        "При входных параметрах: \n%s\n " +
                        "Нашли цикл за %d шагов\n" +
                        "Длина цикла = %d"
                        , Arrays.toString(arr)
                        , res.getCountStep()
                        , res.getLengthCycle()
                )
        );
    }
}
