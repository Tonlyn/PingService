package com.tek.pingservice.handler;

import com.tek.pingservice.constant.ConfigValue;
import com.tek.pingservice.util.ApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public abstract class AbstractLockHandler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractLockHandler.class);

    //文件锁数量
    private int fileLockNum = 2;



    public synchronized void handle() throws IOException {
        //get file lock
        FileLockVo fileLockVo = getFileLock();
        RandomAccessFile randomAccessFile = fileLockVo.getRandomAccessFile();
        FileChannel channel = fileLockVo.getChannel();
        FileLock lock = fileLockVo.getLock();

        if (lock == null) {
            close(lock, channel, randomAccessFile);
            //get lock fail
            lockFail();
            return;
        }
        //get lock success
        try {
            doBusiness();
        } catch (Exception e) {
            logger.error("执行业务失败", e);
        }
        //close lock
        close(lock, channel, randomAccessFile);
    }


    private synchronized FileLockVo getFileLock() throws IOException {
        ConfigValue configValue = (ConfigValue) ApplicationContextUtil.getBean(ConfigValue.class);
        //文件锁路径
        String fileLockPath = configValue.fileLockDir + File.separator + configValue.fileLockFileName;

        RandomAccessFile randomAccessFile = null;
        FileChannel channel = null;
        FileLock lock = null;

        for (int i = 0; i < fileLockNum; i++) {
            randomAccessFile = new RandomAccessFile(fileLockPath + i, "rw");
            channel = randomAccessFile.getChannel();

            lock = channel.tryLock();
            if (lock == null) {
                close(lock, channel, randomAccessFile);
                continue;
            }
            return new FileLockVo(randomAccessFile, channel, lock);
        }
        return new FileLockVo(randomAccessFile, channel, lock);
    }


    private synchronized void close(FileLock lock, FileChannel channel, RandomAccessFile randomAccessFile) throws IOException {
        if (lock != null) {
            lock.release();
        }
        channel.close();
        randomAccessFile.close();
    }

    abstract void doBusiness();

    abstract void lockFail();


}
