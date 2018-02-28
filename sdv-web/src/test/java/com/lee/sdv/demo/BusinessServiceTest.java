package com.lee.sdv.demo;

//import com.lee.sdv.service.DemoBizService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试spring的业务类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-config-test.xml")
public class BusinessServiceTest {

    //@Autowired
    //private DemoBizService demoBizService;

    @Test
    public void testOrderService() {
        //demoBizService.toString();
        //System.out.println(demoBizService.toString());
    }

}
