package net.zaycev.Solution.service;

import net.zaycev.Solution.model.SolutionResult;

import net.zaycev.Solution.model.ValidationException;
import net.zaycev.Solution.model.WrapElem;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by evgeny on 01.02.19.
 */
public class ServiceImplTest {

    private static final Logger logger = Logger.getLogger(ServiceImplTest.class);

    ServiceImpl service = new ServiceImpl();


    /**
     * Решение задачи "В лоб", используется для проверки данных
     * @param elems Входящие параметры
     */
    public SolutionResult solutionSimple(int[] elems) {
        logger.debug("---------------begin solutionSimple---------------");
        long startTime = System.currentTimeMillis();
        SolutionResult ret = new SolutionResult();

        int countStep = 0;

        //Мапа: комбинация, номер шага
        Map<String,Integer> map = new HashMap<>();

        try {
            while (true) {
                countStep++;

                int number = getNumberCellWithMaxValueSimple(elems);
                int delta = elems[number];

                //задаем максимальное число на 0
                elems[number] = 0;
                while (true) {

                    //переход на следующую ячейку
                    number++;

                    //если вышли за пределы массива, переход в начало
                    if (number == elems.length) {
                        number = -1;
                        continue;
                    }

                    //перераспределяем значение
                    elems[number] = elems[number] + 1;
                    delta--;

                    if (delta == 0) {
                        break;
                    }
                }

                String elemsAsString = Arrays.toString(elems);

                if (map.containsKey(elemsAsString)) {
                    ret.setCountStep(countStep);
                    ret.setLengthCycle((countStep-map.get(Arrays.toString(elems))));
                    break;
                }

                //Добавление результата распределения в сет
                map.put(elemsAsString,countStep);

            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(),e);
        }

        logger.debug("Время выполнения: "+(System.currentTimeMillis()-startTime)+" ms");
        logger.debug("---------------end solutionSimple---------------");
        return ret;

    }


    /*
    Получение номера ячейки с макс. значением массива (для теста)
     */
    public static int getNumberCellWithMaxValueSimple(int[] elems) throws ValidationException {
        int max = elems[0];
        int maxIndexCell = 0;
        for (int i = 0; i < elems.length; i++) {
            if (elems[i]>max) {
                max=elems[i];
                maxIndexCell = i;
            }
        }
        return maxIndexCell;
    }




    @Test
    public void getNumberCellWithMaxValueSimple() throws Exception {
        {
            int res = getNumberCellWithMaxValueSimple(new int[]{1, 2, 3});
            assertEquals(2, res);
        }
        {
            int res = getNumberCellWithMaxValueSimple(new int[]{10, 2, 3});
            assertEquals(0, res);
        }
        {
            int res = getNumberCellWithMaxValueSimple(new int[]{0, 1, 0});
            assertEquals(1, res);
        }
    }

    @Test
    public void solutionSimple() throws Exception {
        {
            SolutionResult res = solutionSimple(new int[]{1, 2, 3});
            assertEquals(new SolutionResult(4,3),res);
        }
        {
            SolutionResult res = solutionSimple(new int[]{1});
            assertEquals(new SolutionResult(2,1),res);
        }
        {
            SolutionResult res = solutionSimple(new int[]{1,0,9,5,4,3,5,3});
            assertEquals(new SolutionResult(9,8),res);
        }

    }






    /*
    Провешка решения. В качестве образца берем решение "влоб" для рандомных данных
     */
    @Test
    public void solution() throws Exception {

        Random r = new Random();

        int len = r.nextInt(6)+6;

        int[] arr = new int[len];

        for (int i = 0; i < arr.length; i++) {
            arr[i]= r.nextInt(50);
        }
        logger.debug("array="+Arrays.toString(arr));

        int[] arr2 = Arrays.copyOf(arr,arr.length);

        SolutionResult res1 = solutionSimple(arr);
        SolutionResult res2 = service.solution(arr2);
        assertEquals(res1,res2);
    }

    @Test
    public void getNextRaspred() throws Exception {

        {
            int[] arr = new int[]{1, 2, 3};
            int[] arrExpected = new int[]{2, 3, 1};
            WrapElem wrap = service.getNextRaspred(arr);
            WrapElem expected = new WrapElem(arrExpected,Arrays.hashCode(arrExpected));
            assertEquals(expected,wrap);
        }
        {
            int[] arr = new int[]{9, 8, 7, 6, 5};
            int[] arrExpected = new int[]{1, 10, 9, 8, 7};
            WrapElem wrap = service.getNextRaspred(arr);
            WrapElem expected = new WrapElem(arrExpected,Arrays.hashCode(arrExpected));
            assertEquals(expected,wrap);
        }
        {
            int[] arr = new int[]{0};
            int[] arrExpected = new int[]{0};
            WrapElem wrap = service.getNextRaspred(arr);
            WrapElem expected = new WrapElem(arrExpected,Arrays.hashCode(arrExpected));
            assertEquals(expected,wrap);
        }

    }

    @Test
    public void validationParameters() throws Exception {

        //Корректные параметры:
        service.validationParameters(new int[]{1, 2, 3});
        service.validationParameters(new int[]{2, 1});
        service.validationParameters(new int[]{1});

        //Некорректные параметры:
        try {
            service.validationParameters(new int[]{-999999, 2, 3, 4});
        } catch (ValidationException e) {
            assertNotNull(e);
        }

        try {
            service.validationParameters(null);
        } catch (ValidationException e) {
            assertNotNull(e);
        }
    }

}