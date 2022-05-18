package test;/**
 * Created by nidaye on 2017/11/15.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 测试工具类
 *
 * @author
 * @create 2017-11-15
 */


public  class SystemTest {


    public static void main(String[] ages){
        List list = new ArrayList();
        list.add(111);
        list.add(new Persion(111,"你大爷"));


        System.out.println(list.contains(111));
        System.out.println(list.contains(new Persion(20,"BBB")));

    }


    public static class Persion {
        private int age;
        private String name;

        public Persion(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean equals(Object obj) {
            Persion p = (Persion) obj;
            return (this.name == p.getName() && this.age == p.getAge());
        }
    }

}



