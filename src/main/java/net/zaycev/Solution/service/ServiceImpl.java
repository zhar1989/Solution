package net.zaycev.Solution.service;

import net.zaycev.Solution.model.SolutionResult;
import net.zaycev.Solution.model.ValidationException;
import net.zaycev.Solution.model.WrapElem;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * Created by evgeny on 01.02.19.
 * Реализация интерфейса
 */
public class ServiceImpl implements Service {

    private static final Logger logger = Logger.getLogger(ServiceImpl.class);

    /**
     * Решение задачи
     * @param elems входящие параметры
     * @return Информация о количестве шагов и длине цикла
     * @throws ValidationException
     */
    @Override
    public SolutionResult solution(int[] elems) throws ValidationException {
        logger.debug("---------------begin solution---------------");

        long startTime = System.currentTimeMillis();

        //Различные проверки
        validationParameters(elems);

        SolutionResult ret = new SolutionResult();

        //Количество шагов
        int countStep = 0;

        //Мапа с комбинацией и порядковый номер шага
        Map<WrapElem,Integer> map = new HashMap<>();

        try {
            while (true) {
                countStep++;

                //Получаем новую комбинацию
                WrapElem wrap = getNextRaspred(elems);
                logger.trace(Arrays.toString(wrap.getElems()));

                //Если комбинация уже есть в мапе, значит найден цикл и можно выходить из цикла
                if (map.containsKey(wrap)) {
                    ret.setCountStep(countStep);
                    ret.setLengthCycle((countStep-map.get(wrap)));
                    break;
                }

                //Добавление результата распределения в мапу
                map.put(wrap,countStep);
            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(),e);
        }

        logger.debug("Время выполнения: "+(System.currentTimeMillis()-startTime)+" ms");
        logger.debug("---------------end solution---------------");
        return ret;
    }


    /**
     * Получение новой комбинации распределения
     * @param elems - текущая комбинация
     * @return WrapElem - новая комбинация + уже готовый для нее хэш код (для оптимизации)
     * @throws ValidationException
     */
    public WrapElem getNextRaspred(int[] elems) throws ValidationException {

        //Находим макс. значение ячейки и ее индекс
        int max = elems[0];
        int maxIndexCell = 0;
        for (int i = 0; i < elems.length; i++) {
            if (elems[i]>max) {
                max=elems[i];
                maxIndexCell = i;
            }
        }

        //обнуляем макс
        elems[maxIndexCell] = 0;

        //deltaToAll - это значение заполняем по всем ячейкам
        int addToAll = max/elems.length;

        //overLength - это остаток, который нужно размазать по всем элементам массива, начиная со следующего от макс.
        int overLength = max-addToAll*elems.length;


        int nextIndexCell = maxIndexCell+1;
        if (nextIndexCell==elems.length) {
            nextIndexCell = 0;
        }

        //ostatok1Length - длина заполнения от следующей после макс. ячейки до конца массива
        int over2Length = elems.length-nextIndexCell;
        if (over2Length>overLength) {
            over2Length=overLength;
        }

        //ostatok2Length - от 0 ячейки до оставшегося значения
        int over1Length = overLength-over2Length;

        //Создаем и заполняем массив со значениями, которые нужно прибавить к основному массиву
        int[] arrDelta = new int[elems.length];


        //1) Сначала заполняем все ячейки
        //Можно было бы заполнять не все ячейки, а только определенный диапазон,
        //который не перезапишется следующими двумя fill, но там есть несколько условий и расчетов
        //не будем это делать, т.к. усложнит код в понимании
        Arrays.fill(arrDelta,addToAll);

        //2) Далее заполняем (addToAll + 1) к ячейкам, начиная со след. от макс до конца массива
        //или сколько хватит остатка
        if (over2Length>0) {
            Arrays.fill(arrDelta, nextIndexCell, nextIndexCell + over2Length, addToAll + 1);
        }

        //3) Далее заполняем (addToAll + 1) к ячейкам от 0 до остатка
        if (over1Length>0) {
            Arrays.fill(arrDelta, 0, over1Length, addToAll + 1);
        }

        //В массив elems добавляем значения сформированного ранее массива
        //Для оптимизации сразу расчитываем хэшкод
        int customHashCode=0;
        if (elems!=null) {
            customHashCode=1;
        }
        for (int i = 0; i < elems.length; i++) {
            elems[i]=elems[i]+arrDelta[i];
            customHashCode = 31 * customHashCode + elems[i];
        }

        //В результат добавляем копию массива elem, т.к. elem в дальнейшем будет изменяться и передавать просто ссылку некорректно,
        WrapElem wrapElem = new WrapElem(Arrays.copyOf(elems,elems.length),customHashCode);

        return wrapElem;
    }

    /**
     * Различные проверки
     * @param elems Входящие параметры
     * @throws ValidationException
     */
    public static void validationParameters(int[] elems) throws ValidationException {

        //Проверка на пустой массив
        if (elems==null||elems.length==0) {
            throw new ValidationException("Входные параметры не могут быть пустыми!");
        }

        //Проверка, чтобы значения были в корректном диапазоне
        OptionalInt min = IntStream.of(elems).min();
        OptionalInt max = IntStream.of(elems).max();
//        if (min.getAsInt()<0||max.getAsInt()>Integer.MAX_VALUE) {
        if (min.getAsInt()<-10000||max.getAsInt()>10000) {
            throw new ValidationException("Допустимые значения во входных параметрах: от -10000 до 10000");
        }
    }
}
