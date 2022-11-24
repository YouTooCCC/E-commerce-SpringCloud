package com.atweibo.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.SchemaOutputResolver;
import java.util.*;
import java.util.function.Predicate;

/**
 * @Description   使用方法与思想
 * @Author weibo
 * @Data 2022/11/11 18:57
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PredicateTest {

    public static List<String > MICRO_SERVICE = Arrays.asList(
        "nacos","authority","gateway","ribbon","feign","hstritx","e-commerce"
    );

    /**
     * @return void
     * @Describe:  主要用于参数符不符合规则，返回值是 boolean
     * @author Weibo
     * @date 2022/11/11 19:00
     */

    @Test
    public void testPredicateTest(){
        Predicate<String> letterLenghtLimit = s->s.length()>5;
        MICRO_SERVICE.stream().filter(letterLenghtLimit).forEach(System.out::println);
        System.out.println();
    }

    /**
     * @return void
     * @Describe:  and方法等用于逻辑与 && ，存在短路特性，需要所有的条件都满足
     * @author Weibo
     * @date 2022/11/21 15:29
     */
    @Test
    public void testPredicateand() {
        Predicate<String >   letterLengthLimit = s -> s.length() >5;
        Predicate<String >   letterStarterWith = s -> s.startsWith("gate");
        MICRO_SERVICE.stream().filter(letterLengthLimit.and(letterStarterWith))
                .forEach(System.out::println);
    }

/**
 * @return void
 * @Describe:   等同于逻辑或
 * @author Weibo
 * @date 2022/11/21 15:33
 */
    @Test
    public void testPredicateOr() {
        Predicate<String >   letterLengthLimit = s -> s.length() >5;
        Predicate<String >   letterStarterWith = s -> s.startsWith("gate");
        MICRO_SERVICE.stream().filter(letterLengthLimit.or(letterStarterWith))
                .forEach(System.out::println);
    }

    /**
     * @return void
     * @Describe:   等同于逻辑非
     * @author Weibo
     * @date 2022/11/21 15:33
     */
    @Test
    public void testPredicateNegate() {
        Predicate<String >   letterStarterWith = s -> s.startsWith("gate");
        MICRO_SERVICE.stream().filter(letterStarterWith.negate())
                .forEach(System.out::println);
    }

    /**
     * @return void
     * @Describe:  类似于equal()方法  ,区别在于:先判断对象是否为 NULL,不为NULL 再使用equal进行比较
     * @author Weibo
     * @date 2022/11/21 15:35
     */
    @Test
    public void testPredicateEqual() {
        Predicate<String >   letterStarterWith = s ->Predicate.isEqual("gate").test(s);
        MICRO_SERVICE.stream().filter(letterStarterWith.negate())
                .forEach(System.out::println);
    }



    @Test
    public void test(){
        int[] arr = {1,2,4,3};
        for (int i =0;i<4;i++){
            System.out.println(arr[i]);

            ArrayList<Object> objects = new ArrayList<>();
            ArrayList<Object> objects01 = new ArrayList<>(20);

            LinkedList<Object> objects1 = new LinkedList<>();

            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();

        }
    }
}
