package org.dance.day3;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用原子类引用类型
 * @author ZYGisComputer
 */
public class UseAtomicReference {

    static AtomicReference<UserInfo> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {

        UserInfo src = new UserInfo("彼岸舞",18);

        // 使用原子引用类包装一下
        atomicReference.set(src);

        UserInfo target = new UserInfo("彼岸花",19);

        // 这里就是CAS改变了,这个应用类就好像一个容器也就是内存V,而src就是原值A,target就是新值B
        // 期望原值是src,如果是的话,改变为target,否则不变
        atomicReference.compareAndSet(src,target);

        System.out.println(atomicReference.get());

        System.out.println(src);

    }

    static class UserInfo{
        private String name;
        private int age;

        @Override
        public String toString() {
            return "UserInfo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        public UserInfo() {
        }

        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
