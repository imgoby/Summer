package com.swingfrog.summer.test.ecs.service;

import com.swingfrog.summer.annotation.Service;
import com.swingfrog.summer.lifecycle.Lifecycle;
import com.swingfrog.summer.lifecycle.LifecycleInfo;
import com.swingfrog.summer.test.ecs.component.TestAllBeanComponent;
import com.swingfrog.summer.test.ecs.component.TestMultiBeanComponent;
import com.swingfrog.summer.test.ecs.component.TestSingleBeanComponent;
import com.swingfrog.summer.test.ecs.entity.TestBEntity;
import com.swingfrog.summer.test.ecs.entity.TestEntity;
import com.swingfrog.summer.test.ecs.model.TestAllBean;
import com.swingfrog.summer.test.ecs.model.TestMultiBean;

import java.util.stream.IntStream;

@Service
public class TestService implements Lifecycle {

    @Override
    public LifecycleInfo getInfo() {
        return LifecycleInfo.build("TestService");
    }

    @Override
    public void start() {
        TestEntity testEntity = new TestEntity(100L);
        TestSingleBeanComponent singleBeanComponent = testEntity.getComponent(TestSingleBeanComponent.class);
        TestMultiBeanComponent multiBeanComponent = testEntity.getComponent(TestMultiBeanComponent.class);
        TestAllBeanComponent allBeanComponent = testEntity.getComponent(TestAllBeanComponent.class);

        TestBEntity testBEntity = new TestBEntity(101L);
        //singleBeanComponent = testBEntity.getComponent(TestSingleBeanComponent.class); // 不能获取非TestBEntity的Component

        System.out.println(singleBeanComponent.getOrCreate());

        IntStream.rangeClosed(1, 10).forEach(i -> {
            TestMultiBean testMultiBean = new TestMultiBean();
            testMultiBean.setContent("A " + i);;
            multiBeanComponent.addBean(testMultiBean);

            TestAllBean testAllBean = new TestAllBean();
            testAllBean.setContent("B " + i);
            allBeanComponent.addBean(testAllBean);
        });

        multiBeanComponent.listBean().stream().map(TestMultiBean::getContent).forEach(System.out::println);
        allBeanComponent.listAllBean().stream().map(TestAllBean::getContent).forEach(System.out::println);

        singleBeanComponent.removeBean();
        multiBeanComponent.removeAllBean();
        allBeanComponent.removeAllBean();
    }

    @Override
    public void stop() {

    }

}