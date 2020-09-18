package org.dance.day2.forkjoin;

import org.dance.tools.SleepTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 使用ForkJoin框架实现不定个数的任务执行
 * @author ZYGisComputer
 */
public class FindDirsFiles {

    /**
     * 因为搜索文件不需要返回值,所以我们继承RecursiveAction
     */
    private static class FindFilesByDirs extends RecursiveAction{

        private File path;

        public FindFilesByDirs(File path) {
            this.path = path;
        }

        @Override
        protected void compute() {

            // 创建任务容器
            List<FindFilesByDirs> findFilesByDirs = new ArrayList<>();

            // 获取文件夹下所有的对象
            File[] files = path.listFiles();

            if(null!=files){

                for (File file : files) {
                    // 判断是否是文件夹
                    if (file.isDirectory()){
                        // 添加到任务容器中
                        findFilesByDirs.add(new FindFilesByDirs(file));
                    }else{
                        // 如果是一个文件,那么检查这个文件是否符合需求
                        if(file.getAbsolutePath().endsWith(".txt")){
                            // 如果符合 打印
                            System.out.println("文件:"+file.getAbsolutePath());
                        }
                    }
                }

                // 判断任务容器是否为空
                if(!findFilesByDirs.isEmpty()){
                    // 递交任务组
                    for (FindFilesByDirs filesByDirs : invokeAll(findFilesByDirs)) {
                        // 等待子任务执行完成
                        filesByDirs.join();
                    }

                }

            }

        }
    }

    public static void main(String[] args) {

        // 创建ForkJoin池
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        File path = new File("E:/");

        // 创建任务
        FindFilesByDirs findFilesByDirs = new FindFilesByDirs(path);

        // 异步调用 这个方法是没有返回值的
        forkJoinPool.execute(findFilesByDirs);

        System.out.println("Task is Running................");
        SleepTools.ms(1);

        // 在这里做这个只是测试ForkJoin是否为异步,当执行ForkJoin的时候主线程是否继续执行
        int otherWork = 0;
        for (int i = 0; i < 100; i++) {
            otherWork += i;
        }
        System.out.println("Main thread done sth.......,otherWork:"+otherWork);

        // 如果是有返回值的话,可以获取,当然这个join方法是一个阻塞式的,因为主线程执行的太快了,ForkJoin还没执行完成主线程就死亡了,所以在这里调用一下阻塞,等待ForkJoin执行完成
        findFilesByDirs.join();

        System.out.println("Thread end!");

    }

}
