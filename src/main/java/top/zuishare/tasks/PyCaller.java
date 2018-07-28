package top.zuishare.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zuishare.constants.BussinessConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author niange
 * @ClassName: PyCaller
 * @desp:
 * @date: 2018/7/28 上午11:25
 * @since JDK 1.7
 */
@Service
public class PyCaller {

    private static final Logger logger = LoggerFactory.getLogger(PyCaller.class);
    @Autowired
    private BussinessConfig bussinessConfig;

    public void execPy() {
        Process proc = null;
        try{
            proc = Runtime.getRuntime().exec(bussinessConfig.getPythonVersion() + " " + bussinessConfig.getPythonUrl());
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                logger.info("exec python output => {}",line);
            }
            in.close();
            proc.waitFor();
        }catch (IOException e){
            logger.error("exec python error", e);
        }catch (InterruptedException e){
            logger.error("exec python error", e);
        }catch (Exception e) {
            logger.error("exec python error", e);
        }
    }
    
}
