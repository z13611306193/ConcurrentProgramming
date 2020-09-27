package org.dance.day2;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.concurrent.CountDownLatch;

/**
 * 采用并发编程拷贝文件
 * @author ZYGisComputer
 */
public class CopyFile {

    static CountDownLatch countDownLatch;

    public static void main(String[] args) throws InterruptedException {

        String cd = "G:\\00000000000000000000000000000000";

        String target = "G:\\00000000000000000000000000000001";

        long start = System.currentTimeMillis();
        File fileDir = new File(cd);
        File[] files = fileDir.listFiles();
        countDownLatch = new CountDownLatch(files.length);
        for (File file : files) {
            CpFile cpFile = new CpFile(file, target);
            Thread thread = new Thread(cpFile);
            thread.start();
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();

        System.out.println("用时:" + (end - start));


    }

    static class CpFile implements Runnable {

        File file;
        String target;

        public CpFile(File file, String target) {
            this.file = file;
            this.target = target;
        }

        @Override
        public void run() {
            cpFile(file, target);
            countDownLatch.countDown();
        }

        public static void cpFile(File cd, String target) {

            if (cd.isDirectory()) {
                File[] files = cd.listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        cpFile(file, target);
                    } else {
                        System.out.println("开始拷贝:" + file.getName());
                        cpFileIO(file, target);
                        System.out.println(file.getName() + "拷贝完成");
                    }
                }
            } else {
                cpFileIO(cd, target);
            }

        }

        public static void cpFileIO(File file, String target) {
            FileInputStream fileInputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                String name = file.getName();
                String newFilePath = target + "\\" + name;
                File targetFile = new File(newFilePath);
                fileOutputStream = new FileOutputStream(targetFile);
                IOUtils.copy(fileInputStream, fileOutputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fileOutputStream) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                    if (null != fileInputStream) {
                        fileInputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("IO关闭错误");
                }
            }
        }
    }


}
